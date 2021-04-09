package br.com.ppgi.unirio.luiz.clustering.analyser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import br.com.ppgi.unirio.luiz.clustering.model.Project;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectClass;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectPackage;
import br.com.ppgi.unirio.marlon.smc.mdg.simplifier.MDGSimplifier;
import br.com.ppgi.unirio.marlon.smc.solution.algorithm.heuristic.lns.LNSConfiguration;
import br.com.ppgi.unirio.marlon.smc.solution.algorithm.heuristic.lns.LargeNeighborhoodSearch;

public class LNSInterpreter {
	
	private MDGSimplifier mDGSimplifier;
	private Vector<LargeNeighborhoodSearch> lns;
	private Vector<Integer> cluster;
	protected String OUTPUT_FOLDER  = "data\\Experiment\\LNSInterpretation\\";// Change by Luiz

	
	public LNSInterpreter()
	{
		this.lns = new Vector<LargeNeighborhoodSearch>();
		this.cluster = new Vector<Integer>();
	}
	
	public LNSInterpreter(MDGSimplifier mDGSimplifier)
	{
		this.setmDGSimplifier(mDGSimplifier);
		this.lns = new Vector<LargeNeighborhoodSearch>();	
		
	}
	
	
	/**
	 * Adiciona um pacote na aplicação
	 */
	public LargeNeighborhoodSearch addLNS(LargeNeighborhoodSearch lnsAdd, LNSConfiguration config)
	{
		LargeNeighborhoodSearch aLns = new LargeNeighborhoodSearch(config);
		aLns = lnsAdd;
		lns.add (aLns);
		return aLns;
	}

	
	public String generate() throws IOException
	{
		if(mDGSimplifier!=null){
			cluster.setSize(mDGSimplifier.getRemovedModules().size() + lns.get(0).getBestSolutionFound().getSolution().length);
			for(int i=0; i<(mDGSimplifier.getRemovedModules().size()); i++){
				cluster.set(mDGSimplifier.getRemovedModules().get(i), -1);
			}
		}
		else {
			cluster.setSize(lns.get(0).getBestSolutionFound().getSolution().length);
		}
		
		
		int solution = 0;
		
		for(int i=0; i<(cluster.size()); i++){
			if( cluster.get(i)==null) {
				cluster.set(i, lns.get(0).getBestSolutionFound().getSolution()[solution]);
				solution++;
			}
		}
//		System.out.println(cluster.toString());
		
		Project project = new Project (lns.get(0).getClusterMetrics().getMdg().getName());
		
		for(int j=0; j<(cluster.size()); j++){
			if (project.getPackageName("PKG" + cluster.get(j)) == null) {
				project.addPackage("PKG" + cluster.get(j));
			}
			
			ProjectClass projectClass = new ProjectClass(lns.get(0).getClusterMetrics().getMdg().getModuleNames().get(j), project.getPackageName("PKG" + cluster.get(j)));
			project.addClass(projectClass);			
		}	
		        
		StringBuilder sb = new StringBuilder();
	    
	    for(ProjectPackage projectPackage: project.getPackages()) {
	    	for(ProjectClass projectClass1: project.getClasses(projectPackage)) {
    			sb.append("contain " + projectPackage.getName() + " " + projectClass1.getName());
    			sb.append(System.lineSeparator());	    		
	    	}
	    }
	    	    		    
	    File file = new File(OUTPUT_FOLDER + getStringTime() +lns.get(0).getClusterMetrics().getMdg().getName());
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    try {
	        writer.write(sb.toString());	    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   
		} finally {
			writer.close();
		}
		return file.getPath();		
	}
	
	public MDGSimplifier getmDGSimplifier() {
		return mDGSimplifier;
	}


	public void setmDGSimplifier(MDGSimplifier mDGSimplifier) {
		this.mDGSimplifier = mDGSimplifier;
	}

	public List<Integer> getCluster() {
		return cluster;
	}

	public void setCluster(Vector<Integer> cluster) {
		this.cluster = cluster;
	}
	
	private static String getStringTime() {
		Calendar data;
		data = Calendar.getInstance();
		int segundo = data.get(Calendar.SECOND);
        int minuto = data.get(Calendar.MINUTE);
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int dia = data.get(Calendar.DAY_OF_MONTH);	
        int mes = data.get(Calendar.MONTH)+1;	
        int ano = data.get(Calendar.YEAR);		
		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
	}
}