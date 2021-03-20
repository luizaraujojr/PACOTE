package br.com.ppgi.unirio.arthur.gptraining.inicializacao;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.No;

import java.util.Random;

/**
 * Geração de uma árvore aleatória através do método Grow. Neste, as folhas não precisam estar na 
 * profundidade máxima. A profundidade de cada folha é decidida com sorteios aleatórios. 
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
		//Caso o parâmetro profundidadeLimite passado seja igual a zero, 
		//a árvore retornada terá altura zero, ou seja, uma árvore com apenas um nó terminal
		if (profundidadeLimite == 0)
		{
			No folha = new No();
			folha.preenchimentoAleatorioTerminal(random);
			return folha;
		}
		
		//Caso seja maior que zero, o nó raiz necessariamente será um operador
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
			//filho à esquerda será operador e filho à direita será terminal
			case 0:
				raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				raizSubArvore.setNoFilhoDireita(geraArvoreMetodoGrow(0, random));
				break;
				
			//filho à esquerda será terminal e filho à direita será operador
			case 1:
				raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoGrow(0, random));
				raizSubArvore.setNoFilhoDireita(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				break;
				
			//Ambos os filhos serão operadores
			case 2:
				raizSubArvore.setNoFilhoEsquerda(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				raizSubArvore.setNoFilhoDireita(geraArvoreMetodoGrow(profundidadeLimite - 1, random));
				break;
				
			//Ambos os filhos serão terminais
			case 3:
				return geraArvoreMetodoGrow(1, random);
		
			}
		}
			return raizSubArvore;
			
	}
	
}
