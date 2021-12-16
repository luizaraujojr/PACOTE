package unirio.teaching.clustering.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.IteratedLocalSearch;
import unirio.teaching.clustering.search.model.ClusterMetrics;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;

public class Tests
{
	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//Test";

	private ModuleDependencyGraph mdg;
	
	private Project project;

	@Test
	public void testCalculateICED_beforeMerge() throws Exception
	{
		testInstanceLoad();
		
		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(1, clusterMetrics.calculateInternalClassesWithExternalDependency(index));
	}

	@Test
	public void testCalculateICID_beforeMerge() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(0, clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	@Test
	public void testCalculateAfterMerge() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);

		clusterMetrics.makeMergeClusters(1, 2);

		int[] assertValue1 = { 1, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(0, clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	@Test
	public void testCalculateAfterMerge1() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);

		clusterMetrics.makeMergeClusters(2, 3);
		
		int[] assertValue1 = { 1, 1, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	@Test
	public void testCalculateAfterMerge2() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);

		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);

		int[] assertValue1 = { 1, 1, 0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	@Test
	public void testCalculateAfterMerge3() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);

		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);

		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	@Test
	public void testCalculateDeltaMerge1() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 5, 5, 5, 8, 5};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);

		double result = clusterMetrics.calculateMergeClustersDelta (2, 3);
		double expected = (((2*1)/(2*1.5))-0-0);
		Assert.assertEquals(expected, result, 0.00000001); 
	}

	
	@Test
	public void testCalculateDeltaMerge2() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 5, 5, 5, 8, 5};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams);
 
		clusterMetrics.makeMergeClusters(2, 3);
		
		double result = clusterMetrics.calculateMergeClustersDelta (3, 4);
		double expected = (((3*1)/(3*1.5))-((2*1)/(2*1.5))- 0);
		Assert.assertEquals(expected, result, 0.00000001); 
	}
	
	
	private void testInstanceLoad() throws Exception
	{
		CDAReader reader = new CDAReader();
		this.project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
		this.mdg = IteratedLocalSearch.buildGraph(project, project.getClassCount());
	}

	private int[] createFullyDistributedSolution()
	{
		int classCount = mdg.getSize();
		int[] solution = new int[classCount];

		for (int i = 0; i < classCount; i++)
			solution[i] = i;
		
		return solution;
	}
}