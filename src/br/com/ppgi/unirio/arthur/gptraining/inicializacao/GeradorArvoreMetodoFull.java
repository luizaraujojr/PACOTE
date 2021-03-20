package br.com.ppgi.unirio.arthur.gptraining.inicializacao;

import java.util.Random;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.No;

/**
 * Geração de uma árvore aleatória através do método full. 
 */

public class GeradorArvoreMetodoFull implements IGeradorArvore 
{

	public ArvoreExpressao gerarArvore(int profundidadeLimite, Random random)
	{
		if (profundidadeLimite > 0)
		{
			No raiz = new No();			
			raiz = geraArvoreMetodoFull(profundidadeLimite, random);
			
			ArvoreExpressao arvore = new ArvoreExpressao(raiz);
			return arvore;
		}
		return null;
	}
	
	public No geraArvoreMetodoFull(int profundidadeLimite, Random random)
	{
		if (profundidadeLimite < 0)
		{
			System.out.println("Falha ao tentar gerar uma ï¿½rvore com profundidade negativa.");
			return null;
		}
		
		if (profundidadeLimite == 0)
		{
			No folha = new No();
			folha.preenchimentoAleatorioTerminal(random);
			return folha;
		}
			
		No raizSubArvore = new No();
		raizSubArvore.preenchimentoAleatorioOperador(random);
		
		if (profundidadeLimite == 1)
		{
			raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoFull(0, random));
			raizSubArvore.setNoFilhoDireita(geraArvoreMetodoFull(0, random));
		
		} else 
		{
			raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoFull(profundidadeLimite - 1, random));
			raizSubArvore.setNoFilhoDireita(geraArvoreMetodoFull(profundidadeLimite - 1, random));
		}
		
		return raizSubArvore;
	}
}

