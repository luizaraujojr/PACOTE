package br.com.ppgi.unirio.teaching.clustering.search.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import br.com.ppgi.unirio.teaching.clustering.model.Project;

public class FitnessMetric
	{
		private final ModuleDependencyGraph mdg;
		private int[] solution;

		private int totalClusteres;
		private int[] totalModulesOnCluster;
		private List<List<Integer>> modulesOnCluster;

		private int[] internalDependencyWeight;
		private int[] externalDependencyWeight;
		private Stack<Integer> availableClusters;
		private List<Integer> usedClusters;
		private Project project;
		

		/**
		 * variable to used in the fitness calculation
		 */
		private static int[] functionParams;

		// usado para clonar o objeto
		private FitnessMetric(ModuleDependencyGraph mdg)
		{
			this.mdg = mdg;
		}

		public FitnessMetric(ModuleDependencyGraph mdg, int[] solution, int[] functionParams, Project project)
		{
			this.mdg = mdg;
			this.solution = solution;

			totalClusteres = 0;
			totalModulesOnCluster = new int[mdg.getSize() + 1];
			modulesOnCluster = new ArrayList<>();

			internalDependencyWeight = new int[mdg.getSize() + 1];
			externalDependencyWeight = new int[mdg.getSize() + 1];
			
			this.functionParams = functionParams;
			this.project = project;

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

				for (int j = i; j < mdg.getSize(); j++)
				{// para cada outro m√≥dulo seguinte
					updateDependencyMetrics(i, j);
				}
			}

//			resetAllMF();// calcula o MF de todos os m√≥dulos
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		/**
		 * Verifica se existe depend√™ncia entre os m√≥dulos i e j. Se houver, atualiza as metricas.
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

		/**
		 * Calcula o MQ com base nos MFs existentes
		 */
		public double calculateMQ()
		{
//			double mq = 0;
//			for (int auxi = 0; auxi < totalClusteres; auxi++)
//			{
//				int i = convertToClusterNumber(auxi);
//				mq += modularizationFactor[i];
//			}
//			
//			return mq;
			return  calculateFitness();		
		}
		
		/**
		 * Calcula o fitness com base nas mÈtricas e nos coeficientes multiplicadores
		 */
		
		public double calculateFitness()
		{
			double fitness = 0;
			
			if (functionParams.length==4) {
			
				for (int clusterNumber = 0; clusterNumber < totalClusteres; clusterNumber++) {
					double fa1 = (((double)functionParams[0]-5.0)/2.0 ) * internalDependencyWeight[convertToClusterNumber(clusterNumber)]; //N˙mero de dependÍncias diretas entre classes 
					double fb1 = (((double)functionParams[2]-5.0)/2.0 ) * internalDependencyWeight[convertToClusterNumber(clusterNumber)]; //N˙mero de dependÍncias diretas entre classes
					double fb2 = (((double)functionParams[3]-5.0)/2.0 ) * externalDependencyWeight[convertToClusterNumber(clusterNumber)]; //N˙mero de dependÍncias entre classes de pacotes diferentes
				
					if (fa1!=0) {			
						fitness += (fa1) / (fb1+fb2);
					}
				}
			}
			
			else {
				int[] abstractClassCount = new int[mdg.getSize()]; //N˙mero de classe abstratas
				int[] concreteClassCount = new int[mdg.getSize()]; //N˙mero de classe concretas
				
				int[] insidePackageDependencyClassCount = new int[mdg.getSize()]; // N˙mero de classes com dependÍncias diretas dentro do pacote
				int[] outsidePackageDependencyClassCount = new int[mdg.getSize()];  //N˙mero de classes do pacote sob an·lise com dependÍncias para classes de outro pacote
				
				int[] outsideInternalDependencyClassCount = new int[mdg.getSize()]; //N˙mero de classes de outro pacote com dependÍncias para classes de um pacote sob an·lise
				
				int[] outsideInternalDependencyPackageCount = new int[mdg.getSize()];   //N˙mero de pacotes cujas classes dependem das classes do pacote em an·lise
				int[] outsideExternalDependencyPackageCount = new int[mdg.getSize()];  //N˙mero de pacotes dos quais as classes do pacote em an·lise dependem
				
				int[] insidePackageClassDependencyCount = new int[mdg.getSize()];   //N˙mero de dependÍncias diretas entre classes
				int[] outsidePackageClassDependencyCount  = new int[mdg.getSize()];  //N˙mero de dependÍncias entre classes de pacotes diferentes
				
				
				for (int clusterNumber = 0; clusterNumber < totalClusteres; clusterNumber++)
				{
					insidePackageClassDependencyCount[clusterNumber] = internalDependencyWeight[convertToClusterNumber(clusterNumber)]; //N˙mero de dependÍncias diretas entre classes
					outsidePackageClassDependencyCount[clusterNumber]  = externalDependencyWeight[convertToClusterNumber(clusterNumber)]; //N˙mero de dependÍncias entre classes de pacotes diferentes
									
					for (int classNumber : getModulesOnCluster(convertToClusterNumber(clusterNumber))) {
						boolean internalClassDependencyFound = false;
						boolean externalClassDependencyFound = false;
						ArrayList<Integer> packages = new ArrayList<>();
						
						abstractClassCount[clusterNumber] =+ (this.project.getClassIndex(classNumber).isAbstract() ? 1 : 0);
						concreteClassCount[clusterNumber] =+ (this.project.getClassIndex(classNumber).isAbstract() ? 0 : 1);
						
						for (int classDependencyNumber : mdg.moduleDependencies (classNumber)) {
							
							if (classDependencyNumber ==-1) break;
							
							outsideInternalDependencyClassCount[convertToClusterNumber(solution[classDependencyNumber])]++;
							
							if (solution[classNumber]==solution[classDependencyNumber]) {	
								internalClassDependencyFound = true;
							}
							else {
								externalClassDependencyFound = true;
								
								boolean repeatedPackage =false;
								for (int _package : packages) {
									if (_package==solution[classDependencyNumber]) repeatedPackage = true;
								}
								if (!repeatedPackage) {
									packages.add(solution[classDependencyNumber]);
									outsideExternalDependencyPackageCount[convertToClusterNumber(solution[classDependencyNumber])]++;
								}
							}						
						}
						outsideInternalDependencyPackageCount[clusterNumber] =+ packages.size();
						
						if (internalClassDependencyFound) insidePackageDependencyClassCount[clusterNumber]++;
						if (externalClassDependencyFound) outsidePackageDependencyClassCount[clusterNumber]++;
					}
				}
				
				for (int clusterNumber = 0; clusterNumber < totalClusteres; clusterNumber++) {
					double fa1 = (((double)functionParams[0]-5.0)/2.0 ) * insidePackageClassDependencyCount[clusterNumber]; 
					double fa2 = (((double)functionParams[1]-5.0)/2.0 ) * outsidePackageClassDependencyCount[clusterNumber];  
					double fa3 = (((double)functionParams[2]-5.0)/2.0 ) * abstractClassCount[clusterNumber]; 
					double fa4 = (((double)functionParams[3]-5.0)/2.0 ) * concreteClassCount[clusterNumber];
					double fb1 = (((double)functionParams[4]-5.0)/2.0 ) * insidePackageClassDependencyCount[clusterNumber]; 
					double fb2 = (((double)functionParams[5]-5.0)/2.0 ) * outsidePackageClassDependencyCount[clusterNumber]; 
					double fb3 = (((double)functionParams[6]-5.0)/2.0 ) * abstractClassCount[clusterNumber]; 
					double fb4 = (((double)functionParams[7]-5.0)/2.0 ) * concreteClassCount[clusterNumber];
							
					if (fa1+fa2+fa3+fa4!=0) {			
						fitness += (fa1+fa2+fa3+fa4) / (fb1+fb2+fb3+fb4);
					}
				}
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
			availableClusters.push(cluster);// cluster poderÔøΩ ser utilizado
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

			// valores do cluster de onde o m√≥dulo vai sair
			int fromInternalDependencyWeight = internalDependencyWeight[fromCluster];
			int fromExternalDependencyWeight = externalDependencyWeight[fromCluster];

			// valores do cluster para onde o m√≥dulo vai
			int toInternalDependencyWeight = internalDependencyWeight[toCluster];
			int toExternalDependencyWeight = externalDependencyWeight[toCluster];
			
			if (fromCluster != toCluster)
			{
				// for (int depModule=0;depModule< mdg.getSize();depModule++){
				for (int i = 0; i < mdg.moduleDependenciesCount(module); i++)
				{
					int depModule = mdg.moduleDependencies(module)[i];
					/*
					 * if(depModule==module){//n√£o verifica o pr√≥prio m√≥dulo continue; }
					 */
					int depWeight = mdg.dependencyWeight(module, depModule);
					/*
					 * if(depWeight == 0){ throw new RuntimeException("DEPENDENCIA ESTA NA LISTA MAS NAO EXISTE!");//nAo
					 * existe dependencia entre o modulo module e j }
					 */
					int depCluster = solution[depModule];

					if (depModule == module)
					{// modulo dependente de si
						fromInternalDependencyWeight -= depWeight;
						toInternalDependencyWeight += depWeight;
					} else if (depCluster == fromCluster)
					{// modulos estavam no mesmo cluster -> ser√£o separados
						fromInternalDependencyWeight -= depWeight; // dependencia deixar√° de ser interna na origem
						fromExternalDependencyWeight += depWeight;// passar√° a ser externa nos dois clusteres
						toExternalDependencyWeight += depWeight;// passar√° a ser externa nos dois clusteres
					} else if (depCluster == toCluster)
					{// modulos estavam separados -> ficar√£o no mesmo cluster
						fromExternalDependencyWeight -= depWeight;// dependencia deixar√° de ser externa nos dois clusteres
						toExternalDependencyWeight -= depWeight;// dependencia deixar√° de ser externa nos dois clusteres
						toInternalDependencyWeight += depWeight;// passar√° a ser interna no cluster de destino
					} else
					/* if(depCluster != fromCluster && depCluster != toCluster) */ {// depend√™ncia com m√≥dulos que n√£o
																					// pertencem nem a origem nem ao destino
																					// tem que ser transferidas de um
																					// m√≥dulo ao outro
						fromExternalDependencyWeight -= depWeight; // cluster de origem deixa de ter a depend√™ncia externa
						toExternalDependencyWeight += depWeight; // cluster de destino passa a ter a depend√™ncia externa
					}
				}
			}

			return new int[] { fromInternalDependencyWeight, fromExternalDependencyWeight, toInternalDependencyWeight,
					toExternalDependencyWeight };
		}

		/**
		 * Calcula a altera√ß√£o que a fun√ß√£o objetivo sofrer√° com o movimento de jun√ß√£o de dois clusteres
		 */
		public double calculateMergeClustersDelta(int cluster1, int cluster2)
		{
			FitnessMetric cmTest = this.clone();
			cmTest.makeMergeClusters(cluster1, cluster2);
			
			return cmTest.calculateMQ();
		}

		/**
		 * Transforma 2 clusteres em um
		 */
		public void makeMergeClusters(int cluster1, int cluster2)
		{
			// para cada m√≥dulo, verificar se ele est√° no cluster i, se estiver, move-lo para o cluster j
			int clusterNBefore = totalClusteres;
			for (int module = 0; module < solution.length; module++)
			{
				if (solution[module] == cluster1)
				{// modulo est√° no cluster i
					makeMoviment(module, cluster2);
					if (totalClusteres < clusterNBefore)
					{// acabou de remover o cluster1
						break;
					}
				}
			}
		}

		/**
		 * Cria uma copia do atual array de solu√£o
		 * 
		 * @return
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
		 * Retorna a solu√ß√£o atual
		 * 
		 * @return
		 */
		public int[] getSolution()
		{
			return this.solution;
		}

		/**
		 * Retorna o n√∫mero total de clusteres existentes na solu√ß√£o
		 * 
		 * @return
		 */
		public int getTotalClusteres()
		{
			return this.totalClusteres;
		}

		/**
		 * Retorna a quantidade de m√≥dulos existente no maior cluster
		 * 
		 * @return
		 */
		public int getBiggestClusterSize()
		{
			int bigN = 0;
			for (int i = 0; i < totalModulesOnCluster.length; i++)
			{
				if (totalModulesOnCluster[i] > bigN)
				{
					bigN = totalModulesOnCluster[i];
				}
			}
			return bigN;
		}

		/**
		 * Retorna a quantidade de m√≥dulos existente no menor cluster
		 * 
		 * @return
		 */
		public int getSmallestClusterSize()
		{
			int smallN = Integer.MAX_VALUE;
			for (int i = 0; i < totalModulesOnCluster.length; i++)
			{
				if (totalModulesOnCluster[i] > 0 && totalModulesOnCluster[i] < smallN)
				{
					smallN = totalModulesOnCluster[i];
				}
			}
			return smallN;
		}

		/**
		 * retorna o numero do menor cluster
		 * 
		 * @return
		 */
		public int getSmallestCluster()
		{
			int smallN = Integer.MAX_VALUE;
			int clusterN = -1;
			for (int i = 0; i < totalModulesOnCluster.length; i++)
			{
				if (totalModulesOnCluster[i] > 0 && totalModulesOnCluster[i] < smallN)
				{
					smallN = totalModulesOnCluster[i];
					clusterN = i;
				}
			}
			return clusterN;
		}

		/**
		 * Retorna a quantidade de clusteres que possuem apenas um m√≥dulo
		 * 
		 * @return
		 */
		public int getIsolatedClusterCount()
		{
			int total = 0;
			for (int i = 0; i < totalModulesOnCluster.length; i++)
			{
				if (totalModulesOnCluster[i] == 1)
				{// cluster isolado
					total++;
				}
			}
			return total;
		}

		/**
		 * Verifica se um m√≥dulo est√° sozinho em um cluster
		 * 
		 * @param moduleN
		 * @return
		 */
		public boolean isModuleAlone(int moduleN)
		{
			return totalModulesOnCluster[solution[moduleN]] == 1;
		}

		/**
		 * Transforma a solu√ß√£o corrente em uma String
		 * 
		 * @return
		 */
		public String getSolutionAsString()
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.solution.length; i++)
			{
				if (i > 0)
				{
					sb.append("-");
				}
				sb.append(this.solution[i]);
			}
			return sb.toString();
		}

		public String getClustersStatusAsString()
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < totalClusteres; i++)
			{
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
		 * Retorna o MDG da inst√¢ncia
		 * 
		 * @return
		 */
		public ModuleDependencyGraph getMdg()
		{
			return mdg;
		}

		/**
		 * Retorna o cluster que est√° na posi√ß√£o do array
		 * 
		 * @param position
		 * @return
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
			return availableClusters.peek();// retorna o elemento do topo - criar um novo cluster
		}

		/**
		 * Retorna o pr√≥ximo cluster dispon√≠vel para insers√£o
		 * 
		 * @return
		 */
		public int nextAvailableCluster()
		{
			if (availableClusters.size() > 0)
			{
				return availableClusters.peek();// retorna o elemento do topo - criar um novo cluster
			} else
				return -1;// n√£o h√° mais cluster dispon√≠vel
		}

		@SuppressWarnings("unchecked")
		@Override
		public FitnessMetric clone()
		{
			FitnessMetric cm = new FitnessMetric(mdg);
			cm.solution = this.cloneSolution();
			cm.totalClusteres = this.totalClusteres;
			cm.totalModulesOnCluster = new int[this.totalModulesOnCluster.length];
			System.arraycopy(this.totalModulesOnCluster, 0, cm.totalModulesOnCluster, 0, this.totalModulesOnCluster.length);

			cm.modulesOnCluster = new ArrayList<>(this.modulesOnCluster.size());
			for (List<Integer> list : this.modulesOnCluster)
			{
				ArrayList<Integer> newList = new ArrayList<>(list.size());
				for (Integer element : list)
				{
					newList.add(element);
				}
				cm.modulesOnCluster.add(newList);
			}
			cm.internalDependencyWeight = new int[this.internalDependencyWeight.length];
			System.arraycopy(this.internalDependencyWeight, 0, cm.internalDependencyWeight, 0,
					this.internalDependencyWeight.length);

			cm.externalDependencyWeight = new int[this.externalDependencyWeight.length];
			System.arraycopy(this.externalDependencyWeight, 0, cm.externalDependencyWeight, 0,
					this.externalDependencyWeight.length);

			cm.availableClusters = (Stack<Integer>) this.availableClusters.clone();

			cm.usedClusters = new ArrayList<>(this.usedClusters.size());
			for (Integer used : this.usedClusters)
			{
				cm.usedClusters.add(used);
			}
			
			cm.project = this.project;

			return cm;
		}

		public List<Integer> getModulesOnCluster(int cluster)
		{
			return this.modulesOnCluster.get(cluster);
		}
	}
