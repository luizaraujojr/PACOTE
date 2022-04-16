package unirio.teaching.clustering.search.constructive;

import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.search.model.ModuleDependencyGraph;

/**
 * Abstract constructive method
 * 
 * @author User
 */
public abstract class ConstrutiveAbstract
{
	public abstract int[] createSolution(ModuleDependencyGraph mdg);
//
	public abstract  int[] createSolution(int paramNumber, int minValue, int maxValue);
//
//	public abstract int[] createSolution(ModuleDependencyGraph mdg, int[] equationParams, Project project);

	public int[] createSolution(ModuleDependencyGraph mdg, int[] equationParams, Project project, boolean[] usedMetrics) {
		// TODO Auto-generated method stub
		return null;
	}
}