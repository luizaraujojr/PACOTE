package br.com.ppgi.unirio.arthur.gptraining.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.Dataset;
import br.com.ppgi.unirio.arthur.gptraining.model.No;
import br.com.ppgi.unirio.arthur.operacoes.OperacaoGenetica;

public class TestOperacoesGeneticas {

	/*
	 * �rvore que representa a express�o x� + 2x� + 5
	 */
	private ArvoreExpressao criarArvoreAlturaQuatro()
	{
		No raiz = new No('+', new No('*', new No("x"), new No('*', new No("x"), new No("x"))), 
				new No('+', new No(5.0), new No('*', new No('*', new No("x"), new No("x")), new No(2.0))));
		return new ArvoreExpressao(raiz);
	}
	
	@Test
	public void testQuebraPontoCrossover() {
		ArvoreExpressao arvoreA = criarArvoreAlturaQuatro();
		
		OperacaoGenetica operacaoGenetica = new OperacaoGenetica();
		No raizA = operacaoGenetica.quebraNoPontoCrossover(arvoreA.getRaiz(), 3);
		No raizB = new No('+', new No(5.0), new No('*', new No('*', new No("x"), new No("x")), new No(2.0))); //�rvore 2x� + 5
		No raizC = new No('*', new No('*', new No("x"), new No("x")), new No(2.0)); //�rvore 2x�
		
		assertTrue(arvoreA.compararEstruturaNos(raizA, raizB));
		assertFalse(arvoreA.compararEstruturaNos(raizB, raizC));
	}
	
	@Test
	public void testContagemOperacoes() {		
		ArvoreExpressao arvore = new ArvoreExpressao();
		
		No raizA = new No('+', new No('/', new No(5.0), new No("x")), new No('*', new No('*', new No("x"), new No("x")), new No(2.0))); //�rvore 5/x + 2x�
		No raizB = new No('*', new No('*', new No("x"), new No("x")), new No(2.0)); //�rvore 2x�
		
		assertEquals(4 ,arvore.getQuantidadeOperadores(raizA));
		assertEquals(2 ,arvore.getQuantidadeOperadores(raizB));
	}
	
	@Test
	public void testMutacao() {		
		Random random = new Random();
		OperacaoGenetica operacao = new OperacaoGenetica();
		
		No raizA = new No('+', new No('*', new No("x"), new No("x")), new No(1.0)); //�rvore x� + 1
		assertTrue(operacao.mutacaoPontoAleatorioSimples(raizA, 0, random));
		
		operacao = new OperacaoGenetica();
		
		No raizB = new No('+', new No("x"), new No()); //�rvore com filho direito sem preenchimento de terminal ou opera��o
		assertFalse(operacao.mutacaoPontoAleatorioSimples(raizB, 2, random));
		
	}
	
	@Test
	public void testSelecao() {		
		Random random = new Random();
		random.setSeed(1234567);
		OperacaoGenetica operacao = new OperacaoGenetica();
		
		No raizA = new No('+', new No('*', new No("x"), new No("x")), new No(1.0)); //�rvore x� + 1
		assertTrue(operacao.mutacaoPontoAleatorioSimples(raizA, 0, random));
		
		operacao = new OperacaoGenetica();
		
		No raizB = new No('+', new No("x"), new No()); //�rvore com filho direito sem preenchimento de terminal ou opera��o
		assertFalse(operacao.mutacaoPontoAleatorioSimples(raizB, 2, random));
	}	
	
	public static void monta (Dataset dataset)
	{
		for (int x = 1; x <= 5; x++)
		{
			dataset.adiciona(x, 4 * x);
		}
	}
}
