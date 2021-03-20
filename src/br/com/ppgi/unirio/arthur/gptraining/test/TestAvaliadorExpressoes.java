package br.com.ppgi.unirio.arthur.gptraining.test; 

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.CalculadorFitness;
import br.com.ppgi.unirio.arthur.gptraining.model.Dataset;
import br.com.ppgi.unirio.arthur.gptraining.model.No;
import br.com.ppgi.unirio.arthur.gptraining.model.Operacao;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de teste para o avaliador de expressıes
 * 
 * @author marciobarros
 */
public class TestAvaliadorExpressoes
{
	/**
	 * Dataset que representa a equa√ß√£o Y = x + 1
	 */
	private Dataset criaDatasetLinear()
	{
		Dataset ds = new Dataset();
		ds.adiciona(1, 2);
		ds.adiciona(2, 3);
		ds.adiciona(3, 4);
		ds.adiciona(4, 5);
		return ds;
	}
	
	/**
	 * Dataset que representa a equa√ß√£o Y = 2x
	 */
	private Dataset criaDatasetMultiplicativo()
	{
		Dataset ds = new Dataset();
		ds.adiciona(1, 2);
		ds.adiciona(2, 4);
		ds.adiciona(3, 6);
		ds.adiciona(4, 8);
		return ds;
	}
	
	/**
	 * Dataset que representa a equa√ß√£o Y = 1/x + 1/2 
	 */
	private Dataset criaDatasetDivisivo()
	{
		Dataset ds = new Dataset();
		ds.adiciona(1, 1.5);
		ds.adiciona(2, 1);
		ds.adiciona(3, 0.83);
		ds.adiciona(4, 0.75);
		ds.adiciona(5, 0.7);
		return ds;
	}
	
	/**
	 * Dataset que representa a equa√ß√£o Y = 2x + 3
	 */
	private Dataset criaDatasetComposto()
	{
		Dataset ds = new Dataset();
		ds.adiciona(1, 5);
		ds.adiciona(2, 7);
		ds.adiciona(3, 9);
		ds.adiciona(4, 11);
		
		ds.adiciona(-1, 1);
		ds.adiciona(-2, -1);
		ds.adiciona(-3, -3);
		ds.adiciona(-4, -5);
		return ds;
	}
	
	/**
	 * Dataset que representa a equaÁ„o Y = 3x≤ + 1
	 */
	private Dataset criaDatasetEquacaoSegundoGrau()
	{
		Dataset ds = new Dataset();
		ds.adiciona(1, 4);
		ds.adiciona(2, 13);
		ds.adiciona(3, 28);
		ds.adiciona(4, 49);
		ds.adiciona(5, 76);
		return ds;
	}
	
	/**
	 * Dataset que representa a equaÁ„o Y = x≥ + 2x≤ + 5
	 */
	private Dataset criaDatasetEquacaoCubica()
	{
		Dataset ds = new Dataset();
		ds.adiciona(1, 8);
		ds.adiciona(2, 21);
		ds.adiciona(3, 50);
		ds.adiciona(4, 101);
		ds.adiciona(5, 180);
		return ds;
	}
	
	/**
	 * Dataset que representa a equaÁ„o Y = x≥ - 7x
	 */
	private Dataset criaDatasetEquacaoCubicaXNegativo()
	{
		Dataset ds = new Dataset();
		ds.adiciona(-1, 6);
		ds.adiciona(-2, 6);
		ds.adiciona(-3, -6);
		ds.adiciona(-4, -36);
		ds.adiciona(-5, -90);
		return ds;
	}
	
	@Test
	public void testLinear()
	{
		Dataset ds = criaDatasetLinear();
		No raiz = new No('+', new No("x"), new No(1.0));
		ArvoreExpressao arvore = new ArvoreExpressao(raiz);
		CalculadorFitness calculador = new CalculadorFitness();
		assertEquals(0.0, calculador.calcula(arvore, ds), 0.01);
	}
	
	@Test
	public void testComposto()
	{
		Dataset ds = criaDatasetComposto();
		No raiz = new No('+', new No('*', new No("x"), new No(2.0)), new No(3.0)); //arvore 2x + 3
		ArvoreExpressao arvore = new ArvoreExpressao(raiz);
		CalculadorFitness calculador = new CalculadorFitness();
		assertEquals(0.0, calculador.calcula(arvore, ds), 0.01);
	}
	
	@Test
	public void testEquacaoSegundoGrau()
	{
		Dataset ds = criaDatasetEquacaoSegundoGrau();
		No raiz = new No('+', new No('*', new No("x"), new No('*', new No("x"), new No(3.0))), new No(1.0)); //·rvore 3x≤ + 1
		ArvoreExpressao arvore = new ArvoreExpressao(raiz);
		CalculadorFitness calculador = new CalculadorFitness();
		assertEquals(0.0, calculador.calcula(arvore, ds), 0.01);
	}
	
	@Test
	public void testEquacaoCubica()
	{
		Dataset ds = criaDatasetEquacaoCubica();
		No raiz = new No('+', new No('*', new No("x"), new No('*', new No("x"), new No("x"))), 
					new No('+', new No('*', new No('*', new No("x"), new No("x")), new No(2.0)), new No(5.0))); //·rvore x≥ + 2x≤ + 5
		ArvoreExpressao arvore = new ArvoreExpressao(raiz);
		CalculadorFitness calculador = new CalculadorFitness();
		assertEquals(0.0, calculador.calcula(arvore, ds), 0.01);
	}
	
	@Test
	public void testEquacaoCubicaCoeficienteNegativo()
	{
		Dataset ds = criaDatasetEquacaoCubicaXNegativo();
		No raiz = new No('-', new No('*', new No("x"), new No('*', new No("x"), new No("x"))), 
					new No('*', new No(7.0), new No("x"))); //·rvore x≥ - 7x
		ArvoreExpressao arvore = new ArvoreExpressao(raiz);
		CalculadorFitness calculador = new CalculadorFitness();
		assertEquals(0.0, calculador.calcula(arvore, ds), 0.01);
	}
	
	@Test
	public void testDivisivo()
	{
		Dataset ds = criaDatasetDivisivo();
		No raiz = new No('+', new No('/', new No("1"), new No("x")), new No('/', new No(1.0), new No(2.0))); //·rvore 1/x + 1/2 
		ArvoreExpressao arvore = new ArvoreExpressao(raiz);
		CalculadorFitness calculador = new CalculadorFitness();
		assertEquals(0.0, calculador.calcula(arvore, ds), 0.01);
	}
	
	@Test
	public void testCalculoMmreRobillard()
	{
		Dataset ds = criaDatasetRobillard();
		List<Integer> resultadosGpRobillard = new ArrayList();
		resultadosGpRobillard.add(514);
		resultadosGpRobillard.add(361);
		resultadosGpRobillard.add(385);
		resultadosGpRobillard.add(516);
		resultadosGpRobillard.add(390);
		resultadosGpRobillard.add(483);
		resultadosGpRobillard.add(198);
		resultadosGpRobillard.add(319);
		resultadosGpRobillard.add(219);
		resultadosGpRobillard.add(367);
		resultadosGpRobillard.add(385);
		resultadosGpRobillard.add(453);
		resultadosGpRobillard.add(294);
		resultadosGpRobillard.add(574);
		resultadosGpRobillard.add(109);
		resultadosGpRobillard.add(308);
		resultadosGpRobillard.add(137);
		resultadosGpRobillard.add(198);
		resultadosGpRobillard.add(387);
		resultadosGpRobillard.add(236);
		resultadosGpRobillard.add(173);
		
		assertEquals(0.256, calculaMmre(resultadosGpRobillard, ds));
																
	}

	
	private Dataset criaDatasetRobillard()
	{
		Dataset dataset = new Dataset();
		dataset.adiciona(203, 418);
		dataset.adiciona(132, 468);
		dataset.adiciona(143, 360);
		dataset.adiciona(204, 531);
		dataset.adiciona(145, 471);
		dataset.adiciona(188, 525);
		dataset.adiciona(64, 225);
		dataset.adiciona(114, 229);
		dataset.adiciona(72, 143);
		dataset.adiciona(135, 369);				
		dataset.adiciona(143, 416);		
		dataset.adiciona(174, 428);		
		dataset.adiciona(103, 377);		
		dataset.adiciona(232, 544);	
		dataset.adiciona(31, 52);
		dataset.adiciona(109, 400);		
		dataset.adiciona(41, 187);		
		dataset.adiciona(64, 198);		
		dataset.adiciona(144, 363);
		dataset.adiciona(79, 195);		
		dataset.adiciona(54, 69);	
		
		return dataset;
	}
	
	private double calculaMmre(List<Integer> yCalculados, Dataset dataset)
	{
		double pred = 0.0;
		double mmre = 0.0;
		double somatorioMre = 0.0;
		int tamanhoDataset = 0;
		
		int i = 0;
		for (Dataset.Entrada entrada : dataset.getEntradas())
		{
			tamanhoDataset ++;
			double yConhecido = entrada.getY();
		
			somatorioMre += Math.abs((yConhecido - yCalculados.get(i)) / yConhecido);
			
			if ((yCalculados.get(i) < (yConhecido * 1.25)) && (yCalculados.get(i) > (yConhecido * 0.75)))
				pred++;
			
			i++;
		}
		
		mmre = somatorioMre / tamanhoDataset;
		pred = pred / tamanhoDataset;
		return mmre;
		
	}
}