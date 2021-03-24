package br.com.ppgi.unirio.arthur.operacoes;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import br.com.ppgi.unirio.arthur.gptraining.inicializacao.GeradorPopulacaoInicial;
import br.com.ppgi.unirio.arthur.gptraining.model.ArvoreExpressao;
import br.com.ppgi.unirio.arthur.gptraining.model.Dataset;
import br.com.ppgi.unirio.arthur.gptraining.model.Populacao;

public class Main {
	
	public static void main(String[] args)
	{
		//execucaoTradicional();
		//execucaoMediaMmre();
//		execucaoGeracaoDadosMmre();
		execucaoGeracaoDadosLuiz();
		

	}
	
	/**
	 * Loop de 50 gerações, utilizando os valores escolhidos no início do método: número de resultados para cada dataset, tamanho de população e profundidade limite das árvores iniciais.
	 * Ao terminar a última geração de cada dataset, ocorre a escrita da melhor árvore em arquivos .txt e em um .csv.
	 */
	private static void execucaoMediaMmre() 
	{
		GeradorPopulacaoInicial geradorPop =  new GeradorPopulacaoInicial();
		OperacaoGenetica operacaoGenetica = new OperacaoGenetica();
		int numeroResultados = 5;//30; alterei para fazer um texte
		Random random;
		int tamanhoPopulacao = 70;
		int profundidadeLimiteArvores = 4;
		
		double sumMmre; //somatório dos valores de mmre encontrados em cada árvore-resultado da GP 
		double mMmre = 0; //média do mmre
		
		PrintWriter writer;
		
		try {
			writer = new PrintWriter("resultados.csv");
			writer.println("dataset;num resultado;mmre;pred;tempo");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//i representa a numeração dos datasets, de 1 a 12.
		for (int i = 1; i < 13; i++)
		{
			Dataset dataset = new Dataset();
			montaDatasetArtigo(i, dataset);
			sumMmre = 0;
			try 
			{
				PrintWriter out = new PrintWriter("Media mmre dataset " + i + ".txt");
				
				for (int j = 1; j <= numeroResultados; j++)
				{
					long inicio = System.nanoTime();
					random = new Random();
					Populacao primeiraGeracao = geradorPop.inicializacaoRampedHalfAndHalf(tamanhoPopulacao, profundidadeLimiteArvores, random);
					Populacao proximaGeracao = new Populacao();
					proximaGeracao.setIndividuos(primeiraGeracao.getIndividuos());
					
					System.out.println("RODADA " + j + " do Dataset " + i);
					
					for (int k = 0; k < 50; k++)
					{
						proximaGeracao.setIndividuos(operacaoGenetica.selecao(proximaGeracao, dataset, random).getIndividuos());
						proximaGeracao.setNumeroGeracao(proximaGeracao.getNumeroGeracao() + 1);
					}
					
					sumMmre += proximaGeracao.getIndividuos().get(0).getAptidao();
					
					System.out.println("FIM RODADA " + j + " do Dataset " + i);
					
					out.println("Árvore-Resultado " + j + " - Mmre: " + proximaGeracao.getIndividuos().get(0).getAptidao() + ", Pred: " + proximaGeracao.getIndividuos().get(0).getPred());
					
					long fim = System.nanoTime();
					long duracao = (fim - inicio);
					try
					{
						writer = new PrintWriter(new BufferedWriter(new FileWriter("resultados.csv", true)));
						writer.println(dataset.getNome() + ";" + j + ";" + proximaGeracao.getIndividuos().get(0).getAptidao() + ";" + proximaGeracao.getIndividuos().get(0).getPred()
								+ ";" + duracao/1000000 + "ms");
						writer.flush();
						writer.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
					out.println("Expressão:  " + proximaGeracao.getIndividuos().get(0).stringExpressao(proximaGeracao.getIndividuos().get(0).getRaiz()));
				}
				
				mMmre = (sumMmre/numeroResultados);
				out.println("");
				out.println("\n\nMédia dos valores de mmre: " + mMmre);
				out.flush();
				out.close();
			}
			catch (Exception E)
			{
				E.printStackTrace();
			}
		}
	}
	
	
	private static void execucaoGeracaoDadosLuiz() 
	{
		GeradorPopulacaoInicial geradorPop =  new GeradorPopulacaoInicial();
		OperacaoGenetica operacaoGenetica = new OperacaoGenetica();
		int numeroResultados = 5;//30; alterei para fazer um experimento menor
		Random random;
		int tamanhoPopulacao = 20;//70; alterei para fazer um experimento menor
		int profundidadeLimiteArvores = 4;
		
		// melhor expressão de cada geração gerada para o dataset escolhido será escrita no csv
		int dataset_print_equacoes = 1;
				
		double mmre;
		
		PrintWriter writerMmre = null;
		PrintWriter writerEquacoes = null;
		PrintWriter writerTempoExecucao = null;
		
		try {
			writerMmre = new PrintWriter("trinta_mmre_datasets.csv");
			writerMmre.println("dataset;mmre");
			writerMmre.close();
			
			writerEquacoes = new PrintWriter("equacoes_mmre.csv");
			writerEquacoes.println("dataset;expressao;mmre");
			writerEquacoes.close();
			
			writerTempoExecucao = new PrintWriter("tempo_execucao.csv");
			writerTempoExecucao.println("dataset;num_execucao;tempo(ms)");
			writerTempoExecucao.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> listaResultados = new ArrayList<String>();
		
		Dataset dataset = new Dataset();
		montaDatasetArtigo(13,dataset); 
		
		try 
		{	
			for (int j = 1; j <= numeroResultados; j++)
			{
//				System.out.println ("RODADA GP: " + j);
				long inicio = System.nanoTime();
				random = new Random();
				Populacao primeiraGeracao = geradorPop.inicializacaoRampedHalfAndHalf(tamanhoPopulacao, profundidadeLimiteArvores, random);
				Populacao proximaGeracao = new Populacao();
				proximaGeracao.setIndividuos(primeiraGeracao.getIndividuos());
				
//				for (int k = 0; k < 50; k++) alterei para fazer o experimento menor
				for (int k = 0; k < 10; k++)
				{
					System.out.println ("RODADA GP: " + j + "/" + numeroResultados + " " + k + "/10");
					proximaGeracao.setIndividuos(operacaoGenetica.selecao(proximaGeracao, dataset, random).getIndividuos());
					proximaGeracao.setNumeroGeracao(proximaGeracao.getNumeroGeracao() + 1);
				}
				
				long fim = System.nanoTime();
				long duracao = (fim - inicio);
				String maiorMOJO = String.valueOf(proximaGeracao.getIndividuos().get(0).getAptidao());
				
				writerMmre = new PrintWriter(new BufferedWriter(new FileWriter("trinta_mmre_datasets.csv", true)));
				writerMmre.println(13 + ";" + maiorMOJO);
				writerMmre.flush();
				writerMmre.close();
				
				listaResultados.add(maiorMOJO);
				
				String expressao = proximaGeracao.getIndividuos().get(0).stringExpressao(proximaGeracao.getIndividuos().get(0).getRaiz());
				
				writerEquacoes = new PrintWriter(new BufferedWriter(new FileWriter("equacoes_mmre.csv", true)));
				writerEquacoes.println(13 + ";" + "'" + expressao + "'" + ";" + proximaGeracao.getIndividuos().get(0).getAptidao());
				writerEquacoes.flush();
				writerEquacoes.close();
				
				writerTempoExecucao = new PrintWriter(new BufferedWriter(new FileWriter("tempo_execucao.csv", true)));
				writerTempoExecucao.println(13 + ";" + j + ";" + duracao/1000000);
				writerTempoExecucao.flush();
				writerTempoExecucao.close();
			}
		}
		catch (Exception E)
		{
			E.printStackTrace();
		}	
	}
	
	
	private static void execucaoGeracaoDadosMmre() 
	{
		GeradorPopulacaoInicial geradorPop =  new GeradorPopulacaoInicial();
		OperacaoGenetica operacaoGenetica = new OperacaoGenetica();
		int numeroResultados = 30;
		Random random;
		int tamanhoPopulacao = 70;
		int profundidadeLimiteArvores = 4;
		
		// melhor expressão de cada geração gerada para o dataset escolhido será escrita no csv
		int dataset_print_equacoes = 1;
				
		double mmre;
		
		PrintWriter writerMmre = null;
		PrintWriter writerEquacoes = null;
		PrintWriter writerTempoExecucao = null;
		
		try {
			writerMmre = new PrintWriter("trinta_mmre_datasets.csv");
			writerMmre.println("dataset;mmre");
			writerMmre.close();
			
			writerEquacoes = new PrintWriter("equacoes_mmre.csv");
			writerEquacoes.println("dataset;expressao;mmre");
			writerEquacoes.close();
			
			writerTempoExecucao = new PrintWriter("tempo_execucao.csv");
			writerTempoExecucao.println("dataset;num_execucao;tempo(ms)");
			writerTempoExecucao.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> listaResultados = new ArrayList<String>();
		
		//i representa a numeração dos datasets, de 1 a 12.
		for (int i = 1; i < 13; i++)
		{
			Dataset dataset = new Dataset();
			montaDatasetArtigo(i, dataset);
			
			try 
			{	
				for (int j = 1; j <= numeroResultados; j++)
				{
					long inicio = System.nanoTime();
					random = new Random();
					Populacao primeiraGeracao = geradorPop.inicializacaoRampedHalfAndHalf(tamanhoPopulacao, profundidadeLimiteArvores, random);
					Populacao proximaGeracao = new Populacao();
					proximaGeracao.setIndividuos(primeiraGeracao.getIndividuos());
					
					for (int k = 0; k < 50; k++)
					{
						proximaGeracao.setIndividuos(operacaoGenetica.selecao(proximaGeracao, dataset, random).getIndividuos());
						proximaGeracao.setNumeroGeracao(proximaGeracao.getNumeroGeracao() + 1);
					}
					
					long fim = System.nanoTime();
					long duracao = (fim - inicio);
					String menorMmre = String.valueOf(proximaGeracao.getIndividuos().get(0).getAptidao());
					
					writerMmre = new PrintWriter(new BufferedWriter(new FileWriter("trinta_mmre_datasets.csv", true)));
					writerMmre.println(i + ";" + menorMmre);
					writerMmre.flush();
					writerMmre.close();
					
					listaResultados.add(menorMmre);
					
					String expressao = proximaGeracao.getIndividuos().get(0).stringExpressao(proximaGeracao.getIndividuos().get(0).getRaiz());
					
					writerEquacoes = new PrintWriter(new BufferedWriter(new FileWriter("equacoes_mmre.csv", true)));
					writerEquacoes.println(i + ";" + "'" + expressao + "'" + ";" + proximaGeracao.getIndividuos().get(0).getAptidao());
					writerEquacoes.flush();
					writerEquacoes.close();
					
					writerTempoExecucao = new PrintWriter(new BufferedWriter(new FileWriter("tempo_execucao.csv", true)));
					writerTempoExecucao.println(i + ";" + j + ";" + duracao/1000000);
					writerTempoExecucao.flush();
					writerTempoExecucao.close();
				}
			}
			catch (Exception E)
			{
				E.printStackTrace();
			}
		}	
	}
	
	private static void montaDatasetArtigo(int numeroDataset, Dataset dataset)
	{
		switch(numeroDataset)
		{
		case 1:
			montaDatasetArtigo1(dataset);
			break;
		case 2:
			montaDatasetArtigo2(dataset);
			break;
		case 3:
			montaDatasetArtigo3(dataset);
			break;
		case 4:
			montaDatasetArtigo4(dataset);
			break;
		case 5:
			montaDatasetArtigo5(dataset);
			break;
		case 6:
			montaDatasetArtigo6(dataset);
			break;
		case 7:
			montaDatasetArtigo7(dataset);
			break;
		case 8:
			montaDatasetArtigo8(dataset);
			break;
		case 9:
			montaDatasetArtigo9(dataset);
			break;
		case 10:
			montaDatasetArtigo10(dataset);
			break;
		case 11:
			montaDatasetArtigo11(dataset);
			break;
		case 12:
			montaDatasetArtigo12(dataset);
			break;
		case 13:
			montaDatasetJoda(dataset);
			break;
		}
	}
	
	/**
	 * Dataset Abran and Robillard
	 */
	public static void montaDatasetArtigo1 (Dataset dataset)
	{
		dataset.setNome("Abran and Robillard");
		dataset.adiciona(203, 418);
		dataset.adiciona(132, 468);
		dataset.adiciona(143, 360);
		dataset.adiciona(204, 531);
		dataset.adiciona(145, 471);
		dataset.adiciona(188, 525);
		dataset.adiciona(64, 225);
		dataset.adiciona(114, 229);
		dataset.adiciona(72, 143);
		dataset.adiciona(135, 369);				
		dataset.adiciona(143, 416);		
		dataset.adiciona(174, 428);		
		dataset.adiciona(103, 377);		
		dataset.adiciona(232, 544);	
		dataset.adiciona(31, 52);
		dataset.adiciona(109, 400);		
		dataset.adiciona(41, 187);		
		dataset.adiciona(64, 198);		
		dataset.adiciona(144, 363);
		dataset.adiciona(79, 195);		
		dataset.adiciona(54, 69);		
	}
	
	
	public static void montaDatasetJoda (Dataset dataset)
	{
		dataset.setNome("D:\\Backup\\eclipse-workspace\\PACOTE\\data\\JARFile\\jodamoney-1.0.1.jar");
		dataset.adiciona(203, 418);		
	}
	
	
	/**
	 * Dataset Albrecht and Gaffney
	 */
	public static void montaDatasetArtigo2 (Dataset dataset)
	{
		dataset.setNome("Albrecht and Gaffney");
		dataset.adiciona(1750, 102.4);
		dataset.adiciona(1902, 105.2);
		dataset.adiciona(428, 11.1);
		dataset.adiciona(759, 21.1);
		dataset.adiciona(431, 28.8);
		dataset.adiciona(283, 10);
		dataset.adiciona(205, 8);
		dataset.adiciona(289, 4.9);
		dataset.adiciona(680, 12.9);
		dataset.adiciona(794, 19);				
		dataset.adiciona(512, 10.8);		
		dataset.adiciona(224, 2.9);		
		dataset.adiciona(417, 7.5);		
		dataset.adiciona(682, 12);	
		dataset.adiciona(209, 4.1);
		dataset.adiciona(512, 15.8);		
		dataset.adiciona(606, 18.3);		
		dataset.adiciona(400, 8.9);		
		dataset.adiciona(1235, 38.10);
		dataset.adiciona(1572, 61.20);		
		dataset.adiciona(500, 3);
		dataset.adiciona(694, 11.8);
		dataset.adiciona(199, 0.5);
	}
	
	/**
	 * Dataset Bailey and Basili
	 */
	public static void montaDatasetArtigo3 (Dataset dataset)
	{
		dataset.setNome("Bailey and Basili");
		dataset.adiciona(111.9, 115.8);
		dataset.adiciona(55.2, 96);
		dataset.adiciona(50.9, 79);
		dataset.adiciona(75.4, 90.8);
		dataset.adiciona(75.4, 39.6);
		dataset.adiciona(89.5, 98.4);
		dataset.adiciona(14.9, 18.9);
		dataset.adiciona(14.3, 10.3);
		dataset.adiciona(32.8, 28.5);
		dataset.adiciona(5.5, 7);				
		dataset.adiciona(4.5, 9);		
		dataset.adiciona(9.7, 7.3);		
		dataset.adiciona(2.1, 5);		
		dataset.adiciona(5.2, 8.4);	
		dataset.adiciona(85.4, 98.7);
		dataset.adiciona(10.2, 15.6);		
		dataset.adiciona(14.8, 23.9);		
		dataset.adiciona(110.3, 138.3);		
	}
	
	/**
	 * Dataset Belady and Lehman
	 */
	public static void montaDatasetArtigo4 (Dataset dataset)
	{
		dataset.setNome("Belady and Lehman");
		dataset.adiciona(30000, 77);
		dataset.adiciona(11164, 51);
		dataset.adiciona(17052, 46);
		dataset.adiciona(140000, 462);
		dataset.adiciona(47377, 241);
		dataset.adiciona(229000, 1665);
		dataset.adiciona(401099, 1022);
		dataset.adiciona(712362, 2176);
		dataset.adiciona(58540, 723);
		dataset.adiciona(80990, 527);				
		dataset.adiciona(94000, 673);		
		dataset.adiciona(18775, 199);		
		dataset.adiciona(14390, 227);		
		dataset.adiciona(35057, 71);	
		dataset.adiciona(11122, 43);
		dataset.adiciona(6092, 47);		
		dataset.adiciona(5342, 14);		
		dataset.adiciona(12000, 60);		
		dataset.adiciona(19000, 50);
		dataset.adiciona(25271, 169);		
		dataset.adiciona(20000, 106);
		dataset.adiciona(12000, 57);
		dataset.adiciona(7000, 195);
		dataset.adiciona(13545, 112);
		dataset.adiciona(14779, 67);
		dataset.adiciona(30000, 1107);
		dataset.adiciona(69200, 852);
		dataset.adiciona(486834, 11758);
		dataset.adiciona(220999, 2440);
		dataset.adiciona(128330, 673);
		dataset.adiciona(32026, 136);
		dataset.adiciona(15363, 37);
		dataset.adiciona(4747, 10);
	}
	
	/**
	 * Dataset Boehm
	 */
	public static void montaDatasetArtigo5 (Dataset dataset)
	{
		dataset.setNome("Boehm");
		dataset.adiciona(113000, 2040);
		dataset.adiciona(249000, 1600);
		dataset.adiciona(132000, 243);
		dataset.adiciona(46000, 240);
		dataset.adiciona(16000, 33);
		dataset.adiciona(4000, 43);
		dataset.adiciona(6900, 8);
		dataset.adiciona(22000, 1075);
		dataset.adiciona(30000, 423);
		dataset.adiciona(18000, 321);
		dataset.adiciona(20000, 218);
		dataset.adiciona(37000, 201);
		dataset.adiciona(24000, 79);
		dataset.adiciona(3000, 73);
		dataset.adiciona(3900, 61);
		dataset.adiciona(3700, 40);
		dataset.adiciona(1900, 9);
		dataset.adiciona(320000, 11400);
		dataset.adiciona(966000, 6600);
		dataset.adiciona(287000, 6400);
		dataset.adiciona(252000, 2455);
		dataset.adiciona(109000, 724);
		dataset.adiciona(75000, 539);
		dataset.adiciona(90000, 453);
		dataset.adiciona(38000, 523);
		dataset.adiciona(48000, 387);
		dataset.adiciona(9400, 88);
		dataset.adiciona(13000, 98);
		dataset.adiciona(2140, 7.30);
		dataset.adiciona(1980, 5.90);
		dataset.adiciona(50000, 1063);
		dataset.adiciona(261000, 702);
		dataset.adiciona(40000, 605);
		dataset.adiciona(22000, 230);
		dataset.adiciona(13000, 82);
		dataset.adiciona(12000, 55);
		dataset.adiciona(34000, 47);
		dataset.adiciona(15000, 12);
		dataset.adiciona(6200, 8);
		dataset.adiciona(2500, 8);
		dataset.adiciona(5300, 6);
		dataset.adiciona(19500, 45);
		dataset.adiciona(28000, 83);
		dataset.adiciona(30000, 87);
		dataset.adiciona(32000, 106);
		dataset.adiciona(57000, 126);
		dataset.adiciona(23000, 36);
		dataset.adiciona(311000, 1272);
		dataset.adiciona(91000, 156);
		dataset.adiciona(24000, 176);
		dataset.adiciona(10000, 122);
		dataset.adiciona(8200, 41);
		dataset.adiciona(5300, 14);
		dataset.adiciona(4400, 20);
		dataset.adiciona(6300, 18);
		dataset.adiciona(27000, 958);
		dataset.adiciona(15000, 237);
		dataset.adiciona(25000, 130);
		dataset.adiciona(21000, 70);
		dataset.adiciona(6700, 57);
		dataset.adiciona(28000, 50);
		dataset.adiciona(9100, 38);
		dataset.adiciona(10000, 15);
	}
	
	/**
	 * Dataset Heiat and Heiat
	 */
	public static void montaDatasetArtigo6 (Dataset dataset)
	{
		dataset.setNome("Heiat and Heiat");
		dataset.adiciona(26.6, 290);
		dataset.adiciona(15.3, 137);
		dataset.adiciona(23.4, 244);
		dataset.adiciona(24.4, 226);
		dataset.adiciona(20.6, 179);
		dataset.adiciona(20.9, 205);
		dataset.adiciona(16.6, 178);
		dataset.adiciona(30.9, 268);
		dataset.adiciona(16.1, 192);
		dataset.adiciona(22.7, 234);				
		dataset.adiciona(15.4, 178);		
		dataset.adiciona(14.2, 157);		
		dataset.adiciona(47.8, 368);		
		dataset.adiciona(14.8, 178);	
		dataset.adiciona(30.4, 278);
		dataset.adiciona(32.8, 314);		
		dataset.adiciona(17.6, 187);		
		dataset.adiciona(25.4, 234);		
		dataset.adiciona(15.8, 198);
		dataset.adiciona(17, 222);		
		dataset.adiciona(16.2, 157);
		dataset.adiciona(33.6, 279);
		dataset.adiciona(24.5, 148);
		dataset.adiciona(28.3, 276);
		dataset.adiciona(27.2, 288);
		dataset.adiciona(18.1, 146);
		dataset.adiciona(22.8, 195);
		dataset.adiciona(42.1, 335);
		dataset.adiciona(33.8, 305);
		dataset.adiciona(16.9, 186);
		dataset.adiciona(20.7, 196);
		dataset.adiciona(20.4, 227);
		dataset.adiciona(31.3, 262);
		dataset.adiciona(27.8, 258);
		dataset.adiciona(38.9, 315);
	}
	
	/**
	 * Dataset Academic Environment
	 */
	public static void montaDatasetArtigo7 (Dataset dataset)
	{
		dataset.setNome("Academic Environment");
		dataset.adiciona(3130, 561.4);
		dataset.adiciona(1869, 269.68);
		dataset.adiciona(2633, 219);
		dataset.adiciona(1594, 238.61);
		dataset.adiciona(1917, 135.77);
		dataset.adiciona(1779, 197.75);
		dataset.adiciona(932, 172);
		dataset.adiciona(1403, 319);
		dataset.adiciona(1075, 86.05);
		dataset.adiciona(2980, 339);
		dataset.adiciona(1635, 730);
		dataset.adiciona(8006, 422);
		dataset.adiciona(2374, 299.85);
		dataset.adiciona(2185, 241.35);
		dataset.adiciona(3360, 328.35);
		dataset.adiciona(1480, 261);
		dataset.adiciona(1761, 190.57);
		dataset.adiciona(1913, 156.83);
		dataset.adiciona(3175, 159);
		dataset.adiciona(835, 222.83);
		dataset.adiciona(1510, 327.5);
		dataset.adiciona(3686, 216);
		dataset.adiciona(3806, 374.5);
		dataset.adiciona(2978, 436);
		dataset.adiciona(1185, 220);
		dataset.adiciona(5348, 900);
		dataset.adiciona(2281, 387);
		dataset.adiciona(2688, 742);
		dataset.adiciona(5629, 427.66);
		dataset.adiciona(3695, 866);
		dataset.adiciona(6250, 816);
		dataset.adiciona(2698, 368);
		dataset.adiciona(4160, 671.42);
		dataset.adiciona(1480, 264);
		dataset.adiciona(3131, 239);
		dataset.adiciona(5312, 504);
		dataset.adiciona(8116, 1154);
		dataset.adiciona(6193, 451.5);
		dataset.adiciona(6501, 1271);
		dataset.adiciona(5159, 367);
		dataset.adiciona(4841, 258.65);
		dataset.adiciona(6199, 658.6);
		dataset.adiciona(2816, 312.25);
		dataset.adiciona(1898, 130);
		dataset.adiciona(2445, 290);
		dataset.adiciona(1200, 142.5);
		dataset.adiciona(6700, 247.25);
		dataset.adiciona(2830, 297.5);
	}
	
	/**
	 * Dataset Kemerer
	 */
	public static void montaDatasetArtigo8 (Dataset dataset)
	{
		dataset.setNome("Kemerer");
		dataset.adiciona(1217.1, 287);
		dataset.adiciona(507.3, 82.5);
		dataset.adiciona(2306.8, 1107.31);
		dataset.adiciona(788.5, 86.90);
		dataset.adiciona(1337.6, 336.3);
		dataset.adiciona(421.3, 84);
		dataset.adiciona(99.9, 23.2);
		dataset.adiciona(993, 130.3);
		dataset.adiciona(1592.9, 116);
		dataset.adiciona(240, 72);
		dataset.adiciona(1611, 258.7);
		dataset.adiciona(789, 230.70);
		dataset.adiciona(690.9, 157);
		dataset.adiciona(1347.5, 246.9);
		dataset.adiciona(1044.3, 69.9);
	}
	
	/**
	 * Dataset Miyazaki et al.
	 */
	public static void montaDatasetArtigo9 (Dataset dataset)
	{
		dataset.setNome("Miyazaki et al.");
		dataset.adiciona(44.2, 59.5);
		dataset.adiciona(59, 85);
		dataset.adiciona(36.9, 31.5);
		dataset.adiciona(48.3, 58.9);
		dataset.adiciona(43.1, 67.3);
		dataset.adiciona(9.7, 8.2);
		dataset.adiciona(13.7, 13.9);
		dataset.adiciona(59.7, 36);
		dataset.adiciona(84.4, 89.5);
		dataset.adiciona(56.5, 39.5);
		dataset.adiciona(42.1, 27.5);
		dataset.adiciona(84.6, 103.5);
		dataset.adiciona(390, 212);
		dataset.adiciona(55.8, 48);
		dataset.adiciona(59.8, 18);
		dataset.adiciona(68.9, 43.8);
		dataset.adiciona(8.6, 7.1);
		dataset.adiciona(22.3, 20.6);
		dataset.adiciona(79.1, 32.7);
		dataset.adiciona(13.7, 9);
		dataset.adiciona(7.8, 7.5);
		dataset.adiciona(42.1, 37.2);
		dataset.adiciona(46.6, 59.1);
		dataset.adiciona(86.1, 54);
		dataset.adiciona(72.1, 37.1);
		dataset.adiciona(272.7, 157);
		dataset.adiciona(130.6, 39);
		dataset.adiciona(192.2, 170);
		dataset.adiciona(31.5, 37);
		dataset.adiciona(226.1, 340);
		dataset.adiciona(417.6, 1586);
		dataset.adiciona(20.2, 29);
		dataset.adiciona(16.1, 36.9);
		dataset.adiciona(31.2, 37);
		dataset.adiciona(23.7, 23);
		dataset.adiciona(43, 52);
		dataset.adiciona(20.2, 22);
		dataset.adiciona(54.6, 42.3);
		dataset.adiciona(37.5, 34.3);
		dataset.adiciona(64.1, 55.7);
		dataset.adiciona(49.2, 39);
		dataset.adiciona(50.8, 120);
		dataset.adiciona(17.1, 6);
		dataset.adiciona(6.9, 5.6);
		dataset.adiciona(25.2, 20.1);
		dataset.adiciona(55, 31.50);
		dataset.adiciona(35.1, 50.1);
	}
	
	/**
	 * Dataset Shepperd and Schofield
	 */
	public static void montaDatasetArtigo10 (Dataset dataset)
	{
		dataset.setNome("Shepperd and Schofield");
		dataset.adiciona(105, 305.22);
		dataset.adiciona(237, 330.29);
		dataset.adiciona(98, 333.96);
		dataset.adiciona(24, 150.40);
		dataset.adiciona(197, 544.61);
		dataset.adiciona(39, 117.87);
		dataset.adiciona(284, 1115.54);
		dataset.adiciona(37, 158.56);
		dataset.adiciona(53, 573.71);
		dataset.adiciona(116, 276.95);
		dataset.adiciona(38, 97.45);
		dataset.adiciona(180, 374.34);
		dataset.adiciona(43, 167.12);
		dataset.adiciona(84, 358.37);
		dataset.adiciona(257, 123.1);
		dataset.adiciona(6, 23.54);
		dataset.adiciona(5, 34.25);
		dataset.adiciona(3, 31.8);
	}
	
	/**
	 * Dataset Desharnais
	 */
	public static void montaDatasetArtigo11 (Dataset dataset)
	{
		dataset.setNome("Desharnais");
		dataset.adiciona(305, 5152);
		dataset.adiciona(321, 5635);
		dataset.adiciona(100, 805);
		dataset.adiciona(319, 3829);
		dataset.adiciona(234, 2149);
		dataset.adiciona(186, 2821);
		dataset.adiciona(161, 2569);
		dataset.adiciona(238, 3913);
		dataset.adiciona(260, 7854);
		dataset.adiciona(116, 2422);
		dataset.adiciona(266, 4067);
		dataset.adiciona(258, 9051);
		dataset.adiciona(105, 2282);
		dataset.adiciona(223, 4172);
		dataset.adiciona(344, 4977);
		dataset.adiciona(167, 1617);
		dataset.adiciona(100, 3192);
		dataset.adiciona(384, 3437);
		dataset.adiciona(395, 4494);
		dataset.adiciona(92, 840);
		dataset.adiciona(587, 14973);
		dataset.adiciona(258, 5180);
		dataset.adiciona(438, 5775);
		dataset.adiciona(382, 10577);
		dataset.adiciona(289, 3983);
		dataset.adiciona(316, 3164);
		dataset.adiciona(306, 3542);
		dataset.adiciona(472, 4277);
		dataset.adiciona(286, 7252);
		dataset.adiciona(452, 3948);
		dataset.adiciona(207, 3927);
		dataset.adiciona(183, 710);
		dataset.adiciona(252, 2429);
		dataset.adiciona(285, 6405);
		dataset.adiciona(175, 651);
		dataset.adiciona(436, 9135);
		dataset.adiciona(377, 1435);
		dataset.adiciona(404, 5922);
		dataset.adiciona(217, 847);
		dataset.adiciona(447, 8050);
		dataset.adiciona(499, 4620);
		dataset.adiciona(793, 2352);
		dataset.adiciona(118, 2174);
		dataset.adiciona(514, 19894);
		dataset.adiciona(308, 6699);
		dataset.adiciona(505, 14987);
		dataset.adiciona(259, 4004);
		dataset.adiciona(311, 12824);
		dataset.adiciona(145, 2331);
		dataset.adiciona(204, 5817);
		dataset.adiciona(188, 2989);
		dataset.adiciona(135, 3136);
		dataset.adiciona(342, 14434);
		dataset.adiciona(157, 2583);
		dataset.adiciona(221, 3647);
		dataset.adiciona(432, 8232);
		dataset.adiciona(167, 3276);
		dataset.adiciona(176, 2723);
		dataset.adiciona(246, 3472);
		dataset.adiciona(79, 1575);
		dataset.adiciona(233, 2926);
	}
	
	/**
	 * Dataset Kitchenham and Taylor
	 */
	public static void montaDatasetArtigo12 (Dataset dataset)
	{
		dataset.setNome("Kitchenham and Taylor");
		dataset.adiciona(6050, 16.7);
		dataset.adiciona(8363, 22.6);
		dataset.adiciona(13334, 32.2);
		dataset.adiciona(5942, 3.9);
		dataset.adiciona(3315, 17.3);
		dataset.adiciona(38988, 67.7);
		dataset.adiciona(38614, 10.1);
		dataset.adiciona(12762, 19.3);
		dataset.adiciona(13551, 10.6);
		dataset.adiciona(26500, 59.5);
		dataset.adiciona(17431, 60.5);
		dataset.adiciona(14142, 110.5);
		dataset.adiciona(6534, 36);
		dataset.adiciona(3040, 4);
		dataset.adiciona(4371, 24.8);
		dataset.adiciona(15091, 298.4);
		dataset.adiciona(29570, 47);
		dataset.adiciona(23300, 148.9);
		dataset.adiciona(3000, 14.4);
		dataset.adiciona(25751, 115.9);
		dataset.adiciona(19637, 256.8);
		dataset.adiciona(4000, 15);
		dataset.adiciona(2000, 3);
		dataset.adiciona(2250, 9.5);
		dataset.adiciona(2200, 10);
		dataset.adiciona(13000, 16.5);
		dataset.adiciona(9500, 20);
		dataset.adiciona(7000, 15.5);
		dataset.adiciona(3000, 11);
		dataset.adiciona(8500, 41);
		dataset.adiciona(1500, 12);
		dataset.adiciona(4300, 9);
		dataset.adiciona(7200, 26);
	}

	/**
	 * Execução inicial da programação genética, com a escolha de um dataset (chamada de método para preenchimento), chamada dos métodos de GP e a exibição da árvore de melhor aptidão da última geração e seus valores de MMRE e PRED.
	 * Não é mais utilizada.
	 */
//	private static void execucaoTradicional()
//	{
//		GeradorPopulacaoInicial geradorPop =  new GeradorPopulacaoInicial();
//		OperacaoGenetica operacaoGenetica = new OperacaoGenetica();
//		Dataset dataset = new Dataset();
//		//monta(dataset);
//		montaDatasetArtigo2(dataset);
//		
//		int tamanhoPopulacao = 70;
//		int profundidadeLimiteArvores = 4;
//		Random random = new Random();
//		//random.setSeed(1234567);
//		
//		Populacao primeiraGeracao = geradorPop.inicializacaoRampedHalfAndHalf(tamanhoPopulacao, profundidadeLimiteArvores, random);
//		
//		System.out.println("\n\n<GERAÇÃO 0 -------------------------------------------------->");
//		for (ArvoreExpressao arvore : primeiraGeracao.getIndividuos())
//		{
//			System.out.println(arvore.stringExpressao(arvore.getRaiz()));
//		}
//		System.out.println("<FIM GERAÇÃO 0 -------------------------------------------------->\n");
//		
//		Populacao proximaGeracao = new Populacao();
//		proximaGeracao.setIndividuos(primeiraGeracao.getIndividuos());
//		
//		for (int i = 0; i < 50; i++)
//		{
//			proximaGeracao.setIndividuos(operacaoGenetica.selecao(proximaGeracao, dataset, random).getIndividuos());
//			proximaGeracao.setNumeroGeracao(proximaGeracao.getNumeroGeracao() + 1);
//		}
//		
//		System.out.println("\nÁrvore de melhor aptidão: " + proximaGeracao.getIndividuos().get(0).stringExpressao(proximaGeracao.getIndividuos().get(0).getRaiz()) 
//				+ "\nAptidão --> " + proximaGeracao.getIndividuos().get(0).getAptidao()
//				 + "\nPred(0.25) --> " + proximaGeracao.getIndividuos().get(0).getPred());
//		
//		
//		ArvoreExpressao arvore = new ArvoreExpressao (proximaGeracao.getIndividuos().get(0).getRaiz());
//		
//		try 
//		{
//			PrintWriter out = new PrintWriter("resultado.txt");
//			out.println("Expressão:   " + arvore.stringExpressao(arvore.getRaiz()));
//		   
//			for (Dataset.Entrada entrada : dataset.getEntradas())
//			{
//				double yCalculado = arvore.resolverExpressao(entrada.getX());
//				out.println(yCalculado + "\t" + Math.abs((entrada.getY() - yCalculado) / entrada.getY()));
//			}
//				
//			
//			out.close();
//		}
//		catch (Exception E)
//		{
//			E.printStackTrace();
//		}
//	}
	
	public static void monta (Dataset dataset)
	{
		for (int x = 1; x <= 5; x++)
		{
			//dataset.adiciona(x, (2 * (x * x * x)) + (4 * (x * x)) + (5 * x) - 1);
			//dataset.adiciona(x, (x * x) + 1);
			dataset.adiciona(x, (x * 5) + 2);
			//dataset.adiciona(x, (x * x) - (2 * x) + 1);
			//dataset.adiciona(x, x - 5);
		}
	}
	
}
