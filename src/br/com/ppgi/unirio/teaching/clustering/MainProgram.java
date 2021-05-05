package br.com.ppgi.unirio.teaching.clustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ppgi.unirio.teaching.clustering.model.Project;
import br.com.ppgi.unirio.teaching.clustering.reader.CDAReader;
import br.com.ppgi.unirio.teaching.clustering.search.IteratedLocalSearch;

public class MainProgram
{
//	private static String BASE_DIRECTORY = "C:\\Users\\User\\Desktop\\Codigos\\HillClimbing\\data\\clustering\\";
	private static String ODEM_BASE_DIRECTORY = "data//ODEMFile//";
	private static String DEP_BASE_DIRECTORY = "data//Experiment//ClsDepComb//";
	
	
	private static String[] instanceFilenamesReals =
	{
			"apache_ant1090C.odem",
			"apache_ant_taskdef629C.odem",
			"apache_lucene_core741C.odem",
			"apache_zip36C.odem",
			"dom4j-1.5.2195C.odem",
			"eclipse_jgit912C.odem",
			"forms-1.3.068C.odem",
			"gae_plugin_core140C.odem",
			"itextpdf657C.odem",
			"jace340C.odem",
			"javacc154C.odem",
			"JavaGeom172C.odem",
			"javaocr59C.odem",
			"javaws378C.odem",
			"javaws-7378C.odem",
			"jconsole-1.7.0220C.odem",
			"jdendogram177C.odem",
			"jfluid-1.7.082C.odem",
			"jkaryoscope136C.odem",
			"JMetal190C.odem",
			"jml270C.odem",
			"jnanoxml25C.odem",
			"joda-money26C.odem",
			"jpassword269C.odem",
			"JPassword96C.odem",
			"jscatterplot74C.odem",
			"jstl-1.0.618C.odem",
			"jtreeview329C.odem",
			"Jung-graph207C.odem",
			"Jung-visualization221C.odem",
			"junit-3.8.1100C.odem",
			"jxls-core83C.odem",
			"jxls-reader27C.odem",
			"log4j-1.2.16308C.odem",
			"lwjgl569C.odem",
			"notepad-full299C.odem",
			"notepad-model46C.odem",
			"pdf_renderer199C.odem",
			"pfcda_base67C.odem",
			"pfcda_swing252C.odem",
			"poormans_2.3304C.odem",
			"res_cobol483C.odem",
			"seemp31C.odem",
			"servletapi-2.363C.odem",
			"tinytim134C.odem",
			"udt-java56C.odem",
			"xmlapi184C.odem",
			"xmldom119C.odem",
			"ycomplete2898C.odem",
			"ylayout1162C.odem",
			"y_base558C.odem"
	};
	
	private static String[] instanceFilenamesRealsDep =
	{
		"acqCIGNA",
		"apache_ant",
		"apache_ant_taskdef",
		"apache_lucene_core",
		"apache_zip",
		"bash",
		"bison",
		"bitchx",
		"bootp",
		"boxer",
		"bunch",
		"bunch2",
		"bunchall",
		"bunch_2",
		"cia",
		"cia++",
		"ciald",
		"compiler",
		"crond",
		"cyrus-sasl",
		"dhcpd-1",
		"dhcpd-2",
		"dom4j",
		"dot",
		"eclipse_jgit",
		"elm-1",
		"elm-2",
		"exim",
		"forms",
		"gae_plugin_core",
		"gnupg",
		"graph10up193",
		"graph10up49",
		"grappa",
		"hw",
		"icecast",
		"imapd-1",
		"incl",
		"inn",
		"ispell",
		"itextpdf",
		"JACE",
		"javacc",
		"JavaGeom",
		"javaocr",
		"javaws",
		"jconsole",
		"jdendogram",
		"jfluid",
		"jkaryoscope",
		"jmetal",
		"jml-1.0b4",
		"jodamoney",
		"joe",
		"jpassword",
		"jpassword2",
		"jscatterplot",
		"jstl",
		"jtreeview",
		"Jung_graph_model",
		"jung_visualization",
		"junit",
		"jxlscore",
		"jxlsreader",
		"krb5",
		"lab4",
		"linux",
		"log4j",
		"lslayout",
		"lucent",
		"lwjgl-2.8.4",
		"lynx",
		"mailx",
		"micq",
		"minicom",
		"Modulizer",
		"mod_ssl",
		"mtunis",
		"nanoxml",
		"ncurses",
		"net-tools",
		"netkit-ftp",
		"netkit-inetd",
		"netkit-ping",
		"netkit-tftpd",
		"nmh",
		"nos",
		"notelab-full",
		"nss_ldap",
		"pdf_renderer",
		"pfcda_base",
		"pfcda_swing",
		"php",
		"ping_libc",
		"Poormans CMS",
		"random",
		"rcs",
		"regexp",
		"res_cobol",
		"screen",
		"seemp",
		"servletapi",
		"sharutils",
		"slang",
		"slrn",
		"small",
		"spdb",
		"squid",
		"star",
		"stunnel",
		"swing",
		"sysklogd-1",
		"tcsh",
		"telnetd",
		"tinytim",
		"udt-java",
		"wu-ftpd-1",
		"wu-ftpd-3",
		"xmlapi",
		"xmldom",
		"xntp",
		"xtell",
		"ylayout",
		"y_base"
	};

	
	public static final void main(String[] args) throws Exception
	{
		runILS();
	}
	
		public static void runILS() throws Exception {
			
			int runTimes = 30;
			boolean READ_ODEM_FILE = false;
			
			
			List<Project> instances = new ArrayList<Project>();
			CDAReader reader = new CDAReader();
			
			double a1=1;
			double a2=0;
			double b1=1;
			double b2=0.5;
			
			
			if (READ_ODEM_FILE) {
				for (String filename : instanceFilenamesReals)
					instances.add(reader.execute(ODEM_BASE_DIRECTORY + filename));
			}
			else {
				for (String filename : instanceFilenamesRealsDep)
					instances.add(reader.executeDependencyFile(DEP_BASE_DIRECTORY + filename));
			}

			for (Project instance : instances) {
				StringBuilder sb = new StringBuilder();
			
				new Thread(new Runnable() {
		            @Override
		            public void run() {		            
				
						for(int n=1; n<=runTimes; n++){	
							try {
								new IteratedLocalSearch(instance, 10000, a1, a2, b1, b2).execute(n, sb);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
						}
						
						File file = new File("data//Experiment//ILSoutput//"+ instance.getName() + getStringTime()+ ".comb");
					    BufferedWriter writer;
						try {
							writer = new BufferedWriter(new FileWriter(file));
							writer.write(sb.toString());
							writer.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}			
		          }
		        }).start();  	
			}			
		}
		
		
		public void runILSMQValidation() throws Exception{
			List<Project> instances = new ArrayList<Project>();
			CDAReader reader = new CDAReader();

			for (String filename : instanceFilenamesReals)
				instances.add(reader.execute(ODEM_BASE_DIRECTORY + filename));

			double a1 = -5;
			double a2 = -5;
			double b1 = -5;
			double b2 = -5;

			while (a1 <= 5)
			{
				a2 = -5;

				while (a2 <= 5)
				{
					b1 = -5;

					while (b1 <= 5)
					{
						b2 = -5;

						while (b2 <= 5)
						{
//							a1=1;
//							a2=0;
//							b1=1;
//							b2=0.5;

							for (Project instance : instances)
								new IteratedLocalSearch(instance, 10000, a1, a2, b1, b2).execute();

							b2 = b2 + 0.5;
						}

						b1 = b1 + 0.5;
					}

					a2 = a2 + 0.5;

				}

				a1 = a1 + 0.5;
			}

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