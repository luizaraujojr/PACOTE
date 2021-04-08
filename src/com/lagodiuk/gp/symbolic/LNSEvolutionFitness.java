package com.lagodiuk.gp.symbolic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.lagodiuk.gp.symbolic.interpreter.Context;
import com.lagodiuk.gp.symbolic.interpreter.Expression;

import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;
import br.com.ppgi.unirio.marlon.smc.experiment.algorithm.LNSParameterTest;
import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;

public class LNSEvolutionFitness implements ExpressionFitness {	
	
	public LNSEvolutionFitness() {

	}
	
	@Override
	public double fitness(Expression expression, Context context) {
		double diff = 0;
		
//		List<String> instanceFilenames = new ArrayList<String>();
//		instanceFilenames.add(dataset.getNome());
//		
//		Vector<Project> instances = new Vector<Project>();
//		instances.addAll(ProjectLoader.readJarInstances(instanceFilenames));
		
		List<String> packageClassCombinationFilenames = new ArrayList<String>();
//		packageClassCombinationFilenames = ProjectLoader.runPackageClassCombinationExport(instances,false);
//		packageClassCombinationFilenames.add ("data//Experiment//PkgClsComb//jodamoney-1.0.121022021133253.comb"); //jodamoney original
		packageClassCombinationFilenames.add ("data//Experiment//PkgClsComb//jodamoney-1.0.121022021133212_LNS OPTIMIZATION.comb"); //jodamoney otimizado
		
		
		
		List<String> classDependencyCombinationFilenames = new ArrayList<String>();
//		classDependencyCombinationFilenames = ProjectLoader.runClassDependencyCombinationExport(instances);
		classDependencyCombinationFilenames.add("data//Experiment//ClsDepComb//jodamoney-1.0.121022021133212.comb"); // jodamoney dependencias entre classes, não alterado pelas otimizações
		
		
		LNSParameterTest LNSP = new LNSParameterTest();
		
		File f1 = new File(classDependencyCombinationFilenames.get(0));
		File[] instanceFiles = new File[1];
		instanceFiles[0]= f1;
		
		
//		System.out.println (arvore.getExpressao());
		List<String> lnsExperimentFilenames = new ArrayList<String>();
		try {
			lnsExperimentFilenames = LNSP.runExperiment(expression.print(),instanceFiles);
		} catch (InstanceParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {
			diff= runMOJOComparison(packageClassCombinationFilenames.get(0), lnsExperimentFilenames.get(0),"-fm");
		} catch (InstanceParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 100-diff; //coloquei o 100, pois o algoritmo somente está minimizando.
	}
	
	private double runMOJOComparison(String file1, String file2, String param) throws InstanceParseException, IOException {
//		System.out.println("runMOJOComparison");
		String[] args1 = new String[3];
		args1[0] = file1;
		args1[1] = file2;
		args1[2] = param; 
		return MoJo.MojoFM(args1);
	}
}
