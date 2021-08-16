package br.com.ppgi.unirio.teaching.clustering.search.constructive;

import br.com.ppgi.unirio.teaching.clustering.model.Project;
import br.com.ppgi.unirio.teaching.clustering.search.model.ModuleDependencyGraph;
import br.com.ppgi.unirio.teaching.clustering.search.utils.PseudoRandom;

/**
 * Constructive random method
 * 
 * @author User
 */
public class ConstrutiveRandom extends ConstrutiveAbstract
{
//	@Override
//	public int[] createSolution(ModuleDependencyGraph mdg, double a1, double a2, double b1, double b2)
//	{
//		int classCount = mdg.getSize();
//		int[] solution = new int[classCount];
//		
//		for (int i = 0; i < classCount; i++)
//			solution[i] = PseudoRandom.randInt(0, classCount-1);
//
//		return solution;
//	}
	
	@Override
	public int[] createSolution(int paramNumber, double factor)
	{
		int[] solution = new int[paramNumber];
		
		for (int i = 0; i < paramNumber; i++)
			solution[i] = (int) (PseudoRandom.randInt(1, 9) * factor);

		return solution;
	}

@Override
public int[] createSolution(ModuleDependencyGraph mdg, double a1, double a2, double b1, double b2) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int[] createSolution(ModuleDependencyGraph mdg, int[] functionParams, Project project) {
	// TODO Auto-generated method stub
	return null;
}

//@Override
//public int[] createSolution(ModuleDependencyGraph mdg, double[] functionParams) {
//	// TODO Auto-generated method stub
//	return null;
//}
}