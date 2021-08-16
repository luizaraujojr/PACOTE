package br.com.ppgi.unirio.teaching.clustering.model;

import java.util.Vector;

import org.pfsw.odem.DependencyClassification;
import org.pfsw.odem.TypeClassification;
import org.pfsw.odem.Visibility;

/**
 * Classe que representa um elemento em um programa
 * 
 * @author Marcio Barros
 */
public class ProjectClass
{
//	private String name;
//	private ProjectPackage _package;
//	private ElementType type;
//	private ElementVisibility visibility;
//	private boolean isAbstract;
//	private Vector<Dependency> dependencies;
	
	
	private String name;
	private ProjectPackage _package;
	private TypeClassification type;
	private Visibility visibility;
	private boolean isAbstract;
	private Vector<Dependency> dependencies;
	

	/**
	 * Inicializa uma classe
	 */
//	public ProjectClass(String name, ElementType type, ElementVisibility visibility, boolean isAbstract)
//	{
//		this.name = name;
//		this._package = null;
//		this.type = type;
//		this.visibility = visibility;
//		this.isAbstract = isAbstract;
//		this.dependencies = new Vector<Dependency>();
//	}
	
	public ProjectClass(String name, TypeClassification typeClassification, Visibility visibility, boolean isAbstract)
	{
		this.name = name;
		this._package = null;
		this.type = typeClassification;
		this.visibility = visibility;
		this.isAbstract = isAbstract;
		this.dependencies = new Vector<Dependency>();
	}
	
	

	/**
	 * Inicializa uma classe com valores default
	 */
//	public ProjectClass(String name)
//	{
//		this(name, ElementType.CLASS, ElementVisibility.PUBLIC, false);
//	}
	
	public ProjectClass(String name)
	{
		this(name, TypeClassification.CLASS, Visibility.PUBLIC, false);
	}

	/**
	 * Inicializa uma classe com valores default
	 */
	public ProjectClass(String name, ProjectPackage _package)
	{
		this(name);
		setPackage(_package);
	}
	
	/**
	 * Retorna o nome da classe
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Retorna o pacote da classe
	 */
	public ProjectPackage getPackage()
	{
		return _package;
	}
	
	/**
	 * Altera o pacote da classe
	 */
	public void setPackage(ProjectPackage _package)
	{
		this._package = _package;
	}
	
	/**
	 * Retorna o tipo da classe
	 */
//	public ElementType getType()
//	{
//		return type;
//	}
	public TypeClassification getType()
	{
		return type;
	}

	/**
	 * Retorna a visibilidade da classe
	 */
	public Visibility getVisibility()
	{
		return visibility;
	}

	/**
	 * Verifica se a classe é abstrata
	 */
	public boolean isAbstract()
	{
		return isAbstract;
	}

	/**
	 * Retorna o número de dependências da classe
	 */
	public int getDependencyCount()
	{
		return dependencies.size();
	}
	
	/**
	 * Retorna uma dependência, dado seu índice
	 */
	public Dependency getDependencyIndex(int index)
	{
		return dependencies.elementAt(index);
	}
	
	/**
	 * Adiciona uma dependência na classe
	 */
//	public void addDependency(String elementName, DependencyType type)
//	{
//		Dependency dependency = new Dependency(elementName, type);
//		dependencies.add(dependency);
//	}
	
	
	public void addDependency(String elementName, DependencyClassification type)
	{
		Dependency dependency = new Dependency(elementName, type);
		dependencies.add(dependency);
	}
	
	
	/**
	 * Adiciona uma dependência na classe
	 */
//	public void addDependency(String elementName)
//	{
//		Dependency dependency = new Dependency(elementName, DependencyType.USES);
//		dependencies.add(dependency);
//	}
	
	
	public void addDependency(String elementName)
	{
		Dependency dependency = new Dependency(elementName, DependencyClassification.EXTENSION);
		dependencies.add(dependency);
	}
	
	
	/**
	 * Retorna uma enumeração das dependências da classe
	 */
	public Vector<Dependency> getDependencies()
	{
		return dependencies;
	}
	
	
	/**
	 * Remove uma depend�ncia da classe
	 */
	public void removeDependency(int index)
	{
		dependencies.remove(index);
	}

}