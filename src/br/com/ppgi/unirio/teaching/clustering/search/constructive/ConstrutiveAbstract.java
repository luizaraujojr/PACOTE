package br.com.ppgi.unirio.teaching.clustering.search.constructive;

import br.com.ppgi.unirio.teaching.clustering.search.model.ModuleDependencyGraph;

/**
 * Abstract constructive method
 * 
 * @author User
 */
public abstract class ConstrutiveAbstract
{
	public abstract int[] createSolution(ModuleDependencyGraph mdg, double a1, double a2, double b1, double b2);
	
	public abstract int[] createSolution(int paramNumber, double factor);
	
	public abstract int[] createSolution(ModuleDependencyGraph mdg, int[] functionParams);
}