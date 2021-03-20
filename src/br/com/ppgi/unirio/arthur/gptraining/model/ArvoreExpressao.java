package br.com.ppgi.unirio.arthur.gptraining.model;
import br.com.ppgi.unirio.arthur.operacoes.Comparable;

/**
 * Classe que abriga algumas operações referente às árvores sintáticas e aos nós.
 */
public class ArvoreExpressao implements Comparable<ArvoreExpressao>
{
	private No raiz;
	private double aptidao;
	private double pred; 
	private String expressao; 
	
	public ArvoreExpressao(No noRaiz)
	{
		this.raiz = new No();
		this.aptidao = -1;
		this.pred = 0.0;
		
		if (noRaiz != null)
		{
			this.raiz.noFilhoDireita = (noRaiz.noFilhoDireita) == null ? null : new No(noRaiz.getNoFilhoDireita());
			this.raiz.noFilhoEsquerda = (noRaiz.noFilhoEsquerda) == null ? null : new No(noRaiz.getNoFilhoEsquerda());
			this.raiz.operador = (noRaiz.operador == null) ? null : noRaiz.getOperador();
			this.raiz.simboloTerminal = (noRaiz.simboloTerminal == null) ? null : noRaiz.getSimboloTerminal();
		}
		else
			System.out.println("Construtor de ArvoreExpressao chamado com no raiz null");
		
		this.expressao = stringExpressao(noRaiz);
	}
	
	public ArvoreExpressao()
	{
		this.raiz = new No();
		this.aptidao = -1;
		this.expressao = "";
	}
	
	public String getExpressao() {
		return expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}
	
	public double getPred() {
		return pred;
	}

	public void setPred(double pred) {
		this.pred = pred;
	}

	public double getAptidao() {
		return aptidao;
	}
	
	/**
	 * Recursivamente resolve as operações matemáticas da árvore, representada pelo nó raiz, retornando um resultado double convertido em String. 
	 * Como há recursão, o tipo de retorno escolhido foi String, pois além de inteiros entre -9 e 9 pode haver x no símbolo de um nó.
	 * @throws Exception 
	 */
	public double resolverExpressao(double valorX) throws Exception
	{
		return resolverExpressao(raiz, valorX);
	}
	
	/**
	 * Recursivamente resolve as operações matemáticas da árvore.
	 * @throws Exception 
	 */
	public double resolverExpressao(No raizSubArvore, double valorX) throws Exception
	{
		if (raizSubArvore.getNoFilhoEsquerda() != null && raizSubArvore.getNoFilhoDireita() != null)
		{
			No filhoEsquerda = raizSubArvore.getNoFilhoEsquerda();
			No filhoDireita = raizSubArvore.getNoFilhoDireita();
			Operacao operador = raizSubArvore.getOperador();
			
			if (!filhoEsquerda.possuiFilhos()  && !filhoDireita.possuiFilhos() )
			{
				return resolverOperacao(filhoEsquerda.getSimboloTerminal(), filhoDireita.getSimboloTerminal(), operador, valorX);		
			}
			
			if (filhoEsquerda.possuiFilhos() && !filhoDireita.possuiFilhos())
			{
				return resolverOperacao(String.valueOf(resolverExpressao(filhoEsquerda, valorX)), filhoDireita.getSimboloTerminal(), operador, valorX);
			}
			
			if (!filhoEsquerda.possuiFilhos() && filhoDireita.possuiFilhos())
			{
				return resolverOperacao(filhoEsquerda.getSimboloTerminal(), String.valueOf(resolverExpressao(filhoDireita, valorX)), operador, valorX);
			}
			
			if (filhoEsquerda.possuiFilhos() && filhoDireita.possuiFilhos())
			{
				return resolverOperacao(String.valueOf(resolverExpressao(filhoEsquerda, valorX)), String.valueOf(resolverExpressao(filhoDireita, valorX)), operador, valorX);
			}
		}
		if (raizSubArvore.simboloTerminal != null && raizSubArvore.simboloTerminal.equals("x"))
			return valorX;
		
		throw new Exception("método resolverExpressão sendo chamado de um nó folha.");
	}
	
	/**
	 * Retorna, na forma de String, o resultado de uma operação entre dois terminais.
	 */
	public double resolverOperacao(String terminalEsquerda, String terminalDireita, Operacao operador, double valorX) throws Exception
	{
		double numeroEsquerda = 0.0;
		double numeroDireita = 0.0;

		numeroEsquerda = terminalEsquerda.equalsIgnoreCase("x") ? valorX : Double.valueOf(terminalEsquerda);
		numeroDireita = terminalDireita.equalsIgnoreCase("x") ? valorX : Double.valueOf(terminalDireita);
		
		switch (operador)
		{
			case Soma:
				return numeroEsquerda + numeroDireita;
				
			case Subtracao:
				return numeroEsquerda - numeroDireita;
				
			case Multiplicacao:
				return numeroEsquerda * numeroDireita;
				
			case Divisao:
				if (numeroDireita != 0.0)
//					throw new Exception ("Uma divisão por 0 foi encontrada.");
				
				return numeroEsquerda / numeroDireita;		
				
			case Exponenciacao:
				if (numeroDireita < 0)
				{
					if ((numeroDireita % 1 != 0) && (numeroEsquerda < 0))
						throw new Exception ("Número negativo (" + String.valueOf(numeroEsquerda) + ") elevado a double negativo (" + String.valueOf(numeroDireita) + ")");
					
					double teste = Math.pow((1 / numeroEsquerda), numeroDireita * (-1));
					return teste;
				}
				
				return Math.pow(numeroEsquerda, numeroDireita);
		}
		
		throw new Exception ("Operador invalido");
	}
	
	/**
	 * Garante a presença do terminal "x" na árvore, pois a mesma deve estar em função de "x".
	 */
	public boolean checaExistenciaDeNoX(No noRaiz)
	{
		if (noRaiz == null)
			return false;
		
		if (noRaiz.getSimboloTerminal() != null && (noRaiz.getSimboloTerminal().equalsIgnoreCase("x")))
			return true;
		else 
		{
			if (noRaiz.getNoFilhoEsquerda() != null)
			{
				if (checaExistenciaDeNoX(noRaiz.getNoFilhoEsquerda()))
					return true;
			}
			if (noRaiz.getNoFilhoDireita() != null)
			{
				if (checaExistenciaDeNoX(noRaiz.getNoFilhoDireita()))
					return true;
			}	
			return false;
		}
	}
	
	public int getProfundidade(No noRaiz)
	{
		if (noRaiz.getNoFilhoEsquerda() == null && noRaiz.getNoFilhoDireita() == null)
			return 0;
		
		if (noRaiz.noFilhoEsquerda.possuiFilhos() == false && noRaiz.noFilhoDireita.possuiFilhos() == false)
			return 1;
		
		else 
			return Math.max(1 + getProfundidade(noRaiz.getNoFilhoEsquerda()), 1 + getProfundidade(noRaiz.getNoFilhoDireita()));
		
		//TODO Testes
		
	}
	
	public int compareTo(ArvoreExpressao outraArvore)
	{
		if (this.aptidao < outraArvore.aptidao)
			return -1;
		
		if (this.aptidao > outraArvore.aptidao)
			return 1;
			
		return 0;
	}

	public No getRaiz() {
		return this.raiz;
	}
	
	public void setRaiz(No raiz) {
		this.raiz = raiz;
	}

	public void setAptidao(double aptidao) {
		this.aptidao = aptidao;	
	}
	
	/*
	 * Retorna a quantidade de nós da árvore
	 */
	public int getTamanhoArvore(No raiz)
	{
		if (raiz == null)
			return 0;
		if (raiz.getNoFilhoEsquerda() == null && raiz.getNoFilhoDireita() == null)
			return 1;
		
		return (1 + getTamanhoArvore(raiz.getNoFilhoEsquerda()) + getTamanhoArvore(raiz.getNoFilhoDireita()));
	}
	
	public int getQuantidadeOperadores(No noRaiz)
	{
		if (noRaiz == null || noRaiz.getOperador() == null)
			return 0;
		if (noRaiz.getOperador() != null && noRaiz.getNoFilhoEsquerda().getOperador() == null && noRaiz.getNoFilhoDireita().getOperador() == null)
			return 1;
		
		return (1 + getQuantidadeOperadores(noRaiz.getNoFilhoEsquerda()) + getQuantidadeOperadores(noRaiz.getNoFilhoDireita()));
	}
	
	public boolean isEmpty()
	{
		if (this.raiz == null)
			return true;
		
		return false;
	}
	
	/**
	 * Verifica se nós com operações possuem dois filhos, se nós com terminais são folhas e se existem divisões por zero.
	 */
	public boolean checaValidadeArvore(No noRaiz)
	{
		if (noRaiz == null)
			return false;
		
		if (noRaiz.getOperador() != null)
		{
			if (noRaiz.getSimboloTerminal() != null || noRaiz.getNoFilhoEsquerda() == null || noRaiz.getNoFilhoDireita() == null)
				return false;
			
			//Se uma divisão por zero é encontrada, retorna falso
			if (noRaiz.getOperador().toString().equalsIgnoreCase("Divisao") && noRaiz.getNoFilhoDireita().getSimboloTerminal() != null)
			{
				if (noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0") || noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0.0"))
				{
					return false;
				}
			}
			
			return (checaValidadeArvore(noRaiz.getNoFilhoEsquerda()) == true && checaValidadeArvore(noRaiz.getNoFilhoDireita()) == true ? 
					true : false);
		}	
		else if (noRaiz.getSimboloTerminal() != null)
		{
			if (noRaiz.getOperador() != null || noRaiz.getNoFilhoEsquerda() != null || noRaiz.getNoFilhoDireita() != null)
				return false;
						
			if (!noRaiz.getSimboloTerminal().equalsIgnoreCase("x"))
			{
				try
				{
					Double.parseDouble(noRaiz.getSimboloTerminal());
					
				} catch (NumberFormatException nfe)
				{
					return false;
				}
			}
			return true;
		}
		return false;
		//TODO Casos de Teste
	}
	
	/**
	 * Checa se as árvores representadas pelos nós raiz de entrada são idênticas, sem fazer qualquer resolução de expressão.
	 */
	public boolean compararEstruturaNos(No raizA, No raizB)
	{
		if (raizA.getSimboloTerminal() != null && raizB.getSimboloTerminal() != null)
		{
			if(!raizA.getSimboloTerminal().equals(raizB.getSimboloTerminal()))
				return false;
			
			if ((raizA.getOperador() != null && raizB.getOperador() != null)  && raizA.getOperador() != raizB.getOperador())
				return false;
		}
		
		if ((raizA.possuiFilhos() && !raizB.possuiFilhos()) || (!raizA.possuiFilhos() && raizB.possuiFilhos()))
			return false;
		
		if (raizA.possuiFilhos())
		{
			if (compararEstruturaNos(raizA.getNoFilhoEsquerda(), raizB.getNoFilhoEsquerda()) == false || 
					compararEstruturaNos(raizA.getNoFilhoDireita(), raizB.getNoFilhoDireita()) == false)
				return false;
		}
		return true;		
	}
	
	public String stringExpressao(No noRaiz)
	{
		if (noRaiz == null)
			return "Árvore null";
		
		if (!noRaiz.possuiFilhos())
			return "(" + noRaiz.getSimboloTerminal() + ")";
		
		if (!noRaiz.getNoFilhoEsquerda().possuiFilhos() && !noRaiz.getNoFilhoDireita().possuiFilhos())
		{
			return "(" + noRaiz.getNoFilhoEsquerda().getSimboloTerminal() + " " + String.valueOf(noRaiz.getOperador().operador) + " " + noRaiz.getNoFilhoDireita().getSimboloTerminal() + ")";
		}
		
		if (!noRaiz.getNoFilhoEsquerda().possuiFilhos() && noRaiz.getNoFilhoDireita().possuiFilhos())
		{
			return "(" + noRaiz.getNoFilhoEsquerda().getSimboloTerminal() + " " + String.valueOf(noRaiz.getOperador().operador) + " " + stringExpressao(noRaiz.getNoFilhoDireita()) + ")";
		}
		
		if (noRaiz.getNoFilhoEsquerda().possuiFilhos() && !noRaiz.getNoFilhoDireita().possuiFilhos())
		{
			return "(" + stringExpressao(noRaiz.getNoFilhoEsquerda()) + " " + String.valueOf(noRaiz.getOperador().operador) + " " + noRaiz.getNoFilhoDireita().getSimboloTerminal() + ")";
		}
		
		if (noRaiz.getNoFilhoEsquerda().possuiFilhos() && noRaiz.getNoFilhoDireita().possuiFilhos())
		{
			return "(" + stringExpressao(noRaiz.getNoFilhoEsquerda()) + " " + String.valueOf(noRaiz.getOperador().operador) + " " + stringExpressao(noRaiz.getNoFilhoDireita()) + ")";
		}
		
		return null;
	}
	
	/**
	 * Resolve todas as operações possíveis da árvore, diminuindo a quantidade de nós
	 */
	public ArvoreExpressao simplificarArvore(ArvoreExpressao arvore)
	{
		ArvoreExpressao arv = new ArvoreExpressao(arvore.getRaiz());
		if (arv.getRaiz().possuiFilhos())
		{
			while (true)
			{
				ArvoreExpressao arvoreSimplificada = new ArvoreExpressao();
				arvoreSimplificada.setRaiz(quebraOperacaoSimplificavel(arv.getRaiz()));
				arvoreSimplificada.setExpressao(arvoreSimplificada.stringExpressao(arvoreSimplificada.getRaiz()));
				
				if (!arv.getExpressao().equals(arvoreSimplificada.getExpressao()))
				{
					arv.setRaiz(arvoreSimplificada.getRaiz());
					arv.setExpressao(arvoreSimplificada.getExpressao());
				}
				
				else
					break;
			}
		}
		return arv;
	}
	
	/**
	 * Resolve operações entre dois terminais double e entre terminais x (em caso de subtração ou divisão)
	 */
	public No quebraOperacaoSimplificavel (No noRaiz)
	{
		if (!noRaiz.possuiFilhos())
			return noRaiz;
		
		if (noRaiz.getOperador().toString().equalsIgnoreCase("Exponenciacao"))
		{
			if (noRaiz.noFilhoDireita.simboloTerminal != null)
			{
				if (noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0") || noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0.0"))
					return new No(1);
				
				if (noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("1") || noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("1.0"))
					return new No(noRaiz.getNoFilhoEsquerda());
				
			}
			
			//Se o filho da esquerda for zero ou 1
			if (noRaiz.noFilhoEsquerda.simboloTerminal != null)
			{
				if (noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0") || noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0.0"))
				{
					if (noRaiz.noFilhoDireita.simboloTerminal != null)
					{
						if (noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0") || noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0.0"))
							return new No(1);
						else
							return new No(0);
					}
				}
			}
		}
		
		//Se a operação for de multiplicação ou divisão
		if (noRaiz.getOperador().toString().equalsIgnoreCase("Multiplicacao") || noRaiz.getOperador().toString().equalsIgnoreCase("Divisao"))
		{
			//Se o filho da esquerda for zero
			if (noRaiz.noFilhoEsquerda.simboloTerminal != null)
			{
				if (noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0") || noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0.0"))
					return new No(0);
			}
			
			//Se o filho da direita for zero
			if (noRaiz.noFilhoDireita.simboloTerminal != null)
			{
				if (noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0") || noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0.0"))
					return new No(0);
			}
			
			//Se o filho da direita for 1
			if (noRaiz.noFilhoDireita.simboloTerminal != null)
			{
				if ((noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("1.0") || noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("1")))
					return new No(noRaiz.getNoFilhoEsquerda());
			}
			
			//Se houver multiplicação de 1 por qualquer coisa
			if (noRaiz.getOperador().toString().equalsIgnoreCase("Multiplicacao"))
			{
				if (noRaiz.noFilhoEsquerda.simboloTerminal != null)
				{
					if ((noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("1.0") || noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("1")))
						return new No(noRaiz.getNoFilhoDireita());
				}
			}
		}
		
		//Se a operação for de soma ou subtração
		if (noRaiz.getOperador().toString().equalsIgnoreCase("Soma") || noRaiz.getOperador().toString().equalsIgnoreCase("Subtracao"))
		{
			//Se o filho da direita for 0, a raiz é substituida por seu filho da esquerda
			if (noRaiz.noFilhoDireita.simboloTerminal != null)
			{
				if (noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0.0") || noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("0"))
					return new No(noRaiz.getNoFilhoEsquerda());
			}
			
			if (noRaiz.getOperador().toString().equalsIgnoreCase("Soma"))
			{
				//Se o filho da esquerda for 0
				if (noRaiz.noFilhoEsquerda.simboloTerminal != null)
				{
					if (noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0.0") || noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0"))
						return new No(noRaiz.getNoFilhoDireita());
				}
					
				if (noRaiz.noFilhoDireita.simboloTerminal != null)
				{
					if (!noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("x") && Double.valueOf(noRaiz.getNoFilhoDireita().getSimboloTerminal()) < 0)
					{
						noRaiz.setOperador(Operacao.Subtracao);
						noRaiz.setNoFilhoDireita(new No(Double.valueOf(noRaiz.getNoFilhoDireita().getSimboloTerminal()) * (-1)));
						return noRaiz;
					}
				}
			}
			
			if (noRaiz.getOperador().toString().equalsIgnoreCase("Subtracao"))
			{
				
				if (noRaiz.noFilhoEsquerda.simboloTerminal != null && noRaiz.noFilhoDireita.simboloTerminal != null)
				{
					//Se o filho da direita for x e o da esquerda for 0
					if ((noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0.0") || noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equals("0")) && noRaiz.getNoFilhoDireita().getSimboloTerminal().equalsIgnoreCase("x"))
						return new No('*', new No("x"), new No("-1"));
				
					//Uma subtração de 0 por x se torna uma multiplicação de x por -1
					if (!noRaiz.getNoFilhoDireita().getSimboloTerminal().equals("x") && Double.valueOf(noRaiz.getNoFilhoDireita().getSimboloTerminal()) < 0)
					{
						noRaiz.setOperador(Operacao.get('+'));
						noRaiz.setNoFilhoDireita(new No(Double.valueOf(noRaiz.getNoFilhoDireita().getSimboloTerminal()) * (-1)));
						return noRaiz;
					}
				}
			}		
		}
		
		if (!noRaiz.getNoFilhoEsquerda().possuiFilhos() && !noRaiz.getNoFilhoDireita().possuiFilhos())
		{
			//Se as duas folhas NÃO possuirem valor x
			if (!noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equalsIgnoreCase("x") && !noRaiz.getNoFilhoDireita().getSimboloTerminal().equalsIgnoreCase("x"))
			{
				try {
					return new No(resolverOperacao(noRaiz.getNoFilhoEsquerda().getSimboloTerminal(), noRaiz.getNoFilhoDireita().getSimboloTerminal(), noRaiz.getOperador(), 0));
				} catch (Exception e) {
					e.printStackTrace();
					//return null;
				}
			}
			
			//Se as duas folhas possuirem valor x
			if (noRaiz.getNoFilhoEsquerda().getSimboloTerminal().equalsIgnoreCase("x") && noRaiz.getNoFilhoDireita().getSimboloTerminal().equalsIgnoreCase("x"))
			{
				//subárvores (x - x) e (x / x) se tornam folhas com valor 0 e 1, respectivamente
				if (noRaiz.getOperador().toString().equalsIgnoreCase("Subtracao"))
					return new No(0);
				
				if (noRaiz.getOperador().toString().equalsIgnoreCase("Divisao"))
					return new No(1);
			}
		}
		else 
		{
			No noAux = noRaiz;
			noAux.setNoFilhoEsquerda(quebraOperacaoSimplificavel(noAux.getNoFilhoEsquerda()));
			noAux.setNoFilhoDireita(quebraOperacaoSimplificavel(noAux.getNoFilhoDireita()));	
			
			return noAux;
		}
		return noRaiz;
	}
}
