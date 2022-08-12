package unirio.teaching.clustering.search.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import unirio.teaching.clustering.model.Project;

/**
 * Metricas utilizadas para calulos e operacoes na clusterizacao
 * 
 * @author kiko
 */
public class ClusterMetrics4coef
{
	private final ModuleDependencyGraph mdg;

	private int[] solution;

	private int totalClusteres;
	
	private int[] totalModulesOnCluster;
	
	private List<List<Integer>> modulesOnCluster;

	private int[] internalDependencyWeight;
	
//	private int[] externalDependencyWeight;
	
	private Stack<Integer> availableClusters;
	
	private List<Integer> usedClusters;
	
	
	/**
	 * Vetor com os valores dos coeficientes após a conversão
	 */
	
	double[] c;
	
	/**
	 * Vetor com os clusters de quem uma classe depende. O primeiro indice eh a classe e
	 * o vetor da classe contem os cluster de quem ela depende, terminando por -1
	 */
	private int[][] classClusterReferences;
	
	/**
	 * Vetor com os clusters de quem uma classe depende. O primeiro indice eh a classe e
	 * o segundo indice eh o cluster, contendo true se uma classe depende de um cluster
	 */
	private boolean[][] classClusterReferencesFlat;

	/**
	 * Numero de classes internas a cada cluster com dependencias internas
	 */
//	private int[] internalClassesInternalDependencies;
	
	/**
	 * Numero de classes internas a cada cluster com dependencias externas
	 */
	private int[] internalClassesExternalDependencies;
	
	/**
	 * Numero de classes externas com dependências para cada cluster 
	 */
	private int[] externalClassesInternalDependencies;
	
	
//	/**
//	 * Numero de classes abstratas em cada cluster
//	 */
//	private int[] abstractClasses;
//	
//	
//	/**
//	 * Numero de classes concretas em cada cluster
//	 */
//	private int[] concreteClasses;
//	
//	
//	/**
//	 * vertor com as classes e seus status (true/false) de abstract
//	 */
//	private boolean[] abstractClassesFlat;
//	
//	
//	/**
//	 * Numero de classes abstratas em cada cluster
//	 */
//	private int[] subClasses;
//
//	
//	/**
//	 * vertor com as classes e seus status (true/false) de subclasse
//	 */
//	private boolean[] subClassesFlat;
//	
//	
//	/**
//	 * Numero de classes concretas em cada cluster
//	 */
//	private int[] superClasses;
//	
//	
//	/**
//	 * vertor com as classes e seus status (true/false) de superclasse
//	 */
//	private boolean[] superClassesFlat;
		
	private Project project;
	
	private int classCount; 
	
	
//	private boolean[] metricasUtilizadas;
	
	
	/**
	 * Inicializa o calculador de metricas
	 */
	public ClusterMetrics4coef(ModuleDependencyGraph mdg, int[] solution, int[] equationParams, Project project, boolean[] usedMetrics)
	{
		this.mdg = mdg;
		this.solution = solution;
		this.project = project;
		
		this.totalClusteres = 0;
		this.totalModulesOnCluster = new int[mdg.getSize() + 1];
		
		this.modulesOnCluster = new ArrayList<>();
		this.classCount = mdg.getSize();
		
//		this.c = new double[18];
		this.c = new double[4]; //alteração para 4 coeficientes
		
		this.internalDependencyWeight = new int[classCount + 1];
//		this.externalDependencyWeight = new int[classCount + 1];
		
		this.classClusterReferences = new int[classCount][classCount + 1];
		this.classClusterReferencesFlat = new boolean[classCount][classCount];

//		this.internalClassesInternalDependencies = new int[classCount];
		this.internalClassesExternalDependencies = new int[classCount];
		this.externalClassesInternalDependencies = new int[classCount];

		
		
		//alteração para 5 métricas somente
//		this.abstractClasses = new int[classCount];
//		this.concreteClasses = new int[classCount];
//		this.abstractClassesFlat = new boolean[classCount];
//		
//		this.subClasses = new int[classCount];
//		this.superClasses = new int[classCount];
//		
//		this.subClassesFlat = new boolean[classCount];
//		this.superClassesFlat = new boolean[classCount];
		
		
		for (int i = 0; i < equationParams.length; i++)
		{
			c[i] = (equationParams[i]-5.0)/2.0; 
		}
//		this.metricasUtilizadas=  usedMetrics;
//		int j =0;
//		for (int i = 0; i < metricasUtilizadas.length; i++)
//		{
//			if (metricasUtilizadas[i]) 
//			{
//				c[i] = (equationParams[j]-5.0)/2.0; 
//				j++;
//			}
//			
//		}	
		resetAllMetrics();
	}

	/**
	 * Reseta todas as mericas e calcula o MF de todos os modulos novamente
	 */
	private void resetAllMetrics()
	{
		availableClusters = new Stack<>();
		
		usedClusters = new ArrayList<>(classCount);
		
		for (int i = 0; i < classCount; i++)
		{
			availableClusters.push(classCount - i - 1);
			internalDependencyWeight[i] = 0;
//			externalDependencyWeight[i] = 0;
			modulesOnCluster.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < solution.length; i++)
		{
			int cluster = solution[i];
			modulesOnCluster.get(cluster).add(i);
			totalModulesOnCluster[cluster]++;
			
			if (totalModulesOnCluster[cluster] == 1)
			{
				updateClusterCreatedInfo(cluster);
			}

			for (int j = i; j < classCount; j++)
			{
				updateDependencyMetrics(i, j);
			}
		}
//		createClassInfoTable();
		updateClassClusterReferences();
	}
	
	
//	private void createClassInfoTable()
//	{		
//		for (int _class = 0; _class < classCount; _class++) {
//			
//			abstractClassesFlat[_class] = this.project.getClassIndex(_class).isAbstract();
//			
//			int[] dependencies = mdg.moduleDependencies(_class);
//			for (int j = 0; dependencies[j] != -1; j++) {
//				subClassesFlat[_class]=true;
//				superClassesFlat[dependencies[j]]=true;
//			}			
//		}
//	}
	
	
	private void updateClassClusterReferences()
	{
		for (int i = 0; i < solution.length; i++)
		{
			Arrays.fill(this.classClusterReferencesFlat[i], false);

			int walker = 0;
			int[] dependencies = mdg.moduleDependencies(i);
			
			for (int j = 0; dependencies[j] != -1; j++)
			{
//				subClassesFlat[j]=true;
				
				int classJ = dependencies[j];
				int depWeight = mdg.dependencyWeight(i, classJ);
				
				if (depWeight > 0)
				{
					boolean found = false;
					int targetCluster = this.solution[classJ];
					
					for (int k = 0; k < walker && !found; k++)
					{
						if (this.classClusterReferences[i][k] == targetCluster)
							found = true;
					}
					
					if (!found)
					{
						this.classClusterReferences[i][walker++] = targetCluster;
					}
					this.classClusterReferencesFlat[i][targetCluster] = true;
				}
			}
			this.classClusterReferences[i][walker] = -1;
		}

		updateClusterClassCount();
	}

	private void updateClusterClassCount()
	{
		for (int cluster = 0; cluster < classCount; cluster++)
		{
//			int countICID = 0;
			int countICED = 0;
			
			for (int classCluster : modulesOnCluster.get(cluster))
			{
//				if (this.abstractClassesFlat[classCluster]) 
//					this.abstractClasses[cluster]++;
//					else this.concreteClasses[cluster]++; 				
//				
//				if (this.subClassesFlat[classCluster]) this.subClasses[cluster]++;
//				if (this.superClassesFlat[classCluster]) this.superClasses[cluster]++;
								
//				if (this.classClusterReferencesFlat[classCluster][cluster])
//					countICID++;
				
				int walker = 0;
				
				boolean depFound = false;
				while (this.classClusterReferences[classCluster][walker] != -1)
				{
					if (this.classClusterReferences[classCluster][walker] != cluster && !depFound)
					{
						countICED++;
						depFound = true;
					}
					else this.internalClassesExternalDependencies[this.classClusterReferences[classCluster][walker]]++;
					walker++;
				}
			}
		
//			this.internalClassesInternalDependencies[cluster] = countICID;
			this.internalClassesExternalDependencies[cluster] = countICED;
		}
	}

	/**
	 * Verifica se existe dependencia entre os modulos i e j. Se houver, atualiza as metricas.
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
//			else
//			{
//				externalDependencyWeight[clusteri] += depWeight;
//				externalDependencyWeight[clusterj] += depWeight;
//			}
		}
	}

	private double calculateFitnessClusterMergeDelta(
			int firstInternalDependencyWeightCluster, 
			int secondInternalDependencyWeightCluster,
			int joinClusterInternalDependency, 
//			int firstExternalDependencyWeightCluster,
//			int secondExternalDependencyWeightCluster, 
//			int joinClusterExternalDependency,	
			int firstClusterInternalClassesWithExternalDependency,
			int secondClusterInternalClassesWithExternalDependency,
			int joinClusterInternalClassesWithExternalDependency,
			int firstClusterExternalClassesWithInternalDependency,
			int secondClusterExternalClassesWithInternalDependency,
			int joinClusterExternalClassesWithInternalDependency)
	{		
		
		double fitness = calculateClusterFitness(joinClusterInternalDependency, joinClusterInternalClassesWithExternalDependency, joinClusterExternalClassesWithInternalDependency);
		fitness -= calculateClusterFitness(firstInternalDependencyWeightCluster, firstClusterInternalClassesWithExternalDependency, firstClusterExternalClassesWithInternalDependency);
		fitness -= calculateClusterFitness(secondInternalDependencyWeightCluster, secondClusterInternalClassesWithExternalDependency, secondClusterExternalClassesWithInternalDependency);
		return fitness;
	}

		
	public double calculateClusterFitness(int _numberInternalDependencies, int _numberInternalClassWithDepOutCluster, int _numberExternalClassWithDepCluster)
	{
		double ra1 = c[0] * _numberInternalDependencies;
//		double ra4 = c[1] * _numberInternalClassWithDepOutCluster;
		double ra5 = c[1] * _numberExternalClassWithDepCluster;
		
		
		double fitness = ra1 + ra5;	
		if (fitness != 0.0) 
		{
//			double rb1 = c[3] * _numberInternalDependencies;
			double rb4 = c[2] * _numberInternalClassWithDepOutCluster;
			double rb5 = c[3] * _numberExternalClassWithDepCluster;
			double rb =  rb4 + rb5;			
			fitness /= rb;
		}
		return fitness;
	}

	
	
		
//	/**
//	 * Calcula o fitness da solucao atual
//	 */
	public double calculateFitness()
	{
		double fitness = 0;
		
		for (int clusterNumber = 0; clusterNumber < classCount; clusterNumber++) 
		{
			int _clusterNumber = convertToClusterNumber(clusterNumber);
			fitness += calculateClusterFitness(internalDependencyWeight[_clusterNumber], internalClassesExternalDependencies [_clusterNumber], externalClassesInternalDependencies [_clusterNumber]); 
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
//			externalDependencyWeight[fromCluster] = metrics[1];
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
//			internalDependencyWeight[toCluster] = metrics[2];
			internalDependencyWeight[toCluster] = metrics[1];
//			externalDependencyWeight[toCluster] = metrics[3];
			
			//modularizationFactor[toCluster] = calculateClusterModularizationFactor(internalDependencyWeight[toCluster], externalDependencyWeight[toCluster]);
			totalModulesOnCluster[toCluster]++;

			if (totalModulesOnCluster[toCluster] == 1)
			{
				updateClusterCreatedInfo(toCluster);
			}

			modulesOnCluster.get(toCluster).add(module);
		}
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
		availableClusters.push(cluster);// cluster poderï¿½ ser utilizado
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
//			toCluster = mdg.getSize();
			toCluster = classCount;
		
		if (fromCluster == -1)
//			fromCluster = mdg.getSize();
			fromCluster = classCount;

		// valores do cluster de onde o mÃ³dulo vai sair
		int fromInternalDependencyWeight = internalDependencyWeight[fromCluster];
//		int fromExternalDependencyWeight = externalDependencyWeight[fromCluster];

		// valores do cluster para onde o mÃ³dulo vai
		int toInternalDependencyWeight = internalDependencyWeight[toCluster];
//		int toExternalDependencyWeight = externalDependencyWeight[toCluster];
		
		if (fromCluster != toCluster)
		{
			// for (int depModule=0;depModule< mdg.getSize();depModule++){
			for (int i = 0; i < mdg.moduleDependenciesCount(module); i++)
			{
				int depModule = mdg.moduleDependencies(module)[i];
				/*
				 * if(depModule==module){//nÃ£o verifica o prÃ³prio mÃ³dulo continue; }
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
//					fromExternalDependencyWeight += depWeight;// passarÃ¡ a ser externa nos dois clusteres
//					toExternalDependencyWeight += depWeight;// passarÃ¡ a ser externa nos dois clusteres
				} 
				
				// modulos estavam separados -> ficarao no mesmo cluster
				else if (depCluster == toCluster)
				{
//					fromExternalDependencyWeight -= depWeight;// dependencia deixara de ser externa nos dois clusteres
//					toExternalDependencyWeight -= depWeight;// dependencia deixara de ser externa nos dois clusteres
					toInternalDependencyWeight += depWeight;// passarÃ¡ a ser interna no cluster de destino
				} 
				
				// dependencia com modulos que nao pertencem nem a origem nem ao destino tem que ser transferidas de um modulo ao outro
//				else
//				{
//					fromExternalDependencyWeight -= depWeight; // cluster de origem deixa de ter a dependencia externa
//					toExternalDependencyWeight += depWeight; // cluster de destino passa a ter a dependencia externa
//				}
			}
		}

		return new int[] { fromInternalDependencyWeight, toInternalDependencyWeight};
	}

	/**
	 * Calcula a alteracao que a funcao objetivo sofrera com o movimento de juncao de dois clusteres
	 */
	
	public double calculateMergeClustersDelta(int cluster1, int cluster2)
	{
		int joinClusterInternalDependency = internalDependencyWeight[cluster1] + internalDependencyWeight[cluster2];
//		int joinClusterExternalDependency = externalDependencyWeight[cluster1] + externalDependencyWeight[cluster2];
		
		int firstClusterInternalClassesWithExternalDependency = internalClassesExternalDependencies[cluster1];
		int firstClusterExternalClassesWithInternalDependency = externalClassesInternalDependencies[cluster1];
		
		int secondClusterInternalClassesWithExternalDependency = internalClassesExternalDependencies[cluster2];
		int secondClusterExternalClassesWithInternalDependency = externalClassesInternalDependencies[cluster2];
		
		int joinClusterInternalClassesWithExternalDependency = firstClusterInternalClassesWithExternalDependency + secondClusterInternalClassesWithExternalDependency;
		int joinClusterExternalClassesWithInternalDependency = firstClusterExternalClassesWithInternalDependency + secondClusterExternalClassesWithInternalDependency;
		
		for (int i : modulesOnCluster.get(cluster1))
		{
			boolean inverseDependency = false, depinserted = false;
			for (int j : modulesOnCluster.get(cluster2))
			{
				int dependencyEachOtherWeight = mdg.dependencyWeight(i, j);
				int dependencyEachOtherWeightinverse = mdg.dependencyWeight(j, i);					
					
				if (dependencyEachOtherWeightinverse== 2) {
					inverseDependency = true;
					if (this.classClusterReferencesFlat[i][cluster1]) {
						depinserted = true;
					}
					
				}
				if (dependencyEachOtherWeightinverse== 1) {
						if (!this.classClusterReferencesFlat[i][cluster1]) {
							inverseDependency = true;
						}
						else depinserted = true;
				}
				
				if (dependencyEachOtherWeight != 0)
				{
					joinClusterInternalDependency += dependencyEachOtherWeight;// aresta externa passou a ser interna
//					joinClusterExternalDependency -= dependencyEachOtherWeight;// aresta externa deixou de existir (no cluster1)
//					joinClusterExternalDependency -= dependencyEachOtherWeight;// aresta externa deixou de existir (no cluster2)

					
//					if (this.classClusterReferencesFlat[i][cluster2] && !depinserted) {
//						joinClusterInternalClassesWithInternalDependency++;			// A dependia de B, agora a dependência é interna
//					}
					
					if (this.classClusterReferences[i][1] == -1)					// se só tem uma dependência externa, não tem mais ...
						joinClusterInternalClassesWithExternalDependency--;
				}
			}
			if (depinserted){
				joinClusterExternalClassesWithInternalDependency--;
			}
			if (inverseDependency) {
//				joinClusterInternalClassesWithInternalDependency++;
				joinClusterExternalClassesWithInternalDependency--;
			}
		}
				
		return calculateFitnessClusterMergeDelta(
				internalDependencyWeight[cluster1], 
				internalDependencyWeight[cluster2],
				joinClusterInternalDependency, 
//				externalDependencyWeight[cluster1], 
//				externalDependencyWeight[cluster2], 
//				joinClusterExternalDependency,			
				firstClusterInternalClassesWithExternalDependency,
				secondClusterInternalClassesWithExternalDependency,
				joinClusterInternalClassesWithExternalDependency,
				firstClusterExternalClassesWithInternalDependency,
				secondClusterExternalClassesWithInternalDependency,
				joinClusterExternalClassesWithInternalDependency);
		
	}
	
	
	
	/**
	 * Calcula o numero de classes internas de um cluster com dependencias para classes do proprio cluster 
	 */
	public int calculateInternalClassesWithInternalDependency(int cluster)
	{
		int count = 0;
		
		for (int classCluster : modulesOnCluster.get(cluster))
		{
			if (this.classClusterReferencesFlat[classCluster][cluster])
				count++;
		}

		return count;
	}

	/**
	 * Calcula o numero de classes internas de um cluster com dependencias para classes de outro cluster 
	 */
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

	/**
	 * Transforma dois clusteres em um
	 */
	public void makeMergeClusters(int cluster1, int cluster2)
	{
		// para cada mÃ³dulo, verificar se ele estÃ¡ no cluster 1, se estiver, move-lo para o cluster 2
		int clusterNBefore = totalClusteres;

		for (int module = 0; module < solution.length; module++)
		{
			if (solution[module] == cluster1)
			{
				// modulo estÃ¡ no cluster 1
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
	 * Cria uma copia do atual array de solucao
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
	 * Retorna o numero total de clusteres existentes na solucao
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
		
		if (position < (totalClusteres))
		{
			return usedClusters.get(position);
		}
		
		if (position >= mdg.getSize())
		{
			throw new RuntimeException("POSICAO LIDA TEM QUE SER MENOR QUE O TOTAL!");
		}
		
		return availableClusters.peek();
	}
}