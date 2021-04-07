package br.com.ppgi.unirio.marlon.smc.experiment;

import br.com.ppgi.unirio.marlon.smc.experiment.output.ResultWriter;
import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceFileWorker;
import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;
import br.com.ppgi.unirio.marlon.smc.instance.file.bunch.BunchInstanceFileWorker;
import br.com.ppgi.unirio.marlon.smc.mdg.ModuleDependencyGraph;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

//import org.junit.Test;
import random.number.generator.RandomWrapper;


/**
 * Configura√ß√µes base para execu√ß√£o dos experimentos com os algoritmos
 * @author kiko
 */
public abstract class ExperimentBase {
        
    protected static final ResultWriter.OUTPUT OUTPUT_TO = ResultWriter.OUTPUT.FILE;
    protected static final String[] INSTANCES_FOLDERS = new String[]{"_monografia_base","_all","_all_alexandre","_mitchell_examples","_unirio","perdi_para_alexandre","usadas-em-qq-ref", "_marica_fanpa","_formark","fake"};
    
//    protected String INSTANCES_FOLDER  = INSTANCES_FOLDERS[1];//8
    protected String INSTANCES_FOLDER  = "Experiment/ClsDepComb/";// Change by Luiz
    
    protected final InstanceFileWorker<ModuleDependencyGraph> INSTANCE_WORKER = new BunchInstanceFileWorker(INSTANCES_FOLDER);
    
    protected int RUN_TIMES;
    protected int BEGIN_INSTANCE;
    protected int TOTAL_INSTANCE;
    
    protected  ResultWriter out;
    
    public ExperimentBase() {
        this.BEGIN_INSTANCE = 0;
        this.TOTAL_INSTANCE = Integer.MAX_VALUE;
        this.RUN_TIMES = 1;
    }
	

    protected long beginTestTimestamp;
//    @Test
//    public void runExperiment() throws InstanceParseException, IOException{
//        beginTestTimestamp = System.currentTimeMillis();
//        File[] instances = INSTANCE_WORKER.retrieveAllInstanceFiles();//leitura das instancias
//        
//        System.out.println("INSTANCE;MQ;TEMPO");
//        for(int index=BEGIN_INSTANCE;index<instances.length && index-BEGIN_INSTANCE < TOTAL_INSTANCE;index++){//para cada instancia
//            ModuleDependencyGraph mdg = INSTANCE_WORKER.readInstanceFile(instances[index]);
//            runAlgorithm(mdg);
//            
//            afterEachInstance();
//        }
//        
//        afterAll();
//    }

    
    /**
     * MÈtodo para executar o algoritmo do experimento 
     * Criado por Luiz Antoino
     */	
//    public void runExperiment(String objectiveEquation, String projectName) throws InstanceParseException, IOException{
//        beginTestTimestamp = System.currentTimeMillis();
//        File[] instances = INSTANCE_WORKER.retrieveAllInstanceFiles();//leitura das instancias
//        
//        System.out.println("INSTANCE;MQ;TEMPO");
//        for(int index=BEGIN_INSTANCE;index<instances.length && index-BEGIN_INSTANCE < TOTAL_INSTANCE;index++){//para cada instancia
//            ModuleDependencyGraph mdg = INSTANCE_WORKER.readInstanceFile(instances[index]);
//            runAlgorithm(mdg, objectiveEquation);
//            
//            afterEachInstance();
//        }
//        
//        afterAll();
//    }
    
    
    public Vector<String> runExperiment(String objectiveEquation, File[] instances) throws InstanceParseException, IOException{
    	Vector<String> files = new Vector<String>();
        beginTestTimestamp = System.currentTimeMillis();
//        File[] instances = INSTANCE_WORKER.retrieveAllInstanceFiles();//leitura das instancias
        
//        System.out.println("INSTANCE;MQ;TEMPO");
        for(int index=BEGIN_INSTANCE;index<instances.length && index-BEGIN_INSTANCE < TOTAL_INSTANCE;index++){//para cada instancia
            ModuleDependencyGraph mdg = INSTANCE_WORKER.readInstanceFile(instances[index]);
            files.add(runAlgorithm(mdg, objectiveEquation));
            
            afterEachInstance();
        }
        
        afterAll();
		return files;
    }
    
    
    public Vector<String> runExperiment(String objectiveEquation) throws InstanceParseException, IOException{
    	Vector<String> files = new Vector<String>();
        beginTestTimestamp = System.currentTimeMillis();
        File[] instances = INSTANCE_WORKER.retrieveAllInstanceFiles();//leitura das instancias
        
        System.out.println("INSTANCE;MQ;TEMPO");
        for(int index=BEGIN_INSTANCE;index<instances.length && index-BEGIN_INSTANCE < TOTAL_INSTANCE;index++){//para cada instancia
            ModuleDependencyGraph mdg = INSTANCE_WORKER.readInstanceFile(instances[index]);
            files.add(runAlgorithm(mdg, objectiveEquation));
            
            afterEachInstance();
        }
        
        afterAll();
		return files;
    }
    
    
    
    /**
     * Executado ap√≥s o final de cada inst√¢ncia
     */
    protected void afterEachInstance(){
//        System.out.println("REINICIANDO RANDOM");
        RandomWrapper.restart();
        //coolDown(2);
    }
    
    /**
     * Execucado apos o final de cada repeti√ß√£o do processo
     */	
    protected void afterEachTime(){
        //coolDown(1);
    }
    
    protected void afterAll(){
        
    }
    
//    protected abstract int[] runAlgorithm(ModuleDependencyGraph mdg);
    
    
    /**
     * Metodo para executar o algoritmo
     * Criado por Luiz Antonio
     * @param mdg - objeto que armazena as dependÍncias dos mÛdulos
     * @param objectiveEquation - equaÁ„o que ser· utilizada como funÁ„o objetivo
     */
//    protected abstract int[] runAlgorithm(ModuleDependencyGraph mdg, String objectiveEquation);
    
    protected abstract String runAlgorithm(ModuleDependencyGraph mdg, String objectiveEquation);
    
    
    protected abstract String testName();
    
    /**
     * Deixa o aplicativo dormindo por time segundos
     * @param time - segundos que a aplicacao ficara dormindo
     */
    private void coolDown(int time){
        try{
            System.out.println("DORMINDO por: " + time + " segundos");
            Thread.sleep(time * 1000);
        }catch(Exception e){
            
        }
    }

    
       
}