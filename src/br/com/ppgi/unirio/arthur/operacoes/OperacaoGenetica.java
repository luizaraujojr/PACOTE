package br.com.ppgi.unirio.arthur.operacoes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.com.ppgi.unirio.arthur.gptraining.inicializacao.GeradorArvoreMetodoGrow;
import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.CalculadorFitness;
import br.com.ppgi.unirio.arthur.gptraining.model.Dataset;
import br.com.ppgi.unirio.arthur.gptraining.model.No;
import br.com.ppgi.unirio.arthur.gptraining.model.Operacao;
import br.com.ppgi.unirio.arthur.gptraining.model.Populacao;
import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;

/*
 * Classe responsável pela modificação das árvores de expressão através dos operadores genéticos.
 */
public class OperacaoGenetica {
	
	private int contadorOperacoesCrossover;
	private int contadorNosMutacao;
	private static double probabilidadeCrossover = 0.75;
	private static double probabilidadeTorneio = 0.7;
	private static int tamanhoTorneio = 5;
	
	public OperacaoGenetica()
	{
		contadorOperacoesCrossover = 0;
		contadorNosMutacao = 0;
	}
	
	
	/**
	 * A seleção por torneio ocorre seguindo esta ordem de passos:
	 * 1. Ordenação de uma geração (List<ArvoreExpressao>) pela métrica de Fitness (mmre). 
	 * 2. Sorteio para determinar qual operador genético (crossover ou mutação) agirá sobre os indivíduos de melhor fitness, 
	 * onde o crossover possui por regra uma probabilidade maior de ocorrer do que a mutação.
	 * 3. Realização de torneio clássico para escolher os indivíduos da população que serão alvo dos operadores genéticos.
	 * @throws Exception 
	 */
	public Populacao selecao(Populacao populacao, Dataset dataset, Random random) throws Exception
	{
		CalculadorFitness calculador = new CalculadorFitness();
		
		//faz o calculo do fitness de toda a população, atribuindo a cada árvore seu valor de mmre	
		for (int i = 0; i < populacao.getIndividuos().size(); i++)
		{
//			double aptidao = calculador.calcula(populacao.getIndividuos().get(i), dataset);
			double aptidao = calculador.calculaMOJO(populacao.getIndividuos().get(i), dataset);//luiz
			
			populacao.getIndividuos().get(i).setAptidao(aptidao == -1 ? 1000000 : aptidao);
		}
		
		//Ordena a população pela aptidão (fitness). Quanto mais próxima de zero for a aptidão, melhor é a árvore (Distância entre dois pontos)
//		Collections.sort(populacao.getIndividuos());
		
		//Ordena a população pela aptidão (fitness). Quanto mais próxima de 100 for a aptidão, melhor é a árvore (MOJO)
		
		Collections.reverse(populacao.getIndividuos());//luiz
		
		Populacao proximaGeracao = new Populacao();
		proximaGeracao.setNumeroGeracao(populacao.getNumeroGeracao() + 1);
		int tamanhoPopulacao = populacao.getIndividuos().size();
		double tamanhoElite = (tamanhoPopulacao * 0.3);
		for (int i = 0; i < tamanhoElite; i++)
		{
			//System.out.println("Arvore elite " + (i+1) + ": "  + populacao.getIndividuos().get(i).stringExpressao(populacao.getIndividuos().get(i).getRaiz()) + "----------->" + populacao.getIndividuos().get(i).getAptidao());
			proximaGeracao.adicionaIndividuo(populacao.getIndividuos().get(i));	
		}

		while(proximaGeracao.getIndividuos().size() < populacao.getIndividuos().size())
		{
			/*
			System.out.println("\n\nGeração: Estado atual:");
			for (int i = 0; i < proximaGeracao.getIndividuos().size(); i++)
			{
				System.out.println("Arvore " + (i+1) + ": "  + proximaGeracao.getIndividuos().get(i).stringExpressao(proximaGeracao.getIndividuos().get(i).getRaiz()) + "----------->" + proximaGeracao.getIndividuos().get(i).getAptidao());
			}
			System.out.println("\n\n");
			*/
			
			double escolhaOperadorGenetico = random.nextDouble();
			ArvoreExpressao arvoreProduto = new ArvoreExpressao();
			List<ArvoreExpressao> vencedoresTorneio = new ArrayList<ArvoreExpressao>();
			
			if (escolhaOperadorGenetico < probabilidadeCrossover)
			{
				while(true)
				{
					//Crossover Torneio Clássico
					try
					{
						vencedoresTorneio = torneioClassico(tamanhoTorneio, probabilidadeTorneio, populacao, random, 2);
						arvoreProduto = subtreeCrossover(vencedoresTorneio.get(0), vencedoresTorneio.get(1), random);
						
						if (arvoreProduto != null)
						{
							if (arvoreProduto.checaValidadeArvore(arvoreProduto.getRaiz()) && arvoreProduto.checaExistenciaDeNoX(arvoreProduto.getRaiz()))
							{
								break;
							}
						}		
					} catch (Exception e)
					{
						e.printStackTrace();
						if (!vencedoresTorneio.isEmpty())
						{
							System.out.println("Exceção no crossover entre as árvores:");
							System.out.println(vencedoresTorneio.get(0).stringExpressao(vencedoresTorneio.get(0).getRaiz()) + " e " + vencedoresTorneio.get(1).stringExpressao(vencedoresTorneio.get(1).getRaiz()));
						}
						continue;
					}
					continue;
				}
			}
			else //sorteio determinou que o operador genético será mutação
			{
				while(true)
				{
					try
					{	
						//Mutação Torneio Clássico
						vencedoresTorneio = torneioClassico(tamanhoTorneio, probabilidadeTorneio, populacao, random, 1);
						arvoreProduto = new ArvoreExpressao(mutacao(vencedoresTorneio.get(0), random).getRaiz());
						
						if (arvoreProduto.checaValidadeArvore(arvoreProduto.getRaiz()) && arvoreProduto.checaExistenciaDeNoX(arvoreProduto.getRaiz()))
							break;
					} catch (Exception e)
					{
						e.printStackTrace();
						if (!vencedoresTorneio.isEmpty())
						{
							System.out.println("Exceção na mutação da árvore:");
							System.out.println(vencedoresTorneio.get(0).stringExpressao(vencedoresTorneio.get(0).getRaiz()));
						}	
					}
				}
			}		
			
//			double aptidao = calculador.calcula(arvoreProduto, dataset);
			double aptidao = calculador.calculaMOJO(arvoreProduto, dataset);//luiz
			
			arvoreProduto.setAptidao(aptidao == -1 ? 1000000 : aptidao);
			proximaGeracao.adicionaIndividuo(arvoreProduto);	
		}
	
		/* 
		for (ArvoreExpressao arv : proximaGeracao.getIndividuos())
		{
			double aptidao = calculador.calcula(arv, dataset);
			arv.setAptidao(aptidao == -1 ? 1000000 : aptidao);
		}
		*/
		
		Collections.sort(proximaGeracao.getIndividuos());

		/*
		System.out.println("\n\n <GERAÇÃO " + String.valueOf(proximaGeracao.getNumeroGeracao())  + "-------------------------------------------------->");
		for (ArvoreExpressao arvoreExpressao : proximaGeracao.getIndividuos())
		{
			System.out.println(arvoreExpressao.stringExpressao(arvoreExpressao.getRaiz()) + " --> " + arvoreExpressao.getAptidao());
		}
		*/
		
		return proximaGeracao;
	}
	
	/**
	 * Crossover onde pedaços de duas árvores de expressão são usados para criar um descendente. Um ponto de crossover é escolhido nas duas árvores
	 * 
	 */
	public ArvoreExpressao subtreeCrossover(ArvoreExpressao arvoreA, ArvoreExpressao arvoreB, Random random)
	{
		//Seleciona um ponto aleatório de crossover nas duas árvores
		
		ArvoreExpressao arvoreCrossoverA = new ArvoreExpressao(arvoreA.getRaiz());
		ArvoreExpressao arvoreCrossoverB = new ArvoreExpressao(arvoreB.getRaiz());
		
		int pontoCrossoverA;
		int pontoCrossoverB;
		
		//árvore de nó único com simbolo terminal x
		if (!arvoreCrossoverA.getRaiz().possuiFilhos() && arvoreCrossoverA.getRaiz().simboloTerminal.equals("x"))
		{
			arvoreCrossoverA = new ArvoreExpressao(new No('*', new No(1), new No("x")));
			pontoCrossoverA = 0;
			return null;
		}
		
		else
			pontoCrossoverA = random.nextInt(arvoreCrossoverA.getQuantidadeOperadores(arvoreCrossoverA.getRaiz()));
	
		if (!arvoreCrossoverB.getRaiz().possuiFilhos() && arvoreCrossoverB.getRaiz().simboloTerminal.equals("x"))
		{
			arvoreCrossoverB = new ArvoreExpressao(new No('*', new No(1), new No("x")));
			pontoCrossoverB = 0;
			return null;
		}
		
		else
			pontoCrossoverB = random.nextInt(arvoreB.getQuantidadeOperadores(arvoreB.getRaiz()));
		
		No subarvoreA = quebraNoPontoCrossover(arvoreCrossoverA.getRaiz(), pontoCrossoverA);
		contadorOperacoesCrossover = 0;
		No subarvoreB = quebraNoPontoCrossover(arvoreCrossoverB.getRaiz(), pontoCrossoverB);

		ArvoreExpressao ArvoreA = new ArvoreExpressao(subarvoreA);
		ArvoreExpressao ArvoreB = new ArvoreExpressao(subarvoreB);
		
		//Sorteio aleatório para decidir qual das raízes das duas subarvores será a raiz da árvore originada
		int sorteio = random.nextInt(2);
		ArvoreExpressao arvoreCombinada = new ArvoreExpressao();
		
		//System.out.println("\nÁrvores selecionadas para o crossover: \nArvore A: " + arvoreA.stringExpressao(arvoreA.getRaiz()));
		//System.out.println("Arvore B: " + arvoreB.stringExpressao(arvoreB.getRaiz()));
		
		if (sorteio == 0)
			arvoreCombinada = combina(ArvoreA, ArvoreB);
		else
			arvoreCombinada = combina(ArvoreB, ArvoreA);
		
		arvoreCombinada = arvoreCombinada.simplificarArvore(arvoreCombinada);
		
		//Se a árvore gerada no crossover não possuir um terminal x, o crossover é feito novamente entre as duas árvores iniciais.
		if (!arvoreCombinada.checaExistenciaDeNoX(arvoreCombinada.getRaiz()))
		{
			System.out.println("Inexistência de terminal x na árvore descendente, gerada do crossover entre " + arvoreA.stringExpressao(arvoreA.getRaiz()) + " e " + arvoreB.stringExpressao(arvoreB.getRaiz()));
			contadorOperacoesCrossover = 0;
			return null;
		}
		
		//System.out.println("Árvore descendente (A x B): " + arvoreCombinada.stringExpressao(arvoreCombinada.getRaiz()));
		contadorOperacoesCrossover = 0;
		arvoreCombinada.setExpressao(arvoreCombinada.stringExpressao(arvoreCombinada.getRaiz()));
		return arvoreCombinada;
	}
	
	/**
	 * Retorna a raiz da subarvore localizada no ponto de Crossover sorteado 
	 */
	public No quebraNoPontoCrossover(No no, int pontoCrossover)
	{
		if (no.getOperador() == null)
		{
			return null;
		}

	    if ((this.contadorOperacoesCrossover == pontoCrossover) && no.getOperador() != null)
	    	return new No(no);
	    
	    if ((this.contadorOperacoesCrossover != pontoCrossover) && no.getOperador() != null )
	    	this.contadorOperacoesCrossover++;
	    
	    No noQuebra = quebraNoPontoCrossover(no.getNoFilhoEsquerda(), pontoCrossover);
	    if (noQuebra == null)
	    	noQuebra = quebraNoPontoCrossover(no.getNoFilhoDireita(), pontoCrossover);
	    
	    return noQuebra;
    }
	
	/**
	 * Método que realiza uma mutação de ponto(point mutation). Aqui há um grande potencial de alteração da árvore, pois ocorre a substituição de um nó aleatório por uma árvore grow
	 */
	public ArvoreExpressao mutacao(ArvoreExpressao arvore, Random random) throws Exception
	{
		if (arvore == null || arvore.getRaiz() == null || arvore.getTamanhoArvore(arvore.getRaiz()) == 0)
			throw new Exception ("árvore null ou vazia");
			
		ArvoreExpressao arvoreMutada = new ArvoreExpressao(arvore.getRaiz());
		int quantidadeNos = arvore.getTamanhoArvore(arvore.getRaiz());
		
		
		No raizArvoreMutada;
		int pontoMutacao;
		if (quantidadeNos == 1)
		{
			pontoMutacao = 0;
			//Caso a árvore seja um nó (x), a profundidade limite da árvore grow que a substituirá foi aumentada em 2. 
			//Caso contrário (x) não poderia se tornar outra árvore senão (x), pelo limite de profundidade. Incremento de 2 para radicalizar a mudança, neste caso.
			raizArvoreMutada = mutacaoPontoAleatorioRadical(arvoreMutada.getRaiz(), pontoMutacao, arvore.getProfundidade(arvore.getRaiz()) + 2, random);
		}
			
		else
		{
			pontoMutacao= random.nextInt(quantidadeNos);
			raizArvoreMutada = mutacaoPontoAleatorioRadical(arvoreMutada.getRaiz(), pontoMutacao, arvore.getProfundidade(arvore.getRaiz()), random);
		}
		
		//Mutação com grande potencial de alteração da árvore
		raizArvoreMutada = mutacaoPontoAleatorioRadical(arvoreMutada.getRaiz(), pontoMutacao, arvore.getProfundidade(arvore.getRaiz()), random);
		arvoreMutada = new ArvoreExpressao(raizArvoreMutada);
		
		//Mutação de baixo impacto
		//mutacaoPontoAleatorioSimples(arvoreMutada.getRaiz(), pontoMutacao, random);
		
		arvoreMutada = arvoreMutada.simplificarArvore(arvoreMutada);
		arvoreMutada.setExpressao(arvoreMutada.stringExpressao(arvoreMutada.getRaiz()));
		contadorNosMutacao = 0;
		return arvoreMutada;
	}
	
	/**
	 * Método que percorre a árvore até encontrar o nó representado pelo ponto de mutação. Ao achá-lo, realiza sua substituição 
	 * por uma árvore grow de tamanho máximo igual a altura da árvore original.
	 */
	public No mutacaoPontoAleatorioRadical(No noRaiz, int pontoMutacao, int profundidadeArvoreOrigem, Random random) 
	{
		if (noRaiz == null)
			return null;
		
	    if (this.contadorNosMutacao == pontoMutacao)
	    {	
	    	GeradorArvoreMetodoGrow geradorArvoreGrow = new GeradorArvoreMetodoGrow();
			ArvoreExpressao arvoreGrow = new ArvoreExpressao();
			arvoreGrow = geradorArvoreGrow.gerarArvore(profundidadeArvoreOrigem, random);
			
			while (arvoreGrow == null || arvoreGrow.isEmpty())
				arvoreGrow = geradorArvoreGrow.gerarArvore(profundidadeArvoreOrigem, random);
	    	
			if (pontoMutacao == 0 && noRaiz.possuiFilhos())
			{
				double sorteio = random.nextDouble();
				if (sorteio < 0.5)
					noRaiz.setNoFilhoEsquerda(new No(arvoreGrow.getRaiz()));
				else
					noRaiz.setNoFilhoDireita(new No(arvoreGrow.getRaiz()));
				
				return noRaiz;
			}
			else
			{
				this.contadorNosMutacao++;
				noRaiz = new No(arvoreGrow.getRaiz());
				return noRaiz;
			}
	    }
	   
	    if (this.contadorNosMutacao != pontoMutacao)
	    {
	    	this.contadorNosMutacao++;
	    	
	    	if (!noRaiz.possuiFilhos())
	    		return noRaiz;
	    	
	    	else
	    	{
	    		noRaiz.setNoFilhoEsquerda(mutacaoPontoAleatorioRadical(noRaiz.getNoFilhoEsquerda(), pontoMutacao, profundidadeArvoreOrigem, random));
			    noRaiz.setNoFilhoDireita(mutacaoPontoAleatorioRadical(noRaiz.getNoFilhoDireita(), pontoMutacao, profundidadeArvoreOrigem, random));
			    return noRaiz;
	    	}	
	    }    	
	    return null;
	}
	
	/**
	 * Método que percorre a árvore até encontrar o nó representado pelo ponto de mutação. Ao achá-lo, realiza sua substituição 
	 * por um elemento aleatório equivalente (operador por operador e terminal por terminal)
	 */
	public boolean mutacaoPontoAleatorioSimples(No noRaiz, int pontoMutacao, Random random) 
	{
		if (noRaiz == null)
			return false;
		
	    if (this.contadorNosMutacao == pontoMutacao)
	    {	
	    	if (noRaiz.getOperador() != null)
	    	{
	    		No novoOperador = new No();
	    		novoOperador.preenchimentoAleatorioOperador(random);
	    		noRaiz.setOperador(novoOperador.getOperador());
	    		this.contadorNosMutacao = 0;
	    		return true;
	    	}
	    	else if (noRaiz.getSimboloTerminal() != null)
	    	{
	    		No novoTerminal = new No();
	    		novoTerminal.preenchimentoAleatorioTerminal(random);
	    		noRaiz.setSimboloTerminal(novoTerminal.getSimboloTerminal());
	    		this.contadorNosMutacao = 0;
	    		return true;
	    	}
	    	else
	    		return false;    		
	    }
	   
	    if (this.contadorNosMutacao != pontoMutacao) 
	    	this.contadorNosMutacao++;
	    
	    if (!mutacaoPontoAleatorioSimples(noRaiz.getNoFilhoEsquerda(), pontoMutacao, random))
	    {
	    	return mutacaoPontoAleatorioSimples(noRaiz.getNoFilhoDireita(), pontoMutacao, random);
	    }

		return true;
	}
	
	/**
	 * Substitui aleatoriamente um dos nós filhos da subarvore A pela subarvore B e retorna a árvore resultante.
	 */
	public ArvoreExpressao combina(ArvoreExpressao subarvoreA, ArvoreExpressao subarvoreB)
	{
		ArvoreExpressao arvoreResultado = new ArvoreExpressao(subarvoreA.getRaiz());
		arvoreResultado.getRaiz().setNoFilhoEsquerda(subarvoreB.getRaiz());
			
		return arvoreResultado;
	}
	
	private List<ArvoreExpressao> torneioCustomizado(Populacao populacao, Random random)
	{
		double escolhaGrupo = random.nextDouble();
		List<ArvoreExpressao> grupoTorneio = new ArrayList<ArvoreExpressao>();
		
		
		//Torneio formado pela metade da população. Subdivisão dessa metade em cinco grupos de quantidade igual de indivíduos. 
		//A probabilidade de escolha de cada grupo é a seguinte: g1 - 40%, g2 - 20%, g3 - 20%, g4 - 10, g5 - 10%
		if (escolhaGrupo >= 0 && escolhaGrupo < 0.4)
			grupoTorneio = getGrupoParticipantes(1, populacao.getIndividuos());
		
		else if (escolhaGrupo >= 0.4 && escolhaGrupo < 0.6)
			grupoTorneio = getGrupoParticipantes(2, populacao.getIndividuos());
			
		else if (escolhaGrupo >= 0.6 && escolhaGrupo < 0.8)
			grupoTorneio = getGrupoParticipantes(3, populacao.getIndividuos());
			
		else if (escolhaGrupo >= 0.8 && escolhaGrupo < 0.9)
			grupoTorneio = getGrupoParticipantes(4, populacao.getIndividuos());
			
		else if (escolhaGrupo >= 0.9 && escolhaGrupo < 1.0)
			grupoTorneio = getGrupoParticipantes(5, populacao.getIndividuos());
		
		return grupoTorneio;
	}
	
	/**
	 * Divisão abstrata da população já ordenada em dois. Divisão da primeira metade em cinco grupos (fatias) de quantidades iguais de indivíduos. 
	 * O método retorna a fatia de indivíduos correspondente ao inteiro passado (chamada com numeroGrupo == 1 retorna os primeiros 10% de indivíduos)
	 */
	private List<ArvoreExpressao> getGrupoParticipantes(int numeroGrupo, List<ArvoreExpressao> individuosPopulacao)
	{
		int tamanhoGrupo = individuosPopulacao.size()/10; //quinta parte da metade da população
		List<ArvoreExpressao> grupoSelecionado = new ArrayList<ArvoreExpressao>();
		
		for(int i = numeroGrupo * tamanhoGrupo; i < (numeroGrupo * tamanhoGrupo) + tamanhoGrupo; i++)
		{
			grupoSelecionado.add(individuosPopulacao.get(i - 1));
		}

		return grupoSelecionado;
	}
	
	/**
	 * Implementação de torneio clássico. Um determinado numero de indivíduos é selecionado da população para participar do torneio. 
	 * A probabilidade p equivale a chance do indivíduo com melhor aptidão ser selecionado deste grupo. O segundo melhor individuo tem chance equivalente a p*(1-p), o terceiro a p*(1-p)² e assim por diante.
	 */
	private List<ArvoreExpressao> torneioClassico (int TamanhoTorneio, double probabilidade, Populacao populacao, Random random, int numeroVencedores)
	{
		int lTamanhoTorneio = TamanhoTorneio;
		if (probabilidade > 1)
		{
			System.out.println("Valor da probabilidade p maior que 1");
			return null;
		}
			
		List<ArvoreExpressao> participantes = new ArrayList<ArvoreExpressao>();
		List<ArvoreExpressao> vencedores = new ArrayList<ArvoreExpressao>();
		List<Integer> sorteioIndices = new ArrayList<Integer>();
		
		for (int i = 0; i < populacao.getIndividuos().size(); i++)
		{
			sorteioIndices.add(i);
		}
		Collections.shuffle(sorteioIndices);
		
		for (int i = 0; i < lTamanhoTorneio; i++)
		{
			participantes.add(populacao.getIndividuos().get(sorteioIndices.get(i)));
		}
		
		Collections.sort(participantes);
		
		double p = probabilidade;
		int indiceVencedor = 0;
		for (int i = 0; i < numeroVencedores; i++)
		{
			double sorteio = random.nextDouble();
			double pLocal = p;
			int expoente = 1;
			
			while(true)
			{
				if (sorteio < pLocal || indiceVencedor == (lTamanhoTorneio - 1))
				{
					vencedores.add(participantes.get(indiceVencedor));
					participantes.remove(indiceVencedor);
					lTamanhoTorneio--; 
					break;
				}
				else
				{
					pLocal += (p * Math.pow(1 - p, expoente));
					indiceVencedor++;
					expoente++;
				}
			}
			
			indiceVencedor = 0;
		}
		return vencedores;
	}
}
