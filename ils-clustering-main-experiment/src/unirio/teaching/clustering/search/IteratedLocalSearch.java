package unirio.teaching.clustering.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.model.ProjectClass;
import unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import unirio.teaching.clustering.search.model.ClusterMetrics;
import unirio.teaching.clustering.search.model.EquationFitness;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;
import unirio.teaching.clustering.search.utils.PseudoRandom;

/**
 * Iterated Local Search for the next release problem
 */
public class IteratedLocalSearch
{
	
	private List<int[]> history;
	
	private int PERTURBATION_SIZE = 5;
	
	/**
	 * Constructive method
	 */
	private ConstrutiveAbstract constructor;

	/**
	 * Number of classes in the project
	 */
	private int classCount;
	
	/**
	 * Dependency graph for the project
	 */
	private ModuleDependencyGraph mdg;
	
	/**
	 * Number of fitness evaluations available in the budget
	 */
	private int maxEvaluations;

	/**
	 * Number of fitness evaluations executed
	 */
	private int evaluationsConsumed;

	/**
	 * Number of iterations to best solution
	 */
	private int iterationBestFound;
	
	/**
	 * MQ calculator
	 */
	private ClusterMetrics metrics;
	private EquationFitness equationFitness;

	/**
	 * Fitness of the best solution
	 */
	private double bestFitness; 

	/**
	 * Fitness of the best solution
	 */
	private Project project; 

	private StringBuilder sbRefDepFile;
	
	
	/**
	 * Initializes the ILS search process
	 * @param sbRefDepFile2 
   */
	public IteratedLocalSearch(ConstrutiveAbstract constructor, Project project, StringBuilder sbRefDepFile2, int maxEvaluations, StringBuilder sbRefDepFile) throws Exception
	{
		this.constructor = constructor;
		this.classCount = project.getClassCount();
		this.mdg = buildGraph(project, this.classCount);
		this.maxEvaluations = maxEvaluations;
		this.evaluationsConsumed = 0;
		this.iterationBestFound = 0;
		this.metrics = null;
		this.bestFitness = -1_000_000_000_000.0;
		this.project = project;
		this.project = project;
		this.sbRefDepFile = sbRefDepFile;
		this.history = new ArrayList<int[]>();
	}
	
	/**
	 * Builds the project's dependency graph from its representation
	 */
	private ModuleDependencyGraph buildGraph(Project project, int classCount) throws Exception
	{
		ModuleDependencyGraph mdg = new ModuleDependencyGraph(classCount);
		
		for (int i = 0; i < classCount; i++)
		{
			ProjectClass _class = project.getClassIndex(i);

			for (int j = 0; j < _class.getDependencyCount(); j++)
			{
				String targetName = _class.getDependencyIndex(j).getElementName();
				int classIndex = project.getClassIndex(targetName);
				
//				if (classIndex == -1)
//					throw new Exception ("Class not registered in project: " + targetName);
				
				if (classIndex != -1)
				mdg.addModuleDependency(i, classIndex, 1);
			}
		}
		
		return mdg;
	}

	/**
	 * Returns the number of evaluations consumed during the search
	 */
	public int getEvaluationsConsumed()
	{
		return evaluationsConsumed;
	}

	/**
	 * Returns the maximum number of evaluations to be consumed
	 */
	public int getMaximumEvaluations()
	{
		return maxEvaluations;
	}
	
	/**
	 * Returns the iteration on which the best solution was found
	 */
	public int getIterationBestFound()
	{
		return iterationBestFound;
	}

	/**
	 * Returns the best fitness found
	 */
	public double getBestFitness()
	{
		return bestFitness;
	}
	
	/**
	 * Main loop of the algorithm
	 */
	public int[] executeExperiment(int runTime, long startTimestamp ) throws Exception
	{
		int paramNumber = 4;  
		int minValue = 1;
		int maxValue = 9;
		
//		int[] bestSolution = constructor.createSolution(mdg);
		int[] bestSolution = constructor.createSolution(paramNumber, minValue, maxValue);
		history.add(bestSolution);
		
//		this.metrics = new 	ClusterMetrics(mdg, bestSolution);
		this.equationFitness = new 	EquationFitness(mdg, project, sbRefDepFile);
		
		this.bestFitness = equationFitness.calculateFitness(bestSolution, evaluationsConsumed, runTime, startTimestamp);
		++evaluationsConsumed;

		double fitness =  bestFitness;
		
		while (getEvaluationsConsumed() < getMaximumEvaluations() && bestFitness < 100)
		{
			int[] solution = applyPerturbation(bestSolution, PERTURBATION_SIZE);
					
			fitness = equationFitness.calculateFitness(solution, evaluationsConsumed, runTime, startTimestamp );
			++evaluationsConsumed;
			
			if (fitness > bestFitness)
			{
				bestSolution = solution;
				bestFitness = fitness;
			}
		}

		return bestSolution;
	}
	
	/**
	 * Applies the perturbation operator upon a solution
	 */
//	private void applyPerturbation(ClusterMetrics calculator, int amount)
//	{
//		for (int i = 0; i < amount; i++)
//		{
//			int source = PseudoRandom.randInt(0, classCount-1);
//			int target = PseudoRandom.randInt(0, classCount-1);
//			calculator.makeMoviment(source, target);
//		}
//	}
	
	
	
	/**
	 * Applies the perturbation operator upon a solution
	 */
	private int[] applyPerturbation(int[] solution, int amount)
	{
		int[] perturbedSolution = Arrays.copyOf(solution, solution.length);

		for (int i = 0; i < amount; i++)
		{
			do {
			int classIndex = PseudoRandom.randInt(0, solution.length-1);
			perturbedSolution[classIndex] = PseudoRandom.randInt(1, 9);
			}
			while (!newSolution(perturbedSolution));
		}

		return perturbedSolution;
	}
	
//	private void applyPerturbation(int[] solution, int amount)
//	{
//		
//		int[] perturbedSolution = Arrays.copyOf(solution, solution.length);
//
//		for (int i = 0; i < amount; i++)
//		{
//			do {
//			int classIndex = PseudoRandom.randInt(0, solution.length-1);
//			perturbedSolution[classIndex] = PseudoRandom.randInt(1, 9);
//			}
////			while (!newSolution(perturbedSolution));
//		}
//
//		return perturbedSolution;
		
		
//		for (int i = 0; i < amount; i++)
//		{
//			int source = PseudoRandom.randInt(0, classCount-1);
//			int target = PseudoRandom.randInt(0, classCount-1);
////			calculator.makeMoviment(source, target);
//		}
//	}

	/**
	 * Performs the local search starting from a given solution
	 */
	private void localSearch(ClusterMetrics calculator)
	{
		while (visitNeighbors(calculator))
			;
	}

	/**
	 * Runs a neighborhood visit starting from a given solution
	 */
	private boolean visitNeighbors(ClusterMetrics calculator)
	{
		if (evaluationsConsumed > maxEvaluations)
			return false;

		int source = -1;
		int target = -1;
		double bestGain = Double.NEGATIVE_INFINITY;
		
		for (int i = 0; i < classCount; i++)
		{
			int newPackage = PseudoRandom.randInt(0, classCount-1);
//			int newPackage = PseudoRandom.randInt(0, classCount-1);
			double gain = calculator.calculateMovimentDelta(i, newPackage);
			++evaluationsConsumed;

			if (gain > bestGain)
			{
				source = i;
				target = newPackage;
				bestGain = gain;
			}
		}

		if (bestGain > 0)
		{
			calculator.makeMoviment(source, target);
			return true;
		}
		
		return false;
	}
	
	
	private boolean newSolution (int[] solution) {
		boolean isNew = true;
		int[] s1 = Arrays.copyOf(solution, solution.length);   
		for (int[] h : history) {
			int equal=0;
			for (int i = 0; i <= s1.length-1; i++) {
				if (h[i]==s1[i]) {
					equal++; 
				}
				else break;
			}
			if (equal==s1.length) {
				isNew =false;
				break;
			}
		}
		if (isNew) {
			history.add(s1);
		}
		return isNew;		
	}

}