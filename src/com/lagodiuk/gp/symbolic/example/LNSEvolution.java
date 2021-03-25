package com.lagodiuk.gp.symbolic.example;


import java.util.LinkedList;
import java.util.List;

import com.lagodiuk.gp.symbolic.LNSEvolutionFitness;
import com.lagodiuk.gp.symbolic.SymbolicRegressionEngine;
import com.lagodiuk.gp.symbolic.SymbolicRegressionIterationListener;
import com.lagodiuk.gp.symbolic.interpreter.Expression;
import com.lagodiuk.gp.symbolic.interpreter.Functions;


public class LNSEvolution {

	public static void main(String[] args) {
		
		LNSEvolutionFitness fitnessFunction = new LNSEvolutionFitness (); 
		
		SymbolicRegressionEngine engine =
				new SymbolicRegressionEngine(
						fitnessFunction,
						list("x", "y", "z"),
						list(Functions.ADD, Functions.SUB, Functions.MUL, Functions.VARIABLE, Functions.CONSTANT));

		addListener(engine);

		engine.evolve(100); //coloquei 100, o original era  200.
		System.out.println(engine.getBestSyntaxTree().print());
	}

	private static void addListener(SymbolicRegressionEngine engine) {
		engine.addIterationListener(new SymbolicRegressionIterationListener() {
			private double prevFitValue = -1;

			@Override
			public void update(SymbolicRegressionEngine engine) {
				Expression bestSyntaxTree = engine.getBestSyntaxTree();
				double currFitValue = engine.fitness(bestSyntaxTree);
				if (Double.compare(currFitValue, this.prevFitValue) != 0) {
					System.out.println("Func = " + bestSyntaxTree.print());
				}
				System.out.println(String.format("%s \t %s", engine.getIteration(), currFitValue));
				this.prevFitValue = currFitValue;
				if (currFitValue < 5) {
					engine.terminate();
				}
			}
		});
	}

	private static <T> List<T> list(T... items) {
		List<T> list = new LinkedList<T>();
		for (T item : items) {
			list.add(item);
		}
		return list;
	}

}
