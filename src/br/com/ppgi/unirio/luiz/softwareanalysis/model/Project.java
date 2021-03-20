package br.com.ppgi.unirio.luiz.softwareanalysis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Classe que representa um projeto
 * 
 * @author Marcio Barros
 */
public class Project
{
	private String name;
	private Vector<ProjectPackage> packages;
	private Vector<ProjectClass> classes;

	/**
	 * Inicializa uma aplicacao
	 */
	public Project(String name)
	{
		this.name = name;
		this.packages = new Vector<ProjectPackage>();
		this.classes = new Vector<ProjectClass>();
	}
	
	/**
	 *Retorna o nome da aplicacao
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Retorna o numero de pacotes da aplicacao
	 */
	public int getPackageCount()
	{
		return packages.size();
	}
	
	/**
	 * Retorna um pacote da aplicacao, dado seu indice
	 */
	public ProjectPackage getPackageIndex(int index)
	{
		return packages.elementAt(index);
	}

	/**
	 * Retorna o inndice de um pacote no projeto
	 */
	public int getIndexForPackage(ProjectPackage _package)
	{
		return packages.indexOf(_package);
	}
	
	/**
	 * Retorna um pacote da aplicacao, dado seu nome
	 */
	public ProjectPackage getPackageName(String name)
	{
		for (ProjectPackage p : packages)
			if (p.getName().compareToIgnoreCase(name) == 0)
				return p;
		
		return null;
	}
	
	/**
	 * Gets a list of all packages whom a given package depends on
	 */
	public List<ProjectPackage> getPackageDependencies(ProjectPackage source)
	{
		List<ProjectPackage> resultado = new ArrayList<ProjectPackage>();
		
		for (ProjectClass _class : classes)
			if (_class.getPackage() == source)
				addClassPackageDependencies(resultado, _class);
		
		return resultado;
	}

	/**
	 * Adds all packages on which a class depends to a list of packages
	 */
	private void addClassPackageDependencies(List<ProjectPackage> resultado, ProjectClass _class)
	{
		ProjectPackage sourcePackage = _class.getPackage();
		
		for (Dependency dependency : _class.getDependencies())
		{
			String className = dependency.getElementName();
			ProjectClass targetClass = getClassName(className);
			
			if (targetClass != null)
			{
				ProjectPackage targetPackage = targetClass.getPackage();
				
				if (targetPackage != sourcePackage && !resultado.contains(targetPackage))
					resultado.add(targetPackage);
			}
		}
	}
	
	/**
	 * Adiciona um pacote na aplicacao
	 */
	public ProjectPackage addPackage(String name)
	{
		ProjectPackage aPackage = new ProjectPackage(name);
		packages.add(aPackage);
		return aPackage;
	}

	/**
	 * Remove todos os pacotes do projeto
	 */
	public void clearPackages()
	{
		packages.clear();
	}
	
	/**
	 * Retorna uma enumeracao dos pacotes da aplicacao
	 */
	public Iterable<ProjectPackage> getPackages()
	{
		return packages;
	}
	
	/**
	 * Retorna o numero de classes do projeto
	 */
	public int getClassCount()
	{
		return classes.size();
	}
	
	/**
	 * Retorna o numero de classes em um pacote do projeto
	 */
	public int getClassCount(String packageName)
	{
		int count = 0;
		
		for (ProjectClass _class : classes)
			if (_class.getPackage().getName().compareToIgnoreCase(packageName) == 0)
				count++;
		
		return count;
	}

	/**
	 * Retorna uma classe, dado seu indice no projeto
	 */
	public ProjectClass getClassIndex(int index)
	{
		return classes.elementAt(index);
	}

	/**
	 * Retorna uma classe, dado seu nome
	 */
	public ProjectClass getClassName(String name)
	{
 		for (ProjectClass c : classes)
			if (c.getName().compareToIgnoreCase(name) == 0)
				return c;
		
		return null;
	}

	/**
	 * Retorna o indice de uma classe, dado seu nome
	 */
	public int getClassIndex(String name)
	{
		for (int i = 0; i < classes.size(); i++)
		{
			ProjectClass c = classes.elementAt(i);
			
			if (c.getName().compareToIgnoreCase(name) == 0)
				return i;
		}
		
		return -1;
	}

	/**
	 * Retorna o indice de uma classe no projeto
	 */
	public int getIndexForClass(ProjectClass _class)
	{
		return classes.indexOf(_class);
	}
	
	/**
	 * Adiciona uma classe no projeto
	 */
	public void addClass(ProjectClass c)
	{
		classes.add(c);
	}
	
	/**
	 * Remove uma classe do projeto, dado seu indice
	 */
	public void removeClass(int index)
	{
		classes.remove(index);
	}

	/**
	 * Adiciona uma dependencia entre duas classes no projeto
	 */
	public void addDependency(String sourceClass, String targetClass)
	{
		ProjectClass source = getClassName(sourceClass);
		
		if (source != null)
			source.addDependency(targetClass);
	}
	
	/**
	 * Retorna o numero de dependencias do projeto 
	 */
	public int getDependencyCount()
	{
		int count = 0;
		
		for(ProjectClass c: classes)
			count += c.getDependencyCount();
		
		return count;
	}
	
	/**
	 * Retorna uma enumera��o das classes do projeto
	 */
	public Vector<ProjectClass> getClasses()
	{
		return classes;
	}
	
	/**
	 * Retorna uma enumera��o das classes do projeto
	 */
	public Vector<ProjectClass> getClasses(ProjectPackage projectPackage)
	{
		Vector<ProjectClass> classesByPackage = new Vector<ProjectClass>();
		for(ProjectClass c: classes) {
			if(c.getPackage()==projectPackage) {
				classesByPackage.add(c);
			}
			
		}
			
		return classesByPackage;
	}
}