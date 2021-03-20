package br.com.ppgi.unirio.arthur.gptraining.inicializacao;

import java.util.Random;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.Populacao;

public class GeradorPopulacaoInicial {

	/**
	 * Inicializa a metade da população utilizando o método Grow e a outra metade utilizando o método Full, 
	 * ambos métodos de construção de árvores aleatórias.
	 * 
	 */
	public Populacao inicializacaoRampedHalfAndHalf(int tamanhoPopulacao, int profundidadeLimite, Random random)
	{
		Populacao populacaoInicial = new Populacao(tamanhoPopulacao);
		GeradorArvoreMetodoGrow geradorGrow = new GeradorArvoreMetodoGrow();
		GeradorArvoreMetodoFull geradorFull= new GeradorArvoreMetodoFull();

		if (tamanhoPopulacao % 10 != 0)
		{
			System.out.println("O tamanho da população deve ser múltiplo de 10. Execução abortada");
			System.exit(1);
		}
		
		int quantidadeIndividuosGrow = tamanhoPopulacao / 2;
		int quantidadeIndividuosFull = tamanhoPopulacao - quantidadeIndividuosGrow;
		
		//Preenchimento da população inicial equilibrando o número de invivíduos gerados aleatoriamente pelos métodos Grow e Full.
		int contadorGrow = 1;
		
		for (int i = 0; i < quantidadeIndividuosGrow; i++)
		{
			ArvoreExpressao arvoreGrow = new ArvoreExpressao();
			while(true)
			{
				//árvore aleatória gerada
				arvoreGrow = geradorGrow.gerarArvore(profundidadeLimite, random);
				//System.out.println("Árvore Grow " + contadorGrow + ": " + arvoreGrow.stringExpressao(arvoreGrow.getRaiz()));
				
				//árvore simplificada antes da verificação de validade, para eliminar casos como (x / x) e (x - x)
				arvoreGrow = arvoreGrow.simplificarArvore(arvoreGrow);
				//System.out.println("Árvore Grow " + contadorGrow + " simplificada: " + arvoreGrow.stringExpressao(arvoreGrow.getRaiz()));
				if (arvoreGrow.checaExistenciaDeNoX(arvoreGrow.getRaiz()) && arvoreGrow.checaValidadeArvore(arvoreGrow.getRaiz()))
				{
					//System.out.println("\t Passou no teste!");
					break;
				}
				//else
					//System.out.println("\t Sem terminal x");
			}
			contadorGrow++;
			populacaoInicial.adicionaIndividuo(arvoreGrow);
		}
		
		int contadorFull = 1;
		
		for (int j = 0; j < quantidadeIndividuosFull; j++)
		{
			ArvoreExpressao arvoreFull = new ArvoreExpressao();
			while(true)
			{
				try
				{
					arvoreFull = geradorFull.gerarArvore(profundidadeLimite, random);
					//System.out.println("Árvore Full " + contadorFull + ": " + arvoreFull.stringExpressao(arvoreFull.getRaiz()));
					arvoreFull = arvoreFull.simplificarArvore(arvoreFull);
					//System.out.println("Árvore Full " + contadorFull + " simplificada: " + arvoreFull.stringExpressao(arvoreFull.getRaiz()));
					if (arvoreFull.checaExistenciaDeNoX(arvoreFull.getRaiz()) && arvoreFull.checaValidadeArvore(arvoreFull.getRaiz()) )
					{
						//System.out.println("\t Passou no teste!");
						break;
					}
					//else
						//System.out.println("\t Sem terminal x");
				} catch (Exception e)
				{
					
				}	
			}
			contadorFull++;
			populacaoInicial.adicionaIndividuo(arvoreFull);
		}	
		return populacaoInicial;
	}
}
