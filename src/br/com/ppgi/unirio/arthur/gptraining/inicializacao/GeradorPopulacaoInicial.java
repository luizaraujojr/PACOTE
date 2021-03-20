package br.com.ppgi.unirio.arthur.gptraining.inicializacao;

import java.util.Random;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.Populacao;

public class GeradorPopulacaoInicial {

	/**
	 * Inicializa a metade da popula��o utilizando o m�todo Grow e a outra metade utilizando o m�todo Full, 
	 * ambos m�todos de constru��o de �rvores aleat�rias.
	 * 
	 */
	public Populacao inicializacaoRampedHalfAndHalf(int tamanhoPopulacao, int profundidadeLimite, Random random)
	{
		Populacao populacaoInicial = new Populacao(tamanhoPopulacao);
		GeradorArvoreMetodoGrow geradorGrow = new GeradorArvoreMetodoGrow();
		GeradorArvoreMetodoFull geradorFull= new GeradorArvoreMetodoFull();

		if (tamanhoPopulacao % 10 != 0)
		{
			System.out.println("O tamanho da popula��o deve ser m�ltiplo de 10. Execu��o abortada");
			System.exit(1);
		}
		
		int quantidadeIndividuosGrow = tamanhoPopulacao / 2;
		int quantidadeIndividuosFull = tamanhoPopulacao - quantidadeIndividuosGrow;
		
		//Preenchimento da popula��o inicial equilibrando o n�mero de inviv�duos gerados aleatoriamente pelos m�todos Grow e Full.
		int contadorGrow = 1;
		
		for (int i = 0; i < quantidadeIndividuosGrow; i++)
		{
			ArvoreExpressao arvoreGrow = new ArvoreExpressao();
			while(true)
			{
				//�rvore aleat�ria gerada
				arvoreGrow = geradorGrow.gerarArvore(profundidadeLimite, random);
				//System.out.println("�rvore Grow " + contadorGrow + ": " + arvoreGrow.stringExpressao(arvoreGrow.getRaiz()));
				
				//�rvore simplificada antes da verifica��o de validade, para eliminar casos como (x / x) e (x - x)
				arvoreGrow = arvoreGrow.simplificarArvore(arvoreGrow);
				//System.out.println("�rvore Grow " + contadorGrow + " simplificada: " + arvoreGrow.stringExpressao(arvoreGrow.getRaiz()));
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
					//System.out.println("�rvore Full " + contadorFull + ": " + arvoreFull.stringExpressao(arvoreFull.getRaiz()));
					arvoreFull = arvoreFull.simplificarArvore(arvoreFull);
					//System.out.println("�rvore Full " + contadorFull + " simplificada: " + arvoreFull.stringExpressao(arvoreFull.getRaiz()));
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
