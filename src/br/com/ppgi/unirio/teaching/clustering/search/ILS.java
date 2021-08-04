package br.com.ppgi.unirio.teaching.clustering.search;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.ppgi.unirio.luiz.softwareanalysis.model.Project;
import br.com.ppgi.unirio.luiz.softwareanalysis.model.ProjectClass;
import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import br.com.ppgi.unirio.teaching.clustering.search.model.FitnessMetric;
import br.com.ppgi.unirio.teaching.clustering.search.model.ModuleDependencyGraph;
import br.com.ppgi.unirio.teaching.clustering.search.model.MojoCalculator;

/**
 * Iterated Local Search for the next release problem
 */

public class ILS {
	
	/**
	 * Constructive method
	 */
	private ConstrutiveAbstract construtiveRandom;
	private ConstrutiveAbstract construtiveMQ;
	
	private StringBuilder sbRefDepFile; 
	
	private List<int[]> history;  
	
	/**
	 * Fitness of the best solution
	 */
	private double bestFitness;
	
	/**
	 * Dependency graph for the project
	 */
	private ModuleDependencyGraph mdg;
	
	private int PERTURBATION_SIZE = 2;
	
	/**
	 * Set of requirements to be optimized
	 */
	private Project project;

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

	
	private int  paramNumber;
	
	private double multiplyFactor;
	
	/**
	 * Initializes the ILS search process
	 */
	
	private String projectName;
	private FitnessMetric metrics;
	
	public ILS(ConstrutiveAbstract construtiveRandom, ConstrutiveAbstract construtiveMQ, Project project, String projectName, StringBuilder sbRefDepFile,  int maxEvaluations, int paramNumber, double  multiplyFactor) throws Exception
	{
		this.construtiveRandom = construtiveRandom;
		this.construtiveMQ = construtiveMQ;
		this.project = project;
		this.maxEvaluations = maxEvaluations;
		this.bestFitness = -1_000_000_000_000.0;
		this.paramNumber = paramNumber;
		this.multiplyFactor = multiplyFactor;
		this.projectName = projectName; 
		this.mdg = buildGraph(project, project.getClassCount());
		this.history = new ArrayList<int[]>();
		this.sbRefDepFile = sbRefDepFile;  
	}
	
	
	/**
	 * Returns the number of evaluations consumed during the search
	 */
	public int getEvaluationsConsumed()
	{
		return evaluationsConsumed;
	}
	
	/**
	 * Returns the best fitness found
	 */
	public double getBestFitness()
	{
		return bestFitness;
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
	 * Main loop of the algorithm
	 */
	public int[] execute() throws Exception
	{
		MojoCalculator mojoCalculator = new MojoCalculator(project, projectName, mdg, construtiveMQ, sbRefDepFile);
		
//		int[] bestSolution = construtiveRandom.createSolution(paramNumber, multiplyFactor);
		int[] bestSolution = {2, 5, 5, 5, 2, 3, 7, 7};
		history.add(bestSolution);
//		newSolution(bestSolution);
		this.bestFitness = evaluate(bestSolution, mojoCalculator);
		
		int[] solution = null;
		double fitness = 0; 
//		int[] solution = localSearch(bestSolution, mojoCalculator, bestFitness);
//		double fitness = evaluate(solution, mojoCalculator); 
//		
//		if (fitness > bestFitness)
//		{
//			bestSolution = solution;
//			bestFitness = fitness;
//		}
		
		while (getEvaluationsConsumed() < getMaximumEvaluations() && bestFitness < 100)
		{
			int[] startSolution = applyPerturbation(bestSolution, mojoCalculator, PERTURBATION_SIZE);
			solution = localSearch(startSolution, mojoCalculator, bestFitness);
			fitness = evaluate(solution, mojoCalculator); 
			
			if (fitness > bestFitness)
			{
				bestSolution = solution;
				bestFitness = fitness;
			}
		}

		return bestSolution;
	}
	
	
	 /* Generation of the reference clusterization
	 */
	public int[] executeConstructor() throws Exception
	{		
		int[] functionParams= {9,5,9,7};//MQ
		
		int[] bestSolution = construtiveMQ.createSolution(mdg, functionParams, project);
	
		return bestSolution;
	}

	/**
	 * Applies the perturbation operator upon a solution
	 */
	private int[] applyPerturbation(int[] solution, MojoCalculator calculator, int amount)
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

	/**
	 * Performs the local search starting from a given solution
	 * @throws IOException 
	 */
	private int[] localSearch(int[] solution, MojoCalculator mojoCalculator, double bestFitness) throws IOException
	{
		NeighborhoodVisitorResult result;

		int[] bestLocalSolution = Arrays.copyOf(solution, solution.length);
		double bestLocalFitness = bestFitness;
//		double bestLocalFitness = evaluate(bestLocalSolution, mojoCalculator);

		do
		{
			result = visitNeighbors(bestLocalSolution, mojoCalculator, bestFitness);

			if (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR && result.getNeighborFitness() > bestLocalFitness)
			{
				bestLocalFitness = result.getNeighborFitness();
				this.iterationBestFound = evaluationsConsumed;
			}

		} while (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR);

		return bestLocalSolution;
	}

	/**
	 * Runs a neighborhood visit starting from a given solution
	 * @throws IOException 
	 */
	private NeighborhoodVisitorResult visitNeighbors(int[] solution, MojoCalculator mojoCalculator, double bestFitness) throws IOException
	{
		double startingFitness = evaluate(solution, mojoCalculator); 

		if (evaluationsConsumed > maxEvaluations)
			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

		for (int i = 0; i < solution.length; i++)
		{
			int originalValue = solution[i];
			int cont = 0;
			do {
			solution[i] = PseudoRandom.randInt(1, 9);
			cont++;
			}
			while (!newSolution(solution) && cont <=200);

			double neighborFitness = evaluate(solution, mojoCalculator);

			if (evaluationsConsumed > maxEvaluations)
				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

			if (neighborFitness > startingFitness)
				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);

			solution[i] = originalValue;
		}

		return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
		
	}

//	/**
//	 * Evaluates the fitness of a solution, saving detail information
//	 */
	private double evaluate(int[] solution, MojoCalculator mojoCalculator) throws IOException
	{
		if (++evaluationsConsumed % 100 == 0)
		{
//			System.out.println(evaluationsConsumed + "; " + bestFitness +  " " + Arrays.toString(solution));
		}
//		System.out.println("evaluate" + Arrays.toString(solution));
		
		double result = mojoCalculator.calculateFitness(solution);
//		System.out.println(result);
		return result;
	}

	/**
	 * Set of potential results from visiting neighbors
	 */
	private enum NeighborhoodVisitorStatus
	{
		FOUND_BETTER_NEIGHBOR, NO_BETTER_NEIGHBOR, SEARCH_EXHAUSTED
	}
	
	/**
	 * Class that represents the results of the local search phase
	 */
	private class NeighborhoodVisitorResult
	{
		/**
		 * Status in the end of the local search
		 */
		private NeighborhoodVisitorStatus status;
	
		/**
		 * Fitness of the best neighbor, in case one has been found
		 */
		private double neighborFitness;
	
		/**
		 * Initializes a successful local search status
		 */
		public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status, double fitness)
		{
			this.status = status;
			this.neighborFitness = fitness;
		}
	
		/**
		 * Initializes an unsuccessful local search
		 */
		public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status)
		{
			this.status = status;
			this.neighborFitness = 0.0;
		}
	
		/**
		 * Returns the status of the local search
		 */
		public NeighborhoodVisitorStatus getStatus()
		{
			return status;
		}
	
		/**
		 * Return the fitness of the best neighbor found, if any
		 */
		public double getNeighborFitness()
		{
			return neighborFitness;
		}
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
				
				if (classIndex == -1)
					throw new Exception ("Class not registered in project: " + targetName);
				
				mdg.addModuleDependency(i, classIndex, 1);
			}
		}
		
		return mdg;
	}
	
	/**
	 * Builds the project's dependency graph from its representation
	 */
	private boolean newSolution (int[] solution) {
		boolean isNew = true;
		int[] s1 = Arrays.copyOf(solution, solution.length);   
//		
//		if (history.indexOf(solution1)<0) {
//			isNew=true;
			
//		}
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