package unirio.teaching.clustering;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.IteratedLocalSearch;
import unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import unirio.teaching.clustering.search.constructive.ConstrutiveRandom;

public class MainProgram
{
//	private static String BASE_DIRECTORY = "C://Users//User//Desktop//Codigos//ils-clustering//data//clustering//odem-temp";
	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "//data//clustering//odem-temp";
	private static String ILS_INTERPRETATION_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//ILSInterpretation//";
	
	public static final void main(String[] args) throws Exception
	{
		executeExperiment();
	}
	
	private static void executeExperiment() throws Exception {		
		File file = new File(BASE_DIRECTORY);
		DecimalFormat df4 = new DecimalFormat("0.0000");
		
	//	ConstrutiveAbstract constructor = new ConstrutiveAglomerativeMQ();
		ConstrutiveAbstract constructor = new ConstrutiveRandom();
		
		int runTimeMax = 20;
		
	    for (String projectName : file.list()) 
	    {
	    	for(int runTime=0; runTime<runTimeMax; runTime++)
	    	{
	        	long startTimestamp = System.currentTimeMillis();
	        	
	        	
	        	
//	    		//DependencyReader reader = new DependencyReader();
	        	CDAReader reader = new CDAReader();
	    		Project project = reader.load(BASE_DIRECTORY + "//" + projectName);
	    		
	    		StringBuilder sbRefDepFile = loadDepRefFile(ILS_INTERPRETATION_DIRECTORY + projectName + ".comb");
	
	    		IteratedLocalSearch ils = new IteratedLocalSearch(constructor, project, sbRefDepFile, 400, sbRefDepFile);
	    		int[] solution = ils.executeExperiment(runTime, startTimestamp);
	    		
	    		long finishTimestamp = System.currentTimeMillis();
	    		long seconds = (finishTimestamp - startTimestamp);
	    		
//	    		long memory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
//	    		System.out.println(runTime+ ";" + padLeft(projectName, 20) + ";" + padRight("" + project.getClassCount(), 10) + Arrays.toString(solution) + ";" + padRight(df4.format(ils.getBestFitness()), 10) + ";" + padRight("" + seconds, 10) + " ms ");
	    	}
	    }

	}
	
	private static int countClusters(int[] solution)
	{
		List<Integer> clusters = new ArrayList<Integer>();
		
		for (int i = 0; i < solution.length; i++)
		{
			int cluster = solution[i];
			
			if (!clusters.contains(cluster))
				clusters.add(cluster);
		}
		
		return clusters.size();
	}

	public static String padLeft(String s, int length) 
	{
	    StringBuilder sb = new StringBuilder();
	    sb.append(s);
	    
	    while (sb.length() < length)
	        sb.append(' ');
	    
	    return sb.toString();
	}

	public static String padRight(String s, int length) 
	{
	    StringBuilder sb = new StringBuilder();
	    
	    while (sb.length() < length - s.length())
	        sb.append(' ');
	    
	    sb.append(s);
	    return sb.toString();
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