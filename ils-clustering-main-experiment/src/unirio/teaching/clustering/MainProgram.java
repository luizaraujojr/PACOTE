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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.management.modelmbean.XMLParseException;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.IteratedLocalSearch;
import unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import unirio.teaching.clustering.search.constructive.ConstrutiveAglomerativeMQ;

public class MainProgram
{
//	private static String BASE_DIRECTORY = "C://Users//User//Desktop//Codigos//ils-clustering//data//clustering//odem-temp";
//	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//Test"; //use este para jodamoney
	
	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "//data//clustering//odem-temp"; //use este para a pasta tradicional
	
	private static String ILS_INTERPRETATION_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//ILSInterpretation//";
	private static String RESULT_DIRECTORY = new File("").getAbsolutePath() + "//data//clustering";
	
	static int cycleNumber=0;
	
	public static final void main(String[] args) throws Exception
	{
//		boolean[] metricasUtilizadas1 = {true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false};
//		
//		executeExperiment(1, metricasUtilizadas1);
//		
//		
//		boolean[] metricasUtilizadas2 = {true, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false};
//		
//		executeExperiment(2, metricasUtilizadas2);
		
//		
		boolean[] metricasUtilizadas3 = {true, true, true, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false};
		
		executeExperiment(3, metricasUtilizadas3);

		
//		boolean[] metricasUtilizadas4 = {true, true, true, true, false, false, false, false, false, true, true, true, true, false, false, false, false, false};
//		
//		executeExperiment(4, metricasUtilizadas4);
//		
//		
//		boolean[] metricasUtilizadas5 = {true, true, true, true, true, false, false, false, false, true, true, true, true, true, false, false, false, false};
//		
//		executeExperiment(5, metricasUtilizadas5);
//		
//		
//		
//		boolean[] metricasUtilizadas6 = {true, true, true, true, true, true, false, false, false, true, true, true, true, true, true, false, false, false};
//		
//		executeExperiment(6, metricasUtilizadas6);
//		
//		
//		boolean[] metricasUtilizadas7 = {true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false};
//		
//		executeExperiment(7, metricasUtilizadas7);
//		
//		
//		
//		boolean[] metricasUtilizadas8 = {true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, false};
//		
//		executeExperiment(8, metricasUtilizadas8);
//		
//		
//		
//		boolean[] metricasUtilizadas9 = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
//		
//		executeExperiment(9, metricasUtilizadas9);
//		
//		
//		executeMQReferenceGeneration();
	}
	
	private static void executeMQReferenceGeneration() throws Exception {

		boolean[] usedMetrics = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
	//	
		
		File file = new File(BASE_DIRECTORY);
		DecimalFormat df4 = new DecimalFormat("0.0000");
		
	    for (String projectName : file.list()) 
	    {
	    	OutputStream out = new FileOutputStream (RESULT_DIRECTORY+ "//" + projectName);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
			writer.println("instance;runtime;evaluationsConsumed;bestFitness;Time;bestSolution");
	    	
			CDAReader reader = new CDAReader();    
    		Project project = reader.load(BASE_DIRECTORY + "//" + projectName);
//    		StringBuilder sbRefDepFile = null;//loadDepRefFile(ILS_INTERPRETATION_DIRECTORY + projectName + ".comb");
    		
    		long startTimestamp = System.currentTimeMillis();
        		        	
    		IteratedLocalSearch ils = new IteratedLocalSearch(project, 0, 2, usedMetrics);
    		int[] solution = ils.executeMQReferenceGeneration(1, startTimestamp, writer);

    		long finishTimestamp = System.currentTimeMillis();
    		long seconds = (finishTimestamp - startTimestamp);
    		generateSolution(project, projectName, solution);
    		
//    		System.out.println(cycleNumber+ ";" + projectName + ";" + project.getClassCount() + ";" + Arrays.toString(solution) + ";" + ils.getBestFitness() + ";" + seconds + " ms");
	    	
	    	writer.close();
	    }		
	}
	
	
	@SuppressWarnings("unused")
	private static void executeExperiment(int metricsSize, boolean[] usedMetrics) throws Exception 
	{
		File file = new File(BASE_DIRECTORY);
		int maxCycles = 10;
		
	    for (String projectName : file.list()) 
	    {
//	    	OutputStream out = new FileOutputStream (RESULT_DIRECTORY+ "//" + projectName);
//			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
//			writer.println("instance;runtime;evaluationsConsumed;bestFitness;Time;bestSolution");
	    	
			CDAReader reader = new CDAReader();    
    		Project project = reader.load(BASE_DIRECTORY + "//" + projectName);
    		StringBuilder sbRefDepFile = loadDepRefFile(ILS_INTERPRETATION_DIRECTORY + projectName + ".comb");
    		
    		
    		
    		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    		
	    	for(cycleNumber = 0; cycleNumber < maxCycles; cycleNumber++)
	    	{
	    		int cycle = cycleNumber;
	    		executor.submit(() -> {
		
		        	long startTimestamp = System.currentTimeMillis();
		        		        	
		    		IteratedLocalSearch ils;
					try {
						ils = new IteratedLocalSearch(project, 1, sbRefDepFile, metricsSize, usedMetrics);
						//	    		int[] solution = ils.executeExperiment(cycleNumber, startTimestamp, writer);
			    		int[] solution = ils.executeExperiment(cycleNumber, startTimestamp);
		
			    		long finishTimestamp = System.currentTimeMillis();
			    		long seconds = (finishTimestamp - startTimestamp);
			    		
			    		String solutionText = ""; 
			    		for(int h = 0; h < solution.length; h++)
				    	{
			    			solutionText = solutionText  + String.valueOf((solution[h]-5.0)/2.0) + ",";
				    	}
			    		
			    		System.out.println(cycle+ ";" + projectName + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + ils.getBestFitness() + ";" + ils.getEvaluationsConsumed() + ";" + seconds + " ms");
		    		
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 return null;

		    	});
	    	}
	    	executor.shutdown();
//	    	writer.close();
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
	
private static String generateSolution(Project project, String projectName, int[] bestSolution) throws IOException{
		
		StringBuilder sb1 = new StringBuilder();
		for(int i=0; i<(bestSolution.length); i++){
			if( bestSolution[i]>=0) {
				sb1.append("PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
				sb1.append(System.lineSeparator());			
			}
		}

//		File file = new File(ILS_INTERPRETATION_DIRECTORY + projectName+ getStringTime() +".comb");
		File file = new File(ILS_INTERPRETATION_DIRECTORY + projectName+ ".comb");
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