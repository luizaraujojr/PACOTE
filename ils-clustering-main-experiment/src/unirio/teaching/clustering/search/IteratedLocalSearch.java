package unirio.teaching.clustering.search;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.model.ProjectClass;
import unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import unirio.teaching.clustering.search.constructive.ConstrutiveAglomerativeMQ;
import unirio.teaching.clustering.search.model.ClusterMetrics;
import unirio.teaching.clustering.search.model.EquationFitness;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;
import unirio.teaching.clustering.search.mojo.MoJoCalculator;
import unirio.teaching.clustering.search.utils.PseudoRandom;

/**
 * Iterated Local Search for the next release problem
 */
public class IteratedLocalSearch
{
	/**
	 * Number of metrics that will be considered for the optimization
	 */
	private int metricsSize = 1;
	/**
	 * Number of options for the parameters of the fitness function
	 */
	private static int BOUNDS = 10;
	
	/**
	 * Number of options for the parameters of the fitness function
	 */
	private int solutionLength = metricsSize * 2;
	
	/**
	 * Number of perturbations that will be used during the ILS neighbour search
	 */
	private int perturbationSize = solutionLength /2;
	
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
	public EquationFitness equationFitness;

	/**
	 * Fitness of the best solution
	 */
	private double bestFitness; 

	/**
	 * eval of the best solution
	 */
	private int bestEval; 
	
	/**
	 * Fitness of the best solution
	 */
	private Project project; 
	
	private boolean[] usedMetrics;

	private PrintWriter writer;

	private String[] logData;

	private String logFile;
	
	private int cycle;
	
	private long startTimestamp;
	
	private int[] bestSolution;

	/**
	 * Initializes the ILS search process
     */
	public IteratedLocalSearch(Project project, int maxEvaluations, StringBuilder sbRefDepFile, int metricsSize, boolean[] usedMetrics, PrintWriter writer, int cycle, long startTimestamp, String[] logData, String logFile) throws Exception
	{
		this.classCount = project.getClassCount();
		this.mdg = buildGraph(project, this.classCount);
		this.maxEvaluations = maxEvaluations;
		this.evaluationsConsumed = 0;
		this.iterationBestFound = 0;
		this.bestFitness = -1_000_000_000_000.0;
		this.project = project;
		this.equationFitness = new	EquationFitness(mdg, project, sbRefDepFile);
		this.metricsSize = metricsSize;
		this.solutionLength = this.metricsSize * 2;
		this.perturbationSize = this.solutionLength / 2;
		this.usedMetrics = usedMetrics;
		this.writer = writer;
		this.cycle = cycle;
		this.startTimestamp = startTimestamp;
	
		this.logData = logData;
		this.logFile = logFile;
		this.bestSolution =  new int[solutionLength];
		
	}
	
	
	public IteratedLocalSearch(Project project, int maxEvaluations, int metricsSize, boolean[] usedMetrics, StringBuilder sbRefDepFile) throws Exception
	{
		this.classCount = project.getClassCount();
		this.mdg = buildGraph(project, this.classCount);
		this.maxEvaluations = maxEvaluations;
		this.evaluationsConsumed = 0;
		this.iterationBestFound = 0;
		this.bestFitness = -1_000_000_000_000.0;
		this.project = project;
		this.metricsSize = metricsSize;
		this.solutionLength = this.metricsSize * 2;
		this.perturbationSize = this.solutionLength / 2;
		this.usedMetrics = usedMetrics;
		this.equationFitness = new	EquationFitness(mdg, project, sbRefDepFile);
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

	public int[] getClusterBestSolution()
	{
		return equationFitness.getClusterSolution();
	}
	
	/**
	 * Main loop of the algorithm
	 */
	
	public int[] executeExperiment(int cycleNumber, long startTimestamp) throws Exception
	{
//		int[] bs = {1, 2, 3, 8, 4, 3, 3, 3};
//		this.bestFitness = calculateFitness(bs);	
		
		
		if (logData[0] == null) {
			bestSolution = createRandomSolution(solutionLength);
			this.bestFitness = calculateFitness(bestSolution);	
		}
		else {
			String[] logSolution = logData[2].replace("[", "").replace("]", "").replace(" ", "").split(",");
			for(int i=0; i<solutionLength; i++) {
				bestSolution[i] = Integer.parseInt(logSolution[i]);
			}
			
//			bestSolution = int.Parse(logData.split(";")[1]).toArray();		
			this.bestFitness = Double.parseDouble (logData[3]);
			this.evaluationsConsumed = Integer.parseInt(logData[4]);
		}
		
		int[] solution = bestSolution;
		
		while (evaluationsConsumed < maxEvaluations && bestFitness < 100)
		{
			int[] localSearchSolution = localSearch(solution);
			
			double fitness = calculateFitness(localSearchSolution);
						
			if (fitness > bestFitness || bestFitness ==100)
			{	
				bestSolution = Arrays.copyOf(localSearchSolution, solutionLength);
				bestFitness = fitness;
				iterationBestFound = evaluationsConsumed;
			}
			
			solution = applyPerturbation(localSearchSolution, perturbationSize);
		}
		return bestSolution;
	}

	
	/**
	 * Main loop of the algorithm
	 */
	public int[] executeMQReferenceGeneration(int cycleNumber) throws Exception
	{
		
		int[] equationParams = {7, 5, 5, 5, 5, 5, 5, 5, 5, 7, 6, 5, 5, 5, 5, 5, 5, 5};
		ConstrutiveAbstract constructor = new ConstrutiveAglomerativeMQ();
		int[] bestSolution = constructor.createSolution(mdg, equationParams, project, usedMetrics);
		
		this.bestFitness = calculateFitnessRef(bestSolution);
		
//		ClusterMetrics cm = new ClusterMetrics(mdg, solution, equationParams, project);

		return bestSolution;
	}

	
	/**
	 * Creates a random solution within the bounds
	 */
	public int[] createRandomSolution(int paramNumber)
	{
		int[] solution = new int[paramNumber];
		
		for (int i = 0; i < paramNumber; i++)
			solution[i] = PseudoRandom.randInt(0, BOUNDS);

		return solution;
	}
	
	/**
	 * Performs the local search starting from a given solution
	 * @throws FileNotFoundException 
	 */
	private int[] localSearch(int[] solution) throws FileNotFoundException
	{
		int[] newSolution = Arrays.copyOf(solution, solutionLength);

		while (evaluationsConsumed < maxEvaluations && bestFitness < 100)
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
	 * @throws FileNotFoundException 
	 */
	private int[] visitNeighbors(int[] solution) throws FileNotFoundException
	{
		int[] bestNeighbor = null;
		double bestNeighborFitness = calculateFitness(solution);
		
		for (int i = 0; i < solutionLength; i++)
		{
			int value = solution[i];

			if (value < BOUNDS)
			{
				solution[i] = value + 1;
				double fitness = calculateFitness(solution);

				if (fitness > bestNeighborFitness)
				{
					bestNeighbor = Arrays.copyOf(solution, solutionLength);
					bestNeighborFitness = fitness;
				}
			}

			if (value > 0)
			{
				solution[i] = value - 1;

				double fitness = calculateFitness(solution);

				if (fitness > bestNeighborFitness)
				{
					bestNeighbor = Arrays.copyOf(solution, solutionLength);
					bestNeighborFitness = fitness;
				}
			}
			if (bestNeighborFitness==100) {
				bestFitness = bestNeighborFitness;
				break;
			}

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
			perturbedSolution[classIndex] = PseudoRandom.randInt(0, BOUNDS);
		}

		return perturbedSolution;
	}

	/**
	 * Calculates the fitness accounting for the maximum budget
	 * @throws FileNotFoundException 
	 */
	private double calculateFitness(int[] solution) throws FileNotFoundException
	{
		double fitness = 0;
		fitness = equationFitness.calculateFitness(solution, usedMetrics);
		
//		if (Integer.valueOf(evaluationsConsumed/10000)*10000==evaluationsConsumed) {
//			
//			String solutionText = ""; 
//			for(int h = 0; h < solution.length; h++)
//	    	{
//				solutionText = solutionText  + String.valueOf((solution[h]-5.0)/2.0) + ",";
//	    	}
//			
//			long finishTimestamp = System.currentTimeMillis();
//			long seconds = (finishTimestamp - startTimestamp);
//				
//			writer.println(cycle + ";" + project.getName() + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + getBestFitness() + ";" + getEvaluationsConsumed() + ";"+ getIterationBestFound() + ";" + seconds + " ; " + Arrays.toString(getClusterBestSolution()));
////			writer.println(cycle + ";" + project.getName() + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + fitness + ";" + evaluationsConsumed + ";"+ evaluationsConsumed + ";" + seconds + " ; " + Arrays.toString(getClusterBestSolution()));
//			
//			writer.flush();
//		}
		evaluationsConsumed++;
		
		OutputStream logOut = new FileOutputStream (this.logFile);
		PrintWriter logWriter = new PrintWriter(new OutputStreamWriter(logOut));
		if (fitness >= this.bestFitness) {
			logWriter.println(this.cycle + ";" + project.getName() + ";" + Arrays.toString(solution) + ";" + fitness + ";" + this.evaluationsConsumed);
			
		}
		else {
			logWriter.println(this.cycle + ";" + project.getName() + ";" + Arrays.toString(this.bestSolution) + ";" + this.bestFitness + ";" + this.evaluationsConsumed);	
		}
			
			
		logWriter.flush();
		logWriter.close();
					
		return fitness;	
	}
	
	
	private double calculateFitnessRef(int[] cluster)
	{
		double fitness = 0;
		
		fitness = equationFitness.calculateFitnessRef(cluster);
		
		System.out.println(project.getName() + ";" + project.getClassCount() + ";" + fitness);	
//			
//			writer.println(cycle + ";" + project.getName() + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + getBestFitness() + ";" + getEvaluationsConsumed() + ";"+ getIterationBestFound() + ";" + seconds + " ; " + Arrays.toString(getClusterBestSolution()));
//			writer.println(cycle + ";" + project.getName() + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + fitness + ";" + evaluationsConsumed + ";"+ evaluationsConsumed + ";" + seconds + " ; " + Arrays.toString(getClusterBestSolution()));
			
//			writer.flush();
//		}
//		evaluationsConsumed++;
					
		return fitness;	
	}
	
}