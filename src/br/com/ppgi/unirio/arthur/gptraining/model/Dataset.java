package br.com.ppgi.unirio.arthur.gptraining.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um dataset para o problema de geração de expressões
 * 
 * @author marciobarros
 */
public class Dataset
{
	private List<Entrada> entradas;
	private String nome;

	/**
	 * Classe que representa uma entrada do dataset
	 */
	public class Entrada
	{
		private double x;
		private double y;
		
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
	}
	
	/**
	 * Inicializa o dataset
	 */
	public Dataset()
	{
		this.entradas = new ArrayList<Entrada>();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEntradas(List<Entrada> entradas) {
		this.entradas = entradas;
	}
	
	/**
	 * Adiciona uma entrada no dataset
	 */
	public void adiciona(double x, double y)
	{
		Entrada entrada = new Entrada();
		entrada.setX(x);
		entrada.setY(y);
		entradas.add(entrada);
	}
	
	/**
	 * Retorna todas as entradas do dataset
	 */
	public Iterable<Entrada> getEntradas()
	{
		return entradas;
	}
}