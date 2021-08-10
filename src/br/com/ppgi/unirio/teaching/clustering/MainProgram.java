package br.com.ppgi.unirio.teaching.clustering;

import static br.com.ppgi.unirio.teaching.clustering.Common.*;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

import javax.management.modelmbean.XMLParseException;

import br.com.ppgi.unirio.luiz.softwareanalysis.controller.ProjectLoader;
import br.com.ppgi.unirio.luiz.softwareanalysis.model.Project;
import br.com.ppgi.unirio.teaching.clustering.reader.DependencyReader;
import br.com.ppgi.unirio.teaching.clustering.search.ILS;
import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveAglomerativeMQ;
import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveRandom;

public class MainProgram
{

	private static final String SPLITTER = "\\s+";//split por qualquer espacamento
	
//	private static List<Project> instances = new ArrayList<Project>();
//	private static List<int[]> bestSolutions= new ArrayList<int[]>();
	
	
//	static double fa1 = 0;
//	static double fa2 = 0;
//	static double fb1 = 0;
//	static double fb2 = 0;
//	
	
		
//		public static void runILSMQValidation() throws Exception{
//			double range = 0.5;
//		
//			double a1 = -range;
//			double a2 = -range;
//			double b1 = -range;
//			double b2 = -range;
//			
////			ExecutorService pool = Executors.newFixedThreadPool(8);
//
//			for (Project instance : instances) {
//				StringBuilder sb = new StringBuilder();
//				while (a1 <= range)	{
//					a2 = -range;
//					while (a2 <= range)	{
//						b1 = -range;
//						while (b1 <= range)	{
//							b2 = -range;
//							while (b2 <= range)	{
//								 
////								fa1 = a1;
////								fa2 = a2;
////								fb1 = b1;
////								fb2 = b2;
//								fa1=1;
//								fa2=0;
//								fb1=1;
//								fb2=0.5;
////								pool.execute(new Runnable() {
//////								new Thread(new Runnable() {
////						            @Override
////						            public void run() {		            	
////										try {						
//											new IteratedLocalSearch(instance, 100000, fa1, fa2, fb1, fb2).execute(sb);
////										} catch (Exception e) {
////											// TODO Auto-generated catch block
////											e.printStackTrace();
////										}			
////									}
////								});
////						        }).start();
//								
//								b2 = b2 + 0.5;
//							}
//							b1 = b1 + 0.5;
//						}
//						a2 = a2 + 0.5;
//					}
//					a1 = a1 + 0.5;
//				}
//			
//				File file = new File("data//Experiment//ILSoutput//"+ instance.getName() + getStringTime()+ ".comb");
//			    BufferedWriter writer;
//				try {
//					writer = new BufferedWriter(new FileWriter(file));
//					writer.write(sb.toString());
//					writer.close();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		}		
//		
//		
//		
	public static void readfiles() throws IOException
	{
		File file = new File(VALIDATION_DIRECTORY);
		StringBuilder sb1 = new StringBuilder();
		for (String filename : file.list()) 
        {
			FileInputStream fis = new FileInputStream(VALIDATION_DIRECTORY + filename);
			Scanner sc = new Scanner(fis);

			while (sc.hasNextLine())
			{
				String line = sc.nextLine();
				String[] valores = line.split(";");
				String[] mojo = valores[4].split(":");
				if (mojo[1].equals("100.0%")){
//					System.out.println(line);
					sb1.append(line);
					sb1.append(System.lineSeparator());
				}
			}
			sc.close();		
        }
		File file1 = new File(VALIDATION_DIRECTORY + "RESULTADO.data");
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
	    try {
	        writer.write(sb1.toString());	    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   
		} finally {
			writer.close();
		}
		
	}
					
//		public static final void main(String[] args) throws Exception
//		{	
//			readfiles();
//			
//			
//			double range = 2;
//		
//			double a1 = -range;
//			double a2 = -range;
//			double b1 = -range;
//			double b2 = -range;
//			
//			File file = new File(DEP_BASE_DIRECTORY);
//			DecimalFormat df4 = new DecimalFormat("0.0000");
//			
//			ConstrutiveAbstract constructor = new ConstrutiveAglomerativeMQ();
//			ConstrutiveAbstract construtiveRandom = new ConstrutiveRandom();
//			
//			
//	        for (String projectName : file.list()) 
//	        {
//	        	StringBuilder sb1 = new StringBuilder();
//	        	for(int n=1; n<=1; n++){
//	        		a1 = -range;
//	        		while (a1 <= range)	{
//						a2 = -range;
//						while (a2 <= range)	{
//							b1 = -range;
//							while (b1 <= range)	{
//								b2 = -range;
//								while (b2 <= range)	{
//					        		long startTimestamp = System.currentTimeMillis();
//						        	
//						    		DependencyReader reader = new DependencyReader();
//						    		Project project = reader.load(DEP_BASE_DIRECTORY + projectName);
//				
////						    		IteratedLocalSearch ils = new IteratedLocalSearch(constructor, project, 100_000, 1.0, 0.0, 1.0, 0.5);
//						    		IteratedLocalSearch ils = new IteratedLocalSearch(constructor, project, 100_000, a1, a2, b1, b2);
//				//		    		int[] bestSolution = ils.execute();
//						    		int[] bestSolution = ils.executeConstructor();
//						    		
//						    		long finishTimestamp = System.currentTimeMillis();
//						    		long seconds = (finishTimestamp - startTimestamp);
//						    		
//						    		long memory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
//						    		
//				//		    		generateSolution(project, projectName, bestSolution);
//				//		    		;
////						    		System.out.println(projectName + ";" + project.getClassCount() + ";" + df4.format(ils.getBestFitness()) + ";" + seconds + "ms;" + "MOJOFM:" + runMOJOComparison(ILS_INTERPRETATION_DIRECTORY + projectName+ ".comb", generateSolution(project, projectName, bestSolution), "-fm") + "%;" + "a1:" + a1 + ";" + "a2:" + a2 + ";" +"b1:" + b1 + ";" +"b2:"+ b2);
//						    		sb1.append(projectName + ";" + project.getClassCount() + ";" + df4.format(ils.getBestFitness()) + ";" + seconds + "ms;" + "MOJOFM:" + runMOJOComparison(ILS_INTERPRETATION_DIRECTORY + projectName+ ".comb", generateSolution(project, projectName, bestSolution), "-fm") + "%;" + "a1:" + a1 + ";" + "a2:" + a2 + ";" +"b1:" + b1 + ";" +"b2:"+ b2 + ";" + Arrays.toString(bestSolution));
//				//		    		sb1.append(padLeft(projectName, 20) + " " + padRight("" + project.getClassCount(), 10) + " " + padRight(df4.format(ils.getBestFitness()), 10) + " " + padRight("" + seconds, 10) + " ms " + padRight("" + memory, 10) + " MB" + " MOJOFM " + padRight("" + runMOJOComparison(generateSolution(project, projectName, bestSolution), PKG_BASE_DIRECTORY + projectName + ".comb", "-fm") , 10) + "%");
//				//		    		sb1.append(padLeft(projectName, 20) + " " + padRight("" + project.getClassCount(), 10) + " " + padRight(df4.format(ils.getBestFitness()), 10) + " " + padRight("" + seconds, 10) + " ms " + padRight("" + memory, 10) + " MB" + " MOJOFM " + padRight("" + runMOJOComparison(ILS_INTERPRETATION_DIRECTORY + projectName+ ".comb", PKG_BASE_DIRECTORY + projectName + ".comb", "-fm") , 10) + "%");		    		
//				//		    		sb1.append(padLeft(projectName, 20) + " " + padRight("" + project.getClassCount(), 10) + " " + padRight(df4.format(ils.getBestFitnessConstructor()),10)  + " " + padRight(df4.format(ils.getBestFitness()),10) + " " + Arrays.toString(bestSolution));
//									sb1.append(System.lineSeparator());
//								b2 = b2 + 0.5;
//								}
//							b1 = b1 + 0.5;
//							}
//						a2 = a2 + 0.5;
//						}
//					a1 = a1 + 0.5;
//	        		}
//	        	}
//	        	
//	        	File file1 = new File(EXPERIMENT_DIRECTORY + "RESULTADOValidacaoMQ_"+projectName + ".comb");
//			    BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
//			    try {
//			        writer.write(sb1.toString());	    
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				   
//				} finally {
//					writer.close();
//				}
//	        }
//			
//
//		}
//		
				
		public static final void main(String[] args) throws Exception
		{			
			generateReference();
//			executeExperiment();
		}
		
		private static void executeExperiment() throws Exception
		{
			File file = new File(ODEM_BASE_DIRECTORY);
			DecimalFormat df4 = new DecimalFormat("0.0000");
			
			ProjectLoader loader = new ProjectLoader();
			
			ConstrutiveAbstract construtiveMQ = new ConstrutiveAglomerativeMQ();
			
			ConstrutiveAbstract construtiveRandom = new ConstrutiveRandom();
			
			int runTimeMax = 1;
			
	        for (String projectFile : file.list()) 
	        {
	        	String projectName = projectFile.split(".odem")[0];
	        	
	        	StringBuilder _stringBuilder = new StringBuilder();
	        	
	        	for(int runTime=0; runTime<runTimeMax; runTime++)
	        	{
//		        		        	
		    		long startTimestamp = System.currentTimeMillis();
		        	
//		    		DependencyReader reader = new DependencyReader();		
		    		
		    		Project project = loader.loadODEMRealVersion(ODEM_BASE_DIRECTORY + projectFile);
		    		
		//    		Project project = reader.load(DEP_BASE_DIRECTORY + projectName);
		    		
		    		StringBuilder sbRefDepFile = loadDepRefFile(ILS_INTERPRETATION_DIRECTORY + projectName + ".comb");
		
		    		ILS ils = new ILS(construtiveRandom, construtiveMQ, project, projectName, sbRefDepFile, 100000, 8, 0.5);
		    		int[] bestSolution = ils.execute();
		    		
		    		long finishTimestamp = System.currentTimeMillis();
		    		long seconds = (finishTimestamp - startTimestamp);	
		    		
//		    		long memory = Runtime.getRuntime().freeMemory() / (1024 * 1024);	    		
		    		System.out.println(projectName +";"+ runTime + ";" + project.getClassCount() + ";" + df4.format(ils.getBestFitness()) + ";" + seconds + "ms;" + Arrays.toString(bestSolution ) + ";" + ils.getEvaluationsConsumed());
		    		
		    		_stringBuilder.append(projectName +";"+ runTime + ";" + project.getClassCount() + ";" + df4.format(ils.getBestFitness()) + ";" + seconds + "ms;" + Arrays.toString(bestSolution ) + ";" + ils.getEvaluationsConsumed());
		    		_stringBuilder.append(System.lineSeparator());
	        	}
	        	
				File outPutFile = new File(ILS_OUTPUT_DIRECTORY + projectName+ getStringTime() +".comb");
			    BufferedWriter writer = new BufferedWriter(new FileWriter(outPutFile));
			    try {
			        writer.write(_stringBuilder.toString());	    
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				   
				} finally {
					writer.close();
				}
	       }
		}
		
		private static void generateReference() throws Exception
		{			
			File file = new File(ODEM_BASE_DIRECTORY);
			DecimalFormat df4 = new DecimalFormat("0.0000");
			
			ProjectLoader loader = new ProjectLoader(ODEM_BASE_DIRECTORY);
			
			ConstrutiveAbstract construtiveMQ = new ConstrutiveAglomerativeMQ();
			ConstrutiveAbstract construtiveRandom = new ConstrutiveRandom();
			
			
	        for (String projectFile : file.list()) 
	        {
	        	
	        	String projectName = projectFile.split(".odem")[0]; 
	        		        	
	    		long startTimestamp = System.currentTimeMillis();
	        	
	    		Project project = loader.loadODEMRealVersion(ODEM_BASE_DIRECTORY + projectFile);
	    		
	    		StringBuilder sbRefDepFile = null;
	
	    		ILS ils = new ILS(construtiveRandom, construtiveMQ, project, projectName, sbRefDepFile, 100000, 8, 0.5);
	    		int[] bestSolution = ils.executeConstructor();
	    		long finishTimestamp = System.currentTimeMillis();
	    		long seconds = (finishTimestamp - startTimestamp);	
	    		
	    		long memory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
	    		
	    		System.out.println(projectName + ";" + project.getClassCount() + ";" + df4.format(ils.getBestFitness()) + ";" + seconds + "ms;" + Arrays.toString(bestSolution ) + ";" + ils.getEvaluationsConsumed());
	    		generateSolution(project, projectName, bestSolution);

	       }
		}

		private static String generateSolution(Project project, String projectName, int[] bestSolution) throws IOException{
					
			StringBuilder sb1 = new StringBuilder();
			for(int i=0; i<(bestSolution.length); i++){
				if( bestSolution[i]>=0) {
					sb1.append("PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
					sb1.append(System.lineSeparator());			
				}
			}

			File file = new File(ILS_INTERPRETATION_DIRECTORY + projectName+ getStringTime() +".comb");
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
			
		private static StringBuilder loadDepRefFile(String filename) throws FileNotFoundException
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

		
}