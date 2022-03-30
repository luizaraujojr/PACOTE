package unirio.teaching.clustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.IteratedLocalSearch;

public class MainProgram
{
	private static String referenceInstance_comb_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//referenceInstance_comb//";
	private static String referenceInstance_odem_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//referenceInstance_odem";
	
	private static String testInstance_comb_MQ_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//testInstance_comb_MQ//";
	private static String testInstance_comb_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//testInstance_comb//";
	private static String testInstance_odem_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//testInstance_odem";
	
	private static String pasta_teste_DIRECTORY_odem = new File("").getAbsolutePath() + "//data//Experiment//pastateste_odem";
	private static String pasta_teste_DIRECTORY_comb = new File("").getAbsolutePath() + "//data//Experiment//pastateste_comb//";

	private static String RESULT_DIRECTORY = new File("").getAbsolutePath() + "//data//clustering";
	
	
	static int cycleNumber=0;
	
	public static final void main(String[] args) throws Exception
	{
    	OutputStream out = new FileOutputStream (RESULT_DIRECTORY+ "//" + "789metrica5keval20instanciasPequenasMQ-perturb075");
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
		writer.println("cicle;instance;nclasses;solutionreal;solution;mojo;evalsconsumed;besteval;time;cluster");

		
//		boolean[] metricasUtilizadas1 = {true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false};
//		
//		executeExperiment(1, metricasUtilizadas1, testInstance_odem_DIRECTORY, testInstance_comb_DIRECTORY, writer);
		
		
//		boolean[] metricasUtilizadas2 = {true, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false};
//		
//		executeExperiment(2, metricasUtilizadas2, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas3 = {true, true, true, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false};
//		
//		executeExperiment(3, metricasUtilizadas3, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas4 = {true, true, true, true, false, false, false, false, false, true, true, true, true, false, false, false, false, false};
//		
//		executeExperiment(4, metricasUtilizadas4, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
		
		
//		boolean[] metricasUtilizadas5 = {true, true, true, true, true, false, false, false, false, true, true, true, true, true, false, false, false, false};
//		
//		executeExperiment(5, metricasUtilizadas5, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas6 = {true, true, true, true, true, true, false, false, false, true, true, true, true, true, true, false, false, false};
//		
//		executeExperiment(6, metricasUtilizadas6, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
		boolean[] metricasUtilizadas7 = {true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false};
		
		executeExperiment(7, metricasUtilizadas7, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
		
		
		boolean[] metricasUtilizadas8 = {true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, false};
		
		executeExperiment(8, metricasUtilizadas8, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
		
		
		boolean[] metricasUtilizadas9 = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		
		executeExperiment(9, metricasUtilizadas9, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
		
		
//		executeMQReferenceGeneration();
		
		
//		executeOriginalReference(testInstance_odem_DIRECTORY, testInstance_comb_DIRECTORY);
	
//		executeOriginalReference(referenceInstance_odem_DIRECTORY, referenceInstance_comb_DIRECTORY);
}
	
	private static void executeMQReferenceGeneration() throws Exception {

		boolean[] usedMetrics = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		
		File file = new File(testInstance_odem_DIRECTORY);
		
	    for (String projectName : file.list()) 
	    {	
			CDAReader reader = new CDAReader();    
    		Project project = reader.load(testInstance_odem_DIRECTORY + "//" + projectName);
    		
    		IteratedLocalSearch ils = new IteratedLocalSearch(project, 0, 2, usedMetrics);
    		int[] solution = ils.executeMQReferenceGeneration(1);
    	
    		generateSolution(project, projectName, solution, testInstance_comb_MQ_DIRECTORY);
	    }		
	}
	
	private static void executeOriginalReference(String source, String target) throws Exception {
		File file = new File(source);
	
	    for (String projectName : file.list()) 
	    {	    	
			CDAReader reader = new CDAReader();    
    		Project project = reader.load(source + "//" + projectName);
    		        		        	
    		generateSolutionOriginal(project, projectName, target);
	    }		
	}
	
	
	@SuppressWarnings("unused")
	private static void executeExperiment(int metricsSize, boolean[] usedMetrics, String odemDir, String combDir, PrintWriter writer) throws Exception 
	{
		File file = new File(odemDir);
		int maxCycles = 10;
		
	    for (String projectName : file.list()) 
	    {
	    	
			CDAReader reader = new CDAReader();    
    		Project project = reader.load(odemDir + "//" + projectName);
    		StringBuilder sbRefDepFile = loadDepRefFile(combDir + projectName + ".comb");
    		
    		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    		
	    	for(cycleNumber = 0; cycleNumber < maxCycles; cycleNumber++)
	    	{
	    		int cycle = cycleNumber;
	    		executor.submit(() -> {
		
		        	long startTimestamp = System.currentTimeMillis();
		        		        	
		    		IteratedLocalSearch ils;
					try {
						ils = new IteratedLocalSearch(project, 5000, sbRefDepFile, metricsSize, usedMetrics);
						
						int[] solution = ils.executeExperiment(cycleNumber, startTimestamp);
		
			    		long finishTimestamp = System.currentTimeMillis();
			    		long seconds = (finishTimestamp - startTimestamp);
			    		
			    		String solutionText = ""; 
			    		for(int h = 0; h < solution.length; h++)
				    	{
			    			solutionText = solutionText  + String.valueOf((solution[h]-5.0)/2.0) + ",";
				    	}

//			    		writer 
			    		writer.println(cycle+ ";" + projectName + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + ils.getBestFitness() + ";" + ils.getEvaluationsConsumed() + ";"+ ils.getIterationBestFound() + ";" + seconds + " ; " + Arrays.toString(ils.getClusterBestSolution()));
			    		writer.flush();
//			    		System.out.println(cycle+ ";" + projectName + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + ils.getBestFitness() + ";" + ils.getEvaluationsConsumed() + ";"+ ils.getIterationBestFound() + ";" + seconds + " ; " + Arrays.toString(ils.getClusterBestSolution()));
		    		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 return null;

		    	});
	    	}
	    	executor.shutdown();
////	    	writer.close();
	    }
	}
	
	protected static String padLeft(String s, int length) 
	{
	    StringBuilder sb = new StringBuilder();
	    sb.append(s);
	    
	    while (sb.length() < length)
	        sb.append(' ');
	    
	    return sb.toString();
	}

	protected static String padRight(String s, int length) 
	{
	    StringBuilder sb = new StringBuilder();
	    
	    while (sb.length() < length - s.length())
	        sb.append(' ');
	    
	    sb.append(s);
	    return sb.toString();
	}
	
	public static StringBuilder loadDepRefFile(String filename) throws FileNotFoundException
	{
		StringBuilder sb  = new StringBuilder();
		
		FileInputStream fis = new FileInputStream(filename);
		Scanner sc = new Scanner(fis);

		while (sc.hasNextLine())
		{
			String line = sc.nextLine();
			sb.append (line);
			sb.append(System.lineSeparator());
		}

		sc.close();
		return sb;
	}
	
	public static String getStringTime() {
		Calendar data;
		data = Calendar.getInstance();
		int segundo = data.get(Calendar.SECOND);
	    int minuto = data.get(Calendar.MINUTE);
	    int hora = data.get(Calendar.HOUR_OF_DAY);
	    int dia = data.get(Calendar.DAY_OF_MONTH);	
	    int mes = data.get(Calendar.MONTH);;	
	    int ano = data.get(Calendar.YEAR);;		
		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
	}
	
private static String generateSolution(Project project, String projectName, int[] bestSolution, String folderDestination) throws IOException{
		
		StringBuilder sb1 = new StringBuilder();
		for(int i=0; i<(bestSolution.length); i++){
			if( bestSolution[i]>=0) {
				sb1.append("PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
				sb1.append(System.lineSeparator());			
			}
		}

//		File file = new File(ILS_INTERPRETATION_DIRECTORY + projectName+ getStringTime() +".comb");
		File file = new File(folderDestination  + "//" +  projectName+ ".comb");
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    try {
	        writer.write(sb1.toString());	    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   
		} finally {
			writer.close();
		}
		return file.getCanonicalPath();
	}


private static String generateSolutionOriginal(Project project, String projectName, String target) throws IOException{
	
	StringBuilder sb1 = new StringBuilder();
	for(int i=0; i<(project.getClassCount()); i++){		
		sb1.append("PKG" + project.getIndexForPackage(project.getClassIndex(i).getPackage())  + " " + project.getClassIndex(i).getName());
		sb1.append(System.lineSeparator());			
	}

	File file = new File(target + projectName+ ".comb");
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    try {
        writer.write(sb1.toString());	    
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   
	} finally {
		writer.close();
	}
	return file.getCanonicalPath();
}

}