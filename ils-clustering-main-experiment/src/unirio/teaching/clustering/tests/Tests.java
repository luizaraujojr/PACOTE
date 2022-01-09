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

		int[] assertValue = { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));
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


		int[] assertValue1 = { 1, 0, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

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

		int[] assertValue1 = { 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

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

		int[] assertValue1 = { 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

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

		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 };

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
	
		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 0, 0, 2, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 };

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

		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 3, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 };

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

		
		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	
	public void testCalculateAfterMerge7() throws Exception
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

		
		int[] assertValue1 = { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));

		int[] assertValue2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6 };

		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals(assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}
	
	@Test
	public void testCalculateDeltaMerge1() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);

		double result = clusterMetrics.calculateMergeClustersDelta (2, 3);
		
		double[] c = new double[18];
		
		for (int i = 0; i < equationParams.length; i++)
		{
			c[i] = (equationParams[i]-5.0)/2.0; 
		}
		
		int _numberInternalDependencies1 = 0;
		int _numberExternalDependencies1 = 1;
		int _numberInternalClassWithDepOnCluster1 = 0;
		int _numberInternalClassWithDepOutCluster1 = 1;
		int _numberExternalClassWithDepCluster1 = 2;
		int _numberAbstractClasses1 = 0;
		int _numberConcreteClasses1 = 1;
		int _numberSubClasses1 = 1;
		int _numberSuperClasses1 = 1;
		
		int _numberInternalDependencies2 = 0;
		int _numberExternalDependencies2 = 5;
		int _numberInternalClassWithDepOnCluster2 = 0;
		int _numberInternalClassWithDepOutCluster2 = 1;
		int _numberExternalClassWithDepCluster2 = 10;
		int _numberAbstractClasses2 = 0;
		int _numberConcreteClasses2 = 1;
		int _numberSubClasses2 = 1;
		int _numberSuperClasses2 = 1;
		
		int _numberInternalDependenciesj = 1;
		int _numberExternalDependenciesj = 5;
		int _numberInternalClassWithDepOnClusterj = 1;
		int _numberInternalClassWithDepOutClusterj = 1;
		int _numberExternalClassWithDepClusterj = 11;
		int _numberAbstractClassesj = 0;
		int _numberConcreteClassesj = 2;
		int _numberSubClassesj = 2;
		int _numberSuperClassesj = 2;
	
		
		double expected = clusterMetrics.calculateClusterFitness(_numberInternalDependenciesj, _numberExternalDependenciesj, _numberInternalClassWithDepOnClusterj, _numberInternalClassWithDepOutClusterj, _numberExternalClassWithDepClusterj, _numberAbstractClassesj, _numberConcreteClassesj, _numberSubClassesj, _numberSuperClassesj);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies1, _numberExternalDependencies1, _numberInternalClassWithDepOnCluster1, _numberInternalClassWithDepOutCluster1, _numberExternalClassWithDepCluster1, _numberAbstractClasses1, _numberConcreteClasses1, _numberSubClasses1, _numberSuperClasses1);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies2, _numberExternalDependencies2, _numberInternalClassWithDepOnCluster2, _numberInternalClassWithDepOutCluster2, _numberExternalClassWithDepCluster2, _numberAbstractClasses2, _numberConcreteClasses2, _numberSubClasses2, _numberSuperClasses2);
			
		Assert.assertEquals(expected, result, 0.00000001); 
	}

	
	@Test
	public void testCalculateDeltaMerge2() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		
		double result = clusterMetrics.calculateMergeClustersDelta (3, 4);
		
		double[] c = new double[18];
		
		for (int i = 0; i < equationParams.length; i++)
		{
			c[i] = (equationParams[i]-5.0)/2.0; 
		}
		
		int _numberInternalDependencies1 = 1;
		int _numberExternalDependencies1 = 5;
		int _numberInternalClassWithDepOnCluster1 = 1;
		int _numberInternalClassWithDepOutCluster1 = 1;
		int _numberExternalClassWithDepCluster1 = 11;
		int _numberAbstractClasses1 = 0;
		int _numberConcreteClasses1 = 2;
		int _numberSubClasses1 = 2;
		int _numberSuperClasses1 = 2;
		
		int _numberInternalDependencies2 = 0;
		int _numberExternalDependencies2 = 1;
		int _numberInternalClassWithDepOnCluster2 = 0;
		int _numberInternalClassWithDepOutCluster2 = 1;
		int _numberExternalClassWithDepCluster2 = 2;
		int _numberAbstractClasses2 = 1;
		int _numberConcreteClasses2 = 0;
		int _numberSubClasses2 = 1;
		int _numberSuperClasses2 = 1;
		
		int _numberInternalDependenciesj = 3;
		int _numberExternalDependenciesj = 4;
		int _numberInternalClassWithDepOnClusterj = 3;
		int _numberInternalClassWithDepOutClusterj = 1;
		int _numberExternalClassWithDepClusterj = 11;
		int _numberAbstractClassesj = 1;
		int _numberConcreteClassesj = 2;
		int _numberSubClassesj = 3;
		int _numberSuperClassesj = 3;
	
		
		double expected = clusterMetrics.calculateClusterFitness(_numberInternalDependenciesj, _numberExternalDependenciesj, _numberInternalClassWithDepOnClusterj, _numberInternalClassWithDepOutClusterj, _numberExternalClassWithDepClusterj, _numberAbstractClassesj, _numberConcreteClassesj, _numberSubClassesj, _numberSuperClassesj);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies1, _numberExternalDependencies1, _numberInternalClassWithDepOnCluster1, _numberInternalClassWithDepOutCluster1, _numberExternalClassWithDepCluster1, _numberAbstractClasses1, _numberConcreteClasses1, _numberSubClasses1, _numberSuperClasses1);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies2, _numberExternalDependencies2, _numberInternalClassWithDepOnCluster2, _numberInternalClassWithDepOutCluster2, _numberExternalClassWithDepCluster2, _numberAbstractClasses2, _numberConcreteClasses2, _numberSubClasses2, _numberSuperClasses2);
			
		Assert.assertEquals(expected, result, 0.00000001); 
	}

	
	@Test
	public void testCalculateDeltaMerge3() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		
		double result = clusterMetrics.calculateMergeClustersDelta (4, 25);
		
		double[] c = new double[18];
		
		for (int i = 0; i < equationParams.length; i++)
		{
			c[i] = (equationParams[i]-5.0)/2.0; 
		}
		
		int _numberInternalDependencies1 = 3;
		int _numberExternalDependencies1 = 4;
		int _numberInternalClassWithDepOnCluster1 = 3;
		int _numberInternalClassWithDepOutCluster1 = 1;
		int _numberExternalClassWithDepCluster1 = 11;
		int _numberAbstractClasses1 = 1;
		int _numberConcreteClasses1 = 2;
		int _numberSubClasses1 = 3;
		int _numberSuperClasses1 = 3;
		
		int _numberInternalDependencies2 = 0;
		int _numberExternalDependencies2 = 1;
		int _numberInternalClassWithDepOnCluster2 = 0;
		int _numberInternalClassWithDepOutCluster2 = 1;
		int _numberExternalClassWithDepCluster2 = 6;
		int _numberAbstractClasses2 = 1;
		int _numberConcreteClasses2 = 0;
		int _numberSubClasses2 = 1;
		int _numberSuperClasses2 = 1;
		
		int _numberInternalDependenciesj = 3;
		int _numberExternalDependenciesj = 5;
		int _numberInternalClassWithDepOnClusterj = 3;
		int _numberInternalClassWithDepOutClusterj = 2;
		int _numberExternalClassWithDepClusterj = 17;
		int _numberAbstractClassesj = 2;
		int _numberConcreteClassesj = 2;
		int _numberSubClassesj = 4;
		int _numberSuperClassesj = 4;
	
		
		double expected = clusterMetrics.calculateClusterFitness(_numberInternalDependenciesj, _numberExternalDependenciesj, _numberInternalClassWithDepOnClusterj, _numberInternalClassWithDepOutClusterj, _numberExternalClassWithDepClusterj, _numberAbstractClassesj, _numberConcreteClassesj, _numberSubClassesj, _numberSuperClassesj);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies1, _numberExternalDependencies1, _numberInternalClassWithDepOnCluster1, _numberInternalClassWithDepOutCluster1, _numberExternalClassWithDepCluster1, _numberAbstractClasses1, _numberConcreteClasses1, _numberSubClasses1, _numberSuperClasses1);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies2, _numberExternalDependencies2, _numberInternalClassWithDepOnCluster2, _numberInternalClassWithDepOutCluster2, _numberExternalClassWithDepCluster2, _numberAbstractClasses2, _numberConcreteClasses2, _numberSubClasses2, _numberSuperClasses2);
			
		Assert.assertEquals(expected, result, 0.00000001); 
		}
	
	
	@Test
	public void testCalculateDeltaMerge4() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		
		double result = clusterMetrics.calculateMergeClustersDelta (7, 8);
	
		double[] c = new double[18];
		
		for (int i = 0; i < equationParams.length; i++)
		{
			c[i] = (equationParams[i]-5.0)/2.0; 
		}
		
		int _numberInternalDependencies1 = 0;
		int _numberExternalDependencies1 = 6;
		int _numberInternalClassWithDepOnCluster1 = 0;
		int _numberInternalClassWithDepOutCluster1 = 1;
		int _numberExternalClassWithDepCluster1 = 4;
		int _numberAbstractClasses1 = 0;
		int _numberConcreteClasses1 = 1;
		int _numberSubClasses1 = 1;
		int _numberSuperClasses1 = 1;
		
		int _numberInternalDependencies2 = 0;
		int _numberExternalDependencies2 = 3;
		int _numberInternalClassWithDepOnCluster2 = 0;
		int _numberInternalClassWithDepOutCluster2 = 1;
		int _numberExternalClassWithDepCluster2 = 3;
		int _numberAbstractClasses2 = 0;
		int _numberConcreteClasses2 = 1;
		int _numberSubClasses2 = 1;
		int _numberSuperClasses2 = 1;
		
		int _numberInternalDependenciesj = 2;
		int _numberExternalDependenciesj = 7;
		int _numberInternalClassWithDepOnClusterj = 2;
		int _numberInternalClassWithDepOutClusterj = 2;
		int _numberExternalClassWithDepClusterj = 5;
		int _numberAbstractClassesj = 0;
		int _numberConcreteClassesj = 2;
		int _numberSubClassesj = 2;
		int _numberSuperClassesj = 2;
	
		
		double expected = clusterMetrics.calculateClusterFitness(_numberInternalDependenciesj, _numberExternalDependenciesj, _numberInternalClassWithDepOnClusterj, _numberInternalClassWithDepOutClusterj, _numberExternalClassWithDepClusterj, _numberAbstractClassesj, _numberConcreteClassesj, _numberSubClassesj, _numberSuperClassesj);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies1, _numberExternalDependencies1, _numberInternalClassWithDepOnCluster1, _numberInternalClassWithDepOutCluster1, _numberExternalClassWithDepCluster1, _numberAbstractClasses1, _numberConcreteClasses1, _numberSubClasses1, _numberSuperClasses1);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies2, _numberExternalDependencies2, _numberInternalClassWithDepOnCluster2, _numberInternalClassWithDepOutCluster2, _numberExternalClassWithDepCluster2, _numberAbstractClasses2, _numberConcreteClasses2, _numberSubClasses2, _numberSuperClasses2);
			
		Assert.assertEquals(expected, result, 0.00000001); 
}

	
	@Test
	public void testCalculateDeltaMerge5() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		clusterMetrics.makeMergeClusters(7, 8);
		
		double result = clusterMetrics.calculateMergeClustersDelta (8, 11);
		
		double[] c = new double[18];
		
		for (int i = 0; i < equationParams.length; i++)
		{
			c[i] = (equationParams[i]-5.0)/2.0; 
		}
		
		int _numberInternalDependencies1 = 2;
		int _numberExternalDependencies1 = 7;
		int _numberInternalClassWithDepOnCluster1 = 2;
		int _numberInternalClassWithDepOutCluster1 = 2;
		int _numberExternalClassWithDepCluster1 = 5;
		int _numberAbstractClasses1 = 0;
		int _numberConcreteClasses1 = 2;
		int _numberSubClasses1 = 2;
		int _numberSuperClasses1 = 2;
		
		int _numberInternalDependencies2 = 0;
		int _numberExternalDependencies2 = 5;
		int _numberInternalClassWithDepOnCluster2 = 0;
		int _numberInternalClassWithDepOutCluster2 = 1;
		int _numberExternalClassWithDepCluster2 = 1;
		int _numberAbstractClasses2 = 0;
		int _numberConcreteClasses2 = 1;
		int _numberSubClasses2 = 1;
		int _numberSuperClasses2 = 1;
		
		int _numberInternalDependenciesj = 2;
		int _numberExternalDependenciesj = 12;
		int _numberInternalClassWithDepOnClusterj = 2;
		int _numberInternalClassWithDepOutClusterj = 3;
		int _numberExternalClassWithDepClusterj = 6;
		int _numberAbstractClassesj = 0;
		int _numberConcreteClassesj = 3;
		int _numberSubClassesj = 3;
		int _numberSuperClassesj = 3;
	
		
		double expected = clusterMetrics.calculateClusterFitness(_numberInternalDependenciesj, _numberExternalDependenciesj, _numberInternalClassWithDepOnClusterj, _numberInternalClassWithDepOutClusterj, _numberExternalClassWithDepClusterj, _numberAbstractClassesj, _numberConcreteClassesj, _numberSubClassesj, _numberSuperClassesj);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies1, _numberExternalDependencies1, _numberInternalClassWithDepOnCluster1, _numberInternalClassWithDepOutCluster1, _numberExternalClassWithDepCluster1, _numberAbstractClasses1, _numberConcreteClasses1, _numberSubClasses1, _numberSuperClasses1);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies2, _numberExternalDependencies2, _numberInternalClassWithDepOnCluster2, _numberInternalClassWithDepOutCluster2, _numberExternalClassWithDepCluster2, _numberAbstractClasses2, _numberConcreteClasses2, _numberSubClasses2, _numberSuperClasses2);
			
		Assert.assertEquals(expected, result, 0.00000001); 
	}
	
	
	
	@Test
	public void testCalculateDeltaMerge6() throws Exception
	{
		testInstanceLoad();

		int[] solution = createFullyDistributedSolution();
		int[] equationParams = { 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8};
		ClusterMetrics clusterMetrics = new ClusterMetrics(mdg, solution, equationParams, project);
 
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		clusterMetrics.makeMergeClusters(7, 8);
		clusterMetrics.makeMergeClusters(8, 11);
		
		double result = clusterMetrics.calculateMergeClustersDelta (11, 25);
		
		double[] c = new double[18];
		
		for (int i = 0; i < equationParams.length; i++)
		{
			c[i] = (equationParams[i]-5.0)/2.0; 
		}
		
		int _numberInternalDependencies1 = 2;
		int _numberExternalDependencies1 = 12;
		int _numberInternalClassWithDepOnCluster1 = 2;
		int _numberInternalClassWithDepOutCluster1 = 3;
		int _numberExternalClassWithDepCluster1 = 6;
		int _numberAbstractClasses1 = 0;
		int _numberConcreteClasses1 = 3;
		int _numberSubClasses1 = 3;
		int _numberSuperClasses1 = 3;
		
		int _numberInternalDependencies2 = 3;
		int _numberExternalDependencies2 = 5;
		int _numberInternalClassWithDepOnCluster2 = 3;
		int _numberInternalClassWithDepOutCluster2 = 2;
		int _numberExternalClassWithDepCluster2 = 17;
		int _numberAbstractClasses2 = 2;
		int _numberConcreteClasses2 = 2;
		int _numberSubClasses2 = 4;
		int _numberSuperClasses2 = 4;	
		
		int _numberInternalDependenciesj = 6;
		int _numberExternalDependenciesj = 14;
		int _numberInternalClassWithDepOnClusterj = 6;
		int _numberInternalClassWithDepOutClusterj = 5;
		int _numberExternalClassWithDepClusterj = 23;
		int _numberAbstractClassesj = 2;
		int _numberConcreteClassesj = 5;
		int _numberSubClassesj = 7;
		int _numberSuperClassesj = 7;
	
		
		double expected = clusterMetrics.calculateClusterFitness(_numberInternalDependenciesj, _numberExternalDependenciesj, _numberInternalClassWithDepOnClusterj, _numberInternalClassWithDepOutClusterj, _numberExternalClassWithDepClusterj, _numberAbstractClassesj, _numberConcreteClassesj, _numberSubClassesj, _numberSuperClassesj);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies1, _numberExternalDependencies1, _numberInternalClassWithDepOnCluster1, _numberInternalClassWithDepOutCluster1, _numberExternalClassWithDepCluster1, _numberAbstractClasses1, _numberConcreteClasses1, _numberSubClasses1, _numberSuperClasses1);
		
		expected -= clusterMetrics.calculateClusterFitness(_numberInternalDependencies2, _numberExternalDependencies2, _numberInternalClassWithDepOnCluster2, _numberInternalClassWithDepOutCluster2, _numberExternalClassWithDepCluster2, _numberAbstractClasses2, _numberConcreteClasses2, _numberSubClasses2, _numberSuperClasses2);
			
		Assert.assertEquals(expected, result, 0.00000001); 	}
	
	
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