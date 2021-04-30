package br.com.ppgi.unirio.teaching.clustering.model;

/**
 * Classe que representa uma dependÍncia entre duas classes
 * 
 * @author Marcio Barros
 */
public class Dependency
{
	private String elementName;
	private DependencyType type;

	/**
	 * Inicializa uma dependÍncia de uma classe para outra
	 */
	public Dependency(String name, DependencyType type)
	{
		this.elementName = name;
		this.type = type;
	}
	
	/**
	 * Retorna o nome da classe dependida
	 */
	public String getElementName()
	{
		return elementName;
	}
	
	/**
	 * Retorna o tipo da dependÍncia
	 */
	public DependencyType getType()
	{
		return type;
	}
}