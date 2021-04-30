package br.com.ppgi.unirio.teaching.clustering.search;

import br.com.ppgi.unirio.teaching.clustering.model.Project;
import br.com.ppgi.unirio.teaching.clustering.model.ProjectClass;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ClusteringCalculator
{
	private int classCount;
	private int packageCount;
	private int[][] dependencies;

	/**
	 * Inicializa o calculador de acoplamento
	 */
	public ClusteringCalculator(Project project, int packageCount) throws Exception
	{
		this.classCount = project.getClassCount();
		this.packageCount = packageCount;
		prepareDependenciesAmongClasses(project);
	}

	/**
	 * Retorna o número de classes
	 */
	public int getClassCount()
	{
		return classCount;
	}

	/**
	 * Retorna o número de pacotes
	 */
	public int getPackageCount()
	{
		return packageCount;
	}
	
	/**
	 * Prepara as classes para serem processadas pelo programa
	 */
	private void prepareDependenciesAmongClasses(Project project) throws Exception
	{
		this.dependencies = new int[classCount][classCount];
		
		for (int i = 0; i < classCount; i++)
		{
			ProjectClass _class = project.getClassIndex(i);

			for (int j = 0; j < _class.getDependencyCount(); j++)
			{
				String targetName = _class.getDependencyIndex(j).getElementName();
				int classIndex = project.getClassIndex(targetName);
				
				if (classIndex == -1)
					throw new Exception ("Class not registered in project: " + targetName);
				
				dependencies[i][classIndex]++;
			}
		}
	}

	/**
	 * Calcula o coeficiente de modularidade do projeto
	 */
	public double calculateModularizationQuality(int[] solution, double a1, double a2, double b1, double b2)
	{
		int[] inboundEdges = new int[packageCount];
		int[] outboundEdges = new int[packageCount];
		int[] intraEdges = new int[packageCount];

		for (int i = 0; i < classCount; i++)
		{
			int sourcePackage = solution[i];
			
			for (int j = 0; j < classCount; j++)
			{
				if (dependencies[i][j] > 0)
				{
					int targetPackage = solution[j];
					
					if (targetPackage != sourcePackage)
					{
						outboundEdges[sourcePackage]++;
						inboundEdges[targetPackage]++;
					}
					else
						intraEdges[sourcePackage]++;
				}
			}
		}
		
		double mq = 0.0;

		for (int i = 0; i < packageCount; i++)
		{
			int inter = inboundEdges[i] + outboundEdges[i];
			int intra = intraEdges[i];
			
			if (intra != 0)
//			if (intra != 0 && inter != 0)
			{
				double mf = (a1* intra + a2* inter) / (b1 * intra + b2 * inter);
				
//				double mf = 0;
//    	    	Expression expression = new ExpressionBuilder(objectiveEquation)
//    	        	      .variables("x")
//    	        	      .variables("y")
//    	        	      .build()
//    					  .setVariable("x", intra)
//    					  .setVariable("y", inter);
//    	    	
//    	    	try {
//    	    		mf =  expression.evaluate();	    
//    			} catch (ArithmeticException e) {
////    				 TODO Auto-generated catch block
////    				e.printStackTrace();
//    				mf = 0;
//    			}
//
//    			double mf = intra / (intra + 0.5 * inter);
				
				mq += mf;
			}
		}

		return mq;
	}
}