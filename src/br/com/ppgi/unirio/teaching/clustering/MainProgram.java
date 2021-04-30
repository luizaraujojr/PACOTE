package br.com.ppgi.unirio.teaching.clustering;

import java.util.ArrayList;
import java.util.List;

import br.com.ppgi.unirio.teaching.clustering.model.Project;
import br.com.ppgi.unirio.teaching.clustering.reader.CDAReader;
import br.com.ppgi.unirio.teaching.clustering.search.IteratedLocalSearch;

public class MainProgram
{
//	private static String BASE_DIRECTORY = "C:\\Users\\User\\Desktop\\Codigos\\HillClimbing\\data\\clustering\\";
	private static String BASE_DIRECTORY = "data//ODEMFile//";
	
	
	private static String[] instanceFilenamesReals =
	{
		"joda-money 26C.odem"/*,
		"javacc.odem"/*,
		"junit-3.8.1 100C.odem",
		"servletapi-2.3 74C.odem",
		"xml-apis-1.0.b2 W3C-DOM.odem",
		"jmetal.odem",
		"xml-apis-1.0.b2.odem",
		"dom4j-1.5.2 195C.odem",
		"joda-money 26C.odem",
		"jxls-reader 27C.odem",
		"javaocr 59C.odem",
		"jpassword 96C.odem",
		"jxls-core 83C.odem",
		"tinytim 134C.odem"*/
	};
	
	public static final void main(String[] args) throws Exception
	{
		List<Project> instances = new ArrayList<Project>();
		CDAReader reader = new CDAReader();

		for (String filename : instanceFilenamesReals)
			instances.add(reader.execute(BASE_DIRECTORY + filename));

		

		String equation = "";
		
		double a1 = -5;
		double a2 = -5;
		double b1 = -5;
		double b2 = -5;
		
		while (a1<=5) {
			a2 = -5;
			while (a2<=5) {
				b1 = -5;
				while (b1<=5) {
					b2 = -5;
					while (b2<=5) {			
//						equation = "(" + a1 + "*x + " + a2 + "* y) / ( " + b1 + "*x + " + b2 + "* y)";
						
//						equation = "(1 * x + 0 * y) / (1 * x + 0.5 * y)";
//						a1=1;
//						a2=0;
//						b1=1;
//						b2=0.5;

						for (Project instance : instances)
							new IteratedLocalSearch(instance, 1000, a1, a2, b1, b2).execute();
		

			b2 =b2+0.5;
			}	
			b1 =b1+0.5;
		}
		a2 =a2+0.5;
	}
	a1 =a1+0.5;
	}
	}
	
}