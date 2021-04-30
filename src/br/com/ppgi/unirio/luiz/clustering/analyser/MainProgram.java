package br.com.ppgi.unirio.luiz.clustering.analyser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import br.com.ppgi.unirio.luiz.clustering.calculator.MQCalculator;
import br.com.ppgi.unirio.luiz.clustering.model.Dependency;
import br.com.ppgi.unirio.luiz.clustering.model.Project;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectClass;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectLoader;
import br.com.ppgi.unirio.luiz.clustering.model.ProjectPackage;
import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;
import br.com.ppgi.unirio.marlon.smc.experiment.algorithm.LNSParameterTest;
import br.com.ppgi.unirio.marlon.smc.instance.file.InstanceParseException;
import br.com.ppgi.unirio.marlon.smc.mdg.ModuleDependencyGraph;

@SuppressWarnings("unused")
public class MainProgram
{
	
	private static List<String> instanceFilenames = new ArrayList<String>();
			
	public static final void main(String[] args) throws Exception
	{	
//	PrintStream ps =null;
//	FileOutputStream out = new FileOutputStream("data//Experiment//PkgClsComb//MQValidation_" + getStringTime() + ".data"); 
//	ps = new PrintStream(out);
	

	String equation = "";
	
	double a1 = -5;
	double a2 = -5;
	double b1 = -5;
	double b2 = -5;
	
//	while (a1<=5) {
//		a2 = -5;
//		while (a2<=5) {
//			b1 = -5;
//			while (b1<=5) {
//				b2 = -5;
//				while (b2<=5) {			
//					equation = "(" + a1 + "*x + " + a2 + "* y) / ( " + b1 + "*x + " + b2 + "* y)";
					
					equation = "(1 * x + 0 * y) / (1 * x + 0.5 * y)";

//					List<String> instanceFilenames = new ArrayList<String>();			
//					
//					/*setting the folder for app.jar file after the convertion from .apk to .jar */
//					File jarFilesFolder = new File("data\\JARFile\\"); // current directory
//						
//					/*starting the extraction of dependency relationship */
//					try {
//						/*loading the files from the specified folder for the jar files folder*/
//						File[] files = jarFilesFolder.listFiles(new FilenameFilter(){
//							
//							/*filtering the files to guarantee that only .jar files will be listed */
//							@Override
//							public boolean accept(File dir, String name) {
//								boolean result;
//								if(name.endsWith(".jar")){
//									result=true;
//								}
//								else{
//									result=false;
//								}
//								return result;
//							}
//						});
//						/*looping throw the files to add the .jar files*/
//						for (File file : files) {
//							if (!file.isDirectory()) {
//								instanceFilenames.add(file.getCanonicalPath());
//							}
//						}
//		
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//		Vector<Project> instances = new Vector<	Project>();
//		instances.addAll(ProjectLoader.readJarInstances(instanceFilenames));
														
		List<String> packageClassCombinationFilenamesLNS = new ArrayList<String>();
//		packageClassCombinationFilenamesLNS = ProjectLoader.runPackageClassCombinationExport(instances,false);
		packageClassCombinationFilenamesLNS.add ("data//Experiment//PkgClsComb//25042021000218joda-money-0.614032021020225.comb"); //jodamoney otimizado
							
		List<String> packageClassCombinationFilenames = new ArrayList<String>();
//		packageClassCombinationFilenames = ProjectLoader.runPackageClassCombinationExport(instances,false);
//		packageClassCombinationFilenames.add ("data//Experiment//PkgClsComb//jodamoney-1.0.121022021133253.comb");
		packageClassCombinationFilenames.add ("data//Experiment//PkgClsComb//joda-money-0.614032021020225.comb");
//		packageClassCombinationFilenames.add ("data//Experiment//PkgClsComb//jodamoney-1.0.121022021133212_LNS OPTIMIZATION.comb"); //jodamoney otimizado
		
		List<String> classDependencyCombinationFilenames = new ArrayList<String>();
//		classDependencyCombinationFilenames = ProjectLoader.runClassDependencyCombinationExport(instances);
//		classDependencyCombinationFilenames.add("data//Experiment//ClsDepComb//jodamoney-1.0.121022021133212.comb");
		classDependencyCombinationFilenames.add("data//Experiment//ClsDepComb//joda-money-0.614032021020225.comb");
		
		
		
		LNSParameterTest LNSP = new LNSParameterTest();
		
		File f1 = new File(classDependencyCombinationFilenames.get(0));
		File[] instanceFiles = new File[1];
		instanceFiles[0]= f1;
		
		List<String> lnsExperimentFilenames = new ArrayList<String>();
		try {
			lnsExperimentFilenames = LNSP.runExperiment(equation,instanceFiles);
		} catch (InstanceParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		ps.println("Equation: " + equation + " ; MojoFM: " + runMOJOComparison(packageClassCombinationFilenamesLNS.get(0),lnsExperimentFilenames.get(0), "-fm"));
		
		System.out.println ("Equation: " + equation + " ; MojoFM: " + runMOJOComparison(packageClassCombinationFilenamesLNS.get(0),lnsExperimentFilenames.get(0), "-fm"));
//		System.out.println(" - MojoFM: " + runMOJOComparison(packageClassCombinationFilenamesLNS.get(0),lnsExperimentFilenames.get(0), "-fm"));
			
		
//				b2 =b2+0.5;
//				}	
//				b1 =b1+0.5;
//			}
//			a2 =a2+0.5;
//		}
//		a1 =a1+0.5;
//    }
//
//	
//	ps.close();	
			
	}

	private static void runProjectOverallAnalisis(Vector<Project> projectsInstances) throws Exception {
		FileOutputStream fos = new FileOutputStream("data\\output" + getStringTime() + ".txt" );
		OutputStreamWriter bw = new OutputStreamWriter(fos);
		
		for (int i = 0; i < projectsInstances.size(); i++)
		{	
			bw.write(System.getProperty("line.separator"));
			bw.write(System.getProperty("line.separator"));
			String s1 = projectsInstances.get(i).getName() + "\t" + "ClassCount: " + "\t" +  projectsInstances.get(i).getClassCount() + "\t" +"MQ:" +"\t" +new MQCalculator(projectsInstances.get(i)).calculateModularizationQuality();
			bw.write("+" + s1 + System.getProperty("line.separator"));
			bw.flush();
			System.out.println(s1);
			for (ProjectPackage projectPackage : projectsInstances.get(i).getPackages()) {
				String s2 = projectPackage.getName();
				bw.write(s2 + System.getProperty("line.separator"));
				bw.flush();
				System.out.println(projectPackage.getName());	
			}
		}
		bw.close();
	}
	
	
	private static void runLNSPExperiment(String objectiveEquation) throws InstanceParseException, IOException {
		LNSParameterTest LNSP = new LNSParameterTest();
		LNSP.runExperiment(objectiveEquation);
	}
	
	
	
	private static Vector<String> runClassDependencyCombinationExport(Vector<Project> projectsInstances) throws InstanceParseException, IOException {
		Vector<String> files = new Vector<String>();
		
		for (int i = 0; i < projectsInstances.size(); i++){
			StringBuilder sb = new StringBuilder();		   
			for (ProjectClass projectClass : projectsInstances.get(i).getClasses()) {
				if (projectClass.getDependencyCount()==0) {
					sb.append( projectClass.getName() + " ");
					sb.append(System.lineSeparator());
				}
				for (Dependency dependencyProjectClass : projectClass.getDependencies()){
					sb.append( projectClass.getName() + "  "  + dependencyProjectClass.getElementName());
					sb.append(System.lineSeparator());			
				}
			}
		   			
		    File file = new File("data\\Experiment\\ClsDepComb\\" + projectsInstances.get(i).getName() + getStringTime()+ ".comb");
		    files.add(file.getName());
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    try {
		        writer.write(sb.toString());	    
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   
			} finally {
				writer.close();
			}
		}	
		
		return files;
	}
	
	private static Vector<String> runPackageClassCombinationExport(Vector<Project> projectsInstances, boolean projectPackageName) throws InstanceParseException, IOException {
		Vector<String> files = new Vector<String>();
		
		for (int i = 0; i < projectsInstances.size(); i++){
			StringBuilder sb = new StringBuilder();
		   int j = 1;
		    for(ProjectPackage projectPackage: projectsInstances.get(i).getPackages()) {
		    	for(ProjectClass projectClass1: projectsInstances.get(i).getClasses(projectPackage)) {
//		    		if (projectClass1.getDependencyCount()>0) {
		    			if (projectPackageName) {
			    			sb.append("contain " + projectPackage.getName() + " " + projectClass1.getName());		
			    		}
			    		else {
			    			sb.append("contain " + "PKG" + j + " " + projectClass1.getName());
			    		}		
						sb.append(System.lineSeparator());
			    		j++;				    			
//		    		}		    							
		    	}
		    }
			
		    File file = new File("data//Experiment//PkgClsComb//"+ projectsInstances.get(i).getName() + getStringTime()+ ".comb");
		    files.add(file.getName());
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    try {
		        writer.write(sb.toString());	    
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   
			} finally {
				writer.close();
			}
		}
		return files;
	}
	
	private static double runMOJOComparison(String file1, String file2, String param) throws InstanceParseException, IOException {
//		System.out.println("runMOJOComparison");
		String[] args1 = new String[3];
		args1[0] = file1;
		args1[1] = file2;
		args1[2] = param; 
		return MoJo.MojoFM(args1);
	}

	private static double runMOJOComparison1(String file1, String file2) throws InstanceParseException, IOException {
//		System.out.println("runMOJOComparison");
		String[] args1 = new String[3];
		args1[0] = file1;
		args1[1] = file2; 
		return MoJo.MojoFM(args1);
	}

	
//	private static void runMOJOComparison(String file1, String file2, String param) throws InstanceParseException, IOException {
//		System.out.println("runMOJOComparison");
//		String[] args1 = new String[3];
//		args1[0] = file1;
//		args1[1] = file2;
//		args1[2] = param; 
//		MoJo.main(args1);
//	}
	
	private static void runMOJOComparison(String file1, String file2) throws InstanceParseException, IOException {
		System.out.println("runMOJOComparison");
		String[] args1 = new String[2];
		args1[0] = file1;
		args1[1] = file2; 
		MoJo.main(args1);
	}
	
	
	private static String getStringTime() {
		Calendar data;
		data = Calendar.getInstance();
		int segundo = data.get(Calendar.SECOND);
        int minuto = data.get(Calendar.MINUTE);
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int dia = data.get(Calendar.DAY_OF_MONTH);	
        int mes = data.get(Calendar.MONTH)+1;	
        int ano = data.get(Calendar.YEAR);		
		return  String.format("%02d", dia) + String.format("%02d", mes) + String.format("%04d", ano) +String.format("%02d", hora) + String.format("%02d", minuto) + String.format("%02d", segundo);
	}
}