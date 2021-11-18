package unirio.teaching.clustering.tests;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.Test;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.model.ClusterMetrics;
import unirio.teaching.clustering.search.model.EquationFitness;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;

public class Tests{
	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//Test";

	@Test
	public void testFitnessCalc() throws Exception {
		
		CDAReader reader = new CDAReader();
		Project project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
		StringBuilder sbRefDepFile = unirio.teaching.clustering.MainProgram.loadDepRefFile(BASE_DIRECTORY + "//jodamoney-1.0.1.odem.comb");
		
		ModuleDependencyGraph mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
		EquationFitness equationFitness = new EquationFitness(mdg, project, sbRefDepFile);
		
		int[] solution = { 0, 1, 2, 3, 4, 5 };
		
		assertEquals (29.17, equationFitness.calculateFitness(solution),0);
		
//		fail("Not yet implemented");
	}

	@Test
	public void testMerge_ClassWithDepOnCluster_noDep() throws Exception {
		
		CDAReader reader = new CDAReader();
		Project project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
		ModuleDependencyGraph mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
		clusterMetrics.makeMergeClusters(1, 2);
		
		assertEquals (0, clusterMetrics.classWithDepOnCluster.get(2).size(),0);
		
	}

	@Test
	public void testMerge_ClassWithDepOnCluster_TwoDep() throws Exception {
		
		CDAReader reader = new CDAReader();
		Project project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
		ModuleDependencyGraph mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
				
		clusterMetrics.makeMergeClusters(0, 3);
		
		assertEquals (2, clusterMetrics.classWithDepOnCluster.get(3).size(),0);
		
	}
	
	@Test
	public void testMerge_ClassWithDepOnCluster_ThreeDep() throws Exception {
		
		CDAReader reader = new CDAReader();
		Project project = reader.load(BASE_DIRECTORY + "//jodamoney-1.0.1.odem");
		ModuleDependencyGraph mdg = unirio.teaching.clustering.search.IteratedLocalSearch.buildGraph(project, project.getClassCount());
		int[] solution = new int[mdg.getSize()];		

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		int[] equationParams = { 0, 1, 2, 3, 4, 5 };
		
		ClusterMetrics clusterMetrics = new ClusterMetrics (mdg, solution, equationParams);
		
		clusterMetrics.makeMergeClusters(1, 2);
				
		clusterMetrics.makeMergeClusters(2, 7);
		
		assertEquals (3, clusterMetrics.classWithDepOnCluster.get(7).size(),0);
		
//		fail("Not yet implemented");
	}


}
