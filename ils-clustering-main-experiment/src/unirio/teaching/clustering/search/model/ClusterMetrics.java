package unirio.teaching.clustering.search.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Metricas utilizadas para calulos e operacoes na clusterizacao
 * 
 * @author kiko
 */
public class ClusterMetrics
{
	private final ModuleDependencyGraph mdg;

	private int[] solution;

	private int totalClusteres;
	
	private int[] totalModulesOnCluster;
	
	private List<List<Integer>> modulesOnCluster;

	private int[] internalDependencyWeight;
	
	private int[] externalDependencyWeight;
	
	private double[] modularizationFactor;
	
	private Stack<Integer> availableClusters;
	
	private List<Integer> usedClusters;
	
	double c1, c2, c3, c4, c5, c6, c7, c8;
	
	// The clusters a class references
	private int[][] classClusterReferences;
	
	// The number of the cluster for each class
	//private int[] classCluster;
	
	private int[] internalClassesInternalDependencies;
	
	private int[] internalClassesExternalDependencies;
	
	//public int[] classWithDepOnClusterNew;
	//public List<List<Integer>> classWithDepOnCluster;
	//public List<List<Integer>> classWithDepOutCluster;
	
	/**
	 * Inicializa o calculador de metricas
	 */
	public ClusterMetrics(ModuleDependencyGraph mdg, int[] solution, int[] equationParams)
	{
		this.mdg = mdg;
		this.solution = solution;
		
		totalClusteres = 0;
		totalModulesOnCluster = new int[mdg.getSize() + 1];
		modulesOnCluster = new ArrayList<>();
		
//		classWithDepOnClusterNew =  new int[mdg.getSize() + 1];
//		classWithDepOnCluster = new ArrayList<>();
//		classWithDepOutCluster = new ArrayList<>();

		internalDependencyWeight = new int[mdg.getSize() + 1];
		externalDependencyWeight = new int[mdg.getSize() + 1];
		modularizationFactor = new double[mdg.getSize() + 1];
		
		// The clusters a class references
		this.classClusterReferences = new int[mdg.getSize()][mdg.getSize() + 1];

		this.internalClassesInternalDependencies = new int[mdg.getSize()];
		this.internalClassesExternalDependencies = new int[mdg.getSize()];
		
		// The number of the cluster for each class
		//this.classCluster = new int[mdg.getSize()];
		
		c1 = (equationParams[0]-5.0)/2.0; 
		c2 = (equationParams[1]-5.0)/2.0;
		c3 = (equationParams[2]-5.0)/2.0;
		c4 = (equationParams[3]-5.0)/2.0;
		c5 = (equationParams[4]-5.0)/2.0;
		c6 = (equationParams[5]-5.0)/2.0;
//		c7 = (equationParams[6]-5.0)/2.0;
//		c8 = (equationParams[7]-5.0)/2.0;
			
		resetAllMetrics();
	}

	/**
	 * Reseta todas as mericas e calcula o MF de todos os modulos novamente
	 */
	private void resetAllMetrics()
	{
		availableClusters = new Stack<>();
		usedClusters = new ArrayList<>(mdg.getSize());
		
		for (int i = 0; i < mdg.getSize(); i++)
		{
			availableClusters.push(mdg.getSize() - i - 1);
			internalDependencyWeight[i] = 0;
			externalDependencyWeight[i] = 0;
//			classWithDepOnClusterNew[i] = 0; 
			
			modularizationFactor[i] = 0.0;
			modulesOnCluster.add(new ArrayList<Integer>());
//			classWithDepOnCluster.add(new ArrayList<Integer>());
//			classWithDepOutCluster.add(new ArrayList<Integer>());
			
//			for (int j = 0; j < mdg.getSize(); j++){
//				if (mdg.moduleDependencies(i)[j]!=-1) {
//					classWithDepOutCluster.get(i).add(mdg.moduleDependencies(i)[j]);				
//				}
//				else break;
//			}

//			this.classClusterReferences[i][0] = -1;
		}		

		for (int i = 0; i < solution.length; i++)
		{
			int cluster = solution[i];
			modulesOnCluster.get(cluster).add(i);
			totalModulesOnCluster[cluster]++;
//			classWithDepOnClusterNew[cluster] =0;
			
			if (totalModulesOnCluster[cluster] == 1)
			{
				updateClusterCreatedInfo(cluster);
			}

			for (int j = i; j < mdg.getSize(); j++)
			{
				updateDependencyMetrics(i, j);
			}
			
			//this.classCluster[i] = cluster;
		}

		updateClassClusterReferences();
//		System.out.println("Fim");
	}
	
	private void updateClassClusterReferences()
	{
		for (int i = 0; i < solution.length; i++)
		{
			int walker = 0;
			
			for (int j = 0; j < mdg.getSize(); j++)
			{
				int depWeight = mdg.dependencyWeight(i, j);
				
				if (depWeight > 0)
				{
					boolean found = false;
					int targetCluster = this.solution[j];
					
					for (int k = 0; k < walker; k++)
					{
						if (this.classClusterReferences[i][k] == targetCluster)
							found = true;
					}
					
					if (!found)
					{
						this.classClusterReferences[i][walker++] = targetCluster;
					}
				}
			}

			this.classClusterReferences[i][walker] = -1;
		}

		updateClusterClassCount();
	}

	private void updateClusterClassCount()
	{
		for (int cluster = 0; cluster < totalClusteres; cluster++)
		{
			int countICID = 0;
			
			int countICED = 0;
			
			for (int classCluster : modulesOnCluster.get(cluster))
			{
				if (depends(classCluster, cluster))
					countICID++;
				
				int walker = 0;
				
				while (this.classClusterReferences[classCluster][walker] != -1)
				{
					if (this.classClusterReferences[classCluster][walker] != cluster)
					{
						countICED++;
						break;
					}
					walker++;
				}
			}
			
			this.internalClassesInternalDependencies[cluster] = countICID;
			this.internalClassesExternalDependencies[cluster] = countICED;
		}
	}

	/**
	 * Verifica se existe dependencia entre os módulos i e j. Se houver, atualiza as metricas.
	 */
	private void updateDependencyMetrics(int i, int j)
	{
		if (i > j)
			return;

		int depWeight = mdg.dependencyWeight(i, j);
		
		if (depWeight > 0)
		{
			int clusteri = solution[i];
			int clusterj = solution[j];

			if (clusteri == clusterj)
			{
				internalDependencyWeight[clusteri] += depWeight;
			}
			else
			{
				externalDependencyWeight[clusteri] += depWeight;
				externalDependencyWeight[clusterj] += depWeight;
			}
		}
	}

	private double calculateFitnessClusterMergeDelta(int _internalDependencyWeightCluster1, 
			int _externalDependencyWeightCluster1, 
			int _internalDependencyWeightCluster2, 
			int _externalDependencyWeightCluster2, 
			int _joinClusterInternalDependency, 
			int _joinClusterExternalDependency,	
			int firstClusterInternalClassesWithInternalDependency,
			int secondClusterInternalClassesWithInternalDependency,
			int joinClusterInternalClassesWithInternalDependency,
			int firstClusterInternalClassesWithExternalDependency,
			int secondClusterInternalClassesWithExternalDependency,
			int joinClusterInternalClassesWithExternalDependency)
	{		
		double fitness = calculateClusterFitness(_joinClusterInternalDependency, _joinClusterExternalDependency, joinClusterInternalClassesWithInternalDependency, joinClusterInternalClassesWithExternalDependency);
		fitness -= calculateClusterFitness(_internalDependencyWeightCluster1, _externalDependencyWeightCluster1, firstClusterInternalClassesWithInternalDependency, firstClusterInternalClassesWithExternalDependency);
		fitness -= calculateClusterFitness(_internalDependencyWeightCluster2, _externalDependencyWeightCluster2, secondClusterInternalClassesWithInternalDependency, secondClusterInternalClassesWithExternalDependency);
		return fitness;
	}

	private double calculateClusterFitness(int internalDependencies, int externalDependencies, int classWithDepOnCluster, int classWithDepOutCluster)
	{
		double ra1 = c1 * internalDependencies;
		double ra2 = c2 * externalDependencies;
		double ra3 = c3 * classWithDepOnCluster;
		double fitness = ra1 + ra2 + ra3;		
		
		if (fitness != 0.0) 
		{
			double rb1 = c4 * internalDependencies;
			double rb2 = c5 * externalDependencies;
			double rb3 = c6 * classWithDepOnCluster;
			double rb = rb1 + rb2+ rb3;			
			fitness /= rb;
		}
		return fitness;
	}
	
	/**
	 * Calcula o fitness da solucao atual
	 */
	public double calculateFitness()
	{
		double fitness = 0;
		
		for (int clusterNumber = 0; clusterNumber < totalClusteres; clusterNumber++) 
		{
			int _clusterNumber = convertToClusterNumber(clusterNumber);
			fitness += calculateClusterFitness(internalDependencyWeight[_clusterNumber], externalDependencyWeight[_clusterNumber], 0 /*classWithDepOnCluster.get(_clusterNumber).size()*/, 0 /*classWithDepOutCluster.get(_clusterNumber).size()*/);
		}

		return fitness;
	}

	/**
	 * Retira o modulo de seu cluster atual e o coloca no toCluster
	 */
	public void makeMoviment(int module, int toCluster)
	{
		int fromCluster = solution[module];
	
		if (fromCluster == toCluster)
			return; 

		int[] metrics = calculateMovimentMetrics(module, toCluster);

		// grava o movimento no array
		solution[module] = toCluster;

		// atualizar valores nos arrays
		if (fromCluster != -1)
		{
			internalDependencyWeight[fromCluster] = metrics[0];
			externalDependencyWeight[fromCluster] = metrics[1];
			//modularizationFactor[fromCluster] = calculateClusterModularizationFactor(internalDependencyWeight[fromCluster], externalDependencyWeight[fromCluster]);

			// acerta os totais de modulos em cada cluster
			totalModulesOnCluster[fromCluster]--;

			modulesOnCluster.get(fromCluster).remove((Integer) module);

			if (totalModulesOnCluster[fromCluster] == 0)
			{
				updateClusterRemovedInfo(fromCluster);
			}
		}

		if (toCluster != -1)
		{
			internalDependencyWeight[toCluster] = metrics[2];
			externalDependencyWeight[toCluster] = metrics[3];
			
			//modularizationFactor[toCluster] = calculateClusterModularizationFactor(internalDependencyWeight[toCluster], externalDependencyWeight[toCluster]);
			totalModulesOnCluster[toCluster]++;

			if (totalModulesOnCluster[toCluster] == 1)
			{
				updateClusterCreatedInfo(toCluster);
			}

			modulesOnCluster.get(toCluster).add(module);
		}
		
		/*
		 * Verificar se alguma classe do cluster (pacote) de destino possui depend�ncia com a classe que est� sendo movimentada.
		 */
		
//		int _modulesOnClusterSize = modulesOnCluster.get(toCluster).size();
//		
//		for (int i = 0; i < _modulesOnClusterSize; i++)
//		{
//			int _modulesOnClusterGet = modulesOnCluster.get(toCluster).get(i);
//			if (mdg.checkHasDependency(module, _modulesOnClusterGet)) {
//				if (!classWithDepOnCluster.get(toCluster).contains(module)) {
//					classWithDepOnCluster.get(toCluster).add(module);
//					classWithDepOnClusterNew[toCluster]++; 
//					
//				};
//				if (!classWithDepOnCluster.get(toCluster).contains(_modulesOnClusterGet)) {
//					classWithDepOnCluster.get(toCluster).add(_modulesOnClusterGet);
//					classWithDepOnClusterNew[toCluster]++;
//				}
//			};			
//		}
	}
		
	/**
	 * Atualiza as estruturas de controle informando que um novo cluster foi criado
	 */
	private void updateClusterCreatedInfo(Integer cluster)
	{
		availableClusters.remove(cluster);
		usedClusters.add(cluster);
		totalClusteres++;
	}

	/**
	 * Atualiza as estruturas de controle informando que um cluster foi removido
	 * 
	 * @param cluster
	 */
	private void updateClusterRemovedInfo(Integer cluster)
	{
		availableClusters.push(cluster);// cluster poder� ser utilizado
		usedClusters.remove(cluster);// remove o cluster da lista dos utilizados
		totalClusteres--;
	}

	/**
	 * Gera os dados com os valores que serao alterados com o movimento
	 */
	private int[] calculateMovimentMetrics(int module, int toCluster)
	{
		int fromCluster = solution[module];

		if (toCluster == -1)
			toCluster = mdg.getSize();
		
		if (fromCluster == -1)
			fromCluster = mdg.getSize();

		// valores do cluster de onde o módulo vai sair
		int fromInternalDependencyWeight = internalDependencyWeight[fromCluster];
		int fromExternalDependencyWeight = externalDependencyWeight[fromCluster];

		// valores do cluster para onde o módulo vai
		int toInternalDependencyWeight = internalDependencyWeight[toCluster];
		int toExternalDependencyWeight = externalDependencyWeight[toCluster];
		
		if (fromCluster != toCluster)
		{
			// for (int depModule=0;depModule< mdg.getSize();depModule++){
			for (int i = 0; i < mdg.moduleDependenciesCount(module); i++)
			{
				int depModule = mdg.moduleDependencies(module)[i];
				/*
				 * if(depModule==module){//não verifica o próprio módulo continue; }
				 */
				int depWeight = mdg.dependencyWeight(module, depModule);
				/*
				 * if(depWeight == 0){ throw new RuntimeException("DEPENDENCIA ESTA NA LISTA MAS NAO EXISTE!");//nAo
				 * existe dependencia entre o modulo module e j }
				 */
				int depCluster = solution[depModule];

				// modulo dependente de si
				if (depModule == module)
				{
					fromInternalDependencyWeight -= depWeight;
					toInternalDependencyWeight += depWeight;
				} 
				
				// modulos estavam no mesmo cluster -> serao separados
				else if (depCluster == fromCluster)
				{
					fromInternalDependencyWeight -= depWeight; // dependencia deixara de ser interna na origem
					fromExternalDependencyWeight += depWeight;// passará a ser externa nos dois clusteres
					toExternalDependencyWeight += depWeight;// passará a ser externa nos dois clusteres
				} 
				
				// modulos estavam separados -> ficarao no mesmo cluster
				else if (depCluster == toCluster)
				{
					fromExternalDependencyWeight -= depWeight;// dependencia deixara de ser externa nos dois clusteres
					toExternalDependencyWeight -= depWeight;// dependencia deixara de ser externa nos dois clusteres
					toInternalDependencyWeight += depWeight;// passará a ser interna no cluster de destino
				} 
				
				// dependencia com modulos que nao pertencem nem a origem nem ao destino tem que ser transferidas de um modulo ao outro
				else
				{
					fromExternalDependencyWeight -= depWeight; // cluster de origem deixa de ter a dependencia externa
					toExternalDependencyWeight += depWeight; // cluster de destino passa a ter a dependencia externa
				}
			}
		}

		return new int[] { fromInternalDependencyWeight, fromExternalDependencyWeight, toInternalDependencyWeight,
				toExternalDependencyWeight };
	}

	/**
	 * Calcula a alteração que a função objetivo sofrerá com o movimento de junção de dois clusteres
	 */
	public double calculateMergeClustersDelta(int[] functionParams, int cluster1, int cluster2)
	{
//		List<Integer> joinClassWithDepOnCluster = new ArrayList<>();
//		List<Integer> joinClassWithDepOutCluster = new ArrayList<>();
		
		int joinClusterInternalDependency = internalDependencyWeight[cluster1] + internalDependencyWeight[cluster2];
		int joinClusterExternalDependency = externalDependencyWeight[cluster1] + externalDependencyWeight[cluster2];
		
		int firstClusterInternalClassesWithInternalDependency = internalClassesInternalDependencies[cluster1];
		int firstClusterInternalClassesWithExternalDependency = internalClassesExternalDependencies[cluster1];
		
		int secondClusterInternalClassesWithInternalDependency = internalClassesInternalDependencies[cluster2];
		int secondClusterInternalClassesWithExternalDependency = internalClassesExternalDependencies[cluster2];
		
		int joinClusterInternalClassesWithInternalDependency = firstClusterInternalClassesWithInternalDependency + secondClusterInternalClassesWithInternalDependency;
		int joinClusterInternalClassesWithExternalDependency = firstClusterInternalClassesWithExternalDependency + secondClusterInternalClassesWithExternalDependency;
		
		
//		int classWithDepOnCluster1 = 0;
//		int classWithDepOnCluster2= 0;
//		int classWithDepOutCluster1 = 0;
//		int classWithDepOutCluster2 = 0; 
//		int classWithDepOnCluster1 = 0;//classWithDepOnClusterNew[cluster1]; 
//		int classWithDepOnCluster2 = 0;//classWithDepOnClusterNew[cluster2];
		
//		
//		int classWithDepOutCluster1 = classWithDepOutCluster.get(cluster1).size();
//		int classWithDepOutCluster2 = classWithDepOutCluster.get(cluster2).size();
//	
//		joinClassWithDepOnCluster.addAll(classWithDepOutCluster.get(cluster1));
//		joinClassWithDepOnCluster.addAll(classWithDepOutCluster.get(cluster2));
//		
//		if (!joinClassWithDepOutCluster.contains(classWithDepOutCluster.get(cluster1))) {
//			joinClassWithDepOutCluster.addAll(classWithDepOutCluster.get(cluster1));	
//		}
//		
//		if (!joinClassWithDepOutCluster.contains(classWithDepOutCluster.get(cluster2))) {
//			joinClassWithDepOutCluster.addAll(classWithDepOutCluster.get(cluster2));	
//		}
//		
//		int joinClassWithDepOnCluster = classWithDepOnCluster1 + classWithDepOnCluster2;
//		System.out.println("" + totalClusteres);

		for (int i : modulesOnCluster.get(cluster1))
		{
			for (int j : modulesOnCluster.get(cluster2))
			{
				int dependencyEachOtherWeight = mdg.dependencyWeight(i, j);
				
				if (dependencyEachOtherWeight != 0)
				{
					joinClusterInternalDependency += dependencyEachOtherWeight;// aresta externa passou a ser interna
					joinClusterExternalDependency -= dependencyEachOtherWeight;// aresta externa deixou de existir (no cluster1)
					joinClusterExternalDependency -= dependencyEachOtherWeight;// aresta externa deixou de existir (no cluster2)

					if (!depends(i, cluster1))
						joinClusterInternalClassesWithInternalDependency++;			// A dependia de B, agora a depend�ncia � interna
					
					if (this.classClusterReferences[i][1] == -1)					// se s� tem uma depend�ncia externa, n�o tem mais ...
						joinClusterInternalClassesWithExternalDependency--;
				}
				
//				if (mdg.checkHasDependency(i, j)) {
//					if (!joinClassWithDepOnCluster.contains(i)) {
//						joinClassWithDepOnCluster.add(i);
//					};
//					
//					if (!joinClassWithDepOnCluster.contains(j)) {
//						joinClassWithDepOnCluster.add(j);
//					};

//					boolean include = true;
//					for (int h = 0; h < mdg.moduleDependencies(i).length; i++) {
//						if (mdg.moduleDependencies(i)[h]!=-1){
//							if (solution[mdg.moduleDependencies(i)[h]]!=cluster1 && solution[mdg.moduleDependencies(i)[h]]!=cluster2 ) {
//								include = true;
//								break;	
//							}
//							else {
//								include = false;	
//							}
//						}
//						else break;
//					}
//					if (include) {
//						if (!joinClassWithDepOutCluster.contains(i)) {
//							joinClassWithDepOutCluster.add(i);	
//						}
//					}
//					
//					include = true;
//					for (int h = 0; h < mdg.moduleDependencies(j).length; i++) {
//						if (mdg.moduleDependencies(j)[h]!=-1){
//							if (solution[mdg.moduleDependencies(j)[h]]!=cluster1 && solution[mdg.moduleDependencies(j)[h]]!=cluster2 ) {
//								include = true;
//								break;	
//							}
//							else {
//								include = false;	
//							}
//						}
//						else break;
//					}
//					if (include) {
//						if (!joinClassWithDepOutCluster.contains(j)) {
//							joinClassWithDepOutCluster.add(j);	
//						}
//					}				

//				}
//				else {
//					
//				}
			}
		}
		
		return calculateFitnessClusterMergeDelta(
				internalDependencyWeight[cluster1], 
				externalDependencyWeight[cluster1], 
				internalDependencyWeight[cluster2], 
				externalDependencyWeight[cluster2], 
				joinClusterInternalDependency, 
				joinClusterExternalDependency,			
				firstClusterInternalClassesWithInternalDependency,
				secondClusterInternalClassesWithInternalDependency,
				joinClusterInternalClassesWithInternalDependency,
				firstClusterInternalClassesWithExternalDependency,
				secondClusterInternalClassesWithExternalDependency,
				joinClusterInternalClassesWithExternalDependency);
		
//		return delta
//				+ (calculateClusterModularizationFactor(joinClusterInternalDependency, joinClusterExternalDependency)
//						- modularizationFactor[cluster2]);
	}

	public int calculateInternalClassesWithInternalDependency(int cluster)
	{
		int count = 0;
		
		for (int classCluster : modulesOnCluster.get(cluster))
		{
			if (depends(classCluster, cluster))
				count++;
		}

		return count;
	}

	public int calculateInternalClassesWithExternalDependency(int cluster)
	{
		int count = 0;
		
		for (int classCluster : modulesOnCluster.get(cluster))
		{
			int walker = 0;
			
			while (this.classClusterReferences[classCluster][walker] != -1)
			{
				if (this.classClusterReferences[classCluster][walker] != cluster)
				{
					count++;
					break;
				}

				walker++;
			}
		}

		return count;
	}

	private boolean depends(int classIndex, int cluster)
	{
		int walker = 0;
		
		while (this.classClusterReferences[classIndex][walker] != -1)
		{
			if (this.classClusterReferences[classIndex][walker] == cluster)
				return true;
			
			walker++;
		}

		return false;
	}

	/**
	 * Transforma dois clusteres em um
	 */
	public void makeMergeClusters(int cluster1, int cluster2)
	{
		// para cada módulo, verificar se ele está no cluster 1, se estiver, move-lo para o cluster 2
		int clusterNBefore = totalClusteres;

		for (int module = 0; module < solution.length; module++)
		{
			if (solution[module] == cluster1)
			{
				// modulo está no cluster 1
				makeMoviment(module, cluster2);
				
				// acabou de remover o cluster1
				if (totalClusteres < clusterNBefore)
				{
					break;
				}
			}
		}
		
		updateClassClusterReferences();
	}

	/**
	 * Cria uma copia do atual array de soluão
	 */
	public int[] cloneSolution()
	{
		int[] clone = new int[solution.length];

		for (int i = 0; i < solution.length; i++)
		{
			clone[i] = solution[i];
		}

		return clone;
	}

	/**
	 * Retorna o número total de clusteres existentes na solução
	 */
	public int getTotalClusteres()
	{
		return this.totalClusteres;
	}

	/**
	 * Retorna o cluster que esta na posicao do array
	 */
	public int convertToClusterNumber(int position)
	{
		if (position >= mdg.getSize())
		{
			throw new RuntimeException("POSICAO LIDA TEM QUE SER MENOR QUE O TOTAL!");
		}
		
		if (position < (totalClusteres))
		{
			return usedClusters.get(position);
		}
		
		return availableClusters.peek();
	}

	/**
	 * 
	 */
	public List<Integer> getModulesOnCluster(int cluster)
	{
		return this.modulesOnCluster.get(cluster);
	}
}