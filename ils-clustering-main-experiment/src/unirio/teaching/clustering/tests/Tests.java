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
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(1, clusterMetrics.calculateInternalClassesWithExternalDependency(index));
	}

	@Test
	public void testCalculateICID_beforeMerge() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(0, clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	@Test
	public void testCalculateAfterMerge() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

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
//		unindo dois pacotes (2 e 3) que possuem classes com dependencia cruzada 2-3, 3-2
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

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
//		unindo 3 pacotes (2,3,4) que possuem referencia 2-3, 3-2, 4-2)
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

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
//		unindo 3 pacotes (2,3,4,25) que possuem referencia 2-3, 3-2, 4-2 e o 25 não tem referência com os anteriores)
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

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
	public void testCalculateAfterMerge4() throws Exception
	{
//		unindo os pacotes (2,3,4,25) e os pacotes (7,8) que possuem referencia 2-3, 3-2, 4-2 e o 25 não tem referência com os anteriores)
//		unindo os pacotes (7,8) que possuem referencia 7-8, 8-7)
		
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		clusterMetrics.makeMergeClusters(7, 8);

		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 1, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}


	@Test
	public void testCalculateAfterMerge5() throws Exception
	{
//		unindo os pacotes (2,3,4,25) e os pacotes (7,8) que possuem referencia 2-3, 3-2, 4-2 e o 25 não tem referência com os anteriores)
//		unindo os pacotes (7,8,11) que possuem referencia 7-8, 8-7 e o 11 não tem referência com os anteriores)

		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		clusterMetrics.makeMergeClusters(7, 8);
		clusterMetrics.makeMergeClusters(8, 11);

		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}


	@Test
	public void testCalculateAfterMerge6() throws Exception
	{		
//		unindo os pacotes (2,3,4,25,7,8,11) que possuem referencia 2-3, 3-2, 4-2, 7-8, 8-7 o 25-11, 11-25
		
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 0, 1, 2, 3, 4, 5, 6, 7};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		clusterMetrics.makeMergeClusters(7, 8);
		clusterMetrics.makeMergeClusters(8, 11);
		clusterMetrics.makeMergeClusters(11, 25);

		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	
	@Test
	public void testCalculateDeltaMerge1() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 5, 5, 5, 8, 5};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

		double result = clusterMetrics.calculateMergeClustersDelta (2, 3);
		
//		double v = (((ICID*1)+(ICED*1.5))/((ICID*1.5)+(ICED*2)));
//		
//		v1 = fromcluster
//		v2 = tocluster
//		vj = join dos dois pacotes
		

		double expected = (((2*1)/(2*1.5))-0-0);
		Assert.assertEquals(expected, result, 0.00000001); 
	}

	
	@Test
	public void testCalculateDeltaMerge2() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 8, 5, 5, 8, 9};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		
		double result = clusterMetrics.calculateMergeClustersDelta (3, 4);
		
//		double v = (((ICID*1)+(ICED*1.5))/((ICID*1.5)+(ICED*2)));
//		
//		v1 = fromcluster
//		v2 = tocluster
//		vj = join dos dois pacotes
		
	
		double vj = (((3*1)+(3*1.5))/((3*1.5)+(3*2)));
		double v1 = (((2*1)+(2*1.5))/((2*1.5)+(2*2)));
		double v2 = (((0*1)+(1*1.5))/((0*1.5)+(1*2)));
		
		
		double expected = vj-v1-v2;

		
		Assert.assertEquals(expected, result, 0.00000001); 
	}

	
	@Test
	public void testCalculateDeltaMerge3() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 8, 5, 5, 8, 9};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		
		double result = clusterMetrics.calculateMergeClustersDelta (4, 25);
		
//		double v = (((ICID*1)+(ICED*1.5))/((ICID*1.5)+(ICED*2)));
//		
//		v1 = fromcluster
//		v2 = tocluster
//		vj = join dos dois pacotes
		
				
		double vj = (((3*1)+(4*1.5))/((3*1.5)+(4*2)));
		double v1 = (((3*1)+(3*1.5))/((3*1.5)+(3*2)));
		double v2 = (((0*1)+(1*1.5))/((0*1.5)+(1*2)));
		
		
		double expected = vj-v1-v2;
		
		Assert.assertEquals(expected, result, 0.00000001); 
	}
	
	
	@Test
	public void testCalculateDeltaMerge4() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 8, 5, 5, 8, 9};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		
		double result = clusterMetrics.calculateMergeClustersDelta (7, 8);
		
//		double v = (((ICID*1)+(ICED*1.5))/((ICID*1.5)+(ICED*2)));
//		
//		v1 = fromcluster
//		v2 = tocluster
//		vj = join dos dois pacotes 
		
		double vj = (((2*1)+(2*1.5))/((2*1.5)+(2*2)));
		double v1 = (((0*1)+(1*1.5))/((0*1.5)+(1*2)));
		double v2 = (((0*1)+(1*1.5))/((0*1.5)+(1*2)));
		
		
		double expected = vj-v1-v2;
		
		
		Assert.assertEquals(expected, result, 0.00000001); 
	}

	
	@Test
	public void testCalculateDeltaMerge5() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 8, 5, 5, 8, 9};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		clusterMetrics.makeMergeClusters(7, 8);
		
		double result = clusterMetrics.calculateMergeClustersDelta (8, 11);
		
//		double v = (((ICID*1)+(ICED*1.5))/((ICID*1.5)+(ICED*2)));
//		
//		v1 = fromcluster
//		v2 = tocluster
//		vj = join dos dois pacotes		
		
		double vj = (((2*1)+(3*1.5))/((2*1.5)+(3*2)));
		double v1 = (((2*1)+(2*1.5))/((2*1.5)+(2*2)));
		double v2 = (((0*1)+(1*1.5))/((0*1.5)+(1*2)));
		
		
		double expected = vj-v1-v2;
		Assert.assertEquals(expected, result, 0.00000001); 
	}
	
	
	
	@Test
	public void testCalculateDeltaMerge6() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 5, 5, 7, 8, 5, 5, 8, 9};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		clusterMetrics.makeMergeClusters(7, 8);
		clusterMetrics.makeMergeClusters(8, 11);
		
		double result = clusterMetrics.calculateMergeClustersDelta (11, 25);
		
//		double v = (((ICID*1)+(ICED*1.5))/((ICID*1.5)+(ICED*2)));
//		
//		v1 = fromcluster
//		v2 = tocluster
//		vj = join dos dois pacotes	
		
		double vj = (((7*1)+(7*1.5))/((7*1.5)+(7*2)));
		double v1 = (((2*1)+(3*1.5))/((2*1.5)+(3*2)));
		double v2 = (((3*1)+(4*1.5))/((3*1.5)+(4*2)));
		
		
		double expected = vj-v1-v2;
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