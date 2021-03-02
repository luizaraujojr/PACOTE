package br.unirio.dashboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.management.modelmbean.XMLParseException;

import br.unirio.analyzer.Dependencies;
import br.unirio.analyzer.ProjectCharacteristics;
import br.unirio.analyzer.VersionYearLog;
import br.unirio.calculator.ClusteringCalculator;
import br.unirio.loader.ProjectLoader;
import br.unirio.model.Project;

public class MainDashboard {
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
		generateLogListFile("junit");
		generateLogListFile("jedit");
		generateLogListFile("jhotdraw");
////		
////		/**
////		 * generate a list of versions by commit, including the developer, the number of classes and packages
////		 */
		extractRevisionsByVersion("junit");
		extractRevisionsByVersion("jedit");
		extractRevisionsByVersion("jhotdraw");
////		
////		
////		/**
////		 * generate a list of years by commit, including the developer, the number of classes and packages
////		 */
		extractRevisionsByYear("junit");
		extractRevisionsByYear("jedit");
		extractRevisionsByYear("jhotdraw");
		
//		
//		/**
//		 * generate a list of years by commit, including the developer, the number of classes and packages
//		 */
		extractODEMProjectCharacteristics();
//		
//		/**
//		 * generate a list of years by commit, including the developer, the number of classes and packages
//		 */
		extractJARProjectCharacteristics();
////			
////		/**
////		 * generate a list of years by commit, including the developer, the number of classes and packages
////		 */
		extractODEMPackageCharacteristics();
		
		
		
//		Vector<Project> projectInstances = new Vector<Project>();
//		projectInstances = ProjectLoader.runProjectsReading();
//		runProjectClassCombinationExport(projectInstances, false);
//		executeMOJO("results\\mojo\\jhotdraw-5.2.0-14022001-originalCombination-21072020004912", "results\\mojo\\jhotdraw-5.2.0-14022001-originalCombination-21072020004449", "-fm");
//		executeMOJO("results\\mojo\\jhotdraw-5.2.0-14022001-originalCombination-21072020004912", "results\\mojo\\jhotdraw-7.6.0-09012011-originalCombination-21072020004912", "-fm");
//		executeMOJO("results\\mojo\\jhotdraw-5.2.0-14022001-originalCombination-21072020004912.rsf", "results\\mojo\\jhotdraw-5.3.0-21012002-originalCombination-21072020004912.rsf", "-fm");
		
//		executeMOJO("results\\mojo\\distra2.rsf", "results\\mojo\\distra2.rsf", "-fm");
//		distra1.rsf
//		distra2.rsf
		
	}
	
	
	private static void extractODEMPackageCharacteristics() throws Exception {
		PrintStream ps =null;
//		FileOutputStream out = null;
		try{
//			out = new FileOutputStream("results\\ODEMPackageCharacteristics.data");
			ps = new PrintStream(new FileOutputStream("results\\ODEMPackageCharacteristics.data"));
		
			ps.println("version\tpackage\tcbo\taff\teff\tlcom\tmf\tcs");
			
			ProjectLoader loader = new ProjectLoader(ODEM_DIRECTORY);
			
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
		}
		catch (Exception e) {
			
		}
		
		finally {
			if(!Objects.isNull(ps)) {
				ps.close();	
			}	
		}
		
		System.out.println("extractODEMPackageCharacteristics Finished!");		
	}



	private static void extractJARProjectCharacteristics() throws IOException, ClassNotFoundException {
		PrintStream ps =null;
		try{		
			FileOutputStream out = new FileOutputStream("results\\JARProjectCharacteristics.data"); 
			ps = new PrintStream(out);
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
						
//						if (fileName.endsWith(".class") && !fileName.equals("install.class") && !fileName.equals("Install.class"))
						if (fileName.endsWith(".class"))
						{
							InputStream is = jar.getInputStream(jarEntry);
							ProjectCharacteristics mainCharacteristics = new ProjectCharacteristics();
							mainCharacteristics.extractJARProjectCharacteristics(is, file.getName(), fileName, ps);
							is.close();
						}
					}
	
					jar.close();
				}			
			}
		}
		catch (Exception e) {
			
		}
		
		finally {
			if(!Objects.isNull(ps)) {
				ps.close();	
			}	
		}
		
		System.out.println("extractJARCharacteristics Finished!");	
	}



	private static void extractODEMProjectCharacteristics() throws Exception {
		PrintStream ps =null;
		try{
			FileOutputStream out = new FileOutputStream("results\\ODEMProjectCharacteristics.data");
			ps = new PrintStream(out);
			
			ps.println("versions\tpackageCount\tclassCount\telegance\tsingleClassPackages\tmaximumClassConcentration\tdependencyCount\tcbo\taff\teff\tmq\tevm\tlcom5");
			
			
	//		ProjectCharacteristics mc = new ProjectCharacteristics();
			ProjectLoader loader = new ProjectLoader(ODEM_DIRECTORY);
			
			List<File> files = new ArrayList<File>();
			
			File dir = new File(ODEM_DIRECTORY);
			
			listFilesOnly(dir,files);
			
			for (File file: files) {
				if(file.isDirectory() == false && getFileExtension(file).equals("odem")) {
					Project project = loader.loadODEMRealVersion(file.getAbsolutePath());
					ProjectCharacteristics mainCharacteristics = new ProjectCharacteristics();
					mainCharacteristics.extractODEMProjectCharacteristics(file.getName(), project, ps);
	//				System.out.println(file);
				}			
			}
			
			ps.close();
			
		}
		catch (Exception e) {
			
		}
		
		finally {
			if(!Objects.isNull(ps)) {
				ps.close();	
			}	
		}
		
		System.out.println("extractCharacteristics Finished!");
	}
	
	private static void extractRevisionsByYear(String projectName) throws IOException {
		PrintStream ps = null;
		try{
			FileOutputStream out = new FileOutputStream("results\\" + projectName + "_RevisionsByYear.data");
			ps = new PrintStream(out);
			VersionYearLog mainVersionYearLog = new VersionYearLog();
			mainVersionYearLog.saveRevisionsByYear("results\\" + projectName + "_LogListFile.data", ps);
		}
		catch (Exception e) {
			
		}
		
		finally {
			if(!Objects.isNull(ps)) {
				ps.close();	
			}	
		}
		
		System.out.println("generateRevisionsByYear Finished!");
	}

	private static void extractRevisionsByVersion(String projectName) throws IOException, XMLParseException, ParseException {
		// TODO Auto-generated method stub
		PrintStream ps = null;
		try{
			FileOutputStream out = new FileOutputStream("results\\" + projectName + "_RevisionsByVersion.data");
			ps = new PrintStream(out);
			VersionYearLog mainVersionLog = new VersionYearLog();
			mainVersionLog.saveRevisionsByVersion("results\\" + projectName + "_LogListFile.data", ps, projectName, ODEM_DIRECTORY);
		}
		catch (Exception e) {
			
		}
		
		finally {
			if(!Objects.isNull(ps)) {
				ps.close();	
			}	
		}
		
		System.out.println("generateRevisionsByVersion Finished!");
	}

	private static void generateLogListFile(String projectName ) throws IOException, Throwable {
		PrintStream ps = null;
		try{
			FileOutputStream out = new FileOutputStream("results\\" + projectName + "_LogListFile.data");
			ps = new PrintStream(out);
			VersionYearLog mainVersionLog = new VersionYearLog();
			mainVersionLog.loadLogFile("data\\log\\" + projectName + "log_raw.data", ps);
		}
		catch (Exception e) {
			
		}
		
		finally {
			if(!Objects.isNull(ps)) {
				ps.close();	
			}
		}
			
			System.out.println("generateLogListFile Finished!");		
	}

	public static final void extractGraphData() throws IOException, Exception
	{
		ProjectLoader loader = new ProjectLoader(ODEM_DIRECTORY);
		
		List<File> files = new ArrayList<File>();
		
		File dir = new File(ODEM_DIRECTORY);
		
		listFilesOnly(dir,files);
		
		for (File file: files) {
			if(file.isDirectory() == false && getFileExtension(file).equals("odem")) {
				Project project = loader.loadODEMRealVersion(file.getAbsolutePath());
				Dependencies mainArchitectureDSM = new Dependencies();
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






//private static void executeMOJO(String file1, String file2, String param) throws IOException {
//	// TODO Auto-generated method stub
//	
//	
//	System.out.println("runMOJOComparison");
//	String[] args1 = new String[3];
//	args1[0] = file1;
//	args1[1] = file2;
//	args1[2] = param; 
//	MoJo.main(args1);
//		
//	System.out.println("MoJo executed!");
//	}



//	private static void runProjectClassCombinationExport(Vector<Project> projectsInstances, boolean packageName) throws IOException {		
//		for (int i = 0; i < projectsInstances.size(); i++){
//			StringBuilder sb = new StringBuilder();
//			    
//		    for(ProjectPackage projectPackage: projectsInstances.get(i).getPackages()) {
//		    	for(ProjectClass projectClass1: projectsInstances.get(i).getClasses(projectPackage)) {		    			
//					sb.append(projectPackage.getName() + " " + projectClass1.getName());
//					sb.append(System.lineSeparator());
//		    				
//		    	}
//		    }
//			
//		    File file = new File("results\\mojo\\"+ projectsInstances.get(i).getName()+ "-originalCombination-" + getStringTime());
//		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//		    try {
//		        writer.write(sb.toString());	    
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			   
//			} finally {
//				writer.close();
//			}
//		}
//	}


//	private static String getStringTime() {
//		Calendar data;
//		data = Calendar.getInstance();
//		int segundo = data.get(Calendar.SECOND);
//	    int minuto = data.get(Calendar.MINUTE);
//	    int hora = data.get(Calendar.HOUR_OF_DAY);
//	    int dia = data.get(Calendar.DAY_OF_MONTH);	
//	    int mes = data.get(Calendar.MONTH);;	
//	    int ano = data.get(Calendar.YEAR);;		
//		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
//	}
}