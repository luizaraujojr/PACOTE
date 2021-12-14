package unirio.teaching.clustering.tests;

import static org.junit.Assert.*;

import java.io.File;

import javax.management.modelmbean.XMLParseException;

import org.junit.Test;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.model.ClusterMetrics;
import unirio.teaching.clustering.search.model.EquationFitness;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;

public class Tests{
	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//Test";
	private ModuleDependencyGraph mdg;
	private Project project;
	private CDAReader reader = new CDAReader();

//	@Test
//	public void testFitnessCalc() throws Exception {
//		
//		CDAReader reader = new CDAReader();
//		Project project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
//		StringBuilder sbRefDepFile = unirio.teaching.clustering.MainProgram.loadDepRefFile(BASE_DIRECTORY + "//jodamoney-1.0.1.odem.comb");
//		
//		ModuleDependencyGraph mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
//		EquationFitness equationFitness = new EquationFitness(mdg, project, sbRefDepFile);
//		
//		int[] solution = { 0, 1, 2, 3, 4, 5 };
//		
//		assertEquals (29.17, equationFitness.calculateFitness(solution),0);
//		
////		fail("Not yet implemented");
//	}
	
	
	@Test
	public void testCalculateICED_beforeMerge() throws Exception {
		
		
		testInstanceLoad();
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
//		int[] assertValue = {15, 4, 3, 13, 2, 2, 4, 7, 4, 3, 7, 6, 3, 2, 10, 5, 5, 8, 8, 8, 7, 12, 10, 6, 8, 8};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (1, clusterMetrics.calculateInternalClassesWithExternalDependency(index));		
	}
	
	
	@Test
	public void testCalculateICID_beforeMerge() throws Exception {	
		testInstanceLoad();
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
//		int[] assertValue = {15, 4, 3, 13, 2, 2, 4, 7, 4, 3, 7, 6, 3, 2, 10, 5, 5, 8, 8, 8, 7, 12, 10, 6, 8, 8};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (0, clusterMetrics.calculateInternalClassesWithInternalDependency(index));		
	}

	@Test
	public void testCalculateAfterMerge() throws Exception {	
		testInstanceLoad();
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
		clusterMetrics.makeMergeClusters(1, 2);
		
		int[] assertValue1 = {1, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));		
		
//		int[] assertValue2 = {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (0, clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}
	

	
	@Test
	public void testCalculateAfterMerge1() throws Exception {	
		testInstanceLoad();
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
		clusterMetrics.makeMergeClusters(2, 3);
		
		int[] assertValue1 = {1, 1, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));		
		
		int[] assertValue2 = {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}

	
	@Test
	public void testCalculateAfterMerge2() throws Exception {	
		testInstanceLoad();
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		
		int[] assertValue1 = {1, 1, 0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));		
		
		int[] assertValue2 = {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}
	

	@Test
	public void testCalculateAfterMerge3() throws Exception {	
		testInstanceLoad();
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
		clusterMetrics.makeMergeClusters(2, 3);
		clusterMetrics.makeMergeClusters(3, 4);
		clusterMetrics.makeMergeClusters(4, 25);
		
		int[] assertValue1 = {1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (assertValue1[index], clusterMetrics.calculateInternalClassesWithExternalDependency(index));		
		
		int[] assertValue2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3};
		
		for (int index = 0; index < mdg.getSize(); index++)
			assertEquals (assertValue2[index], clusterMetrics.calculateInternalClassesWithInternalDependency(index));
	}
	

//	@Test
//	public void testMerge_ClassWithDepOnCluster_noDep() throws Exception {
//		
//		testInstanceLoad();
//		int[] solution = new int[mdg.getSize()];		
//
//		for (int index = 0; index < mdg.getSize(); index++)
//			solution[index] = index;
//
//		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
//		
//		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
//		
//		clusterMetrics.makeMergeClusters(1, 2);
//		
//		assertEquals (0, clusterMetrics.calculateInternalClassesWithInternalDependency(2));
//		
//	}
//
//	@Test
//	public void testMerge_ClassWithDepOnCluster_TwoDep() throws Exception {
//		
//		CDAReader reader = new CDAReader();
//		Project project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
//		ModuleDependencyGraph mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
//		int[] solution = new int[mdg.getSize()];		
//
//		for (int index = 0; index < mdg.getSize(); index++)
//			solution[index] = index;
//
//		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
//		
//		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
//				
//		clusterMetrics.makeMergeClusters(0, 3);
//		
////		assertEquals (2, clusterMetrics.classWithDepOnCluster.get(3).size(),0);
//		
//	}
//	
//	@Test
//	public void testMerge_ClassWithDepOnCluster_ThreeDep() throws Exception {
//		
//		CDAReader reader = new CDAReader();
//		Project project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
//		ModuleDependencyGraph mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
//		int[] solution = new int[mdg.getSize()];		
//
//		for (int index = 0; index < mdg.getSize(); index++)
//			solution[index] = index;
//
//		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
//		
//		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
//		
//		clusterMetrics.makeMergeClusters(1, 2);
//				
//		clusterMetrics.makeMergeClusters(2, 7);
//		
////		assertEquals (3, clusterMetrics.classWithDepOnCluster.get(7).size(),0);
//		
////		fail("Not yet implemented");
//	}

	private void testInstanceLoad() throws Exception {
		CDAReader reader = new CDAReader();
		project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
		mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
}

	
	
}
