package br.com.ppgi.unirio.arthur.gptraining.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Populacao 
{
	public List<ArvoreExpressao> individuos;
	public int  numeroGeracao;
	
	public Populacao()
	{
		individuos = new ArrayList<ArvoreExpressao>();
		numeroGeracao = 0;
	}
	
	public Populacao(int tamanhoPopulacao)
	{
		individuos = new ArrayList<ArvoreExpressao>(tamanhoPopulacao);
		numeroGeracao = 0;
	}
	
	public Populacao(List<ArvoreExpressao> listaIndividuos)
	{
		individuos = listaIndividuos;
	}
	
	public List<ArvoreExpressao> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(List<ArvoreExpressao> individuos) {
		this.individuos = individuos;
	}

	public int getNumeroGeracao() {
		return numeroGeracao;
	}

	public void setNumeroGeracao(int numeroGeracao) {
		this.numeroGeracao = numeroGeracao;
	}
	
	public void adicionaIndividuo(ArvoreExpressao arvore)
	{
		individuos.add(arvore);
	}

	public void embaralhaPopulacao() {
		Collections.shuffle(individuos);
	}
}
