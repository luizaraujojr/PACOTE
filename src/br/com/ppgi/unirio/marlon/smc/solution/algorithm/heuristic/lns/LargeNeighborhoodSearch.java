package br.com.ppgi.unirio.marlon.smc.solution.algorithm.heuristic.lns;

import br.com.ppgi.unirio.marlon.smc.mdg.ClusterMetrics;
import br.com.ppgi.unirio.marlon.smc.mdg.ModuleDependencyGraph;
import br.com.ppgi.unirio.marlon.smc.solution.algorithm.heuristic.sa.SimulatedAnnealingMath;
import random.number.generator.RandomWrapper;

public class LargeNeighborhoodSearch {
    
    private LNSConfiguration config;
    
    //as variaveis abaixo sero gravadas aps a execuo do algooritmo
    private ClusterMetrics clusterMetrics;
    
    private double bestCost;
    private long bestSolutionIteration;
    private long lastIteration;
    private long timeElapsed;
    private double initialSolutionCost;
    private long biggestNoImprovementGap;//maior quantidade de iteraes sem melhoria
    
    private int algorthmRestarts = 0;
    private ClusterMetrics bestSolutionFound;

    public LargeNeighborhoodSearch(LNSConfiguration config){
        this.config = config;
    }
    
    public String name(){
        return "LNS";
    }
    
  
    
    /**
     * Utiliza a solucao informada como solucao inicial e executa a busca com as configuracoes definidas
     * @param solution
     * @return 
     */
    public int[] execute(int[] solution){
        return execute(config.getMdg(), solution);
    }
    
    /**
     * Gera uma solucao utilizando o algoritmo definido no config
     * @return 
     */
    public int[] execute(){
        int[] solution = config.getInitialSolutionBuilder().createSolution(config.getMdg());
        return execute(config.getMdg(), solution);
    }
    
   
   
	
   
    
    /**
     * Executa a busca e retorna a melhor solucao encontrada
     * @param mdg
     * @param solution
     * @return 
     */
    protected int[] execute(ModuleDependencyGraph mdg, int[] solution){
        final long startTime = System.currentTimeMillis();//tempo inicial da execucao
        
//        final int n = solution.length;
        
        ClusterMetrics cm = new ClusterMetrics(mdg, solution);// Controlador da solucao - passa a solucao inicial
        double currentCost = cm.calculateMQ(); //custo da solucao atual
        this.initialSolutionCost = currentCost;//guarda o valor da soluo inicial
        //estado da melhor solucao
        int[] bestSolution = cm.cloneSolution();//best solution found
        config.setBestSolution(cm);
        double bestCost = currentCost;//best solution metric
        long bestSolutionIteration = 0; //iterao onde ocorreu a melhor soluo
        
        //Controles internos da busca
        long currentIteration = 0;// current iteration
        //long iterationsWithoutImprovement = 0; //itraµes sem melhoria       
        long biggestNoImprovementGap=0;
        long timeElapsed;
        
        int currentAlgorithm=0;
        int lastAlgorithmImprovement=0;
        long algorithmNoImprovementGap=0;
        
        config.writeIterationReport(cm, bestCost, bestSolutionIteration, currentIteration, System.currentTimeMillis() - startTime, name());
        
        //calcular temperatura inicial do algoritmo
        double temperarure = SimulatedAnnealingMath.calculateInitialTemperature(bestCost, config.getInicialTemperatureRatio(), 0.5d);
        
        
        do{
            currentIteration++;
            
            ClusterMetrics cmTemp = destroyAndRepairSolution(cm);
            if(accept(bestCost, cmTemp,temperarure)){
                cm = cmTemp;
                double readMQ = cm.calculateMQ();
                currentCost = readMQ;
            }
            
            
            if(currentCost > bestCost){
                bestSolution = cm.cloneSolution();
                bestCost = currentCost;
                bestSolutionIteration = currentIteration;
                //iterationsWithoutImprovement = 0;
                algorithmNoImprovementGap=-1;
                lastAlgorithmImprovement=currentAlgorithm;
                config.setBestSolution(cm);
                config.writeIterationReport(cm, bestCost, bestSolutionIteration, currentIteration, System.currentTimeMillis() - startTime, name());
            }else{
                //iterationsWithoutImprovement++;
            }
            
            long currentNoImprovementGap = currentIteration - bestSolutionIteration;
            algorithmNoImprovementGap++;
            if(currentNoImprovementGap > biggestNoImprovementGap){
                biggestNoImprovementGap=currentNoImprovementGap;
            }
            if(config.algorithNoImprovementLimit>0 && algorithmNoImprovementGap>=(calculateMaxNoImprovimentAlgorithmGap())){
                int auxAlgorithm = config.changeRepairMethod();
                if(auxAlgorithm < currentAlgorithm){//se nao puder reiniar, aborta.
                    if(config.mixedRestart == false){
                        break;
                    }
                    algorthmRestarts++;
                }
                currentAlgorithm=auxAlgorithm;
                if(currentAlgorithm==lastAlgorithmImprovement){//rodou por todos os algoritmos sem nenhuma melhora
                    break;
                }
                
                //verificar se pode voltar ao primeiro
                
                algorithmNoImprovementGap=0;
            }
            temperarure *= config.getCoolingRate();//diminui a temperatura
            
            timeElapsed = System.currentTimeMillis() - startTime;//tempo que a busca esta rodando
        }while(canIterate(currentIteration, timeElapsed,biggestNoImprovementGap));
        config.writeIterationReport(cm, bestCost, bestSolutionIteration, currentIteration, System.currentTimeMillis() - startTime, name());
        saveLastStatus(cm, bestCost, bestSolutionIteration, currentIteration, System.currentTimeMillis() - startTime, biggestNoImprovementGap, config.getBestSolution());
        config.restart();
        return bestSolution;
    }
    
    private int calculateMaxNoImprovimentAlgorithmGap(){
        double currentGap = algorthmRestarts==0 ? config.algorithNoImprovementLimit : algorthmRestarts * config.reductionFactor * config.algorithNoImprovementLimit;
        return (int)currentGap;
    }
    
    private void saveLastStatus(ClusterMetrics cm, double bestCost, long bestSolutionIteration, long lastIteration, long timeElapsed, long biggestNoImprovementGap, ClusterMetrics bestSolutionFound){
        this.clusterMetrics = cm;
        this.bestCost = bestCost;
        this.bestSolutionIteration = bestSolutionIteration;
        this.lastIteration = lastIteration;
        this.timeElapsed = timeElapsed;
        this.biggestNoImprovementGap = biggestNoImprovementGap;
        this.bestSolutionFound = bestSolutionFound;
    }
    
    protected boolean accept(double bestMQ, ClusterMetrics temp, double temperature){
       return
               (
               temp.calculateMQ() >= bestMQ//se for melhor
               
               ||//ou 
               (config.useSA &&
               SimulatedAnnealingMath.checkProbability(
                       bestMQ
                       , temp.calculateMQ()
                       , temperature) 
               > RandomWrapper.rando())
               );//se passar no simulated annealing;
    }
    
    /**
     * Destroi e repara a solucao utilizando o metodo configurado
     * @param cm
     * @return 
     */
    protected ClusterMetrics destroyAndRepairSolution(ClusterMetrics cm){
        ClusterMetrics cm2 = cm.clone();
        config.changeDestructionFactor();//altera o destructionfactor se estiver configurado para isso
        config.getDestroyAlgorithm().destroy(cm2);//executa o medoto
        config.getRepairAlgorithm().repair(cm2);//executa o medoto
        return cm2;
    }
    
    /**
     * Verifica se a condicao de parada foi alcancada
     * @param currentIteration
     * @param timeElapsed
     * @param noImprovementIterations
     * @return 
     */
    protected boolean canIterate(long currentIteration, long timeElapsed, long noImprovementIterations){
        return 
                (config.getIterationLimit() < 0 || config.getIterationLimit() > currentIteration)
                &&
                (config.getTimeLimit() < 0 || config.getTimeLimit() > timeElapsed)
                &&
                (config.getNoImprovementLimit()< 0 || config.getNoImprovementLimit()> noImprovementIterations)
        ;
    }

    public ClusterMetrics getClusterMetrics() {
        return clusterMetrics;
    }

    public double getBestCost() {
        return bestCost;
    }

    public long getBestSolutionIteration() {
        return bestSolutionIteration;
    }

    public long getLastIteration() {
        return lastIteration;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public LNSConfiguration getConfig() {
        return config;
    }

    public double getInitialSolutionCost() {
        return initialSolutionCost;
    }

    public long getBiggestNoImprovementGap() {
        return biggestNoImprovementGap;
    }

    public ClusterMetrics getBestSolutionFound() {
        return bestSolutionFound;
    }
}

