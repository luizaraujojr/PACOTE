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

import javax.management.modelmbean.XMLParseException;

import unirio.teaching.clustering.model.Dependency;
import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.model.ProjectClass;
import unirio.teaching.clustering.model.ProjectPackage;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.IteratedLocalSearch;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;

public class MainProgram
{
	private static String referenceInstance_comb_MQ_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//referenceInstance_comb_MQ//";
	private static String referenceInstance_comb_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//referenceInstance_comb//";
	private static String referenceInstance_odem_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//referenceInstance_odem";
	
	private static String testInstance_comb_MQ_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//testInstance_comb_MQ//";
	private static String testInstance_comb_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//testInstance_comb//";
	private static String testInstance_odem_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//testInstance_odem";
	
	private static String pasta_teste_DIRECTORY_odem = new File("").getAbsolutePath() + "//data//Experiment//pastateste_odem";
	private static String pasta_teste_DIRECTORY_comb = new File("").getAbsolutePath() + "//data//Experiment//pastateste_comb//";

	private static String pasta_log = new File("").getAbsolutePath() + "//data//Experiment//log//";

	
	private static String RESULT_DIRECTORY = new File("").getAbsolutePath() + "//data//clustering";
	
	
	static int cycleNumber=0;
	
	public static final void main(String[] args) throws Exception
	{
    	OutputStream out = new FileOutputStream (RESULT_DIRECTORY+ "//" + "teste.csv");
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
		writer.println("cicle;instance;nclasses;solutionreal;solution;mojo;evalsconsumed;besteval;time;cluster");

		
		
//		executeMetricExtraction (referenceInstance_odem_DIRECTORY);
//
//		executeMetricExtraction (testInstance_odem_DIRECTORY);
		
		
//		boolean[] metricasUtilizadas1 = {true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false};
//		
//		executeExperiment(1, metricasUtilizadas1, testInstance_odem_DIRECTORY, testInstance_comb_DIRECTORY, writer);
		
		
//		boolean[] metricasUtilizadas2 = {true, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false};
//		
//		executeExperiment(2, metricasUtilizadas2, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
		
		
//		boolean[] metricasUtilizadas3 = {true, true, true, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false};
//		
//		executeExperiment(3, metricasUtilizadas3, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
		boolean[] metricasUtilizadas4 = {true, true, false, true, true, false, false, false, false, true, true, false, true, true, false, false, false, false};
		
		executeExperiment(4, metricasUtilizadas4, referenceInstance_odem_DIRECTORY, referenceInstance_comb_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas5 = {true, true, true, true, true, false, false, false, false, true, true, true, true, true, false, false, false, false};
//		
//		executeExperiment(5, metricasUtilizadas5,referenceInstance_odem_DIRECTORY, referenceInstance_comb_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas6 = {true, true, true, true, true, true, false, false, false, true, true, true, true, true, true, false, false, false};
//		
//		executeExperiment(6, metricasUtilizadas6, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas7 = {true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, false, false};
//		
//		executeExperiment(7, metricasUtilizadas7, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas8 = {true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, false};
//		
//		executeExperiment(8, metricasUtilizadas8, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		
//		boolean[] metricasUtilizadas9 = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
//
//		executeExperiment(9, metricasUtilizadas9, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
		
		
//		boolean[] metricasUtilizadas9 = {true, false, true, false, true, false, true, false, true, true, false, true, false, true, false, true, false, true};
//		
//		
//		executeExperiment(5, metricasUtilizadas9, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
		

//		boolean[] metricasUtilizadas51 = {true, true, true, true, true, false, false, false, false};
		
		
		
		
		//cenario para 5 metricas e 20 combinações
			
		
//		boolean[] metricasUtilizadas51 = {true, true, false, false, false, true, true, true, false, true, true, false, false, false, true, true, true, false};
//
//		executeExperiment(5, metricasUtilizadas51, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//
//		boolean[] metricasUtilizadas52 = {false, false, true, true, true, true, true, false, false, false, false, true, true, true, true, true, false, false};
//	
//		executeExperiment(5, metricasUtilizadas52, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//
//		boolean[] metricasUtilizadas53 = {true, true, false, false, false, false, true, true, true, true, true, false, false, false, false, true, true, true};
//		
//		executeExperiment(5, metricasUtilizadas53, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//
//		boolean[] metricasUtilizadas54 = {true, true, true, false, true, false, true, false, false, true, true, true, false, true, false, true, false, false};
//		
//		executeExperiment(5, metricasUtilizadas54, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//
//		boolean[] metricasUtilizadas55 = {true, false, true, false, true, false, true, false, true, true, false, true, false, true, false, true, false, true};
//		
//		executeExperiment(5, metricasUtilizadas55, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//
//		boolean[] metricasUtilizadas56 = {false, true, true, false, false, true, true, true, false, false, true, true, false, false, true, true, true, false};
//		
//		executeExperiment(5, metricasUtilizadas56, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas57 = {true, false, true, false, true, false, true, true, false, true, false, true, false, true, false, true, true, false};
//		
//		executeExperiment(5, metricasUtilizadas57, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//
//		boolean[] metricasUtilizadas58 = {false, true, true, false, true, false, true, true, false, false, true, true, false, true, false, true, true, false};
//		
//		executeExperiment(5, metricasUtilizadas58, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas59 = {false, true, false, true, true, false, true, true, false, false, true, false, true, true, false, true, true, false};
//		
//		executeExperiment(5, metricasUtilizadas59, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas510 = {false, true, false, true, false, true, true, true, false, false, true, false, true, false, true, true, true, false};
//		
//		executeExperiment(5, metricasUtilizadas510, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas511 = {false, true, false, true, false, true, true, false, true, false, true, false, true, false, true, true, false, true};
//
//		executeExperiment(5, metricasUtilizadas511, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas512 = {false, false, false, true, false, true, true, true, true, false, false, false, true, false, true, true, true, true};
//
//		executeExperiment(5, metricasUtilizadas512, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas513 = {true, false, false, false, false, true, true, true, true, true, false, false, false, false, true, true, true, true};
//
//		executeExperiment(5, metricasUtilizadas513, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas514 = {true, false, true, false, false, true, false, true, true, true, false, true, false, false, true, false, true, true};
//
//		executeExperiment(5, metricasUtilizadas514, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas515 = {false, true, true, false, false, true, false, true, true, false, true, true, false, false, true, false, true, true};
//
//		executeExperiment(5, metricasUtilizadas515, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas516 = {false, true, false, false, true, true, false, true, true, false, true, false, false, true, true, false, true, true};
//
//		executeExperiment(5, metricasUtilizadas516, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas517 = {true, true, false, false, true, true, false, false, true, true, true, false, false, true, true, false, false, true};
//
//		executeExperiment(5, metricasUtilizadas517, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas518 = {true, false, false, true, true, true, false, false, true, true, false, false, true, true, true, false, false, true};
//
//		executeExperiment(5, metricasUtilizadas518, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas519 = {true, false, false, true, false, true, false, true, true, true, false, false, true, false, true, false, true, true};
//
//		executeExperiment(5, metricasUtilizadas519, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);
//		
//		boolean[] metricasUtilizadas520 = {true, false, false, true, false, false, true, true, true, true, false, false, true, false, false, true, true, true};
//		
//		executeExperiment(5, metricasUtilizadas520, testInstance_odem_DIRECTORY, testInstance_comb_MQ_DIRECTORY, writer);		
		
		
//		executeMQReferenceGeneration();
		
		
//		executeOriginalReference(testInstance_odem_DIRECTORY, testInstance_comb_DIRECTORY);
	
//		executeOriginalReference(referenceInstance_odem_DIRECTORY, referenceInstance_comb_DIRECTORY);
}
	
	private static void executeMQReferenceGeneration() throws Exception {

		boolean[] usedMetrics = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		
		File file = new File(referenceInstance_odem_DIRECTORY);
		
	    for (String projectName : file.list()) 
	    {	
			CDAReader reader = new CDAReader();    
    		Project project = reader.load(referenceInstance_odem_DIRECTORY + "//" + projectName);
    		StringBuilder sbRefDepFile = loadDepRefFile(referenceInstance_comb_DIRECTORY + projectName + ".comb");
    		
    		
    		IteratedLocalSearch ils = new IteratedLocalSearch(project, 0, 2, usedMetrics, sbRefDepFile);
    		int[] solution = ils.executeMQReferenceGeneration(1);
    	
//    		generateSolution(project, projectName, solution, referenceInstance_comb_MQ_DIRECTORY);
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
    		
    		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    		
	    	for(cycleNumber = 0; cycleNumber < maxCycles; cycleNumber++)
	    	{
	    		int cycle = cycleNumber;
	    		executor.submit(() -> {

	    			String[] logdata = new String[5];
	    			String logfile = (pasta_log + cycle + ";" + project.getName() );
	    			
	    			File fileLog = new File(logfile);
	    			if (fileLog.exists()) {
	    				FileInputStream fis1 = new FileInputStream(fileLog);
		    			Scanner sc1 = new Scanner(fis1);
		    			
		    			logdata = sc1.nextLine().split(";");
		    			sc1.close();	
	    			}    	
	    			
//	    			OutputStream logOut = new FileOutputStream (logfile);
//	    			PrintWriter logWriter = new PrintWriter(new OutputStreamWriter(logOut));
//	    			logWriter.println("cicle;instance;nclasses;solutionreal;solution;mojo;evalsconsumed;besteval;time;cluster");
	    			
	    			
		        	long startTimestamp = System.currentTimeMillis();
		        		        	
		    		IteratedLocalSearch ils;
					try {
						ils = new IteratedLocalSearch(project, 10000, sbRefDepFile, metricsSize, usedMetrics, writer, cycle, startTimestamp, logdata, logfile);
						
						int[] solution = ils.executeExperiment(cycleNumber, startTimestamp);
		
			    		long finishTimestamp = System.currentTimeMillis();
			    		long seconds = (finishTimestamp - startTimestamp);
			    		
			    		String solutionText = ""; 
			    		for(int h = 0; h < solution.length; h++)
				    	{
			    			solutionText = solutionText  + String.valueOf((solution[h]-5.0)/2.0) + ",";
				    	}

//			    		writer 
			    		writer.println(cycle+ ";" + projectName + ";" + project.getClassCount()  + ";[" + solutionText + "];" +  Arrays.toString(solution) + ";" + ils.getBestFitness() + ";" + ils.getEvaluationsConsumed() + ";"+ ils.getIterationBestFound() + ";" + seconds + " ; " + Arrays.toString(ils.getClusterBestSolution()) + ";[" + Arrays.toString(usedMetrics) + "];");
			    		writer.flush();
//			    		System.out.println(cycle+ ";" + projectName + ";" + project.getClassCount() + ";[" + solutionText + "];" + Arrays.toString(solution) + ";" + ils.getBestFitness() + ";" + ils.getEvaluationsConsumed() + ";"+ ils.getIterationBestFound() + ";" + seconds + " ; " + Arrays.toString(ils.getClusterBestSolution()));
//			    		sc1.close();
			    		fileLog.delete();
			    		
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
	
	private static void executeMetricExtraction(String odemDir) throws Exception {
		File file = new File(odemDir);
		ModuleDependencyGraph mdg;
		
		System.out.println("projectName;intDep;extDep;intDepClas;extDepClas;extclasDep");
	    for (String projectName : file.list()) 
	    {
	    	
			CDAReader reader = new CDAReader();   
			reader.setIgnoredClasses(REAL_VERSION_EXTERNAL_DEPENDENCIES);
			
    		Project project = reader.load(odemDir + "//" + projectName);
//    		StringBuilder sbRefDepFile = loadDepRefFile(combDir + projectName + ".comb");
    		
    		int[] intDep = new int[project.getPackageCount()];
			int[] extDep = new int[project.getPackageCount()];
			
			
			
			for (ProjectClass projectClass : project.getClasses()) {
				for (Dependency dependency : projectClass.getDependencies()) {
					if (projectClass.getPackage().equals(project.getClassName(dependency.getElementName()).getPackage())) {
						intDep[project.getIndexForPackage(projectClass.getPackage())]++;
					}
					else {
						extDep[project.getIndexForPackage(projectClass.getPackage())]++;
					}
				}
			}

			int[] intDepClas = new int[project.getPackageCount()];
			int[] extDepClas = new int[project.getPackageCount()];
			int[] extclasDep = new int[project.getPackageCount()];
			
		
				
			for (ProjectClass projectClass : project.getClasses()) {
				boolean temIntDepClas = false;
				boolean temExtDepClas = false;
				boolean[] temExtClasDep = new boolean[project.getPackageCount()];
				
				for (Dependency dependency : projectClass.getDependencies()) {
					if (projectClass.getPackage().equals(project.getClassName(dependency.getElementName()).getPackage())) {
						temIntDepClas = true;
					}
					else {
						temExtDepClas = true;
						temExtClasDep[project.getIndexForPackage(project.getClassName(dependency.getElementName()).getPackage())] = true;
					}
				}
				if(temIntDepClas) intDepClas[project.getIndexForPackage(projectClass.getPackage())]++;
				if(temExtDepClas) extDepClas[project.getIndexForPackage(projectClass.getPackage())]++;
				
				for(int p = 0; p < project.getPackageCount(); p++) {
					if (temExtClasDep[p]) extclasDep[p]++;
				}
			}
		
			
			
			for(int p = 0; p < project.getPackageCount(); p++) {
				System.out.println(projectName + ";" + intDep [p] + ";" + extDep [p]+ ";" + intDepClas [p]+ ";" + extDepClas [p]+ ";" + extclasDep [p]);	
			}
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

private static String[] REAL_VERSION_EXTERNAL_DEPENDENCIES = 
{
		"apple.laf.AquaFileChooserUI",
		"bsh.classpath.ClassManagerImpl",
		"CH.ifa.draw.standard.AlignCommand$1",
		"ch.randelshofer.quaqua.QuaquaManager",
		"com.apple.eawt.Application",
		"com.apple.eawt.ApplicationAdapter",
		"com.apple.eawt.ApplicationEvent",
		"com.apple.eawt.ApplicationListener",
		"com.apple.eio.FileManager",
		"com.apple.mrj.jdirect.Linker",
		"com.apple.mrj.jdirect.MethodClosure",
		"com.apple.mrj.jdirect.MethodClosureUPP",
		"com.apple.mrj.macos.libraries.InterfaceLib",
		"com.apple.mrj.MRJAboutHandler",
		"com.apple.mrj.MRJApplicationUtils",
		"com.apple.mrj.MRJFileUtils",
		"com.apple.mrj.MRJOpenApplicationHandler",
		"com.apple.mrj.MRJOpenDocumentHandler",
		"com.apple.mrj.MRJOSType",
		"com.apple.mrj.MRJPrefsHandler",
		"com.apple.mrj.MRJPrintDocumentHandler",
		"com.apple.mrj.MRJQuitHandler",
		"com.apple.mrj.swing.MacFileChooserUI",
		"com.ibm.uvm.tools.DebugSupport",
		"com.sun.awt.AWTUtilities",
		"com.sun.image.codec.jpeg.JPEGCodec",
		"com.sun.image.codec.jpeg.JPEGDecodeParam",
		"com.sun.image.codec.jpeg.JPEGImageDecoder",
		"com.sun.tools.javac.Main",
		"edu.stanford.ejalbert.BrowserLauncher",
		"edu.umd.cs.findbugs.annotations.Nullable",
		"FirewallPlugin",
		"glguerin.io.FileForker",
		"glguerin.io.Pathname",
		"glguerin.util.MacPlatform",
		"java.awt.BorderLayout",
		"java.awt.Button",
		"java.awt.Canvas",
		"java.awt.Checkbox",
		"java.awt.Color",
		"java.awt.Component",
		"java.awt.Container",
		"java.awt.Cursor",
		"java.awt.Dialog",
		"java.awt.Dimension",
		"java.awt.event.ActionEvent",
		"java.awt.event.ActionListener",
		"java.awt.event.ItemEvent",
		"java.awt.event.ItemListener",
		"java.awt.event.KeyAdapter",
		"java.awt.event.KeyEvent",
		"java.awt.event.KeyListener",
		"java.awt.event.MouseAdapter",
		"java.awt.event.MouseEvent",
		"java.awt.event.MouseListener",
		"java.awt.event.TextEvent",
		"java.awt.event.TextListener",
		"java.awt.event.WindowAdapter",
		"java.awt.event.WindowEvent",
		"java.awt.event.WindowListener",
		"java.awt.FlowLayout",
		"java.awt.Font",
		"java.awt.Frame",
		"java.awt.Graphics",
		"java.awt.GridBagConstraints",
		"java.awt.GridBagLayout",
		"java.awt.GridLayout",
		"java.awt.Image",
		"java.awt.image.ImageObserver",
		"java.awt.image.ImageProducer",
		"java.awt.Insets",
		"java.awt.Label",
		"java.awt.LayoutManager",
		"java.awt.List",
		"java.awt.MediaTracker",
		"java.awt.Menu",
		"java.awt.MenuBar",
		"java.awt.MenuItem",
		"java.awt.Panel",
		"java.awt.Rectangle",
		"java.awt.SystemColor",
		"java.awt.TextArea",
		"java.awt.TextComponent",
		"java.awt.TextField",
		"java.awt.Toolkit",
		"java.awt.Window",
		"java.beans.BeanInfo",
		"java.beans.IntrospectionException",
		"java.beans.Introspector",
		"java.beans.PropertyChangeEvent",
		"java.beans.PropertyChangeListener",
		"java.beans.PropertyChangeSupport",
		"java.beans.PropertyDescriptor",
		"java.beans.PropertyVetoException",
		"java.io.BufferedReader",
		"java.io.BufferedWriter",
		"java.io.ByteArrayOutputStream",
		"java.io.File",
		"java.io.DataInputStream",
		"java.io.DataOutputStream",
		"java.io.InputStreamReader",
		"java.lang.InternalError",
		"javax.swing.JEditorPane",
		"javax.swing.JFileChooser",
		"javax.swing.Box",
		"javax.swing.BoxLayout",
		"javax.swing.border.EmptyBorder",
		"javax.swing.ButtonModel",
		"java.lang.Runtime",
		"java.lang.Process",
		"java.io.BufferedOutputStream",
		"java.io.FileInputStream",
		"java.io.FileNotFoundException",
		"java.io.FileOutputStream",
		"java.io.FileReader",
		"java.io.FileWriter",
		"java.io.InputStream",
		"java.io.IOException",
		"java.io.ObjectInputStream",
		"java.io.ObjectInputStream$GetField",
		"java.io.ObjectOutputStream",
		"java.io.ObjectOutputStream$PutField",
		"java.io.ObjectStreamClass",
		"java.io.ObjectStreamField",
		"java.io.OutputStream",
		"java.io.PrintStream",
		"java.io.PrintWriter",
		"java.io.Reader",
		"java.io.Serializable",
		"java.io.StringReader",
		"java.io.StringWriter",
		"java.io.Writer",
		"java.lang.annotation.Annotation",
		"java.lang.annotation.Documented",
		"java.lang.annotation.ElementType",
		"java.lang.annotation.Inherited",
		"java.lang.annotation.Retention",
		"java.lang.annotation.RetentionPolicy",
		"java.lang.annotation.Target",
		"java.lang.Appendable",
		"java.lang.AssertionError",
		"java.lang.Boolean",
		"java.lang.Byte",
		"java.lang.Character",
		"java.lang.CharSequence",
		"java.lang.Class",
		"java.lang.ClassCastException",
		"java.lang.ClassLoader",
		"java.lang.ClassNotFoundException",
		"java.lang.Deprecated",
		"java.lang.Double",
		"java.lang.Enum",
		"java.lang.Error",
		"java.lang.Exception",
		"java.lang.Float",
		"java.lang.IllegalAccessException",
		"java.lang.IllegalArgumentException",
		"java.lang.IllegalStateException",
		"java.lang.IllegalThreadStateException",
		"java.lang.InstantiationException",
		"java.lang.Integer",
		"java.lang.InterruptedException",
		"java.lang.Iterable",
		"java.lang.Long",
		"java.lang.management.ManagementFactory",
		"java.lang.management.RuntimeMXBean",
		"java.lang.management.ThreadMXBean",
		"java.lang.Math",
		"java.lang.NoClassDefFoundError",
		"java.lang.NoSuchFieldError",
		"java.lang.NoSuchMethodError",
		"java.lang.NoSuchMethodException",
		"java.lang.NullPointerException",
		"java.lang.Number",
		"java.lang.NumberFormatException",
		"java.lang.Object",
		"java.lang.reflect.Array",
		"java.lang.reflect.Constructor",
		"java.lang.reflect.Field",
		"java.lang.reflect.GenericArrayType",
		"java.lang.reflect.InvocationTargetException",
		"java.lang.reflect.Method",
		"java.lang.reflect.Modifier",
		"java.lang.reflect.ParameterizedType",
		"java.lang.reflect.Type",
		"java.lang.reflect.TypeVariable",
		"java.lang.reflect.WildcardType",
		"java.lang.Runnable",
		"java.lang.RuntimeException",
		"java.lang.SecurityException",
		"java.lang.Short",
		"java.lang.StackOverflowError",
		"java.lang.StackTraceElement",
		"java.lang.String",
		"java.lang.StringBuffer",
		"java.lang.StringBuilder",
		"java.lang.System",
		"java.lang.Thread",
		"java.lang.Thread$State",
		"java.lang.ThreadDeath",
		"java.lang.ThreadGroup",
		"java.lang.ThreadLocal",
		"java.lang.Throwable",
		"java.lang.UnsupportedOperationException",
		"java.lang.Void",
		"java.math.BigInteger",
		"java.net.Authenticator",
		"java.net.HttpURLConnection",
		"java.net.InetAddress",
		"java.net.MalformedURLException",
		"java.net.PasswordAuthentication",
		"java.net.ServerSocket",
		"java.net.Socket",
		"java.net.UnknownHostException",
		"java.net.URI",
		"java.net.URISyntaxException",
		"java.net.URL",
		"java.net.URLClassLoader",
		"java.net.URLConnection",
		"java.net.URLDecoder",
		"java.net.URLEncoder",
		"java.net.URLStreamHandler",
		"java.nio.Buffer",
		"java.nio.ByteBuffer",
		"java.nio.channels.FileChannel",
		"java.nio.CharBuffer",
		"java.nio.charset.CharacterCodingException",
		"java.nio.charset.Charset",
		"java.nio.charset.CharsetDecoder",
		"java.nio.charset.CharsetEncoder",
		"java.nio.charset.CodingErrorAction",
		"java.nio.charset.IllegalCharsetNameException",
		"java.nio.charset.MalformedInputException",
		"java.nio.charset.UnsupportedCharsetException",
		"java.nio.file.CopyOption",
		"java.nio.file.Files",
		"java.nio.file.FileSystem",
		"java.nio.file.FileSystems",
		"java.nio.file.Path",
		"java.nio.file.StandardCopyOption",
		"java.security.AccessControlException",
		"java.security.AccessController",
		"java.security.MessageDigest",
		"java.security.NoSuchAlgorithmException",
		"java.security.Permission",
		"java.security.PrivilegedAction",
		"java.text.AttributedCharacterIterator",
		"java.text.AttributedCharacterIterator$Attribute",
		"java.text.AttributedString",
		"java.text.BreakIterator",
		"java.text.CharacterIterator",
		"java.text.CollationKey",
		"java.text.Collator",
		"java.text.DateFormat",
		"java.text.DateFormat$Field",
		"java.text.DecimalFormat",
		"java.text.FieldPosition",
		"java.text.Format",
		"java.text.Format$Field",
		"java.text.MessageFormat",
		"java.text.NumberFormat",
		"java.text.ParseException",
		"java.text.RuleBasedCollator",
		"java.text.SimpleDateFormat",
		"java.util.AbstractList",
		"java.util.ArrayList",
		"java.util.Arrays",
		"java.util.Collection",
		"java.util.Collections",
		"java.util.Comparator",
		"java.util.concurrent.atomic.AtomicInteger",
		"java.util.concurrent.atomic.AtomicLong",
		"java.util.concurrent.Callable",
		"java.util.concurrent.ConcurrentHashMap",
		"java.util.concurrent.ConcurrentLinkedQueue",
		"java.util.concurrent.ConcurrentMap",
		"java.util.concurrent.CopyOnWriteArrayList",
		"java.util.concurrent.CountDownLatch",
		"java.util.concurrent.ExecutionException",
		"java.util.concurrent.Executors",
		"java.util.concurrent.ExecutorService",
		"java.util.concurrent.Future",
		"java.util.concurrent.FutureTask",
		"java.util.concurrent.locks.Lock",
		"java.util.concurrent.locks.ReentrantLock",
		"java.util.concurrent.TimeoutException",
		"java.util.concurrent.TimeUnit",
		"java.util.Enumeration",
		"java.util.HashMap",
		"java.util.HashSet",
		"java.util.Hashtable",
		"java.util.IdentityHashMap",
		"java.util.Iterator",
		"java.util.LinkedHashMap",
		"java.util.LinkedHashSet",
		"java.util.List",
		"java.util.Map",
		"java.util.Map$Entry",
		"java.util.Properties",
		"java.util.Random",
		"java.util.regex.Matcher",
		"java.util.regex.Pattern",
		"java.util.Set",
		"java.util.StringTokenizer",
		"java.util.Vector",
		"java.util.zip.ZipEntry",
		"java.util.zip.ZipFile",
		"javax.accessibility.Accessible",
		"javax.accessibility.AccessibleContext",
		"javax.annotation.concurrent.GuardedBy",
		"javax.annotation.concurrent.ThreadSafe",
		"javax.annotation.Nonnull",
		"javax.annotation.Nullable",
		"javax.imageio.ImageIO",
		"javax.jnlp.ServiceManager",
		"javax.print.attribute.Attribute",
		"javax.print.attribute.AttributeSet",
		"javax.print.attribute.DocAttribute",
		"javax.print.attribute.DocAttributeSet",
		"javax.print.attribute.HashAttributeSet",
		"javax.print.attribute.HashDocAttributeSet",
		"javax.print.attribute.HashPrintRequestAttributeSet",
		"javax.print.attribute.IntegerSyntax",
		"javax.print.attribute.PrintJobAttribute",
		"javax.print.attribute.PrintRequestAttribute",
		"javax.print.attribute.PrintRequestAttributeSet",
		"javax.print.attribute.Size2DSyntax",
		"javax.print.attribute.standard.Chromaticity",
		"javax.print.attribute.standard.Copies",
		"javax.print.attribute.standard.Destination",
		"javax.print.attribute.standard.Finishings",
		"javax.print.attribute.standard.JobHoldUntil",
		"javax.print.attribute.standard.JobName",
		"javax.print.attribute.standard.JobPriority",
		"javax.print.attribute.standard.Media",
		"javax.print.attribute.standard.MediaPrintableArea",
		"javax.print.attribute.standard.MediaSize",
		"javax.print.attribute.standard.MediaSizeName",
		"javax.print.attribute.standard.MediaTray",
		"javax.print.attribute.standard.NumberUp",
		"javax.print.attribute.standard.OrientationRequested",
		"javax.print.attribute.standard.PageRanges",
		"javax.print.attribute.standard.PresentationDirection",
		"javax.print.attribute.standard.PrinterResolution",
		"javax.print.attribute.standard.PrintQuality",
		"javax.print.attribute.standard.SheetCollate",
		"javax.print.attribute.standard.Sides",
		"javax.print.Doc",
		"javax.print.DocFlavor",
		"javax.print.DocFlavor$SERVICE_FORMATTED",
		"javax.print.DocPrintJob",
		"javax.print.event.PrintJobAdapter",
		"javax.print.event.PrintJobEvent",
		"javax.print.event.PrintJobListener",
		"javax.print.PrintException",
		"javax.print.PrintService",
		"javax.print.PrintServiceLookup",
		"javax.print.SimpleDoc",
		"javax.print.StreamPrintService",
		"javax.print.StreamPrintServiceFactory",
		"javax.swing.AbstractButton",
		"javax.swing.AbstractListModel",
		"javax.swing.border.BevelBorder",
		"javax.swing.border.Border",
		"javax.swing.BorderFactory",
		"javax.swing.ComboBoxEditor",
		"javax.swing.DefaultListCellRenderer",
		"javax.swing.DefaultListModel",
		"javax.swing.event.ChangeEvent",
		"javax.swing.event.ChangeListener",
		"javax.swing.event.DocumentEvent",
		"javax.swing.event.DocumentListener",
		"javax.swing.event.ListSelectionEvent",
		"javax.swing.event.ListSelectionListener",
		"javax.swing.event.TreeModelEvent",
		"javax.swing.event.TreeModelListener",
		"javax.swing.event.TreeSelectionEvent",
		"javax.swing.event.TreeSelectionListener",
		"javax.swing.Icon",
		"javax.swing.ImageIcon",
		"javax.swing.JButton",
		"javax.swing.JCheckBox",
		"javax.swing.JComboBox",
		"javax.swing.JComponent",
		"javax.swing.JDialog",
		"javax.swing.JFrame",
		"javax.swing.JLabel",
		"javax.swing.JList",
		"javax.swing.JMenu",
		"javax.swing.JMenuBar",
		"javax.swing.JMenuItem",
		"javax.swing.JOptionPane",
		"javax.swing.JPanel",
		"javax.swing.JProgressBar",
		"javax.swing.JRootPane",
		"javax.swing.JScrollPane",
		"javax.swing.JSeparator",
		"javax.swing.JSplitPane",
		"javax.swing.JTabbedPane",
		"javax.swing.JTextArea",
		"javax.swing.JTextField",
		"javax.swing.JTree",
		"javax.swing.ListCellRenderer",
		"javax.swing.ListModel",
		"javax.swing.ListSelectionModel",
		"javax.swing.ScrollPaneConstants",
		"javax.swing.SwingConstants",
		"javax.swing.SwingUtilities",
		"javax.swing.text.JTextComponent",
		"javax.swing.ToolTipManager",
		"javax.swing.tree.DefaultTreeCellRenderer",
		"javax.swing.tree.TreeCellRenderer",
		"javax.swing.tree.TreeModel",
		"javax.swing.tree.TreePath",
		"javax.swing.UIManager",
		"net.roydesign.app.AboutJMenuItem",
		"net.roydesign.app.Application",
		"net.roydesign.app.QuitJMenuItem",
		"net.roydesign.event.ApplicationEvent",
		"netscape.javascript.JSObject",
		"org.apache.batik.dom.GenericDOMImplementation",
		"org.apache.batik.svggen.SVGGraphics2D",
		"org.hamcrest.BaseMatcher",
		"org.hamcrest.core.CombinableMatcher",
		"org.hamcrest.core.CombinableMatcher$CombinableBothMatcher",
		"org.hamcrest.core.CombinableMatcher$CombinableEitherMatcher",
		"org.hamcrest.CoreMatchers",
		"org.hamcrest.Description",
		"org.hamcrest.Factory",
		"org.hamcrest.Matcher",
		"org.hamcrest.MatcherAssert",
		"org.hamcrest.SelfDescribing",
		"org.hamcrest.StringDescription",
		"org.hamcrest.TypeSafeMatcher",
		"org.jdesktop.layout.Baseline",
		"org.jdesktop.layout.LayoutStyle",
		"org.w3c.dom.Document",
		"org.w3c.dom.DocumentType",
		"org.w3c.dom.DOMImplementation",
		"org.xml.sax.Attributes",
		"org.xml.sax.ContentHandler",
		"org.xml.sax.DTDHandler",
		"org.xml.sax.EntityResolver",
		"org.xml.sax.ErrorHandler",
		"org.xml.sax.helpers.DefaultHandler",
		"org.xml.sax.helpers.XMLReaderFactory",
		"org.xml.sax.InputSource",
		"org.xml.sax.SAXException",
		"org.xml.sax.SAXParseException",
		"org.xml.sax.XMLReader",
		"quicktime.app.view.GraphicsImporterDrawer",
		"quicktime.app.view.QTImageProducer",
		"quicktime.QTSession",
		"quicktime.std.image.GraphicsImporter",
		"quicktime.util.QTHandle",
		"quicktime.util.QTHandleRef",
		"quicktime.util.QTUtils",
		"sun.awt.CausedFocusEvent",
		"sun.awt.CausedFocusEvent$Cause",
		"sun.security.util.SecurityConstants",
		"sun.tools.javac.Main",
		"junit.ui.TestRunner$13"

	};

}