package br.com.ppgi.unirio.teaching.clustering.search.constructive;

import br.com.ppgi.unirio.teaching.clustering.model.Project;
import br.com.ppgi.unirio.teaching.clustering.search.model.ClusterMetrics;
import br.com.ppgi.unirio.teaching.clustering.search.model.ModuleDependencyGraph;

/**
 * Constructive aglomerative method
 * 
 * @author User
 */
public class ConstrutiveAglomerativeMQ extends ConstrutiveAbstract
{
	@Override
	public int[] createSolution(ModuleDependencyGraph mdg, int[] functionParams, Project project)
	{
		return createSolution(mdg, 1, functionParams, project)[0];
	}

	private int[][] createSolution(ModuleDependencyGraph mdg, int quantity, int[] functionParams, Project project)
	{
		int[] solution = new int[mdg.getSize()];

		for (int index = 0; index < mdg.getSize(); index++)
			solution[index] = index;

		return aglomerateClustering(mdg, solution, quantity, functionParams, project);
	}

	private int[][] aglomerateClustering(ModuleDependencyGraph mdg, int[] solution, int solutionsQuantity, int[] functionParams, Project project)
	{
		int[][] topSolutions = new int[solutionsQuantity][solution.length];
		Double[] topSolutionsMQ = new Double[solutionsQuantity];

		int n = mdg.getSize();
		ClusterMetrics cm = new ClusterMetrics(mdg, solution, functionParams, project);

		// solucao de entrada e a melhor. Unica conhecida
		topSolutions[0] = solution;
		topSolutionsMQ[0] = cm.calculateMQ();

		int k = 1;
		while (n - k > 1)
		{
			// selecionar elementos para a aglutinacao
			int aglutinatei = -1;
			int aglutinatej = -1;
			Double currentMaxDelta = null;

			for (int auxi = 0; auxi < cm.getTotalClusteres(); auxi++)
			{
				for (int auxj = auxi + 1; auxj < cm.getTotalClusteres(); auxj++)
				{
					int i = cm.convertToClusterNumber(auxi);
					int j = cm.convertToClusterNumber(auxj); 
					
					// verificar o delta da uniao desses dois clusteres
					double currentDelta = cm.calculateMergeClustersDelta(i, j);
					System.out.println (currentDelta);
					if (currentMaxDelta == null || currentDelta > currentMaxDelta)
					{
						aglutinatei = i;
						aglutinatej = j;
						currentMaxDelta = currentDelta;
					}
				}
			}

			// algutinar elementos
			cm.makeMergeClusters(aglutinatei, aglutinatej);

			// gravar solucao atual na lista de melhores
			addSolutionOnTopSolutions(cm.cloneSolution(), cm.calculateMQ(), topSolutions, topSolutionsMQ);

			k += 1;
		}
		return topSolutions;
	}

	private void addSolutionOnTopSolutions(int[] currentSolution, double currentSolutionMQ, int[][] topSolutions, Double[] topSolutionsMQ)
	{
		for (int i = 0; i < topSolutionsMQ.length; i++)
		{
			if (topSolutionsMQ[i] == null)
			{
				topSolutions[i] = currentSolution;
				topSolutionsMQ[i] = currentSolutionMQ;
				break;
			} 
			else if (topSolutionsMQ[i] < currentSolutionMQ)
			{
				int[] auxSolution = topSolutions[i];
				double auxSolutionMQ = topSolutionsMQ[i];

				topSolutions[i] = currentSolution;
				topSolutionsMQ[i] = currentSolutionMQ;

				currentSolution = auxSolution;
				currentSolutionMQ = auxSolutionMQ;
			}
		}
	}

	@Override
	public int[] createSolution(int paramNumber, double factor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] createSolution(ModuleDependencyGraph mdg, double a1, double a2, double b1, double b2) {
		// TODO Auto-generated method stub
		return null;
	}
}