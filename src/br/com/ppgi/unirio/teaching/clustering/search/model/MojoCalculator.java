package br.com.ppgi.unirio.teaching.clustering.search.model;

import static br.com.ppgi.unirio.teaching.clustering.Common.ILS_INTERPRETATION_DIRECTORY;
import static br.com.ppgi.unirio.teaching.clustering.Common.getStringTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;
import br.com.ppgi.unirio.luiz.softwareanalysis.model.Project;
import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;

public class MojoCalculator {

	private ConstrutiveAbstract constructorMQ;
	private final ModuleDependencyGraph mdg;
	private Project project;
	private String projectName;
	private StringBuilder sbRefDepFile;

	/**
	 * Initializes the Mojo metric
	 */
	public MojoCalculator(Project project, String projectName, ModuleDependencyGraph mdg, ConstrutiveAbstract constructorMQ, StringBuilder sbRefDepFile) {
		this.project = project;
		this.mdg = mdg;
		this.constructorMQ = constructorMQ;
		this.projectName = projectName;		
		this.sbRefDepFile = sbRefDepFile;
	}
	
	/**
	 * Calcula o fitness da solução
	 * @throws IOException 
	 */
	
	public double calculateFitness(int[] functionParams) throws IOException
	{		
		int[] bestSolution = constructorMQ.createSolution(mdg, functionParams, project);
//		new ClusterMetrics(mdg, bestSolution, functionParams);
		
		
//		System.out.println (Arrays.toString(bestSolution));
				
		return MoJo.MojoFMSB( sbRefDepFile,generateSBSolution (project, projectName, bestSolution, false));
	}

	
	/**
	 * generates the solution with a stringbuilder as an output for the mojofm analyser and a .comb file if necessary
	 * @throws IOException 
	 */
	private static StringBuilder generateSBSolution(Project project, String projectName, int[] bestSolution, boolean generateOutputFile) throws IOException{
		StringBuilder sb1 = new StringBuilder();
		for(int i=0; i<(bestSolution.length); i++){
			if( bestSolution[i]>=0) {
				sb1.append("PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
				sb1.append(System.lineSeparator());			
			}
		}
		
		if (generateOutputFile) {
			File file = new File(ILS_INTERPRETATION_DIRECTORY + projectName+ getStringTime() +".comb");
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    try {
		        writer.write(sb1.toString());	    
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			   
			} finally {
				writer.close();
			}
		}			
		return sb1;
	}
}