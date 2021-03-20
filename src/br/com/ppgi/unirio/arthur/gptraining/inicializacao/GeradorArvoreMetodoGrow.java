package br.com.ppgi.unirio.arthur.gptraining.inicializacao;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.No;

import java.util.Random;

/**
 * Gera��o de uma �rvore aleat�ria atrav�s do m�todo Grow. Neste, as folhas n�o precisam estar na 
 * profundidade m�xima. A profundidade de cada folha � decidida com sorteios aleat�rios. 
 */
public class GeradorArvoreMetodoGrow implements IGeradorArvore 
{

	public ArvoreExpressao gerarArvore(int profundidadeLimite, Random random)
	{
		if (profundidadeLimite > 0)
		{
			No raiz = new No();			
			raiz = geraArvoreMetodoGrow(profundidadeLimite, random);
			
			if (raiz == null)
				return gerarArvore(profundidadeLimite, random);
			
			ArvoreExpressao arvore = new ArvoreExpressao(raiz);
			return arvore;
		}
		return null;
	}
	
	private No geraArvoreMetodoGrow(int profundidadeLimite, Random random)
	{
		//Caso o par�metro profundidadeLimite passado seja igual a zero, 
		//a �rvore retornada ter� altura zero, ou seja, uma �rvore com apenas um n� terminal
		if (profundidadeLimite == 0)
		{
			No folha = new No();
			folha.preenchimentoAleatorioTerminal(random);
			return folha;
		}
		
		//Caso seja maior que zero, o n� raiz necessariamente ser� um operador
		No raizSubArvore = new No();
		raizSubArvore.preenchimentoAleatorioOperador(random);

		if (profundidadeLimite == 1)
		{
			raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoGrow(0, random));
			raizSubArvore.setNoFilhoDireita(geraArvoreMetodoGrow(0, random));
			
		} else 
		{
			int decisao = random.nextInt(4);
			
			switch (decisao)
			{
			//filho � esquerda ser� operador e filho � direita ser� terminal
			case 0:
				raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				raizSubArvore.setNoFilhoDireita(geraArvoreMetodoGrow(0, random));
				break;
				
			//filho � esquerda ser� terminal e filho � direita ser� operador
			case 1:
				raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoGrow(0, random));
				raizSubArvore.setNoFilhoDireita(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				break;
				
			//Ambos os filhos ser�o operadores
			case 2:
				raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				raizSubArvore.setNoFilhoDireita(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				break;
				
			//Ambos os filhos ser�o terminais
			case 3:
				return geraArvoreMetodoGrow(1, random);
		
			}
		}
			return raizSubArvore;
			
	}
	
}
