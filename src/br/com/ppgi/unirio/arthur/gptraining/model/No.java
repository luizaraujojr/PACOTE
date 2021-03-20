package br.com.ppgi.unirio.arthur.gptraining.model;

import java.util.Random;

/**
 * Estrutura das árvores sintáticas, que contém um terminal ou uma operação.
 */
public class No 
{
	public String simboloTerminal;
	public Operacao operador;
	public No noFilhoEsquerda;
	public No noFilhoDireita;
	
	public No()
	{
		this.simboloTerminal = null;
		this.operador = null;
		this.noFilhoEsquerda = null;
		this.noFilhoDireita = null;
	}
	
	public No(char operador, No noEsquerda, No noDireita)
	{
		this.simboloTerminal = null;
		this.operador = Operacao.get(operador);
		this.noFilhoEsquerda = noEsquerda;
		this.noFilhoDireita = noDireita;
	}
	
	public No(String terminal)
	{
		this.simboloTerminal = terminal;
		this.operador = null;
		this.noFilhoEsquerda = null;
		this.noFilhoDireita = null;
	}
	
	public No(double terminal)
	{
		this.simboloTerminal = String.valueOf(terminal);
		this.operador = null;
		this.noFilhoEsquerda = null;
		this.noFilhoDireita = null;
	}
	
	public No(No no)
	{
		this.setOperador(no.getOperador());
		this.setNoFilhoDireita((no.noFilhoDireita) == null ? null : new No(no.getNoFilhoDireita()));
		this.setNoFilhoEsquerda((no.noFilhoEsquerda) == null ? null : new No(no.getNoFilhoEsquerda()));
		this.setSimboloTerminal(no.getSimboloTerminal());
	}

	public Operacao getOperador()
	{
		return operador;
	}
	
	public void setOperador(Operacao operador) {
		this.operador = operador;
	}
	
	public boolean possuiFilhos()
	{
		if (this.noFilhoEsquerda != null || this.noFilhoDireita != null)
			return true;
		
		return false;
	}
	
	public void preenchimentoAleatorioOperador(Random random)
	{
		int numDecisaoOperador = random.nextInt(4);
		this.operador = Operacao.get(numDecisaoOperador);
	}
	
	/*
	 * TODO
	 * Carece de atualização, pois ainda está trabalhando com inteiros. Terminal numérico agora é double.
	 */
	public void preenchimentoAleatorioTerminal(Random random)
	{
		String terminalEscolhido = null;
		int numDecisaoTerminal = random.nextInt(12) ; 
		int terminalInt = (numDecisaoTerminal % 12) + 1;  //Soma com 1 para evitar terminal 0
		
		/* A chance do terminal ser a variável X é o dobro da chance dos outros terminais. Os sorteios 10 e 11 resultarão na 
		* atribuição de "x" ao terminal. Sorteios menores (de 0 a 9) passarão por um novo processo de aleatoriedade para decidir
		* o sinal do terminal. 
		*/
		if (terminalInt > 9)
		{
			terminalEscolhido = "x";
			
		} else
		{
			//sorteio para decidir se o número terminal é negativo ou positivo
			boolean decisao = (random.nextInt() % 2 == 0? true : false);
			
			if (decisao == false)
				terminalInt *= -1;
			
			terminalEscolhido = String.valueOf(terminalInt);
		}
		
		this.simboloTerminal = terminalEscolhido;
	}

	public No getNoFilhoEsquerda() {
		
		return noFilhoEsquerda;
	}
	
	public No getNoFilhoDireita() {
		
		return noFilhoDireita;
	}

	public String getSimboloTerminal() {
		return simboloTerminal;
	}

	public void setSimboloTerminal(String simboloTerminal) {
		this.simboloTerminal = simboloTerminal;
	}

	public void setNoFilhoEsquerda(No noFilhoEsquerda) {
		this.noFilhoEsquerda = noFilhoEsquerda;
	}

	public void setNoFilhoDireita(No noFilhoDireita) {
		this.noFilhoDireita = noFilhoDireita;
	}
}