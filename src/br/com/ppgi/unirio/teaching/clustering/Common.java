package br.com.ppgi.unirio.teaching.clustering;

import java.io.IOException;
import java.util.Calendar;

import br.com.ppgi.unirio.luiz.clustering.mojo.MoJo;

public final class Common {

	public static final String ILS_INTERPRETATION_DIRECTORY = "data//Experiment//ILSInterpretation//";
	public static final String EXPERIMENT_DIRECTORY = "data//Experiment//";
	public static final String VALIDATION_DIRECTORY = "data//Experiment//validation//";
	public static final String DEP_BASE_DIRECTORY = "data//Experiment//ClsDepComb//";
	public static final String PKG_BASE_DIRECTORY = "data//Experiment//PkgClsComb//";
	
	
	public static String getStringTime() {
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

	public static double runMOJOComparison(String file1, String file2, String param) throws IOException {
		String[] args1 = new String[3];
		args1[0] = file1;
		args1[1] = file2;
		args1[2] = param; 
		return MoJo.MojoFM(args1);
	}

	
}
