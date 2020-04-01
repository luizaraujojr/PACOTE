package br.unirio.odem.calc.metrics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import br.unirio.odem.calc.loader.ProjectLoader;
import br.unirio.odem.controller.ClusteringCalculator;
import br.unirio.odem.model.Project;

/**
 * Publishes metrics and characteristics for a set of projects
 * 
 * @author Marcio
 */
public class MainPackages
{
	
	private static String ODEM_DIRECTORY = new File("").getAbsolutePath() + "\\data\\odem";
	
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
		FileOutputStream out = new FileOutputStream("results\\package_metrics.data"); 
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
		System.out.println("Finished!");
	}
}