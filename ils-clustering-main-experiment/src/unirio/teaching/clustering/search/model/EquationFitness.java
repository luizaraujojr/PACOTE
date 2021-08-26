package unirio.teaching.clustering.search.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.mojo.MoJo;
import unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import unirio.teaching.clustering.search.constructive.ConstrutiveAglomerativeMQ;

public class EquationFitness {

	private ModuleDependencyGraph mdg;
	private int[] equationParams;
	private ConstrutiveAbstract construtiveMQ;
	private Project project;
	private StringBuilder sbRefDepFile;
	private static String ILS_INTERPRETATION_DIRECTORY = new File("").getAbsolutePath() + "\\data\\Experiment\\ILSInterpretation\\";
	

	public EquationFitness(ModuleDependencyGraph mdg, Project project, StringBuilder sbRefDepFile )
	{
		this.mdg = mdg;
//		this.equationParams = equationParams;
		this.project = project; 
		this.sbRefDepFile = sbRefDepFile;
		
		this.construtiveMQ = new ConstrutiveAglomerativeMQ();
	}

	
	/**
	 * Calcula o MQ com base nos MFs existentes
	 * @throws IOException 
	 */
	public double calculateFitness(int[] equationParams) throws IOException {
		this.equationParams = equationParams;
		
		int[] bestSolution = construtiveMQ.createSolution(mdg, equationParams);
				
		return MoJo.MojoFMSB( sbRefDepFile, generateSBSolution (project, project.getName(), bestSolution, false));

	}
	
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
