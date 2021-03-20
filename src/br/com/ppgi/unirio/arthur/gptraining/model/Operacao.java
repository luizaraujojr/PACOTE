package br.com.ppgi.unirio.arthur.gptraining.model;

import lombok.Getter;

/**
 * Operadores matematicos, usados nos nos internos da arvore para representar expressoes matematicas.
 */
public enum Operacao 
{
	Soma(0, '+'),
	Multiplicacao(1, '*'),
	Subtracao(2, '-'),
	Divisao(3, '/'),
	Exponenciacao(4, '^');

	public int codigo;
	public char operador;
	
	private Operacao(int codigo, char operador)
    {
        this.codigo = codigo;
        this.operador = operador;
    }
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public char getOperador() {
		return operador;
	}

	public void setOperador(char operador) {
		this.operador = operador;
	}
	
	public static Operacao get(int codigo)
	{
		for (Operacao operador : values())
			if (operador.getCodigo() == codigo)
				return operador;
		
		return null;
	}
	
	public static Operacao get(char op)
	{
		for (Operacao operador : values())
			if (operador.getOperador() == op)
				return operador;
		
		return null;
	}
}