package br.com.ppgi.unirio.teaching.clustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;
import br.com.ppgi.unirio.teaching.clustering.model.Project;
import br.com.ppgi.unirio.teaching.clustering.reader.DependencyReader;
import br.com.ppgi.unirio.teaching.clustering.search.IteratedLocalSearch;
import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveAbstract;
import br.com.ppgi.unirio.teaching.clustering.search.constructive.ConstrutiveRandom;

public class MainProgram
{
//	private static String BASE_DIRECTORY = "C:\\Users\\User\\Desktop\\Codigos\\HillClimbing\\data\\clustering\\";
//	private static String ODEM_BASE_DIRECTORY = "data//ODEMFile//";
	private static String DEP_BASE_DIRECTORY = "data//Experiment//ClsDepComb//";
	private static String ILS_INTERPRETATION_DIRECTORY = "data//Experiment//ILSInterpretation//";
	private static String PKG_BASE_DIRECTORY = "data//Experiment//PkgClsComb//";
	
//	private static List<Project> instances = new ArrayList<Project>();
//	private static List<int[]> bestSolutions= new ArrayList<int[]>();
	
	
//	static double fa1 = 0;
//	static double fa2 = 0;
//	static double fb1 = 0;
//	static double fb2 = 0;
//	
	
//	private static String[] instanceFilenamesReals =
//	{
//			"apache_ant1090C.odem",
//			"apache_ant_taskdef629C.odem",
//			"apache_lucene_core741C.odem",
//			"apache_zip36C.odem",
//			"dom4j-1.5.2195C.odem",
//			"eclipse_jgit912C.odem",
//			"forms-1.3.068C.odem",
//			"gae_plugin_core140C.odem",
//			"itextpdf657C.odem",
//			"jace340C.odem",
//			"javacc154C.odem",
//			"JavaGeom172C.odem",
//			"javaocr59C.odem",
//			"javaws378C.odem",
//			"javaws-7378C.odem",
//			"jconsole-1.7.0220C.odem",
//			"jdendogram177C.odem",
//			"jfluid-1.7.082C.odem",
//			"jkaryoscope136C.odem",
//			"JMetal190C.odem",
//			"jml270C.odem",
//			"jnanoxml25C.odem",
//			"joda-money26C.odem",
//			"jpassword269C.odem",
//			"JPassword96C.odem",
//			"jscatterplot74C.odem",
//			"jstl-1.0.618C.odem",
//			"jtreeview329C.odem",
//			"Jung-graph207C.odem",
//			"Jung-visualization221C.odem",
//			"junit-3.8.1100C.odem",
//			"jxls-core83C.odem",
//			"jxls-reader27C.odem",
//			"log4j-1.2.16308C.odem",
//			"lwjgl569C.odem",
//			"notepad-full299C.odem",
//			"notepad-model46C.odem",
//			"pdf_renderer199C.odem",
//			"pfcda_base67C.odem",
//			"pfcda_swing252C.odem",
//			"poormans_2.3304C.odem",
//			"res_cobol483C.odem",
//			"seemp31C.odem",
//			"servletapi-2.363C.odem",
//			"tinytim134C.odem",
//			"udt-java56C.odem",
//			"xmlapi184C.odem",
//			"xmldom119C.odem",
//			"ycomplete2898C.odem",
//			"ylayout1162C.odem",
//			"y_base558C.odem"
//	};
//	
//	private static String[] instanceFilenamesRealsDep =
//	{
////		"aaa-fake-06",
////		"acqCIGNA",
////		"apache_ant",
////		"apache_ant_taskdef",
////		"apache_lucene_core",
////		"apache_zip",
////		"bash",
////		"bison",
////		"bitchx",
////		"bootp",
////		"boxer",
////		"bunch",
////		"bunch2",
////		"bunchall",
////		"bunch_2",
////		"cia",
////		"cia++",
////		"ciald",
////		"compiler",
////		"crond",
////		"cyrus-sasl",
////		"dhcpd-1",
////		"dhcpd-2",
////		"dom4j",
////		"dot",
////		"eclipse_jgit",
////		"elm-1",
////		"elm-2",
////		"exim",
////		"forms",
////		"gae_plugin_core",
////		"gnupg",
////		"graph10up193",
////		"graph10up49",
////		"grappa",
////		"hw",
////		"icecast",
////		"imapd-1",
////		"incl",
////		"inn",
////		"ispell",
////		"itextpdf",
////		"JACE",
////		"javacc",
////		"JavaGeom",
////		"javaocr",
////		"javaws",
////		"jconsole",
////		"jdendogram",
////		"jfluid",
////		"jkaryoscope",
//		"jmetal"
////		"jml-1.0b4",
////		"jodamoney",
////		"joe",
////		"jpassword",
////		"jpassword2",
////		"jscatterplot",
////		"jstl",
////		"jtreeview",
////		"Jung_graph_model",
////		"jung_visualization",
////		"junit",
////		"jxlscore",
////		"jxlsreader",
////		"krb5",
////		"lab4",
////		"linux",
////		"log4j",
////		"lslayout",
////		"lucent",
////		"lwjgl-2.8.4",
////		"lynx",
////		"mailx",
////		"micq",
////		"minicom",
////		"Modulizer",
////		"mod_ssl",
////		"mtunis",
////		"nanoxml",
////		"ncurses",
////		"net-tools",
////		"netkit-ftp",
////		"netkit-inetd",
////		"netkit-ping",
////		"netkit-tftpd",
////		"nmh",
////		"nos",
////		"notelab-full",
////		"nss_ldap",
////		"pdf_renderer",
////		"pfcda_base",
////		"pfcda_swing",
////		"php",
////		"ping_libc",
////		"Poormans CMS",
////		"random",
////		"rcs",
////		"regexp",
////		"res_cobol",
////		"screen",
////		"seemp",
////		"servletapi",
////		"sharutils",
////		"slang",
////		"slrn",
////		"small",
////		"spdb",
////		"squid",
////		"star",
////		"stunnel",
////		"swing",
////		"sysklogd-1",
////		"tcsh",
////		"telnetd",
////		"tinytim",
////		"udt-java",
////		"wu-ftpd-1",
////		"wu-ftpd-3",
////		"xmlapi",
////		"xmldom",
////		"xntp",
////		"xtell",
////		"ylayout",
////		"y_base"
//	};
//
//	
//	public static final void main(String[] args) throws Exception
//	{
//		boolean READ_ODEM_FILE = false;
//		CDAReader reader = new CDAReader();
//		
//		if (READ_ODEM_FILE) {
//			for (String filename : instanceFilenamesReals)
//				instances.add(reader.execute(ODEM_BASE_DIRECTORY + filename));
//		}
//		else {
//			for (String filename : instanceFilenamesRealsDep)
//				instances.add(reader.executeDependencyFile(DEP_BASE_DIRECTORY + filename));
//		}
//		
////		generateSolution ();
//		
//		runILSMQValidation();
//		
////		runILS();
//	}
//	
//		public static void runILS() throws Exception {
//			
//			int runTimes = 1;
//			
//			double a1=1;
//			double a2=0;
//			double b1=1;
//			double b2=0.5;		
//
//			for (Project instance : instances) {
//				StringBuilder sb = new StringBuilder();
//			
//				new Thread(new Runnable() {
//		            @Override
//		            public void run() {		            
//				
//						for(int n=1; n<=runTimes; n++){	
//							try {
//								new IteratedLocalSearch(instance, 100000, a1, a2, b1, b2).execute(n, sb);
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}	
//						}
//						
//						File file = new File("data//Experiment//ILSoutput//"+ instance.getName() + getStringTime()+ ".comb");
//					    BufferedWriter writer;
//						try {
//							writer = new BufferedWriter(new FileWriter(file));
//							writer.write(sb.toString());
//							writer.close();
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}			
//		          }
//		        }).start();  	
//			}			
//		}
//		
//		
//		public static void runILSMQValidation() throws Exception{
//			double range = 0.5;
//		
//			double a1 = -range;
//			double a2 = -range;
//			double b1 = -range;
//			double b2 = -range;
//			
////			ExecutorService pool = Executors.newFixedThreadPool(8);
//
//			for (Project instance : instances) {
//				StringBuilder sb = new StringBuilder();
//				while (a1 <= range)	{
//					a2 = -range;
//					while (a2 <= range)	{
//						b1 = -range;
//						while (b1 <= range)	{
//							b2 = -range;
//							while (b2 <= range)	{
//								 
////								fa1 = a1;
////								fa2 = a2;
////								fb1 = b1;
////								fb2 = b2;
//								fa1=1;
//								fa2=0;
//								fb1=1;
//								fb2=0.5;
////								pool.execute(new Runnable() {
//////								new Thread(new Runnable() {
////						            @Override
////						            public void run() {		            	
////										try {						
//											new IteratedLocalSearch(instance, 100000, fa1, fa2, fb1, fb2).execute(sb);
////										} catch (Exception e) {
////											// TODO Auto-generated catch block
////											e.printStackTrace();
////										}			
////									}
////								});
////						        }).start();
//								
//								b2 = b2 + 0.5;
//							}
//							b1 = b1 + 0.5;
//						}
//						a2 = a2 + 0.5;
//					}
//					a1 = a1 + 0.5;
//				}
//			
//				File file = new File("data//Experiment//ILSoutput//"+ instance.getName() + getStringTime()+ ".comb");
//			    BufferedWriter writer;
//				try {
//					writer = new BufferedWriter(new FileWriter(file));
//					writer.write(sb.toString());
//					writer.close();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		}		
//		
//		
////		preparar o interpretador primeiro
//		
//		private static void generateSolution() throws IOException{
//			
//			int[] fakeSolution= {2, 2, 9, 9, 1, 1};
//			
//			int[] solution = {156, 191, 324, 173, 355, 41, 326, 212, 344, 82, 54, 231, 205, 104, 38, 38, 234, 326, 1, 1, 326, 291, 43, 377, 377, 326, 234, 234, 326, 187, 74, 229, 212, 351, 54, 281, 108, 49, 229, 108, 41, 369, 54, 330, 82, 351, 351, 41, 228, 41, 82, 46, 46, 54, 45, 238, 238, 49, 229, 228, 225, 228, 225, 358, 358, 205, 358, 10, 358, 101, 95, 95, 101, 12, 12, 101, 177, 238, 237, 355, 143, 143, 143, 211, 203, 302, 304, 187, 367, 203, 353, 302, 208, 3, 302, 156, 263, 290, 173, 367, 156, 290, 59, 234, 282, 282, 59, 11, 12, 326, 49, 34, 35, 355, 367, 353, 225, 191, 191, 367, 324, 291, 133, 74, 67, 291, 234, 12, 324, 324, 355, 350, 355, 12, 156, 326, 12, 344, 34, 156, 156, 104, 173, 43, 324, 326, 58, 265, 90, 191, 177, 299, 347, 191, 347, 374, 374, 374, 347, 329, 374, 139, 31, 13, 24, 369, 95, 265, 90, 323, 65, 323, 113, 65, 101, 237, 169, 169, 169, 185, 8, 185, 245, 41, 245, 228, 364, 121, 98, 98};
//			
//			bestSolutions.add(fakeSolution);
//			bestSolutions.add(solution);
//			
//			int n = 0;
//			for (Project instance : instances) {
//				StringBuilder sb1 = new StringBuilder();
//				int [] bestSolution = bestSolutions.get(n);
//				for(int i=0; i<(bestSolution.length); i++){
//					if( bestSolution[i]>=0) {
////						System.out.println("contain PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
//						sb1.append("contain PKG" + bestSolution[i] + " " + instance.getClassIndex(i).getName());
//						sb1.append(System.lineSeparator());			
//					}
//				}
//
//				File file = new File("data//Experiment//ILSInterpretation//"+ instance.getName() + getStringTime()+ ".comb");
//			    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//			    try {
//			        writer.write(sb1.toString());	    
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				   
//				} finally {
//					writer.close();
//				}
//			    n++;
//			}
//		}
//		
//
//		private static String getStringTime() {
//			Calendar data;
//			data = Calendar.getInstance();
//			int segundo = data.get(Calendar.SECOND);
//	        int minuto = data.get(Calendar.MINUTE);
//	        int hora = data.get(Calendar.HOUR_OF_DAY);
//	        int dia = data.get(Calendar.DAY_OF_MONTH);	
//	        int mes = data.get(Calendar.MONTH);;	
//	        int ano = data.get(Calendar.YEAR);;		
//			return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
//		}
				
		public static final void main(String[] args) throws Exception
		{
			File file = new File(DEP_BASE_DIRECTORY);
			DecimalFormat df4 = new DecimalFormat("0.0000");
			
			//ConstrutiveAbstract constructor = new ConstrutiveAglomerativeMQ();
			ConstrutiveAbstract constructor = new ConstrutiveRandom();

	        for (String projectName : file.list()) 
	        {
	        	for(int n=1; n<=100; n++){

	        		long startTimestamp = System.currentTimeMillis();
		        	
		    		DependencyReader reader = new DependencyReader();
		    		Project project = reader.load(DEP_BASE_DIRECTORY + projectName);

		    		IteratedLocalSearch ils = new IteratedLocalSearch(constructor, project, 100_000, 1.0, 0.0, 1.0, 0.5);
		    		int[] bestSolution = ils.execute();
		    		
		    		long finishTimestamp = System.currentTimeMillis();
		    		long seconds = (finishTimestamp - startTimestamp);
		    		
		    		long memory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
		    		System.out.println(padLeft(projectName, 20) + " " + padRight("" + project.getClassCount(), 10) + " " + padRight(df4.format(ils.getBestFitness()), 10) + " " + padRight("" + seconds, 10) + " ms " + padRight("" + memory, 10) + " MB" + " MOJOFM " + padRight("" + runMOJOComparison(generateSolution(project, projectName, bestSolution), PKG_BASE_DIRECTORY + projectName + ".comb", "-fm") , 10) + "%") ;
	        	}	        	
	        }
		}
		

		private static String generateSolution(Project project, String projectName, int[] bestSolution) throws IOException{
					
			StringBuilder sb1 = new StringBuilder();
			for(int i=0; i<(bestSolution.length); i++){
				if( bestSolution[i]>=0) {
//					System.out.println("contain PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
					sb1.append("PKG" + bestSolution[i] + " " + project.getClassIndex(i).getName());
					sb1.append(System.lineSeparator());			
				}
			}

			File file = new File(ILS_INTERPRETATION_DIRECTORY + projectName + getStringTime()+ ".comb");
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    try {
		        writer.write(sb1.toString());	    
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   
			} finally {
				writer.close();
			}
			return file.getCanonicalPath();
	}

		
		private static double runMOJOComparison(String file1, String file2, String param) throws IOException {
			String[] args1 = new String[3];
			args1[0] = file1;
			args1[1] = file2;
			args1[2] = param; 
			return MoJo.MojoFM(args1);
		}

		public static String padLeft(String s, int length) 
		{
		    StringBuilder sb = new StringBuilder();
		    sb.append(s);
		    
		    while (sb.length() < length)
		        sb.append(' ');
		    
		    return sb.toString();
		}

		public static String padRight(String s, int length) 
		{
		    StringBuilder sb = new StringBuilder();
		    
		    while (sb.length() < length - s.length())
		        sb.append(' ');
		    
		    sb.append(s);
		    return sb.toString();
		}
		
		private static String getStringTime() {
		Calendar data;
		data = Calendar.getInstance();
		int segundo = data.get(Calendar.SECOND);
        int minuto = data.get(Calendar.MINUTE);
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int dia = data.get(Calendar.DAY_OF_MONTH);	
        int mes = data.get(Calendar.MONTH);;	
        int ano = data.get(Calendar.YEAR);;		
		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
	}

}