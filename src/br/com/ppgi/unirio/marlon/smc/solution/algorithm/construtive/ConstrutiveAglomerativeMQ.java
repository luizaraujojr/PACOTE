package br.com.ppgi.unirio.marlon.smc.solution.algorithm.construtive;

import br.com.ppgi.unirio.marlon.smc.mdg.ClusterMetrics;
import br.com.ppgi.unirio.marlon.smc.mdg.ModuleDependencyGraph;

public class ConstrutiveAglomerativeMQ extends AConstrutiveSolutionBuilder{
	
    public static final String NAME="CAMQ";
    
    @Override
    public String getName(){
        return NAME;
    }
    
    @Override
    public int[] createSolution(ModuleDependencyGraph mdg, String objectiveEquation){
        return createSolution(mdg,1, objectiveEquation)[0];
    }
    
    @Override
    public int[][] createSolution(ModuleDependencyGraph mdg, int quantity, String objectiveEquation) {
        int[] solution = new ConstrutiveBasicOneModulePerCluster().createSolution(mdg, objectiveEquation);
    	
        //aglomerar os clusteres iterativamente
        int[][] newSolutions = aglomerateClustering(mdg, solution,quantity, objectiveEquation);

        return newSolutions;
    }
	
    private static int[][] aglomerateClustering(ModuleDependencyGraph mdg, int[] solution, int solutionsQuantity, String objectiveEquation){
        int [][] topSolutions = new int[solutionsQuantity][solution.length];
        Double[] topSolutionsMQ = new Double[solutionsQuantity];
        
        int n = mdg.getSize();
        ClusterMetrics cm = new ClusterMetrics(mdg, solution, objectiveEquation);
        //double lastMQ = cm.calculateMQ();
		
        //solucao de entrada � a melhor. Unica conhecida
        topSolutions[0] = solution;
        topSolutionsMQ[0] = cm.calculateCost();
        
            int k=1;
            while(n-k>1){
                //selecionar elementos para a aglutina��o
                int aglutinatei = -1;
                int aglutinatej = -1;
                Double currentMaxDelta=null;


                for(int auxi=0;auxi<cm.getTotalClusters();auxi++){
                    for(int auxj=auxi+1;auxj<cm.getTotalClusters();auxj++){
                        int i=cm.convertToClusterNumber(auxi);
                        int j=cm.convertToClusterNumber(auxj);                    
                        //verificar o delta da uniao desses dois clusters
                        double currentDelta = cm.calculateMergeClustersDelta(i, j);

                        if(currentMaxDelta== null || currentDelta > currentMaxDelta){
                            aglutinatei = i;
                            aglutinatej = j;
                            currentMaxDelta = currentDelta;
                        }
                    }
                }


                //algutinar elementos
                cm.makeMergeClusters(aglutinatei,aglutinatej);

                //gravar solu��o atual na lista de melhores
                addSolutionOnTopSolutions(cm.cloneSolution(),cm.calculateCost(),topSolutions, topSolutionsMQ);

                k += 1;
            }
            return topSolutions;
	}
    
    private static void addSolutionOnTopSolutions(int[] currentSolution,double currentSolutionMQ, int [][] topSolutions, Double[] topSolutionsMQ){
        for(int i=0; i< topSolutionsMQ.length; i++){
            if(topSolutionsMQ[i] == null){//n�o h� solu��o no ponto atual. adicionar e parar
                topSolutions[i] = currentSolution;
                topSolutionsMQ[i] = currentSolutionMQ;
                break;
            }else if(topSolutionsMQ[i] < currentSolutionMQ){
                int[] auxSolution = topSolutions[i];
                double auxSolutionMQ = topSolutionsMQ[i];
                
                topSolutions[i] = currentSolution;
                topSolutionsMQ[i] = currentSolutionMQ;
                
                currentSolution = auxSolution;
                currentSolutionMQ = auxSolutionMQ;
            }
        }
    }
}
