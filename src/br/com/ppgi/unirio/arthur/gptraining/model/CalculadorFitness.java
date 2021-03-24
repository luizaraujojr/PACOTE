package br.com.ppgi.unirio.arthur.gptraining.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.com.ppgi.unirio.luiz.clustering.model.Project;
import br.com.ppgi.unirio.marlon.smc.experiment.algorithm.LNSParameterTest;
import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectLoader;
import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;

public class CalculadorFitness
{
	public double pred;
	
	/*
	public double calcula(ArvoreExpressao arvore, Dataset dataset)
	{
		double distancia = 0.0;
		
		for (Dataset.Entrada entrada : dataset.getEntradas())
		{
			double yConhecido = entrada.getY();
			double yCalculado;
			try 
			{
				yCalculado = arvore.resolverExpressao(entrada.getX());
			} catch (Exception e) {
				return -1;
			}
			distancia += Math.pow(yConhecido - yCalculado, 2);
		}
		
		return Math.sqrt(distancia);
	}
	*/
	
	public double calcula (ArvoreExpressao arvore, Dataset dataset)
	{
		pred = 0.0;
		double mmre = 0.0;
		double somatorioMre = 0.0;
		int tamanhoDataset = 0;
		
		for (Dataset.Entrada entrada : dataset.getEntradas())
		{
			tamanhoDataset ++;
			double yConhecido = entrada.getY();
			double yCalculado;
			try 
			{
				yCalculado = arvore.resolverExpressao(entrada.getX());
				
			} catch (Exception e) {
				e.printStackTrace();
				return 10000;
			}
			somatorioMre += Math.abs((yConhecido - yCalculado) / yConhecido);
			
			if ((yCalculado < (yConhecido * 1.25)) && (yCalculado > (yConhecido * 0.75)))
				pred++;
		}
		
		mmre = somatorioMre / tamanhoDataset;
		pred = pred / tamanhoDataset;
		arvore.setPred(pred);
		
		if (Double.isNaN(mmre))
			return 10000;
		
		return mmre;	
	}
	
	private double runMOJOComparison(String file1, String file2, String param) throws InstanceParseException, IOException {
//		System.out.println("runMOJOComparison");
		String[] args1 = new String[3];
		args1[0] = file1;
		args1[1] = file2;
		args1[2] = param; 
		return MoJo.MojoFM(args1);
	}
	
	public double calculaMOJO (ArvoreExpressao arvore, Dataset dataset) throws Exception
	{
//		List<String> instanceFilenames = new ArrayList<String>();
//		instanceFilenames.add(dataset.getNome());
//		
//		Vector<Project> instances = new Vector<Project>();
//		instances.addAll(ProjectLoader.readJarInstances(instanceFilenames));
		
		List<String> packageClassCombinationFilenames = new ArrayList<String>();
//		packageClassCombinationFilenames = ProjectLoader.runPackageClassCombinationExport(instances,false);
		packageClassCombinationFilenames.add ("data//Experiment//PkgClsComb//jodamoney-1.0.121022021133253.comb");
		
		
		List<String> classDependencyCombinationFilenames = new ArrayList<String>();
//		classDependencyCombinationFilenames = ProjectLoader.runClassDependencyCombinationExport(instances);
		classDependencyCombinationFilenames.add("data//Experiment//ClsDepComb//jodamoney-1.0.121022021133212.comb");
		
		
		LNSParameterTest LNSP = new LNSParameterTest();
		
		File f1 = new File(classDependencyCombinationFilenames.get(0));
		File[] instanceFiles = new File[1];
		instanceFiles[0]= f1;
		
		
//		System.out.println (arvore.getExpressao());
		List<String> lnsExperimentFilenames = new ArrayList<String>();
		lnsExperimentFilenames = LNSP.runExperiment(arvore.getExpressao(),instanceFiles);
		
		
		return  runMOJOComparison(packageClassCombinationFilenames.get(0), lnsExperimentFilenames.get(0),"-fm");		
	}
}