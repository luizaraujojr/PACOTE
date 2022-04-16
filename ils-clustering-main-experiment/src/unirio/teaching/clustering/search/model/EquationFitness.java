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
	
	int[] clusterSolution; 
	
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
		
		clusterSolution = construtiveMQ.createSolution(mdg, equationParams, project, usedMetrics);
		return mojoCalculator.mojofmnew(project, clusterSolution);
	}
	
	/**
	 * Returns the best fitness found
	 */
	public int[] getClusterSolution()
	{
		return clusterSolution;
	}
}