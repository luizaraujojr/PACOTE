package br.com.ppgi.unirio.teaching.clustering.search.constructive;

import br.com.ppgi.unirio.teaching.clustering.search.model.ModuleDependencyGraph;

/**
 * Abstract constructive method
 * 
 * @author User
 */
public abstract class ConstrutiveAbstract
{
	public abstract int[] createSolution(ModuleDependencyGraph mdg);
}