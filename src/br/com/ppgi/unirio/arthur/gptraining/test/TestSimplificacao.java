package br.com.ppgi.unirio.arthur.gptraining.test;
import static org.junit.Assert.*;

import org.junit.Test;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.CalculadorFitness;
import br.com.ppgi.unirio.arthur.gptraining.model.Dataset;
import br.com.ppgi.unirio.arthur.gptraining.model.No;

public class TestSimplificacao {

	/**
	 * Testes envolvendo a simplifica豫o/redu豫o de 햞vores
	 */
	@Test
	public void testSimplificadorMuitoSimplesUm()
	{
		ArvoreExpressao arvore = criaArvoreMuitoSimplesUm();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getSimboloTerminal().equals("2") || arvore.getRaiz().getSimboloTerminal().equals("2.0"));
	}
	
	@Test
	public void testSimplificadorMuitoSimplesDois()
	{
		ArvoreExpressao arvore = criaArvoreMuitoSimplesDois();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getSimboloTerminal().equals("0") || arvore.getRaiz().getSimboloTerminal().equals("0.0"));
	}
	
	@Test
	public void testSimplificadorMuitoSimplesTres()
	{
		ArvoreExpressao arvore = criaArvoreMuitoSimplesTres();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getSimboloTerminal().equals("x"));
	}
	
	@Test
	public void testSimplificadorMuitoSimplesQuatro()
	{
		ArvoreExpressao arvore = criaArvoreMuitoSimplesQuatro();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getSimboloTerminal().equals("x"));
	}
	
	@Test
	public void testSimplificadorSimples()
	{
		ArvoreExpressao arvore = criaArvoreSimples();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("4") || arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("4.0"));
	}
	
	@Test
	public void testSimplificadorComplexoUm()
	{
		ArvoreExpressao arvore = criaArvoreComplexaUm();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("2") || arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("2.0"));
		assertEquals("Subtracao", arvore.getRaiz().getOperador().toString());
	}
	
	@Test
	public void testSimplificadorComplexoDois()
	{
		ArvoreExpressao arvore = criaArvoreComplexaDois();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getNoFilhoEsquerda().getSimboloTerminal().equals("x") && 
				(arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("-1.0") || arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("-1")));
	}
	
	
	@Test
	public void testSimplificadorMuitoComplexo()
	{
		ArvoreExpressao arvore = criaArvoreMuitoComplexa();
		arvore = arvore.simplificarArvore(arvore);
		assertTrue(arvore.getRaiz().getNoFilhoEsquerda().getSimboloTerminal().equals("x") && 
				(arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("-1.0") || arvore.getRaiz().getNoFilhoDireita().getSimboloTerminal().equals("-1")));
	}
	
	/**
	 * 햞vore (1 + 1)
	 */
	public ArvoreExpressao criaArvoreMuitoSimplesUm()
	{
		return new ArvoreExpressao(new No('+', new No(1), new No(1)));
	}
	
	/**
	 * 햞vore (x * 0)
	 */
	public ArvoreExpressao criaArvoreMuitoSimplesDois()
	{
		return new ArvoreExpressao(new No('*', new No("x"), new No(0)));
	}
	
	/**
	 * 햞vore (1 * x)
	 */
	public ArvoreExpressao criaArvoreMuitoSimplesTres()
	{
		return new ArvoreExpressao(new No('*', new No(1), new No("x")));
	}
	
	/**
	 * 햞vore (x / 1)
	 */
	public ArvoreExpressao criaArvoreMuitoSimplesQuatro()
	{
		return new ArvoreExpressao(new No('/', new No("x"), new No(1)));
	}
	
	
	/**
	 * 햞vore (x + (1 + 3))
	 */
	public ArvoreExpressao criaArvoreSimples()
	{
		return new ArvoreExpressao(new No('+', new No("x"), new No('+', new No(1), new No(3))));
	}
	
	/**
	 * 햞vore (x + (1 - 3))
	 */
	public ArvoreExpressao criaArvoreComplexaUm()
	{
		return new ArvoreExpressao(new No('+', new No("x"), new No('-', new No(1), new No(3))));
	}
	
	/**
	 * 햞vore ((5 - 5) - x)
	 */
	public ArvoreExpressao criaArvoreComplexaDois()
	{
		return new ArvoreExpressao(new No('-', new No('-', new No(5), new No(5)), new No("x")));
	}
	
	/**
	 * 햞vore ((5 - 5) - x / (5 - 1) - (1 + 2))
	 */
	public ArvoreExpressao criaArvoreMuitoComplexa()
	{
		return new ArvoreExpressao(new No('/', new No('-', new No('-', new No(5), new No(5)), new No("x")), 
				new No('-', new No('-', new No(5), new No(1)), new No('+', new No(1), new No(2)))));
	}
}
