package unirio.teaching.clustering.search.constructive;

import unirio.teaching.clustering.search.model.ModuleDependencyGraph;
import unirio.teaching.clustering.search.utils.PseudoRandom;

/**
 * Constructive random method
 * 
 * @author User
 */
public class ConstrutiveRandom extends ConstrutiveAbstract
{
	@Override
	public int[] createSolution(ModuleDependencyGraph mdg)
	{
		int classCount = mdg.getSize();
		int[] solution = new int[classCount];
		
		for (int i = 0; i < classCount; i++)
			solution[i] = PseudoRandom.randInt(0, classCount-1);

		return solution;
	}
	
	
	@Override
	public int[] createSolution(int paramNumber, int minValue, int maxValue)
	{
		int[] solution = new int[paramNumber];
		
		for (int i = 0; i < paramNumber; i++)
			solution[i] = (int) (PseudoRandom.randInt(minValue, maxValue));

		return solution;
	}


	@Override
	public int[] createSolution(ModuleDependencyGraph mdg, int[] equationParams) {
		// TODO Auto-generated method stub
		return null;
	}
}