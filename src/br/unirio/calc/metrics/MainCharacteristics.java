package br.unirio.calc.metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.Const;
//import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import br.unirio.calc.loader.ProjectLoader;
import br.unirio.calc.controller.ClusteringCalculator;
import br.unirio.model.Project;

/**
 * Publishes metrics and characteristics for a set of projects
 * 
 * @author Marcio
 */
@SuppressWarnings("unused")
public class MainCharacteristics
{
//	private static String JAR_DIRECTORY = "..\\Users\\Marcio\\Desktop\\Resultados Pesquisa\\Resultados Clustering Apache ANT\\versoes";

	private static String JAR_DIRECTORY = new File("").getAbsolutePath() + "\\data\\jar";
	
	private static String ODEM_DIRECTORY = new File("").getAbsolutePath() + "\\data\\odem";
	
		/**
	 * Publishes information about a JAva class
	 */
    public void processClass(InputStream is, String version, String filename, PrintStream ps) throws ClassNotFoundException, IOException
	{
		ClassParser cp = new ClassParser(is, filename);
		JavaClass clazz = cp.parse();
		
		int countAttributes = clazz.getAttributes().length;
		int countMethods = clazz.getMethods().length;
		int countPublicMethods = 0;
		
		for (Method method : clazz.getMethods())
			if ((method.getAccessFlags() & Const.ACC_PUBLIC) != 0)
				countPublicMethods++;
		
		String packageName = filename.substring(0, filename.lastIndexOf("/")).replace('/', '.');
		String className = filename.substring(filename.lastIndexOf("/")+1, filename.lastIndexOf("."));
		ps.println(version + "\t" + packageName + "\t" + className + "\t" + countAttributes + "\t" + countMethods + "\t" + countPublicMethods);
	}
	
    /**
     * Transverses a JAR file to publish information about its contents
     */
	private static void transverseJarFile() throws IOException, FileNotFoundException, ClassNotFoundException
	{	
		
//		FileOutputStream out = new FileOutputStream("results\\jar_file_metrics.data"); 
//		PrintStream ps = new PrintStream(out);
//		ps.println("versions\tpackages\tclasses\tattrs\tmeths\tpmeths");
//				
//		List<File> files = new ArrayList<File>();
//		
//		File dir = new File(JAR_DIRECTORY);
//		
//		listFilesOnly(dir,files);
//		
//		for (File file: files) {
//			if(file.isDirectory() == false && getFileExtension(file).equals("jar")) {
//				
//				JarFile jar = new JarFile(file);
//				Enumeration<JarEntry> e = jar.entries();
//
//				while (e.hasMoreElements())
//				{
//					JarEntry jarEntry = e.nextElement();
//					String fileName = jarEntry.getName();
//					
//					if (fileName.endsWith(".class") && !fileName.equals("install.class") && !fileName.equals("Install.class"))
//					{
//						InputStream is = jar.getInputStream(jarEntry);
//						processClass(is, file.getName(), fileName, ps);
//						is.close();
//					}
//				}
//
//				jar.close();
//			}			
//		}
	}
	
	/**
	 * Publishes the characteristics and metrics for the coupling a given project
	 */
	private static void publishCouplingInformation(String version, Project project, PrintStream ps) throws Exception
	{
		ClusteringCalculator cc = new ClusteringCalculator(project, project.getPackageCount());
		int dependencyCount = project.getDependencyCount();
		double mq = cc.calculateModularizationQuality();
		int evm = cc.calculateEVM();
		double aff = cc.calculateAfferentCoupling();
		double eff = cc.calculateEfferentCoupling();
		double lcom5 = cc.calculateLCOM5();
		double cbo = cc.calculateCBO();
//		System.out.println(version + "; D: " + dependencyCount + "; CBO: " + cbo + "; AFF: " + aff + "; EFF: " + eff + "; MQ: " + mq + "; EVM: " + evm + "; LCOM: " + lcom5);
		ps.println(version.substring(0, version.length() -14) + "\t" + dependencyCount + "\t" + cbo + "\t" + aff + "\t" + eff + "\t" + mq + "\t" + evm + "\t" + lcom5);
	}

	/**
	 * Publishes the characteristics and metrics for the size of a given project
	 */
	private static void publishSizeInformation(String version, Project project, PrintStream ps) throws Exception
	{
		ClusteringCalculator cc = new ClusteringCalculator(project, project.getPackageCount());
		int packageCount = cc.getPackageCount();
		int singleClassPackages = cc.countSingleClassPackages();
		int maximumClassConcentration = cc.getMaximumClassCount();
		double elegance = cc.calculateClassElegance();
//		System.out.println(version + "; P: " + packageCount + "; ELG: " + elegance + "; SCP: " + singleClassPackages + "; CONC: " + maximumClassConcentration);
		ps.println(version.substring(0, version.length() -14) + "\t" + packageCount + "\t" + elegance + "\t" + singleClassPackages + "\t" + maximumClassConcentration);
	}
	
	
	/**
	 * Extract the characteristics and metrics for the size and coupling of a given project
	 */
	public void extractCharacteristics(String version, Project project, PrintStream ps) throws Exception
	{
		ClusteringCalculator cc = new ClusteringCalculator(project, project.getPackageCount());
		int dependencyCount = project.getDependencyCount();
		double mq = cc.calculateModularizationQuality();
		int evm = cc.calculateEVM();
		double aff = cc.calculateAfferentCoupling();
		double eff = cc.calculateEfferentCoupling();
		double lcom5 = cc.calculateLCOM5();
		double cbo = cc.calculateCBO();
		int packageCount = cc.getPackageCount();
		int classCount = project.getClassCount();
		int singleClassPackages = cc.countSingleClassPackages();
		int maximumClassConcentration = cc.getMaximumClassCount();
		double elegance = cc.calculateClassElegance();
		
//		System.out.println(version + "; P: " + packageCount + "; ELG: " + elegance + "; SCP: " + singleClassPackages + "; CONC: " + maximumClassConcentration + "; D: " + dependencyCount + "; CBO: " + cbo + "; AFF: " + aff + "; EFF: " + eff + "; MQ: " + mq + "; EVM: " + evm + "; LCOM: " + lcom5););
		
		ps.println(version.substring(0, version.length() -14) + "\t" + packageCount + "\t" + classCount +"\t" + elegance + "\t" + singleClassPackages + "\t" + maximumClassConcentration+ "\t" + dependencyCount + "\t" + cbo + "\t" + aff + "\t" + eff + "\t" + mq + "\t" + evm + "\t" + lcom5);
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
	 * Loads and publishes data for all projects
	 */
	public static final void main(String[] args) throws Exception
	{
		FileOutputStream out = new FileOutputStream("results\\projects_coupling_metrics.data");
//		FileOutputStream out = new FileOutputStream("results\\projects_size_metrics.data");
		PrintStream ps = new PrintStream(out);
		
		ps.println("version\tdependencyCount\tcbo\taff\teff\tmq\tevm\tlcom5"); //for coupling_metrics
//		ps.println("version\tpackageCount\telegance\tsingleClassPackages\tmaximumClassConcentration");  // for size_metrics
		
		MainCharacteristics mc = new MainCharacteristics();
		ProjectLoader loader = new ProjectLoader();
				
		
		List<File> files = new ArrayList<File>();
		
		File dir = new File(ODEM_DIRECTORY);
		
		listFilesOnly(dir,files);
		
		for (File file: files) {
			if(file.isDirectory() == false && getFileExtension(file).equals("odem")) {
				Project project = loader.loadODEMRealVersion(file.getAbsolutePath());
				publishCouplingInformation(file.getName(), project, ps);
//				System.out.println(file.getName());
//				publishSizeInformation(file.getName(), project, ps);
			}			
		}
			
		ps.close();
		System.out.println("Finished!");

		
		
		transverseJarFile();
		
//		List<Project> projectsEVM = loader.loadOptimizedVersionsEVM();
//		
//		for (Project project : projectsEVM)
//			publishSizeInformation("EMVopt", project);
//
//		List<Project> projectsMQ = loader.loadOptimizedVersionsMQ();
//
//		for (Project project : projectsMQ)
//			publishSizeInformation("MQopt", project);
//		
//		ps.close();
//		System.out.println("Finished!");
	}
}