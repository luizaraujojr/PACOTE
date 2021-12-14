package unirio.teaching.clustering.search;

import java.io.PrintWriter;
import java.util.Arrays;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.model.ProjectClass;
import unirio.teaching.clustering.search.model.EquationFitness;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;
import unirio.teaching.clustering.search.utils.PseudoRandom;

/**
 * Iterated Local Search for the next release problem
 */
public class IteratedLocalSearch
{
	private static int BOUNDS = 5;
	
	private static int solutionLength = 6; // 2 por métrica
	
//	private List<int[]> history;
	
	private int PERTURBATION_SIZE = 5;
	
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
	 * Fitness calculator
	 */
	private EquationFitness equationFitness;

	/**
	 * Fitness of the best solution
	 */
	private double bestFitness; 

	/**
	 * Fitness of the best solution
	 */
	private Project project; 

	/**
	 * Initializes the ILS search process
     */
	public IteratedLocalSearch(Project project, int maxEvaluations, StringBuilder sbRefDepFile) throws Exception
	{
		this.classCount = project.getClassCount();
		this.mdg = buildGraph(project, this.classCount);
		this.maxEvaluations = maxEvaluations;
		this.evaluationsConsumed = 0;
		this.iterationBestFound = 0;
		this.bestFitness = -1_000_000_000_000.0;
		this.project = project;
		this.equationFitness = new	EquationFitness(mdg, project, sbRefDepFile);
//		this.history = new ArrayList<>(); ;
	}
	
	/**
	 * Builds the project's dependency graph from its representation
	 */
	public static ModuleDependencyGraph buildGraph(Project project, int classCount) throws Exception
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
	public int[] executeExperiment(int cycleNumber, long startTimestamp, PrintWriter writer) throws Exception
	{
		int[] bestSolution = createRandomSolution(solutionLength);
		this.bestFitness = calculateFitness(bestSolution);
//		newSolution(bestSolution);
	
		writer.println(project.getName() + ";"  + cycleNumber + ";"  + evaluationsConsumed + ";"  + bestFitness + ";"  + (System.currentTimeMillis() - startTimestamp) + ";" +  Arrays.toString(bestSolution));
		writer.flush();

		int[] solution = bestSolution;
	
		while (evaluationsConsumed < maxEvaluations && bestFitness < 100)
		{
			int[] localSearchSolution = localSearch(solution);
			double fitness = calculateFitness(localSearchSolution);
			
			if (fitness > bestFitness)
			{
				bestSolution = Arrays.copyOf(solution, solutionLength);
				bestFitness = fitness;
				iterationBestFound = evaluationsConsumed;
			}
			
			solution = applyPerturbation(solution, PERTURBATION_SIZE);

			writer.println(project.getName() + ";"  + cycleNumber + ";"  + evaluationsConsumed + ";"  + bestFitness + ";"  + (System.currentTimeMillis() - startTimestamp) + ";" +  Arrays.toString(bestSolution));
			writer.flush();
		}

		return bestSolution;
	}

	/**
	 * Creates a random solution within the bounds
	 */
	public int[] createRandomSolution(int paramNumber)
	{
		int[] solution = new int[paramNumber];
		
		for (int i = 0; i < paramNumber; i++)
			solution[i] = PseudoRandom.randInt(0, 2 * BOUNDS);

		return solution;
	}
	
	/**
	 * Performs the local search starting from a given solution
	 */
	private int[] localSearch(int[] solution)
	{
		int[] newSolution = Arrays.copyOf(solution, solutionLength);

		while (evaluationsConsumed < maxEvaluations)
		{
			int[] results = visitNeighbors(newSolution);
			
			if (results == null)
				return newSolution;
			
			newSolution = results;
		}
		
		return newSolution;
	}

	/**
	 * Runs a neighborhood visit starting from a given solution
	 */
	private int[] visitNeighbors(int[] solution)
	{
		int[] bestNeighbor = null;
		double bestNeighborFitness = calculateFitness(solution);
		
		for (int i = 0; i < solutionLength; i++)
		{
			int value = solution[i];
			
			if (value < BOUNDS)
			{
				solution[i] = value + 1;
			}
			
			if (value > -BOUNDS)
			{
				solution[i] = value - 1;
			}
			
//			if (newSolution(solution)) {
				double fitness = calculateFitness(solution);
				
				if (fitness > bestNeighborFitness)
				{
					bestNeighbor = Arrays.copyOf(solution, solutionLength);
					bestNeighborFitness = fitness;
				}	
//			}
			
			solution[i] = value;
		}

		return bestNeighbor;
	}
	
	/**
	 * Applies the perturbation operator upon a solution
	 */
	private int[] applyPerturbation(int[] solution, int amount)
	{
		int[] perturbedSolution = Arrays.copyOf(solution, solutionLength);

		for (int i = 0; i < amount; i++)
		{
			int classIndex = PseudoRandom.randInt(0, solutionLength-1);
			perturbedSolution[classIndex] = PseudoRandom.randInt(0, BOUNDS * 2);
		}

		return perturbedSolution;
	}

	/**
	 * Calculates the fitness accounting for the maximum budget
	 */
	private double calculateFitness(int[] solution)
	{
		double fitness = equationFitness.calculateFitness(solution);
//		System.out.print(" " + evaluationsConsumed);
		evaluationsConsumed++;
		return fitness;
	}
	
//	private boolean newSolution(int[] solution)
//	{
//		boolean isNew = true;
//		int[] s1 = Arrays.copyOf(solution, solutionLength);
//		
//		for (int[] h : history)
//		{
//			int equal = 0;
//
//			for (int i = 0; i <= s1.length - 1; i++)
//			{
//				if (h[i] == s1[i])
//				{
//					equal++;
//				} 
//				else
//					break;
//			}
//			
//			if (equal == s1.length)
//			{
//				isNew = false;
//				break;
//			}
//		}
//		
//		if (isNew)
//		{
//			history.add(s1);
//		}
//
//		return isNew;
//	}
}