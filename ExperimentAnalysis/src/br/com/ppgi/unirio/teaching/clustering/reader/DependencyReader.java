package br.com.ppgi.unirio.teaching.clustering.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import br.com.ppgi.unirio.teaching.clustering.model.Project;
import br.com.ppgi.unirio.teaching.clustering.model.ProjectClass;

public class DependencyReader
{
	private static final String SPLITTER = "\\s+";//split por qualquer espacamento

	public Project load(String filename) throws FileNotFoundException
	{
		FileInputStream fis = new FileInputStream(filename);
		Scanner sc = new Scanner(fis);
		Project project = new Project(filename);

		while (sc.hasNextLine())
		{
			String line = sc.nextLine();
			String[] token = line.split(SPLITTER);
			
            if(token.length  >= 2){
            	String firstClass = token[0];
				String secondClass = token[1];
				
				int firstIndex = project.getClassIndex(firstClass);
				
				if (firstIndex == -1)
				{
					project.addClass(new ProjectClass(firstClass));
					firstIndex = project.getClassCount() - 1;
				}
				
				int secondIndex = project.getClassIndex(secondClass);
				
				if (secondIndex == -1)
				{
					project.addClass(new ProjectClass(secondClass));
//					secondIndex = project.getClassCount() - 1;
				}
				
				ProjectClass firstProjectClass = project.getClassIndex(firstIndex);
				firstProjectClass.addDependency(secondClass);
            }
		}

		sc.close();
		return project;
	}
}