package unirio.teaching.clustering.search.mojo;

import java.util.ArrayList;

class Cluster
{
	/* no. of cluster in B */
	private int numberClustersTarget = 0;

	/* max V(ij), the maximum tags */
	private int maxtag = 0;

	/* |Ai|, total objects in Ai */
	private int totalTags = 0;

	/* the group No that Ai belongs to */
	private int group = 0;

	/* tags */
	private int tags[];

	/* object list */
	private ArrayList<ArrayList<String>> objectList;

	/* group list */
	private ArrayList<Integer> groupList;

	/* creates the new cluster */
	public Cluster(int m)
	{
		this.numberClustersTarget = m;

		tags = new int[m];
		objectList = new ArrayList<ArrayList<String>>(m);
		groupList = new ArrayList<Integer>();
		
		for (int j = 0; j < m; j++)
		{
			tags[j] = 0;
			objectList.add(j, new ArrayList<String>());
		}
	}

	public int getGroup()
	{
		return group;
	}

	public void setGroup(int group)
	{
		this.group = group;
	}

	public int getMaximumTag()
	{
		return maxtag;
	}

	public int getTotalTags()
	{
		return totalTags;
	}

	/* adds an object to the cluster */
	public int addObject(int t, String object)
	{
		if (t >= 0 && t < numberClustersTarget)
		{
			tags[t] += 1;
			totalTags += 1;
			objectList.get(t).add(object);

			/* if tags is max & unique,then change group to it & clear grouplist */
			if (tags[t] > maxtag)
			{
				maxtag = tags[t];
				group = t;
				groupList.clear();
				groupList.add(t);
			}
			/* if tags is max but not nuique,then add it to the grouplist */
			else if (tags[t] == maxtag)
			{
				groupList.add(t);
			}
		}
		return group;
	}

	/* move objects to another cluster */
	public void move(int grouptag, Cluster sub)
	{
		ArrayList<String> entry = objectList.get(grouptag);
		ArrayList<String> subEntry = sub.objectList.get(grouptag);
				
		for (int i = 0; i < entry.size(); i++)
		{
			subEntry.add(entry.get(i));
		}
		
		entry.clear();
	}

	/* merge with another cluster */
	public void merge(Cluster sub)
	{
		maxtag += sub.maxtag;
		totalTags += sub.totalTags;

		for (int j = 0; j < numberClustersTarget; j++)
		{
			ArrayList<String> entry = objectList.get(j);
			ArrayList<String> subEntry = sub.objectList.get(j);
			
			for (int i = 0; i < subEntry.size(); i++)
			{
				entry.add(subEntry.get(i));
			}
		}
	}

	/* returns the group list */
	public ArrayList<Integer> getGroupList()
	{
		return groupList;
	}
}