package unirio.teaching.clustering.search.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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
	
	int[] clusterSolution; 
	
	public EquationFitness(ModuleDependencyGraph mdg, Project project, StringBuilder sbRefDepFile)
	{
		this.mdg = mdg;
		this.project = project;
		this.construtiveMQ = new ConstrutiveAglomerativeMQ();
		this.depFile =  sbRefDepFile;
		
		this.mojoCalculator = new MoJoCalculator(depFile);
	}

	/**
	 * Calcula o MOJO entre da distribuição gerada com base nos parâmetros da equação e a distribuição de referência
	 */
	public double calculateFitness(int[] equationParams, boolean[] usedMetrics)
	{
		clusterSolution = construtiveMQ.createSolution(mdg, equationParams, project, usedMetrics);
//		System.out.println(Arrays.toString(clusterSolution));
		return mojoCalculator.mojofmnew(project, clusterSolution);
	}
	
	
	/**
	 * Calcula o MOJO entre da distribuição MQ e a distribuição de referênciao 
	 */
	public double calculateFitnessRef(int[] cluster)
	{
		this.mojoCalculator = new MoJoCalculator(depFile);
		
		return mojoCalculator.mojofmnew(project, cluster);
	}
	
	
	/**
	 * Returns the best fitness found
	 */
	public int[] getClusterSolution()
	{
		return clusterSolution;
	}
}