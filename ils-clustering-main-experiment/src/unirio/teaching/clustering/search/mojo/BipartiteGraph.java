package unirio.teaching.clustering.search.mojo;

import java.util.*;

/* Vertex in a bipartite graph */
class Vertex
{
	boolean matched = false;

	boolean isLeft = false;

	int outdegree = 0;

	int indegree = 0;
}

class BipartiteGraph
{
	/* represents a edge list in a directed graph. For example, adjacentList[1] has 2 means there is a edge from point 1 to point 2 */
	private ArrayList<ArrayList<Integer>> adjacentList;

	/* vertex list */
	private Vertex vertex[];

	/* this list is used to store the augmenting Path we got from matching */
	private ArrayList<Integer> augmentPath;

	/* total number of points in left side */
	private int leftpoints;

	/* create the graph,points means the toal number of points */
	public BipartiteGraph(int points, int leftpoints, int rightpoints)
	{
		this.leftpoints = leftpoints;

		adjacentList = new ArrayList<ArrayList<Integer>>(points);
		vertex = new Vertex[points];
		augmentPath = new ArrayList<Integer>();

		for (int i = 0; i < points; i++)
		{
			vertex[i] = new Vertex();

			if (i < leftpoints)
				vertex[i].isLeft = true;
			
			adjacentList.add(i, new ArrayList<Integer>());
		}
	}

	/* add an edge to the graph */
	public void addEdge(int startPoint, int endPoint)
	{
		/* insert the edge to the adjacentList of startPoint */
		adjacentList.get(startPoint).add(endPoint);
		
		/* increase the outdegree of startPoint, indegree of endPoint */
		vertex[startPoint].outdegree += 1;
		vertex[endPoint].indegree += 1;
		
		/*
		 * if the edge is from right to left side, mark both start and end as mached point
		 */
		if (isRight(startPoint) && isLeft(endPoint))
		{
			vertex[startPoint].matched = true;
			vertex[endPoint].matched = true;

		}
	}

	/* do the maximum bipartiture matching */
	public String matching()
	{
		String str = "";

		while (findAugmentPath())
		{
			str += XOR();
		}

		return str;
	}

	/* Reverse all the edges in the augmenting path */
	private String XOR()
	{
		/* the first point of augmenting path */
		int start = augmentPath.get(0);
		String str = "";
		int end;

		for (int i = 1; i < augmentPath.size(); i++)
		{
			end = augmentPath.get(i);
			reverseEdge(start, end);
			start = end;
		}
		
		return str;
	}

	/* removes an edge from the graph */
	private void removeEdge(int startPoint, int endPoint)
	{
		/* find the index of edge in the adjacentList of startPoint */
		int index = adjacentList.get(startPoint).indexOf(endPoint);
		
		/* remove the edge from adjacentList of startPoint */
		if (index > -1)
			adjacentList.get(startPoint).remove(index);
		
		/* decrease the outdegree of startPoint and indegree of endPoint */
		vertex[startPoint].outdegree -= 1;
		vertex[endPoint].indegree -= 1;

		/*
		 * if the startPoint is on the right, and its outdegree become zero, mark the startPoint as unmached
		 */
		if (isRight(startPoint) && vertex[startPoint].outdegree == 0)
			vertex[startPoint].matched = false;

		/*
		 * if the endPoint is on the left, and its indegree become zero, mark the endPoint as unmached
		 */
		if (isLeft(endPoint) && vertex[endPoint].indegree == 0)
			vertex[endPoint].matched = false;

	}

	/* Change the direction of an edge, e.g. change i -> j to j -> i */
	private void reverseEdge(int startPoint, int endPoint)
	{
		removeEdge(startPoint, endPoint);
		addEdge(endPoint, startPoint);
	}

	/* finds the augmented path */
	private boolean findAugmentPath()
	{
		augmentPath.clear(); /* init the path */
		
		/*
		 * use all the unmatched left points as start, see if we can find a augmenting path
		 */
		for (int i = 0; i < leftpoints; i++)
		{
			if (vertex[i].matched == false)
			{
				if (findPath(i))
					return true;
				else
					augmentPath.clear(); /* re init the path */
			}
		}
		
		return false;
	}

	/* recursive find a path using DFS */
	private boolean findPath(int start)
	{
		int nextPt, index;
		
		/* if the current vertex has no out edge, return false */
		if (vertex[start].outdegree == 0)
			return false;
		
		/* insert the current point to the path */
		augmentPath.add(start);

		/*
		 * use the pts that the current point is linked to as next point, recursively call findPath function
		 */
		for (int i = 0; i < adjacentList.get(start).size(); i++)
		{
			nextPt = adjacentList.get(start).get(i);
			
			/* if the next point was already in the path, discard it */
			if (augmentPath.indexOf(nextPt) > -1)
				continue;
			
			/* find a terminal, add it to the path and return true */
			if (vertex[nextPt].matched == false)
			{
				augmentPath.add(nextPt);
				return true;
			}
			
			/* otherwise recursive call using depth first search */
			else if (findPath(nextPt))
				return true;

		}

		/* if failed, delete the current pt from path and return false */
		index = augmentPath.indexOf(start);
		augmentPath.remove(index);
		return false;

	}

	/* indicate whether the current point is in right side */
	private boolean isLeft(int pt)
	{
		if (pt < leftpoints)
			return true;
		else
			return false;
	}

	/* indicate whether the current point is in right side */
	private boolean isRight(int pt)
	{
		if (pt > leftpoints - 1)
			return true;
		else
			return false;
	}

	/* Checks whether a vertex is matched */
	public boolean isVertexMatched(int index)
	{
		return vertex[index].matched;
	}

	/* Returns the first target of edges from a given node */
	public int getFirstTargetAdjacentList(int index)
	{
		return adjacentList.get(index).get(0);
	}
}