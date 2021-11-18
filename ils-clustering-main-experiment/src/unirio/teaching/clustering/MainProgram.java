package unirio.teaching.clustering;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.IteratedLocalSearch;

public class MainProgram
{
//	private static String BASE_DIRECTORY = "C://Users//User//Desktop//Codigos//ils-clustering//data//clustering//odem-temp";
	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "//data//clustering//odem-temp";
	private static String ILS_INTERPRETATION_DIRECTORY = new File("").getAbsolutePath() + "//data//Experiment//ILSInterpretation//";
	private static String RESULT_DIRECTORY = new File("").getAbsolutePath() + "//data//clustering";
	
	public static final void main(String[] args) throws Exception
	{
		executeExperiment();
	}
	
	@SuppressWarnings("unused")
	private static void executeExperiment() throws Exception 
	{
		File file = new File(BASE_DIRECTORY);
		int maxCycles = 1;
		
	    for (String projectName : file.list()) 
	    {
	    	OutputStream out = new FileOutputStream (RESULT_DIRECTORY+ "//" + projectName);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
			writer.println("instance;runtime;evaluationsConsumed;bestFitness;Time;bestSolution");
	    	
	    	for(int cycleNumber = 0; cycleNumber < maxCycles; cycleNumber++)
	    	{
	        	long startTimestamp = System.currentTimeMillis();
	        	
	    		//DependencyReader reader = new DependencyReader();
	        	CDAReader reader = new CDAReader();    
	    		Project project = reader.load(BASE_DIRECTORY + "//" + projectName);
	    		StringBuilder sbRefDepFile = loadDepRefFile(ILS_INTERPRETATION_DIRECTORY + projectName + ".comb");
	
	    		IteratedLocalSearch ils = new IteratedLocalSearch(project, 100, sbRefDepFile);
	    		int[] solution = ils.executeExperiment(cycleNumber, startTimestamp, writer);

//	    		long finishTimestamp = System.currentTimeMillis();
//	    		long seconds = (finishTimestamp - startTimestamp);
//	    		System.out.println(runTime+ ";" + projectName + ";" + project.getClassCount() + ";" + Arrays.toString(solution) + ";" + ils.getBestFitness() + ";" + seconds + " ms");
	    	}

	    	writer.close();
	    }
	}
	
	protected static int countClusters(int[] solution)
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
}