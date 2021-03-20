package br.com.ppgi.unirio.arthur.operacoes;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.CalculadorFitness;
import br.com.ppgi.unirio.arthur.gptraining.model.Dataset;

import java.util.Collections;
import java.util.List;

/**
 * Classe onde será gerada a população inicial, a medição e classificação por aptidão, e onde serão realizadas as operações genéticas conforme o avanço das gerações.
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
	 * Ordena por aptidão as árvores presentes na lista de entrada do método.
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
