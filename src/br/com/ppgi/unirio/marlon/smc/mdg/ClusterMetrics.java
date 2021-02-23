package br.com.ppgi.unirio.marlon.smc.mdg;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * MÃ©tricas utilizadas para cÃ¡lulos e operaÃ§Ãµes na clusterizaÃ§Ã£o
 * @author kiko
 */
public class ClusterMetrics {
	
    private final ModuleDependencyGraph mdg;
    private int[] solution;

    private int totalClusters;
    private int[] totalModulesOnCluster;//total de mÃ³dulos em cada cluster
    private List<List<Integer>> modulesOnCluster;//guarda os mÃ³dulos que estÃ£o em cada cluster

    private int[] internalDependencyWeight;
    private int[] externalDependencyWeight;
    private double[] modularizationFactor;
    private Stack<Integer> availableClusters;
    private List<Integer> usedClusters;
    private String objectiveEquation;


    //usado para clonar o objeto
    private ClusterMetrics(ModuleDependencyGraph mdg){
        this.mdg = mdg;
    }
    
    public ClusterMetrics(ModuleDependencyGraph mdg, int[] solution, String objectiveEquation){
            this.mdg = mdg;
            this.solution = solution;
            this.objectiveEquation = objectiveEquation;

            totalClusters = 0;
            totalModulesOnCluster = new int[mdg.getSize()+1];
            modulesOnCluster = new ArrayList<>();


            internalDependencyWeight = new int[mdg.getSize()+1];
            externalDependencyWeight = new int[mdg.getSize()+1];
            modularizationFactor = new double[mdg.getSize()+1];


            resetAllMetrics();
    }

    /**
     * Reseta todas as mÃ©tricas e calcula o MF de todos os mÃ³dulos novamente
     */
    private void resetAllMetrics(){
            availableClusters = new Stack<>();
            usedClusters = new ArrayList<>(mdg.getSize());
            for(int i=0;i<mdg.getSize();i++){
                    availableClusters.push(mdg.getSize()-i-1);//coloca na pilha de ordem inversa
                    internalDependencyWeight[i]= 0;
                    externalDependencyWeight[i]= 0;
                    modularizationFactor[i]= 0d;
                    modulesOnCluster.add(new ArrayList<Integer>());
            }


            for(int i=0;i<solution.length;i++){//para cada mÃ³dulo
                    //controle do total de clusteres
                    int cluster = solution[i];
                    modulesOnCluster.get(cluster).add(i);
                    totalModulesOnCluster[cluster]++;
                    if(totalModulesOnCluster[cluster] == 1){
                        updateClusterCreatedInfo(cluster);
                    }

                    for(int j=i;j< mdg.getSize();j++){//para cada outro mÃ³dulo seguinte
                            updateDependencyMetrics(i,j);
                    }
            }

            resetAllMF();//calcula o MF de todos os mÃ³dulos
    }

    /**
     * Verifica se existe dependÃªncia entre os mÃ³dulos i e j. Se houver, atualiza as metricas.
     * @param i
     * @param j
     */
    private void updateDependencyMetrics(int i, int j){
        if (i>j){
            return;
        }
        int depWeight = mdg.dependencyWeight(i, j);
        if(depWeight > 0){//mÃ³duo i depende de j
            int clusteri = solution[i];
            int clusterj = solution[j];

            if(clusteri == clusterj){//mÃ³dulos estÃ£o no mesmo cluster
                internalDependencyWeight[clusteri] += depWeight;
            }else{
                externalDependencyWeight[clusteri] += depWeight;
                externalDependencyWeight[clusterj] += depWeight;
            }
        }
    }

    /**
     * Atualiza o array modularizationFactor com todos os MFs considerando as mÃ©tricas existentes
     */
    private void resetAllMF(){
            for(int i=0;i<modularizationFactor.length;i++){
                    modularizationFactor[i] = calculateClusterModularizationFactor(internalDependencyWeight[i],externalDependencyWeight[i]);
            }
    }


    /**
     * Calcula o MF de um cluster dado as suas arestas internas e externas
     * @param i
     * @param j
     * @return
     */
    private static double calculateClusterModularizationFactor(int i, int j){
            if(i == 0 ){
                    return 0;
            }else{
                    return (i)/(i+0.5*j);
            }
    }

    /**
     * Calcula o MQ com base nos MFs existentes
     * @return 
     */
//    public double calculateMQ(){
//            double mq = 0;
//            for(int auxi=0;auxi<totalClusters;auxi++){
//                    int i= convertToClusterNumber(auxi);
//                    mq += modularizationFactor[i];
//            }
//            return mq;
//    }
    
    
//    /**
//     * Define e calcula a função fitness
//     * @return 
//     */
//    public double calculateFitness(){
//            double mq = 0;
//            for(int auxi=0;auxi<totalClusters;auxi++){
//                    int i= convertToClusterNumber(auxi);
//                    mq += modularizationFactor[i];
//            }
//            return mq;
//    }


    /**
     * Retira o modulo de seu cluster atual e o coloca no toCluster
     * @param module
     * @param toCluster 
     */
    public void makeMoviment(int module, int toCluster){//TODO - validar a logica do movimento
            int fromCluster = solution[module];
            if(fromCluster == toCluster) return; //mover para o prÃ³prio cluster
            
            int[] metrics = calculateMovimentMetrics(module, toCluster);//TODO - estou aqui

             //grava o movimento no array
            solution[module] = toCluster;            
            

            //atualizar valores nos arrays
            if(fromCluster != -1){
                internalDependencyWeight[fromCluster] = metrics[0];
                externalDependencyWeight[fromCluster] = metrics[1];
                modularizationFactor[fromCluster] = calculateClusterModularizationFactor(internalDependencyWeight[fromCluster],externalDependencyWeight[fromCluster]);
                
                //acerta os totais de modulos em cada cluster
                totalModulesOnCluster[fromCluster]--;
                
                modulesOnCluster.get(fromCluster).remove((Integer)module);
                
                if(totalModulesOnCluster[fromCluster] == 0){
                    updateClusterRemovedInfo(fromCluster);
                }
            }

            if(toCluster != -1){
                internalDependencyWeight[toCluster] = metrics[2];
                externalDependencyWeight[toCluster] = metrics[3];
                modularizationFactor[toCluster] = calculateClusterModularizationFactor(internalDependencyWeight[toCluster],externalDependencyWeight[toCluster]);
                totalModulesOnCluster[toCluster]++;
                
                if(totalModulesOnCluster[toCluster] == 1){
                    updateClusterCreatedInfo(toCluster);
                }
                
                modulesOnCluster.get(toCluster).add(module);
            }
            
    }
    
    /**
     * Atualiza as estruturas de controle informando que um novo cluster foi criado
     * @param cluster 
     */
    private void updateClusterCreatedInfo(Integer cluster){
        availableClusters.remove(cluster);//TODO - garantir que e o elemento para fazer com pop()
        usedClusters.add(cluster);//marca o cluster como utilizado na posicao corrente
        totalClusters++;
    }
    
    /**
     * Atualiza as estruturas de controle informando que um cluster foi removido
     * @param cluster 
     */
    private void updateClusterRemovedInfo(Integer cluster){
        availableClusters.push(cluster);//cluster poderï¿½ ser utilizado
        usedClusters.remove(cluster);//remove o cluster da lista dos utilizados
        totalClusters--;
    }

    /**
     * Calcula a alteraÃ§Ã£o que a funÃ§Ã£o objetivo sofrerÃ¡ com o movimento
     * @param module
     * @param toCluster
     * @return 
     */
    public double calculateMovimentDelta(int module, int toCluster){
            int fromCluster = solution[module];
            if(fromCluster == toCluster) return 0d; //mover para o prÃ³prio cluster
            
            if(toCluster == -1){
                toCluster = mdg.getSize();
            }
            if(fromCluster == -1){
                fromCluster = mdg.getSize();
            }

            double MFBefore = modularizationFactor[fromCluster] + modularizationFactor[toCluster];

            int[] metrics = calculateMovimentMetrics(module, toCluster);

            double MFAfter = calculateClusterModularizationFactor( metrics[0], metrics[1]) + calculateClusterModularizationFactor(metrics[2], metrics[3]);

            return MFAfter - MFBefore;
    }

    /**
     * Gera os dados com os valores que serÃ£o alterados com o movimento
     * @param module
     * @param toCluster
     * @return 
     */
    private int[] calculateMovimentMetrics(int module, int toCluster){
            int fromCluster = solution[module];
            if(toCluster == -1){
                toCluster = mdg.getSize();
            }
            if(fromCluster == -1){
                fromCluster = mdg.getSize();
            }

            //valores do cluster de onde o mÃ³dulo vai sair
            int fromInternalDependencyWeight = internalDependencyWeight[fromCluster];
            int fromExternalDependencyWeight = externalDependencyWeight[fromCluster];
           
            //valores do cluster para onde o mÃ³dulo vai
            int toInternalDependencyWeight = internalDependencyWeight[toCluster];
            int toExternalDependencyWeight = externalDependencyWeight[toCluster];
            if(fromCluster != toCluster){
                //for (int depModule=0;depModule< mdg.getSize();depModule++){
                for(int i=0; i<mdg.moduleDependenciesCount(module);i++){
                    int depModule = mdg.moduleDependencies(module)[i];
                    /*if(depModule==module){//nÃ£o verifica o prÃ³prio mÃ³dulo
                        continue;
                    }*/
                    int depWeight = mdg.dependencyWeight(module, depModule);
                   /* if(depWeight == 0){
                        throw new RuntimeException("DEPENDENCIA ESTÃ� NA LISTA MAS NÃƒO EXISTE!");//nÃ£o existe dependencia entre o modulo module e j
                    }*/
                    int depCluster = solution[depModule];

                    if(depModule == module){//modulo dependente de si
                        fromInternalDependencyWeight -= depWeight;
                        toInternalDependencyWeight += depWeight;
                    }else if(depCluster == fromCluster){//modulos estavam no mesmo cluster -> serÃ£o separados   
                        fromInternalDependencyWeight -= depWeight; //dependencia deixarÃ¡ de ser interna na origem
                        fromExternalDependencyWeight += depWeight;//passarÃ¡ a ser externa nos dois clusteres
                        toExternalDependencyWeight += depWeight;//passarÃ¡ a ser externa nos dois clusteres
                    }else if(depCluster == toCluster){//modulos estavam separados -> ficarÃ£o no mesmo cluster
                        fromExternalDependencyWeight -= depWeight;//dependencia deixarÃ¡ de ser externa nos dois clusteres
                        toExternalDependencyWeight -= depWeight;//dependencia deixarÃ¡ de ser externa nos dois clusteres
                        toInternalDependencyWeight += depWeight;//passarÃ¡ a ser interna no cluster de destino
                    }else /*if(depCluster != fromCluster && depCluster != toCluster)*/{//dependÃªncia com mÃ³dulos que nÃ£o pertencem nem a origem nem ao destino tem que ser transferidas de um mÃ³dulo ao outro
                        fromExternalDependencyWeight -= depWeight; //cluster de origem deixa de ter a dependÃªncia externa
                        toExternalDependencyWeight += depWeight; //cluster de destino passa a ter a dependÃªncia externa
                    }
                }
            }
            
            return new int[] {fromInternalDependencyWeight, fromExternalDependencyWeight, toInternalDependencyWeight, toExternalDependencyWeight};
    }
   
    /**
     * Calcula a alteraÃ§Ã£o que a funÃ§Ã£o objetivo sofrerÃ¡ com o movimento de junÃ§Ã£o de dois clusteres
     * @param cluster1
     * @param cluster2
     * @return 
     */
    public double calculateMergeClustersDelta(int cluster1, int cluster2){
            double delta = 0 - modularizationFactor[cluster1];//esse mÃ³dulo deixarÃ¡ de existir

            int joinClusterInternalDependency = internalDependencyWeight[cluster1] + internalDependencyWeight[cluster2];
            int joinClusterExternalDependency = externalDependencyWeight[cluster1] +  externalDependencyWeight[cluster2];

            for(int i : modulesOnCluster.get(cluster1)){
                    for(int j : modulesOnCluster.get(cluster2)){
                            int dependencyEachOtherWeight = mdg.dependencyWeight(i, j);
                            joinClusterInternalDependency += dependencyEachOtherWeight;//aresta externa passou a ser interna
                            joinClusterExternalDependency -= dependencyEachOtherWeight;//aresta externa deixou de existir (no cluster1)
                            joinClusterExternalDependency -= dependencyEachOtherWeight;//aresta externa deixou de existir (no cluster2)
                    }
            }

            return delta 
                            + (calculateClusterModularizationFactor(joinClusterInternalDependency, joinClusterExternalDependency)
                            - modularizationFactor[cluster2]
                            );
    }

    
    
    
    
    /**
     * Transforma 2 clusteres em um
     * @param cluster1
     * @param cluster2 
     */
    public void makeMergeClusters(int cluster1, int cluster2){
            //para cada mÃ³dulo, verificar se ele estÃ¡ no cluster i, se estiver, move-lo para o cluster j
            int clusterNBefore = totalClusters;
            for (int module=0;module<solution.length;module++){
                    if(solution[module] == cluster1){//modulo estÃ¡ no cluster i
                            makeMoviment(module, cluster2);
                            if(totalClusters < clusterNBefore){//acabou de remover o cluster1
                                    break;
                            }
                    }
            }
    }

    
    
    

    /**
     * Cria uma copia do atual array de soluÃ£o
     * @return 
     */
    public int[] cloneSolution(){
            int[] clone = new int[solution.length];

            for(int i=0;i<solution.length;i++){
                    clone[i] = solution[i];
            }

            return clone;
    }

    /**
     * Retorna a soluÃ§Ã£o atual
     * @return 
     */
    public int[] getSolution(){
            return this.solution;
    }

    /**
     * Retorna o nÃºmero total de clusteres existentes na soluÃ§Ã£o
     * @return 
     */
    public int getTotalClusters(){
        return this.totalClusters;
    }

    /**
     * Retorna a quantidade de mÃ³dulos existente no maior cluster
     * @return 
     */
    public int getBiggestClusterSize(){
        int bigN = 0;
        for(int i=0;i<totalModulesOnCluster.length;i++){
            if(totalModulesOnCluster[i] > bigN){
                bigN = totalModulesOnCluster[i];
            }
        }
        return bigN;
    }
    
    /**
     * Retorna a quantidade de mÃ³dulos existente no menor cluster
     * @return 
     */
    public int getSmallestClusterSize(){
        int smallN = Integer.MAX_VALUE;
        for(int i=0;i<totalModulesOnCluster.length;i++){
            if(totalModulesOnCluster[i] > 0 && totalModulesOnCluster[i] < smallN){
                smallN = totalModulesOnCluster[i];
            }
        }
        return smallN;
    }
    
    /**
     * retorna o numero do menor cluster
     * @return 
     */
    public int getSmallestCluster(){
        int smallN = Integer.MAX_VALUE;
        int clusterN = -1;
        for(int i=0;i<totalModulesOnCluster.length;i++){
            if(totalModulesOnCluster[i] > 0 && totalModulesOnCluster[i] < smallN){
                smallN = totalModulesOnCluster[i];
                clusterN = i;
            }
        }
        return clusterN;
    }
    
    /**
     * Retorna a quantidade de clusteres que possuem apenas um mÃ³dulo
     * @return 
     */
    public int getIsolatedClusterCount(){
        int total = 0;
        for(int i=0;i<totalModulesOnCluster.length;i++){
            if(totalModulesOnCluster[i] == 1){//cluster isolado
                total++;
            }
        }
        return total;
    }
    
    /**
     * Verifica se um mÃ³dulo estÃ¡ sozinho em um cluster
     * @param moduleN
     * @return 
     */
    public boolean isModuleAlone(int moduleN){
        return totalModulesOnCluster[solution[moduleN]] == 1;
    }
    
    /**
     * Transforma a soluÃ§Ã£o corrente em uma String
     * @return 
     */
    public String getSolutionAsString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.solution.length;i++){
            if(i>0){
                sb.append("-");
            }
            sb.append(this.solution[i]);
        }
        return sb.toString();
    }
    
    public String getClustersStatusAsString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< totalClusters;i++){
            int clusterNumber = convertToClusterNumber(i);
            sb.append('\n');
            sb.append(clusterNumber);
            sb.append(";");
            sb.append(internalDependencyWeight[clusterNumber]);
            sb.append(";");
            sb.append(externalDependencyWeight[clusterNumber]);
        }
        return sb.toString();
    }

    /**
     * Retorna o MDG da instÃ¢ncia
     * @return 
     */
    public ModuleDependencyGraph getMdg() {
        return mdg;
    }
    
    /**
     * Retorna o cluster que estÃ¡ na posiÃ§Ã£o do array
     * @param position
     * @return 
     */
    public int convertToClusterNumber(int position){
        if(position >= mdg.getSize()){
            throw new RuntimeException("POSICAO LIDA TEM QUE SER MENOR QUE O TOTAL!");
        }
        if(position < (totalClusters)){
            return usedClusters.get(position);
        }
        return availableClusters.peek();//retorna o elemento do topo - criar um novo cluster
    }
    
    /**
     * Retorna o prÃ³ximo cluster disponÃ­vel para insersÃ£o
     * @return 
     */
    public int nextAvailableCluster(){
        if(availableClusters.size()>0){
            return availableClusters.peek();//retorna o elemento do topo - criar um novo cluster
        }else return -1;//nÃ£o hÃ¡ mais cluster disponÃ­vel
    }
    /**
     * Retorna o maior MF entre todos os clusteres
     * @return 
     */
    public double biggestClusterMF(){
        double value = Integer.MIN_VALUE;
            for(int auxi=0;auxi<totalClusters;auxi++){
                    int i= convertToClusterNumber(auxi);
                    if(modularizationFactor[i] > value){
                        value = modularizationFactor[i];
                    }
            }
            return value;
    }
    
    /**
     * Retorna o menor MF entre todos os clusteres
     * @return 
     */
    public double smallestClusterMF(){
        double value = Integer.MAX_VALUE;
            for(int auxi=0;auxi<totalClusters;auxi++){
                    int i= convertToClusterNumber(auxi);
                    if(modularizationFactor[i] < value){
                        value = modularizationFactor[i];
                    }
            }
            return value;
    }
    
    /**
     * Retorna o menor MF entre todos os clusteres
     * @return 
     */
    public int smallestClusterMFNumber(){
        double value = Integer.MAX_VALUE;
        int cluster = -1;
            for(int auxi=0;auxi<totalClusters;auxi++){
                    int i= convertToClusterNumber(auxi);
                    if(modularizationFactor[i] < value){
                        value = modularizationFactor[i];
                        cluster = i;
                    }
            }
            if(totalClusters == 0)
                return convertToClusterNumber(0);
            return cluster;
    }
    
    @Override
    public ClusterMetrics clone(){
        ClusterMetrics cm = new ClusterMetrics(mdg);
        cm.solution = this.cloneSolution();
        cm.totalClusters = this.totalClusters;
        cm.totalModulesOnCluster = new int[this.totalModulesOnCluster.length];
        System.arraycopy(this.totalModulesOnCluster, 0, cm.totalModulesOnCluster, 0, this.totalModulesOnCluster.length);
        
        cm.modulesOnCluster = new ArrayList<>(this.modulesOnCluster.size());
        for(List<Integer> list : this.modulesOnCluster){
            ArrayList<Integer> newList = new ArrayList<>(list.size());
            for(Integer element : list){
                newList.add(element);
            }
            cm.modulesOnCluster.add(newList);
        }
        cm.internalDependencyWeight = new int[this.internalDependencyWeight.length];
        System.arraycopy(this.internalDependencyWeight, 0, cm.internalDependencyWeight, 0, this.internalDependencyWeight.length);
        
        cm.externalDependencyWeight = new int[this.externalDependencyWeight.length];
        System.arraycopy(this.externalDependencyWeight, 0, cm.externalDependencyWeight, 0, this.externalDependencyWeight.length);
        
        cm.modularizationFactor = new double[this.modularizationFactor.length];
        System.arraycopy(this.modularizationFactor, 0, cm.modularizationFactor, 0, this.modularizationFactor.length);
        
        cm.availableClusters = (Stack<Integer>)this.availableClusters.clone();
    
        cm.usedClusters = new ArrayList<>(this.usedClusters.size());
        for(Integer used : this.usedClusters){
            cm.usedClusters.add(used);
        }
        
        cm.objectiveEquation = this.objectiveEquation;
        
        return cm;
    }
    
    public double readClusterMF(int cluster){
        return modularizationFactor[cluster];
    }
    
    public List<Integer> getModulesOnCluster(int cluster){
        return this.modulesOnCluster.get(cluster);
    }
    
    
    
    
   public double calculateCost(){
//      return cm1.calculateMQ();
//  	String parsedEquation = objectiveEquation;
//  	parsedEquation.replaceAll("a1", String.valueOf(cm1.getTotalClusters()));
  	
  	
      Expression expression = new ExpressionBuilder(objectiveEquation)
      	      .variables("a")
      	      .variables("b")
      	      .build()
      	      .setVariable("a", getTotalClusters())
      		  .setVariable("b", getIsolatedClusterCount());
      	 
      	    double result = expression.evaluate();
  	
  	
  	System.out.println(result);
      return result;
  }
    
}

