//
//package br.com.ppgi.unirio.teaching.clustering.search;
//
//import br.com.ppgi.unirio.teaching.clustering.model.Project;
//import br.com.ppgi.unirio.teaching.clustering.model.ProjectClass;
//import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
//import br.com.ppgi.unirio.teaching.clustering.search.model.ClusterMetrics;
//import br.com.ppgi.unirio.teaching.clustering.search.model.ModuleDependencyGraph;
//
///**
// * Iterated Local Search for the next release problem
// */
//public class IteratedLocalSearch
//{
//	private int PERTURBATION_SIZE = 5;
//	
//	/**
//	 * Constructive method
//	 */
//	private ConstrutiveAbstract constructorRandom;
//	private ConstrutiveAbstract constructorMQ;
//
//	/**
//	 * Number of classes in the project
//	 */
//	private int classCount;
//	
//	/**
//	 * Dependency graph for the project
//	 */
//	private ModuleDependencyGraph mdg;
//	
//	/**
//	 * Number of fitness evaluations available in the budget
//	 */
//	private int maxEvaluations;
//
//	/**
//	 * Number of fitness evaluations executed
//	 */
//	private int evaluationsConsumed;
//
//	/**
//	 * Number of iterations to best solution
//	 */
//	private int iterationBestFound;
//	
//	/**
//	 * MQ calculator
//	 */
//	private MojoMetrics mojoMetrics;
//
//	/**
//	 * Fitness of the best solution
//	 */
//	private double bestFitness; 
//
//	
//	/**
//	 * variable to used in the fitness calculation
//	 */
//	private double a1;
//	
//	/**
//	 * variable to used in the fitness calculation
//	 */
//	private double a2;
//	
//	/**
//	 * variable to used in the fitness calculation
//	 */
//	private double b1;
//	
//	/**
//	 * variable to used in the fitness calculation
//	 */
//	private double b2;
//	
//	/**
//	 * variable to used in the fitness calculation
//	 */
//	private double bestFitnessConstructor;
//	
//	private int  paramNumber;
//	
//	private double multiplyFactor;
//	
//	/**
//	 * Initializes the ILS search process
//	 */
//	public IteratedLocalSearch(ConstrutiveAbstract constructorRandom, ConstrutiveAbstract constructorMQ, Project project, int maxEvaluations, int paramNumber, double  multiplyFactor) throws Exception
//	{
//		this.constructorRandom = constructorRandom;
//		this.constructorMQ = constructorMQ;
//		this.classCount = project.getClassCount();
//		this.mdg = buildGraph(project, this.classCount);
//		this.maxEvaluations = maxEvaluations;
//		this.evaluationsConsumed = 0;
//		this.iterationBestFound = 0;
//		this.mojoMetrics = null;
//		this.bestFitness = -1_000_000_000_000.0;
//		this.paramNumber = paramNumber;
//		this.multiplyFactor = multiplyFactor;    
//	}
//	
//	/**
//	 * Builds the project's dependency graph from its representation
//	 */
//	private ModuleDependencyGraph buildGraph(Project project, int classCount) throws Exception
//	{
//		ModuleDependencyGraph mdg = new ModuleDependencyGraph(classCount);
//		
//		for (int i = 0; i < classCount; i++)
//		{
//			ProjectClass _class = project.getClassIndex(i);
//
//			for (int j = 0; j < _class.getDependencyCount(); j++)
//			{
//				String targetName = _class.getDependencyIndex(j).getElementName();
//				int classIndex = project.getClassIndex(targetName);
//				
//				if (classIndex == -1)
//					throw new Exception ("Class not registered in project: " + targetName);
//				
//				mdg.addModuleDependency(i, classIndex, 1);
//			}
//		}
//		
//		return mdg;
//	}
//
//	/**
//	 * Returns the number of evaluations consumed during the search
//	 */
//	public int getEvaluationsConsumed()
//	{
//		return evaluationsConsumed;
//	}
//
//	/**
//	 * Returns the maximum number of evaluations to be consumed
//	 */
//	public int getMaximumEvaluations()
//	{
//		return maxEvaluations;
//	}
//	
//	/**
//	 * Returns the iteration on which the best solution was found
//	 */
//	public int getIterationBestFound()
//	{
//		return iterationBestFound;
//	}
//
//	/**
//	 * Returns the best fitness found
//	 */
//	public double getBestFitness()
//	{
//		return bestFitness;
//	}
//	
//	/**
//	 * Returns the bestFitnessConstructor
//	 */
//	public double getBestFitnessConstructor()
//	{
//		return bestFitnessConstructor;
//	}
//	
////	/**
////	 * Main loop of the algorithm
////	 */
////	public int[] execute() throws Exception
////	{
////		double[] bestSolution = constructorRandom.createSolution(paramNumber, multiplyFactor);
////		this.mojoMetrics = new MojoMetrics(mdg, constructorMQ, bestSolution);
////		
////		this.bestFitness = mojoMetrics.calculateFitness();
////		
////		this.bestFitnessConstructor = this.bestFitness;
////		++evaluationsConsumed;
////
//////		localSearch(this.mojoMetrics);
////		double fitness = mojoMetrics.calculateFitness();
////		++evaluationsConsumed;
////		
////		if (fitness > bestFitness)
////		{
////			bestSolution = this.mojoMetrics.cloneSolution();
////			this.bestFitness = fitness;
////		}
////		
////		while (getEvaluationsConsumed() < getMaximumEvaluations())
////		{
////			applyPerturbation(this.mojoMetrics, PERTURBATION_SIZE);
////			
////			localSearch(this.mojoMetrics);
////			fitness = mojoMetrics.calculateFitness();
////			++evaluationsConsumed;
////			
////			if (fitness > this.bestFitness)
////			{
////				bestSolution = this.mojoMetrics.cloneSolution();
////				this.bestFitness = fitness;
////			}
////		}
////
////		return bestSolution;
////	}
//
//	
//	/**
//	 * Main loop of the algorithm
//	 */
////	public int[] executeConstructor() throws Exception
////	{
////		int[] bestSolution = constructor.createSolution(mdg, a1, a2, b1, b2);
////		this.metrics = new ClusterMetrics(mdg, bestSolution, a1, a2, b1, b2);
////		
////		this.bestFitness = metrics.calculateMQ();
////		++evaluationsConsumed;
////
////		localSearch(this.metrics);
////		double fitness = metrics.calculateMQ();
////		++evaluationsConsumed;
////		
////		if (fitness > bestFitness)
////		{
////			bestSolution = this.metrics.cloneSolution();
////			this.bestFitness = fitness;
////		}
////		
////		while (getEvaluationsConsumed() < getMaximumEvaluations())
////		{
////			applyPerturbation(this.metrics, PERTURBATION_SIZE);
////			
////			localSearch(this.metrics);
////			fitness = metrics.calculateMQ();
////			++evaluationsConsumed;
////			
////			if (fitness > this.bestFitness)
////			{
////				bestSolution = this.metrics.cloneSolution();
////				this.bestFitness = fitness;
////			}
////		}
//////
//////		return bestSolution;
////	}
//
//	/**
//	 * Applies the perturbation operator upon a solution
//	 */
//	private void applyPerturbation(ClusterMetrics calculator, int amount)
//	{
//		for (int i = 0; i < amount; i++)
//		{
//			int source = PseudoRandom.randInt(0, classCount-1);
//			int target = PseudoRandom.randInt(0, classCount-1);
//			calculator.makeMoviment(source, target);
//		}
//	}
//
//	/**
//	 * Performs the local search starting from a given solution
//	 */
//	private void localSearch(MojoMetrics mojoMetrics)
//	{
//		while (visitNeighbors(mojoMetrics))
//			;
//	}
//
//	/**
//	 * Runs a neighborhood visit starting from a given solution
//	 */
//	private boolean visitNeighbors(MojoMetrics mojoMetrics)
//	{
//		if (evaluationsConsumed > maxEvaluations)
//			return false;
//
//		int source = -1;
//		int target = -1;
//		double bestGain = Double.NEGATIVE_INFINITY;
//		
//		for (int i = 0; i < classCount; i++)
//		{
//			int newPackage = PseudoRandom.randInt(0, classCount-1);
//			double gain = calculator.calculateMovimentDelta(i, newPackage);
//			++evaluationsConsumed;
//
//			if (gain > bestGain)
//			{
//				source = i;
//				target = newPackage;
//				bestGain = gain;
//			}
//		}
//
//		if (bestGain > 0)
//		{
//			calculator.makeMoviment(source, target);
//			return true;
//		}
//		
//		return false;
//	}
//}
//
////package br.com.ppgi.unirio.teaching.clustering.search;
////
////import java.io.BufferedWriter;
////import java.io.File;
////import java.io.FileNotFoundException;
////import java.io.FileWriter;
////import java.io.IOException;
////import java.util.ArrayList;
////import java.util.Arrays;
////import java.util.Calendar;
////import java.util.List;
////
////import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;
////import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;
////import br.com.ppgi.unirio.teaching.clustering.model.Project;
////
/////**
//// * Iterated Local Search for the next release problem
//// */
////public class IteratedLocalSearch
////{
////	private int PERTURBATION_SIZE = 5;
////	
////	/**
////	 * Set of requirements to be optimized
////	 */
////	private Project project;
////
////	/**
////	 * Number of fitness evaluations available in the budget
////	 */
////	private int maxEvaluations;
////
////	/**
////	 * Number of fitness evaluations executed
////	 */
////	private int evaluationsConsumed;
////
////	/**
////	 * Number of iterations to best solution
////	 */
////	private int iterationBestFound;
////
////
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private String equation;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double a1;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double a2;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double b1;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double b2;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double bestFitness;
////	
////	
////	/**
////	 * Initializes the ILS search process
////	 */
////	public IteratedLocalSearch(Project project, int maxEvaluations, double a1, double a2, double b1, double b2) throws Exception
////	{
////		this.project = project;
////		this.maxEvaluations = maxEvaluations;
////		this.evaluationsConsumed = 0;
////		this.iterationBestFound = 0;
//////		this.equation = equation;
////		this.a1 = a1;
////		this.a2 = a2;
////		this.b1 = b1;
////		this.b2 = b2;
////	}
////
////	/**
////	 * Returns the number of evaluations consumed during the search
////	 */
////	public int getEvaluationsConsumed()
////	{
////		return evaluationsConsumed;
////	}
////
////	/**
////	 * Returns the maximum number of evaluations to be consumed
////	 */
////	public int getMaximumEvaluations()
////	{
////		return maxEvaluations;
////	}
////	
////	/**
////	 * Returns the iteration on which the best solution was found
////	 */
////	public int getIterationBestFound()
////	{
////		return iterationBestFound;
////	} 
////	
////	/**
////	 * Returns the iteration on which the best solution was found
////	 */
////	public double getBestFitness()
////	{
////		return bestFitness;
////	}
////	
////	/**
////	 * Main loop of the algorithm
////	 */
////	public int[] execute(StringBuilder sb) throws Exception
////	{
////		double startTime = System.currentTimeMillis();
////		ClusteringCalculator calculator = new ClusteringCalculator(project, project.getPackageCount() * 2);
////		
////		int[] bestSolution = createRandomSolution(calculator);
//////		double bestFitness = evaluate(bestSolution, calculator, 0.0);
////		bestFitness = evaluate(bestSolution, calculator, 0.0);
////
////		int[] solution = localSearch(bestSolution, calculator, bestFitness);
////		double fitness = evaluate(solution, calculator, bestFitness);
////		
////		if (fitness > bestFitness)
////		{
////			bestSolution = solution;
////			bestFitness = fitness;
////		}
////		
////		while (getEvaluationsConsumed() < getMaximumEvaluations())
////		{
////			int[] startSolution = applyPerturbation(bestSolution, calculator, PERTURBATION_SIZE);
////			solution = localSearch(startSolution, calculator, bestFitness);
////			fitness = evaluate(solution, calculator, bestFitness);
//////			System.out.println(Arrays.toString(bestSolution) + "; " + bestFitness);
////			
////			if (fitness > bestFitness)
////			{
////				bestSolution = solution;
////				bestFitness = fitness;
////			}
////		}
////		
////		StringBuilder sb1 = new StringBuilder();
////		for(int i=0; i<(bestSolution.length); i++){
////			if( bestSolution[i]>=0) {
//////				System.out.println("contain PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
////				sb1.append("contain PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
////				if (i<(bestSolution.length-1)){
////					sb1.append(System.lineSeparator());	
////				}
////			}
////		}
////		
////		sb1.delete(sb1.length()-1, sb1.length()-1); 
////
////		File file = new File("data//Experiment//ILSInterpretation//"+ project.getName() + getStringTime()+ ".comb");
////	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
////	    try {
////	        writer.write(sb1.toString());	    
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		   
////		} finally {
////			writer.close();
////		}
////	   	    
////	    List<String> packageClassCombinationFilenamesILS = new ArrayList<String>();
//////	    packageClassCombinationFilenamesILS.add ("data//Experiment//ILSInterpretation//jmetal08042021031320.comb"); //jodamoney otimizado
////	    packageClassCombinationFilenamesILS.add ("data//Experiment//ILSInterpretation//jmetal11042021101221.comb"); //jodamoney otimizado
////	    	    
////		double mojoValue =	runMOJOComparison(file.getAbsolutePath(),packageClassCombinationFilenamesILS.get(0), "-fm");
////		
////		sb.append (project.getName() + ";" + Arrays.toString(bestSolution) + ";" + bestFitness + "a1:" + a1 + " a2:" + a2 + " b1:" + b1+  " b2:" + b2+ " ; MojoFM: " + mojoValue +  ";" + (System.currentTimeMillis() - startTime));
////		sb.append(System.lineSeparator());   
////		return bestSolution;
////	}
////	
////	
////	
////	
////	
////	public int[] execute(int runTime, StringBuilder sb) throws Exception
////	{
////		double startTime = System.currentTimeMillis();
////		ClusteringCalculator calculator = new ClusteringCalculator(project, project.getPackageCount() * 2);
////		
////		int[] bestSolution = createRandomSolution(calculator);
////		double bestFitness = evaluate(bestSolution, calculator, 0.0);
////
////		int[] solution = localSearch(bestSolution, calculator, bestFitness);
////		double fitness = evaluate(solution, calculator, bestFitness);
////		
////		if (fitness > bestFitness)
////		{
////			bestSolution = solution;
////			bestFitness = fitness;
////		}
////		
////		while (getEvaluationsConsumed() < getMaximumEvaluations())
////		{
////			int[] startSolution = applyPerturbation(bestSolution, calculator, PERTURBATION_SIZE);
////			solution = localSearch(startSolution, calculator, bestFitness);
////			fitness = evaluate(solution, calculator, bestFitness);		
////			
////			if (fitness > bestFitness)
////			{
////				bestSolution = solution;
////				bestFitness = fitness;
////			}
////		}
////	    
////		sb.append (project.getName() + ";" + runTime + ";" + Arrays.toString(bestSolution) + ";" + bestFitness +  ";" + (System.currentTimeMillis() - startTime) );
////		sb.append(System.lineSeparator());	    
////		return bestSolution;
////	}
////
////	
////
////	/**
////	 * Creates a random solution
////	 */
////	private int[] createRandomSolution(ClusteringCalculator calculator)
////	{
////		int classCount = calculator.getClassCount();
////		int packageCount = calculator.getPackageCount();
////		
////		int[] solution = new int[classCount];
////		
////		for (int i = 0; i < classCount; i++)
////			solution[i] = PseudoRandom.randInt(0, packageCount-1);
////		
////		return solution;
////	}
////
////	/**
////	 * Applies the perturbation operator upon a solution
////	 */
////	private int[] applyPerturbation(int[] solution, ClusteringCalculator calculator, int amount)
////	{
////		int[] perturbedSolution = Arrays.copyOf(solution, solution.length);
////		int classCount = calculator.getClassCount();
////		int packageCount = calculator.getPackageCount();
////
////		for (int i = 0; i < amount; i++)
////		{
////			int classIndex = PseudoRandom.randInt(0, classCount-1);
////			perturbedSolution[classIndex] = PseudoRandom.randInt(0, packageCount-1);
////		}
////
////		return perturbedSolution;
////	}
////
////	/**
////	 * Performs the local search starting from a given solution
////	 */
////	private int[] localSearch(int[] solution, ClusteringCalculator calculator, double bestFitness)
////	{
////		NeighborhoodVisitorResult result;
////
////		int[] bestLocalSolution = Arrays.copyOf(solution, solution.length);
////		double bestLocalFitness = evaluate(bestLocalSolution, calculator, bestFitness);
////
////		do
////		{
////			result = visitNeighbors(bestLocalSolution, calculator, bestFitness);
////
////			if (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR && result.getNeighborFitness() > bestLocalFitness)
////			{
////				bestLocalFitness = result.getNeighborFitness();
////				this.iterationBestFound = evaluationsConsumed;
////			}
////
////		} while (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR);
////
////		return bestLocalSolution;
////	}
////
////	/**
////	 * Runs a neighborhood visit starting from a given solution
////	 */
////	private NeighborhoodVisitorResult visitNeighbors(int[] solution, ClusteringCalculator calculator, double bestFitness)
////	{
////		double startingFitness = evaluate(solution, calculator, bestFitness);
////
////		if (evaluationsConsumed > maxEvaluations)
////			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
////
////		int classCount = calculator.getClassCount();
////		int packageCount = calculator.getPackageCount();
////
////		for (int i = 0; i < classCount; i++)
////		{
////			int originalPackage = solution[i];
////			solution[i] = PseudoRandom.randInt(0, packageCount-1);
////
////			double neighborFitness = evaluate(solution, calculator, bestFitness);
////
////			if (evaluationsConsumed > maxEvaluations)
////				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
////
////			if (neighborFitness > startingFitness)
////				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);
////
////			solution[i] = originalPackage;
////		}
////
////		return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
////	}
////
////	/**
////	 * Evaluates the fitness of a solution, saving detail information
////	 */
////	private double evaluate(int[] solution, ClusteringCalculator calculator, double bestFitness)
////	{
////		if (++evaluationsConsumed % 10000 == 0)
////		{
//////			System.out.println(evaluationsConsumed + "; " + bestFitness  + "; " + Arrays.toString(solution));
////		}
////
////		return calculator.calculateModularizationQuality(solution, a1, a2, b1, b2);
////	}
////
////	/**
////	 * Set of potential results from visiting neighbors
////	 */
////	private enum NeighborhoodVisitorStatus
////	{
////		FOUND_BETTER_NEIGHBOR, NO_BETTER_NEIGHBOR, SEARCH_EXHAUSTED
////	}
////	
////	/**
////	 * Class that represents the results of the local search phase
////	 */
////	private class NeighborhoodVisitorResult
////	{
////		/**
////		 * Status in the end of the local search
////		 */
////		private NeighborhoodVisitorStatus status;
////	
////		/**
////		 * Fitness of the best neighbor, in case one has been found
////		 */
////		private double neighborFitness;
////	
////		/**
////		 * Initializes a successful local search status
////		 */
////		public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status, double fitness)
////		{
////			this.status = status;
////			this.neighborFitness = fitness;
////		}
////	
////		/**
////		 * Initializes an unsuccessful local search
////		 */
////		public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status)
////		{
////			this.status = status;
////			this.neighborFitness = 0.0;
////		}
////	
////		/**
////		 * Returns the status of the local search
////		 */
////		public NeighborhoodVisitorStatus getStatus()
////		{
////			return status;
////		}
////	
////		/**
////		 * Return the fitness of the best neighbor found, if any
////		 */
////		public double getNeighborFitness()
////		{
////			return neighborFitness;
////		}
////	}
////	
////	
////	private static String getStringTime() {
////		Calendar data;
////		data = Calendar.getInstance();
////		int segundo = data.get(Calendar.SECOND);
////        int minuto = data.get(Calendar.MINUTE);
////        int hora = data.get(Calendar.HOUR_OF_DAY);
////        int dia = data.get(Calendar.DAY_OF_MONTH);	
////        int mes = data.get(Calendar.MONTH);;	
////        int ano = data.get(Calendar.YEAR);;		
////		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
////	}
////	
//
////	
////}
//
////package br.com.ppgi.unirio.teaching.clustering.search;
////
////import java.io.BufferedWriter;
////import java.io.File;
////import java.io.FileNotFoundException;
////import java.io.FileWriter;
////import java.io.IOException;
////import java.util.ArrayList;
////import java.util.Arrays;
////import java.util.Calendar;
////import java.util.List;
////
////import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;
////import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;
////import br.com.ppgi.unirio.teaching.clustering.model.Project;
////
/////**
//// * Iterated Local Search for the next release problem
//// */
////public class IteratedLocalSearch
////{
////	private int PERTURBATION_SIZE = 5;
////	
////	/**
////	 * Set of requirements to be optimized
////	 */
////	private Project project;
////
////	/**
////	 * Number of fitness evaluations available in the budget
////	 */
////	private int maxEvaluations;
////
////	/**
////	 * Number of fitness evaluations executed
////	 */
////	private int evaluationsConsumed;
////
////	/**
////	 * Number of iterations to best solution
////	 */
////	private int iterationBestFound;
////
////
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private String equation;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double a1;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double a2;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double b1;
////	
////	/**
////	 * equation to used in the fitness calculation
////	 */
////	private double b2;
////	
////	
////	/**
////	 * Initializes the ILS search process
////	 */
////	public IteratedLocalSearch(Project project, int maxEvaluations, double a1, double a2, double b1, double b2) throws Exception
////	{
////		this.project = project;
////		this.maxEvaluations = maxEvaluations;
////		this.evaluationsConsumed = 0;
////		this.iterationBestFound = 0;
//////		this.equation = equation;
////		this.a1 = a1;
////		this.a2 = a2;
////		this.b1 = b1;
////		this.b2 = b2;
////	}
////
////	/**
////	 * Returns the number of evaluations consumed during the search
////	 */
////	public int getEvaluationsConsumed()
////	{
////		return evaluationsConsumed;
////	}
////
////	/**
////	 * Returns the maximum number of evaluations to be consumed
////	 */
////	public int getMaximumEvaluations()
////	{
////		return maxEvaluations;
////	}
////	
////	/**
////	 * Returns the iteration on which the best solution was found
////	 */
////	public int getIterationBestFound()
////	{
////		return iterationBestFound;
////	}
////	
////	/**
////	 * Main loop of the algorithm
////	 */
////	public int[] execute(StringBuilder sb) throws Exception
////	{
////		double startTime = System.currentTimeMillis();
////		ClusteringCalculator calculator = new ClusteringCalculator(project, project.getPackageCount() * 2);
////		
////		int[] bestSolution = createRandomSolution(calculator);
////		double bestFitness = evaluate(bestSolution, calculator, 0.0);
////
////		int[] solution = localSearch(bestSolution, calculator, bestFitness);
////		double fitness = evaluate(solution, calculator, bestFitness);
////		
////		if (fitness > bestFitness)
////		{
////			bestSolution = solution;
////			bestFitness = fitness;
////		}
////		
////		while (getEvaluationsConsumed() < getMaximumEvaluations())
////		{
////			int[] startSolution = applyPerturbation(bestSolution, calculator, PERTURBATION_SIZE);
////			solution = localSearch(startSolution, calculator, bestFitness);
////			fitness = evaluate(solution, calculator, bestFitness);
//////			System.out.println(Arrays.toString(bestSolution) + "; " + bestFitness);
////			
////			if (fitness > bestFitness)
////			{
////				bestSolution = solution;
////				bestFitness = fitness;
////			}
////		}
////		
////		StringBuilder sb1 = new StringBuilder();
////		for(int i=0; i<(bestSolution.length); i++){
////			if( bestSolution[i]>=0) {
//////				System.out.println("contain PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
////				sb1.append("contain PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
////				if (i<(bestSolution.length-1)){
////					sb1.append(System.lineSeparator());	
////				}
////			}
////		}
////		
////		sb1.delete(sb1.length()-1, sb1.length()-1); 
////
////		File file = new File("data//Experiment//ILSInterpretation//"+ project.getName() + getStringTime()+ ".comb");
////	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
////	    try {
////	        writer.write(sb1.toString());	    
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		   
////		} finally {
////			writer.close();
////		}
////	   	    
////	    List<String> packageClassCombinationFilenamesILS = new ArrayList<String>();
//////	    packageClassCombinationFilenamesILS.add ("data//Experiment//ILSInterpretation//jmetal08042021031320.comb"); //jodamoney otimizado
////	    packageClassCombinationFilenamesILS.add ("data//Experiment//ILSInterpretation//jmetal11042021101221.comb"); //jodamoney otimizado
////	    
////	    
////	    
////		double mojoValue =	runMOJOComparison(file.getAbsolutePath(),packageClassCombinationFilenamesILS.get(0), "-fm");
////		
////		sb.append (project.getName() + ";" + Arrays.toString(bestSolution) + ";" + bestFitness + "a1:" + a1 + " a2:" + a2 + " b1:" + b1+  " b2:" + b2+ " ; MojoFM: " + mojoValue +  ";" + (System.currentTimeMillis() - startTime));
////		sb.append(System.lineSeparator());   
////		return bestSolution;
////	}
////	
////	
////	
////	
////	
////	public int[] execute(int runTime, StringBuilder sb) throws Exception
////	{
////		double startTime = System.currentTimeMillis();
////		ClusteringCalculator calculator = new ClusteringCalculator(project, project.getPackageCount() * 2);
////		
////		int[] bestSolution = createRandomSolution(calculator);
////		double bestFitness = evaluate(bestSolution, calculator, 0.0);
////
////		int[] solution = localSearch(bestSolution, calculator, bestFitness);
////		double fitness = evaluate(solution, calculator, bestFitness);
////		
////		if (fitness > bestFitness)
////		{
////			bestSolution = solution;
////			bestFitness = fitness;
////		}
////		
////		while (getEvaluationsConsumed() < getMaximumEvaluations())
////		{
////			int[] startSolution = applyPerturbation(bestSolution, calculator, PERTURBATION_SIZE);
////			solution = localSearch(startSolution, calculator, bestFitness);
////			fitness = evaluate(solution, calculator, bestFitness);		
////			
////			if (fitness > bestFitness)
////			{
////				bestSolution = solution;
////				bestFitness = fitness;
////			}
////		}
////	    
////		sb.append (project.getName() + ";" + runTime + ";" + Arrays.toString(bestSolution) + ";" + bestFitness +  ";" + (System.currentTimeMillis() - startTime) );
////		sb.append(System.lineSeparator());	    
////		return bestSolution;
////	}
////
////	
////
////	/**
////	 * Creates a random solution
////	 */
////	private int[] createRandomSolution(ClusteringCalculator calculator)
////	{
////		int classCount = calculator.getClassCount();
////		int packageCount = calculator.getPackageCount();
////		
////		int[] solution = new int[classCount];
////		
////		for (int i = 0; i < classCount; i++)
////			solution[i] = PseudoRandom.randInt(0, packageCount-1);
////		
////		return solution;
////	}
////
////	/**
////	 * Applies the perturbation operator upon a solution
////	 */
////	private int[] applyPerturbation(int[] solution, ClusteringCalculator calculator, int amount)
////	{
////		int[] perturbedSolution = Arrays.copyOf(solution, solution.length);
////		int classCount = calculator.getClassCount();
////		int packageCount = calculator.getPackageCount();
////
////		for (int i = 0; i < amount; i++)
////		{
////			int classIndex = PseudoRandom.randInt(0, classCount-1);
////			perturbedSolution[classIndex] = PseudoRandom.randInt(0, packageCount-1);
////		}
////
////		return perturbedSolution;
////	}
////
////	/**
////	 * Performs the local search starting from a given solution
////	 */
////	private int[] localSearch(int[] solution, ClusteringCalculator calculator, double bestFitness)
////	{
////		NeighborhoodVisitorResult result;
////
////		int[] bestLocalSolution = Arrays.copyOf(solution, solution.length);
////		double bestLocalFitness = evaluate(bestLocalSolution, calculator, bestFitness);
////
////		do
////		{
////			result = visitNeighbors(bestLocalSolution, calculator, bestFitness);
////
////			if (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR && result.getNeighborFitness() > bestLocalFitness)
////			{
////				bestLocalFitness = result.getNeighborFitness();
////				this.iterationBestFound = evaluationsConsumed;
////			}
////
////		} while (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR);
////
////		return bestLocalSolution;
////	}
////
////	/**
////	 * Runs a neighborhood visit starting from a given solution
////	 */
////	private NeighborhoodVisitorResult visitNeighbors(int[] solution, ClusteringCalculator calculator, double bestFitness)
////	{
////		double startingFitness = evaluate(solution, calculator, bestFitness);
////
////		if (evaluationsConsumed > maxEvaluations)
////			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
////
////		int classCount = calculator.getClassCount();
////		int packageCount = calculator.getPackageCount();
////
////		for (int i = 0; i < classCount; i++)
////		{
////			int originalPackage = solution[i];
////			solution[i] = PseudoRandom.randInt(0, packageCount-1);
////
////			double neighborFitness = evaluate(solution, calculator, bestFitness);
////
////			if (evaluationsConsumed > maxEvaluations)
////				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
////
////			if (neighborFitness > startingFitness)
////				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);
////
////			solution[i] = originalPackage;
////		}
////
////		return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
////	}
////
////	/**
////	 * Evaluates the fitness of a solution, saving detail information
////	 */
////	private double evaluate(int[] solution, ClusteringCalculator calculator, double bestFitness)
////	{
////		if (++evaluationsConsumed % 10000 == 0)
////		{
//////			System.out.println(evaluationsConsumed + "; " + bestFitness  + "; " + Arrays.toString(solution));
////		}
////
////		return calculator.calculateModularizationQuality(solution, a1, a2, b1, b2);
////	}
////
////	/**
////	 * Set of potential results from visiting neighbors
////	 */
////	private enum NeighborhoodVisitorStatus
////	{
////		FOUND_BETTER_NEIGHBOR, NO_BETTER_NEIGHBOR, SEARCH_EXHAUSTED
////	}
////	
////	/**
////	 * Class that represents the results of the local search phase
////	 */
////	private class NeighborhoodVisitorResult
////	{
////		/**
////		 * Status in the end of the local search
////		 */
////		private NeighborhoodVisitorStatus status;
////	
////		/**
////		 * Fitness of the best neighbor, in case one has been found
////		 */
////		private double neighborFitness;
////	
////		/**
////		 * Initializes a successful local search status
////		 */
////		public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status, double fitness)
////		{
////			this.status = status;
////			this.neighborFitness = fitness;
////		}
////	
////		/**
////		 * Initializes an unsuccessful local search
////		 */
////		public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status)
////		{
////			this.status = status;
////			this.neighborFitness = 0.0;
////		}
////	
////		/**
////		 * Returns the status of the local search
////		 */
////		public NeighborhoodVisitorStatus getStatus()
////		{
////			return status;
////		}
////	
////		/**
////		 * Return the fitness of the best neighbor found, if any
////		 */
////		public double getNeighborFitness()
////		{
////			return neighborFitness;
////		}
////	}
////	
////	
////	private static String getStringTime() {
////		Calendar data;
////		data = Calendar.getInstance();
////		int segundo = data.get(Calendar.SECOND);
////        int minuto = data.get(Calendar.MINUTE);
////        int hora = data.get(Calendar.HOUR_OF_DAY);
////        int dia = data.get(Calendar.DAY_OF_MONTH);	
////        int mes = data.get(Calendar.MONTH);;	
////        int ano = data.get(Calendar.YEAR);;		
////		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
////	}
////	
////	private static double runMOJOComparison(String file1, String file2, String param) throws InstanceParseException, IOException {
//////		System.out.println("runMOJOComparison");
////		String[] args1 = new String[3];
////		args1[0] = file1;
////		args1[1] = file2;
////		args1[2] = param; 
////		return MoJo.MojoFM(args1);
////	}
////	
////}