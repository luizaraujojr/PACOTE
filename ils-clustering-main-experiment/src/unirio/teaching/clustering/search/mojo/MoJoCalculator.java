package unirio.teaching.clustering.search.mojo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import unirio.teaching.clustering.model.Project;

public class MoJoCalculator
{
	/* use for store the name of each items */
	private List<String> clusterNamesInSource = new ArrayList<String>();

	/* The mappings of clusters to tags in both A and B */
	private Map<String, Integer> mapClusterTagSource = new Hashtable<String, Integer>();

	/* This vector contains a vector for each cluster in A */
	private ArrayList<ArrayList<String>> partitionSource = new ArrayList<ArrayList<String>>();

	/* number of clusters in A */
	private int clusterCountInSource = 0;

	/* number of objects in A */
	private long objectCountSource;

	/* number of objects in each cluster in partition B */
	private List<Integer> cardinalitiesInTarget = new ArrayList<Integer>();

	/* The mapping between objects and clusters in B */
	private Map<String, String> mapObjectClusterInTarget = new Hashtable<String, String>();

	/* ... */
	private Map<String, Integer> mapClusterTagTarget = new Hashtable<String, Integer>();

	/* number of clusters in B */
	private int clusterCountInTarget = 0;

	/* ... */
	private Cluster A[] = null;

	/* record the capacity of each group, if the group is empty ,the count is zero, otherwise >= 1 */
	private int groupscount[] = null;

	/* after join operations, each group will have only one cluster left, we use grouptags[i] to indicate the remain cluster in group i */
	/* every none empty group have a tag point to a cluster in A */
	private Cluster grouptags[] = null;

	public MoJoCalculator(StringBuilder sf)
	{
		prepareSource(sf);
	}

	public double mojofmnew(Project project, int[] solution)
	{
		prepareTarget(project, solution);
		compareSourceToTarget();

		commonPreparation();
		maxBipartiteMatching();
		
		double totalCost = calculateCost();
		long maxDis = maxDistanceTo(cardinalitiesInTarget, objectCountSource);
		return Math.rint((1 - (double) totalCost / (double) maxDis) * 10000) / 100;
	}

	private void prepareSource(StringBuilder source)
	{
		objectCountSource = 0;

		String[] lines = source.toString().split(System.lineSeparator());

		for (String line : lines)
		{
			StringTokenizer st = new StringTokenizer(line);

			if (st.countTokens() == 1)
			{
				String message = "Incorrect RSF format in the following line:\n" + line;
				throw new RuntimeException(message);
			}

			int index = -1;
			String clusterName = st.nextToken();
			String objectName = st.nextToken();

			objectCountSource++;
			Integer objectIndex = mapClusterTagSource.get(clusterName);
			
			if (objectIndex == null)
			{
				index = mapClusterTagSource.size();
				clusterNamesInSource.add(clusterName);
				mapClusterTagSource.put(clusterName, index);
				partitionSource.add(new ArrayList<String>());
			} 
			else
			{
				index = objectIndex.intValue();
			}
			
			partitionSource.get(index).add(objectName);
		}
	}

	private void prepareTarget(Project project, int[] solution)
	{
		int len = solution.length;
		
		mapClusterTagTarget.clear();
		cardinalitiesInTarget.clear();
		mapObjectClusterInTarget.clear();
		
		for (int i = 0; i < len; i++)
		{
			String clusterName = "PKG" + solution[i];
			String objectName = project.getClassIndex(i).getName();

			/* Search for the cluster name in mapClusterTagB */
			Integer objectIndex = mapClusterTagTarget.get(clusterName);

			if (objectIndex == null)
			{
				// This cluster is not in mapClusterTagB yet
				int index = mapClusterTagTarget.size();
				
				// Since it is a new cluster, it currently contains 1 object
				cardinalitiesInTarget.add(1);
				mapClusterTagTarget.put(clusterName, index);
			} 
			else
			{
				int index = objectIndex.intValue();
				// Increase the cluster's cardinality in vector cardinalitiesInB
				int newCardinality = 1 + cardinalitiesInTarget.get(index);
				cardinalitiesInTarget.set(index, newCardinality);
			}
			
			mapObjectClusterInTarget.put(objectName, clusterName);
		}
	}

	private void compareSourceToTarget()
	{
		long extraInA = 0;

		for (ArrayList<String> partition : partitionSource)
		{
			for (String objectName : partition)
			{
				if (!mapObjectClusterInTarget.keySet().contains(objectName))
					extraInA++;
			}
		}
		
		if (extraInA > 0)
			System.out.println("Warning: " + extraInA + " objects in source were not found in target. They will be ignored.");
		
		long extraInB = mapObjectClusterInTarget.keySet().size() - objectCountSource;
		
		if (extraInB > 0)
			System.out.println("Warning: " + extraInB + " objects in target were not found in source. They will be ignored.");
	}

	private void commonPreparation()
	{
		clusterCountInSource = mapClusterTagSource.size();
		clusterCountInTarget = mapClusterTagTarget.size();

		A = new Cluster[clusterCountInSource];
		groupscount = new int[clusterCountInTarget];
		grouptags = new Cluster[clusterCountInTarget];

		/* init group tags */
		for (int j = 0; j < clusterCountInTarget; j++)
		{
			grouptags[j] = null;
		}

		/* create each cluster in A */
		for (int i = 0; i < clusterCountInSource; i++)
		{
			A[i] = new Cluster(clusterCountInTarget);
		}
		
		/* Tag assignment */
		for (int i = 0; i < clusterCountInSource; i++)
		{
			int tag = -1;
			String clusterName = "";
			
			for (int j = 0; j < partitionSource.get(i).size(); j++)
			{
				String objName = partitionSource.get(i).get(j);
				clusterName = mapObjectClusterInTarget.get(objName);
				tag = mapClusterTagTarget.get(clusterName);
				A[i].addObject(tag, objName);
			}
		}
	}

	private void maxBipartiteMatching()
	{
		/* Create the graph and add all the edges */
		BipartiteGraph bgraph = new BipartiteGraph(clusterCountInSource + clusterCountInTarget, clusterCountInSource, clusterCountInTarget);

		for (int i = 0; i < clusterCountInSource; i++)
		{
			ArrayList<Integer> cluster = A[i].getGroupList();
			
			for (int j = 0; j < cluster.size(); j++)
			{
				bgraph.addEdge(i, clusterCountInSource + cluster.get(j));
			}
		}

		/* Use maximum bipartite matching to calculate the groups */
		bgraph.matching();

		/*
		 * Assign group after matching, for each Ai in matching, assign the corresponding group, for other cluster in A,
		 * just leave them alone
		 */
		for (int i = clusterCountInSource; i < clusterCountInSource + clusterCountInTarget; i++)
		{
			if (bgraph.isVertexMatched(i))
			{
				int index = bgraph.getFirstTargetAdjacentList(i);
				A[index].setGroup(i - clusterCountInSource);
			}
		}
	}

	private long calculateCost()
	{
		int moves = 0;
		int non_empty_groups = 0;

		/* find none empty groups and find total number of moves */
		for (int i = 0; i < clusterCountInSource; i++)
		{
			/* caculate the count of nonempty groups */
			/* when we found that a group was set to empty but in fact is not empty, we increase the number of noneempty group by 1 */
			if (groupscount[A[i].getGroup()] == 0)
			{
				non_empty_groups += 1;
			}
			
			/* assign group tags - if this group has no tag, then we assign A[i] to its tag */
			if (grouptags[A[i].getGroup()] == null)
			{
				grouptags[A[i].getGroup()] = A[i];
			}
			
			/* assign the group count */
			groupscount[A[i].getGroup()] += 1;

			/* calculate the number of move opts for each cluster */
			moves += A[i].getTotalTags() - A[i].getMaximumTag();
		}
		
		long totalCost = moves + clusterCountInSource - non_empty_groups;
		return totalCost;
	}

	private long maxDistanceTo(List<Integer> number_of_B, long obj_number)
	{
		int group_number = 0;
		int[] B = new int[number_of_B.size()];

		for (int i = 0; i < B.length; i++)
		{
			B[i] = number_of_B.get(i);
		}
		
		/* sort the array in ascending order */
		java.util.Arrays.sort(B);

		/* calculate the minimum maximum possible groups for partition B */
		/*after sort the B_i in ascending order B_i: 1, 2, 3, 4, 5, 6, 7, 8, 10, 10, 10, 15 we can calculate g in this way g: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 11 */
		for (int i = 0; i < B.length; i++)
		{
			if (group_number < B[i])
				group_number++;
		}
		
		/* return n - l + l - g = n - g */
		return obj_number - group_number;
	}
}