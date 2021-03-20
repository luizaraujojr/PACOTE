package br.com.ppgi.unirio.arthur.operacoes;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.CalculadorFitness;
import br.com.ppgi.unirio.arthur.gptraining.model.Dataset;

import java.util.Collections;
import java.util.List;

/**
 * Classe onde ser� gerada a popula��o inicial, a medi��o e classifica��o por aptid�o, e onde ser�o realizadas as opera��es gen�ticas conforme o avan�o das gera��es.
 */
public class ProgramacaoGenetica {

	public List<ArvoreExpressao> evoluir(List<ArvoreExpressao> populacaoInicial, int numeroGeracoes)
	{
		
		return null;
	}
	
	public void avancarGeracao(List<ArvoreExpressao> populacao)
	{
		
	}
	
	/**
	 * Ordena por aptid�o as �rvores presentes na lista de entrada do m�todo.
	 */
	public List<ArvoreExpressao> avaliarAptidaoCandidatos(List<ArvoreExpressao> geracao, Dataset dataset)
	{
		List<ArvoreExpressao> listaOrdenada = geracao;
		ArvoreExpressao arvore = new ArvoreExpressao();
		CalculadorFitness calculador = new CalculadorFitness();
		for (int i = 0; i < geracao.size(); i++)
		{
			listaOrdenada.get(i).setAptidao(calculador.calcula(listaOrdenada.get(i), dataset));
		}
		Collections.sort(listaOrdenada);
		return listaOrdenada;
		
	}
	
}
