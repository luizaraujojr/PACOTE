package unirio.teaching.clustering.tests;

import junit.framework.TestCase;
import unirio.teaching.clustering.model.Project;
import unirio.teaching.clustering.model.ProjectClass;
import unirio.teaching.clustering.search.mojo.MoJoCalculator;

public class TestMojo extends TestCase
{
	public void testStrict()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG3 Classe3" + System.lineSeparator());
		sb.append("PKG4 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 2, 3 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(100.0, mojo, 0.001);
	}

	public void testOneFlip()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG3 Classe3" + System.lineSeparator());
		sb.append("PKG4 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 3, 2 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(100.0, mojo, 0.001);
	}

	public void testBindTwoClasses()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG3 Classe3" + System.lineSeparator());
		sb.append("PKG3 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 2, 2 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(100.0, mojo, 0.001);
	}

	public void testBindTwoClassesOnDifferentCluster()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG3 Classe3" + System.lineSeparator());
		sb.append("PKG3 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 3, 3 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(100.0, mojo, 0.001);
	}

	public void testBindThreeClasses()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG2 Classe3" + System.lineSeparator());
		sb.append("PKG2 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 1, 1 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(100.0, mojo, 0.001);
	}

	public void testBindThreeClassesWithOneError()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG2 Classe3" + System.lineSeparator());
		sb.append("PKG2 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 1, 2 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(50.0, mojo, 0.001);
	}

	public void testBindThreeClassesWithTwoErrors()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG2 Classe3" + System.lineSeparator());
		sb.append("PKG2 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 2, 3 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(33.33, mojo, 0.001);
	}

	public void testBindThreeClassesWithTwoErrorsSameCluster()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG2 Classe2" + System.lineSeparator());
		sb.append("PKG2 Classe3" + System.lineSeparator());
		sb.append("PKG2 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 2, 2 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(50.0, mojo, 0.001);
	}

	public void testWeAreAllTogether()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG1 Classe2" + System.lineSeparator());
		sb.append("PKG1 Classe3" + System.lineSeparator());
		sb.append("PKG1 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 0, 0, 0 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(100.0, mojo, 0.001);
	}

	public void testWeShouldBeTogetherButThereIsRogue()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG1 Classe2" + System.lineSeparator());
		sb.append("PKG1 Classe3" + System.lineSeparator());
		sb.append("PKG1 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 0, 0, 1 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(50.0, mojo, 0.001);
	}

	public void testWeShouldBeTogetherButSadlySplit()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG1 Classe2" + System.lineSeparator());
		sb.append("PKG1 Classe3" + System.lineSeparator());
		sb.append("PKG1 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 0, 1, 1 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(0.0, mojo, 0.001);
	}

	public void testWeShouldBeTogetherButHateEachOther()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PKG1 Classe1" + System.lineSeparator());
		sb.append("PKG1 Classe2" + System.lineSeparator());
		sb.append("PKG1 Classe3" + System.lineSeparator());
		sb.append("PKG1 Classe4" + System.lineSeparator());
		
		Project project = new Project("Test");
		project.addClass(new ProjectClass("Classe1"));
		project.addClass(new ProjectClass("Classe2"));
		project.addClass(new ProjectClass("Classe3"));
		project.addClass(new ProjectClass("Classe4"));
		
		int[] solution = { 0, 1, 2, 3 };
		
		MoJoCalculator calculator = new MoJoCalculator(sb);
		double mojo = calculator.mojofmnew(project, solution);
		assertEquals(0.0, mojo, 0.001);
	}
}