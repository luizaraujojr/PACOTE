package br.unirio.analyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.management.modelmbean.XMLParseException;

import br.unirio.calc.architecture.MainArchitectureDSM;
import br.unirio.calc.controller.ClusteringCalculator;
import br.unirio.calc.loader.ProjectLoader;
import br.unirio.calc.metrics.MainCharacteristics;
import br.unirio.calc.revision.MainVersionLog;
import br.unirio.calc.revision.MainYearLog;
import br.unirio.model.Project;

public class MainAnalyzer {
	/**
	 * ODEM input directory
	 */
	private static String ODEM_DIRECTORY = new File("").getAbsolutePath() + "\\data\\odem";
	
	/**
	 * Graph data Output directory
	 */
	private static String GRAPH_OUTPUT_DIRECTORY= new  File("").getAbsolutePath() + "\\results\\graph\\";
	
	
	private static String JAR_DIRECTORY = new File("").getAbsolutePath() + "\\data\\jar";
	
	
	public static final void main(String[] args) throws Throwable
	{
		extractGraphData();
////		
////		/**
////		 * Interpret and generate a list of log file containing the commits.
////		 */
//		generateLogListFile("junit");
//		generateLogListFile("jedit");
//		generateLogListFile("jhotdraw");
////		
////		/**
////		 * generate a list of versions by commit, including the developer, the number of classes and packages
////		 */
//		extractRevisionsByVersion("junit");
//		extractRevisionsByVersion("jedit");
//		extractRevisionsByVersion("jhotdraw");
////		
////		
////		/**
////		 * generate a list of years by commit, including the developer, the number of classes and packages
////		 */
//		extractRevisionsByYear("junit");
//		extractRevisionsByYear("jedit");
//		extractRevisionsByYear("jhotdraw");
//		
//		
//		/**
//		 * generate a list of years by commit, including the developer, the number of classes and packages
//		 */
//		extractODEMProjectCharacteristics();
//		
//		/**
//		 * generate a list of years by commit, including the developer, the number of classes and packages
//		 */
//		extractJARProjectCharacteristics();
////		
////		/**
////		 * generate a list of years by commit, including the developer, the number of classes and packages
////		 */
//		extractODEMPackageCharacteristics();
//		
	}
	
	
	private static void extractODEMPackageCharacteristics() throws Exception {
		// TODO Auto-generated method stub
		
		FileOutputStream out = new FileOutputStream("results\\ODEMPackageCharacteristics.data"); 
		PrintStream ps = new PrintStream(out);
		ps.println("version\tpackage\tcbo\taff\teff\tlcom\tmf\tcs");
		
		ProjectLoader loader = new ProjectLoader();
		
		List<File> files = new ArrayList<File>();
		
		File dir = new File(ODEM_DIRECTORY);
		
		listFilesOnly(dir,files);
		
		for (File file: files) {
			if(file.isDirectory() == false && getFileExtension(file).equals("odem")) {
				Project project = loader.loadODEMRealVersion(file.getAbsolutePath());
				ClusteringCalculator cc = new ClusteringCalculator(project, project.getPackageCount());
				
				for (int i = 0; i < project.getPackageCount(); i++)
					ps.println(file.getName().substring(0, file.getName().length() -14) + "\t" + (project.getPackageIndex(i).getName().equals("") != true ? project.getPackageIndex(i).getName() : "NONAME") + 
								"\t" + cc.calculateCBO(i) + 
								"\t" + cc.calculateAfferentCoupling(i) + 
								"\t" + cc.calculateEfferentCoupling(i) + 
								"\t" + cc.calculateLCOM5(i) +
								"\t" + cc.calculateModularizationFactor(i) + 
								"\t" + cc.calculateClusterScore(i));	
			}	
		}		
		
		ps.close();
		System.out.println("extractODEMPackageCharacteristics Finished!");		
	}



	private static void extractJARProjectCharacteristics() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		
		FileOutputStream out = new FileOutputStream("results\\JARProjectCharacteristics.data"); 
		PrintStream ps = new PrintStream(out);
		ps.println("versions\tpackages\tclasses\tattrs\tmeths\tpmeths");
				
		List<File> files = new ArrayList<File>();
		
		File dir = new File(JAR_DIRECTORY);
		
		listFilesOnly(dir,files);
		
		for (File file: files) {
			if(file.isDirectory() == false && getFileExtension(file).equals("jar")) {
				
				JarFile jar = new JarFile(file);
				Enumeration<JarEntry> e = jar.entries();

				while (e.hasMoreElements())
				{
					JarEntry jarEntry = e.nextElement();
					String fileName = jarEntry.getName();
					
					if (fileName.endsWith(".class") && !fileName.equals("install.class") && !fileName.equals("Install.class"))
					{
						InputStream is = jar.getInputStream(jarEntry);
						MainCharacteristics mainCharacteristics = new MainCharacteristics();
						mainCharacteristics.processClass(is, file.getName(), fileName, ps);
						is.close();
					}
				}

				jar.close();
			}			
		}
		System.out.println("extractJARCharacteristics Finished!");	
	}



	private static void extractODEMProjectCharacteristics() throws Exception {
		// TODO Auto-generated method stub
		FileOutputStream out = new FileOutputStream("results\\ODEMProjectCharacteristics.data");
		PrintStream ps = new PrintStream(out);
		
		ps.println("version\tpackageCount\telegance\tsingleClassPackages\tmaximumClassConcentration\tdependencyCount\tcbo\taff\teff\tmq\tevm\tlcom5");
		
//		MainCharacteristics mc = new MainCharacteristics();
		ProjectLoader loader = new ProjectLoader();
		
		List<File> files = new ArrayList<File>();
		
		File dir = new File(ODEM_DIRECTORY);
		
		listFilesOnly(dir,files);
		
		for (File file: files) {
			if(file.isDirectory() == false && getFileExtension(file).equals("odem")) {
				Project project = loader.loadODEMRealVersion(file.getAbsolutePath());
				MainCharacteristics mainCharacteristics = new MainCharacteristics();
				mainCharacteristics.extractCharacteristics(file.getName(), project, ps);
//				System.out.println(file);
			}			
		}
		
		ps.close();
		System.out.println("extractCharacteristics Finished!");
	}
	
	private static void extractRevisionsByYear(String projectName) throws IOException {
		// TODO Auto-generated method stub
		
		FileOutputStream out = new FileOutputStream("results\\" + projectName + "_RevisionsByYear.data");
		PrintStream ps = new PrintStream(out);
		MainYearLog mainYearLog = new MainYearLog();
		mainYearLog.saveRevisionsByYear("results\\" + projectName + "_LogListFile.data", ps);
		ps.close();
		
		System.out.println("generateRevisionsByYear Finished!");
	}

	private static void extractRevisionsByVersion(String projectName) throws IOException, XMLParseException, ParseException {
		// TODO Auto-generated method stub
		FileOutputStream out = new FileOutputStream("results\\" + projectName + "_RevisionsByVersion.data");
		PrintStream ps = new PrintStream(out);
		MainVersionLog mainVersionLog = new MainVersionLog();
		mainVersionLog.saveRevisionsByVersion("results\\" + projectName + "_LogListFile.data", ps, projectName);
		ps.close();
		
		System.out.println("generateRevisionsByVersion Finished!");
	}

	private static void generateLogListFile(String projectName ) throws IOException, Throwable {
		// TODO Auto-generated method stub
		
//		MainVersionLog mainVersionLog = new MainVersionLog();
		
		FileOutputStream out = new FileOutputStream("results\\" + projectName + "_LogListFile.data");
		PrintStream ps = new PrintStream(out);
		MainVersionLog mainVersionLog = new MainVersionLog();
		mainVersionLog.loadLogFile("data\\log\\" + projectName + "log_raw.data", ps);
		ps.close();
		
		System.out.println("generateLogListFile Finished!");		
	}

	public static final void extractGraphData() throws IOException, Exception
	{
		ProjectLoader loader = new ProjectLoader();
//		MainArchitectureDSM mainArchitectureDSM = new MainArchitectureDSM();
		
		List<File> files = new ArrayList<File>();
		
		File dir = new File(ODEM_DIRECTORY);
		
		listFilesOnly(dir,files);
		
		for (File file: files) {
			if(file.isDirectory() == false && getFileExtension(file).equals("odem")) {
				Project project = loader.loadODEMRealVersion(file.getAbsolutePath());
				MainArchitectureDSM mainArchitectureDSM = new MainArchitectureDSM();
				mainArchitectureDSM.saveDependencies(project, GRAPH_OUTPUT_DIRECTORY + file.getName() + "_GraphData.data");
//				System.out.println (GRAPH_OUTPUT_DIRECTORY + file.getName() + ".graph.txt");
			}			
		}
		
		System.out.println("generateGraphData Finished!");	
	}
	
	/**
	 * Get the file extension of a file
	 * @param file under analysis
	 * @return the extension without the dot
	 */
	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".")+1);
		else return "";
	}
	
	/**
	 * List all the files from the main directory and its subdirectories
	 * @param inputFile - the main directory
	 * @param files - return a list of files
	 */
	private static void listFilesOnly(File inputFile, List<File> files) {
		File[] listfiles = inputFile.listFiles();
		for (File file: listfiles) {
			if(file.isDirectory()) {
				listFilesOnly(file, files);
			}
			else {
				files.add(file);
			}
		}
	}
}



