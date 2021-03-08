package br.com.ppgi.unirio.marlon.smc.solution.algorithm.construtive;

import br.com.ppgi.unirio.marlon.smc.mdg.ModuleDependencyGraph;

public class ConstrutiveBasicAllModuleInSameClusterSolution extends AConstrutiveSolutionBuilder{

    @Override
    public String getName() {
        return "CBAllModuleInSameClusterSolution";
    }

    /**
    * Cria uma solução onde todos os módulos estão no mesmo cluster
    * @param mdg
    * @return
    */
    @Override
    public int[] createSolution(ModuleDependencyGraph mdg, String objectiveEquation) {
        return createAllModuleInSameClusterSolution(mdg, 0, objectiveEquation);
    }

    @Override
    public int[][] createSolution(ModuleDependencyGraph mdg, int quantity, String objectiveEquation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /**
    * Cria uma solução onde todos os módulos estão no mesmo cluster
    * @param mdg
    * @return
    */
    private static int[] createAllModuleInSameClusterSolution(ModuleDependencyGraph mdg, int n, String objectiveEquation){
        int[] solution = new int[mdg.getSize()];

        for(int index=0;index<mdg.getSize();index++){
            solution[index] = n;
        }
        return solution;
    }
}
