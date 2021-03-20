package br.com.ppgi.unirio.arthur.gptraining.inicializacao;

import java.util.Random;

import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;

public interface IGeradorArvore {

	public ArvoreExpressao gerarArvore(int profundidadeLimite, Random random);
	
}
