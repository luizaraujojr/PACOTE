package unirio.teaching.clustering.search.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.search.constructive.ConstrutiveAglomerativeMQ;
import unirio.teaching.clustering.search.mojo.MoJoCalculator;

public class EquationFitness
{
	private ModuleDependencyGraph mdg;

	private ConstrutiveAglomerativeMQ construtiveMQ;
	
	private Project project;
	
	private MoJoCalculator mojoCalculator;
	
	private StringBuilder depFile;
	
	int[] bestSolution; 
	
	private static String ILS_INTERPRETATION_DIRECTORY = new File("").getAbsolutePath() + "\\data\\Experiment\\ILSInterpretation\\";

	public EquationFitness(ModuleDependencyGraph mdg, Project project, StringBuilder sbRefDepFile)
	{
		this.mdg = mdg;
		this.project = project;
		this.construtiveMQ = new ConstrutiveAglomerativeMQ();
		this.depFile =  sbRefDepFile;
	}

	/**
	 * Calcula o MQ com base nos MFs existentes
	 */
	public double calculateFitness(int[] equationParams, boolean[] usedMetrics)
	{
		
		this.mojoCalculator = new MoJoCalculator(depFile);
		bestSolution = construtiveMQ.createSolution(mdg, equationParams, project, usedMetrics);

		return mojoCalculator.mojofmnew(project, bestSolution);
	}

	protected StringBuilder generateSolution(Project project, String projectName, int[] bestSolution) throws IOException
	{
		StringBuilder sb1 = new StringBuilder();
		
		for (int i = 0; i < bestSolution.length; i++)
		{
			if (bestSolution[i] >= 0)
			{
				sb1.append("PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
				sb1.append(System.lineSeparator());
			}
		}

		File file = new File(ILS_INTERPRETATION_DIRECTORY + projectName + getStringTime() + ".comb");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		try
		{
			writer.write(sb1.toString());
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			writer.close();
		}
		
		return sb1;
	}
	
	/**
	 * Returns the best fitness found
	 */
	public int[] getBestSolution()
	{
		return bestSolution;
	}

	public String getStringTime()
	{
		Calendar data = Calendar.getInstance();
		int segundo = data.get(Calendar.SECOND);
		int minuto = data.get(Calendar.MINUTE);
		int hora = data.get(Calendar.HOUR_OF_DAY);
		int dia = data.get(Calendar.DAY_OF_MONTH);
		int mes = data.get(Calendar.MONTH);
		int ano = data.get(Calendar.YEAR);
		return String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) + String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
	}
}