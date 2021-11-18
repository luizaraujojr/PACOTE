package unirio.teaching.clustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.reader.CDAReader;
import unirio.teaching.clustering.search.IteratedLocalSearch;
import unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import unirio.teaching.clustering.search.constructive.ConstrutiveAglomerativeMQ;

public class MainProgram
{
//	private static String BASE_DIRECTORY = "C:\\Users\\User\\Desktop\\Codigos\\ils-clustering\\data\\clustering\\odem-temp";

	private static String BASE_DIRECTORY = new File("").getAbsolutePath() + "\\data\\clustering\\odem-temp";
	private static String ILS_INTERPRETATION_DIRECTORY = new File("").getAbsolutePath() + "\\data\\Experiment\\ILSInterpretation\\";
	
	public static final void main(String[] args) throws Exception
	{
		File file = new File(BASE_DIRECTORY);
		DecimalFormat df4 = new DecimalFormat("0.0000");
		
		ConstrutiveAbstract constructor = new ConstrutiveAglomerativeMQ();
		//ConstrutiveAbstract constructor = new ConstrutiveRandom();
		int runTimeMax = 20;
		
		for(int runTime=0; runTime<runTimeMax; runTime++)
    	{
	        for (String projectName : file.list()) 
	        {
	        	long startTimestamp = System.currentTimeMillis();
	        	
	    		//DependencyReader reader = new DependencyReader();
	        	CDAReader reader = new CDAReader();
	    		Project project = reader.load(BASE_DIRECTORY + "\\" + projectName);
	
	    		IteratedLocalSearch ils = new IteratedLocalSearch(constructor, project, 100_000);
	    		int[] solution = ils.execute();
	    		
	    		long finishTimestamp = System.currentTimeMillis();
	    		long seconds = (finishTimestamp - startTimestamp);
	    		
	    		long memory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
	//    		System.out.println(padLeft(projectName, 20) + " " + padRight("" + project.getClassCount(), 10) + padRight("" + countClusters(solution), 10) + " " + padRight(df4.format(ils.getBestFitness()), 10) + " " + padRight("" + seconds, 10) + " ms " + padRight("" + memory, 10) + " MB");
	    		generateSolution(project, projectName, solution);
	    		
	    		System.out.println("" + seconds + " ms ");
	    		
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

}
