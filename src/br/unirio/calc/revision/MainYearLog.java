package br.unirio.calc.revision;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class MainYearLog
{
	private static boolean hasPackage(List<String> packages, String packageName)
	{
		for (String name : packages)
			if (name.compareToIgnoreCase(packageName) == 0)
				return true;
		
		return false;
	}
	
	public void saveRevisionsByYear(String filename, PrintStream ps) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		
		int lastYear = 0;
		String lastRevision = "";
		String lastAuthor = "";
		int lastClasses = 0;
		List<String> lastPackages = new ArrayList<String>();
		ps.println("year\tauthor\tclasses\tpackages");
		br.readLine(); // jumping the title of the file. 
		while ((line = br.readLine()) != null) 
		{
			if (line.length() > 0)
			{
				String tokens[] = line.split(" ");
				
				String sdata = tokens[0];
				String revision = tokens[1];
				String author = tokens[2];
				String clazz = tokens[3];
				
				clazz = clazz.substring(0, clazz.lastIndexOf('.'));
				//String className = clazz.substring(clazz.lastIndexOf('.') + 1);
				String packageName = clazz.substring(0, clazz.lastIndexOf('/'));

				if (revision.compareToIgnoreCase(lastRevision) != 0)
				{
					if (lastYear > 0)
						ps.println(lastYear + "\t" + lastAuthor + "\t" + lastClasses + "\t" + lastPackages.size());
					
					lastYear = Integer.parseInt(sdata.substring(0, 4));
					lastRevision = revision;
					lastAuthor = author;
					lastClasses = 0;
					lastPackages.clear();
				}
				
				if (!hasPackage(lastPackages, packageName))
					lastPackages.add(packageName);
				
				lastClasses++;
			}
		}

		if (lastYear > 0)
			ps.println(lastYear + "\t" + lastAuthor + "\t" + lastClasses + "\t" + lastPackages.size());

		br.close();		
	}

	public static void main(String[] args) throws IOException
	{
		String projectName ="jedit";
		
//		FileOutputStream out = new FileOutputStream("results\\versioncontrol\\log_years.data");
//		FileOutputStream out = new FileOutputStream("results\\jhotdraw_years.data");
		FileOutputStream out = new FileOutputStream("results\\jedit_years.data");
		PrintStream ps = new PrintStream(out);

		MainYearLog me = new MainYearLog();
//		me.saveRevisionsByYear("data\\log\\jhotdrawlog_list.data", ps);
		me.saveRevisionsByYear("data\\log\\jeditlog_list.data", ps);
		me.saveRevisionsByYear("data\\log\\" + projectName + "log_list.data", ps);

		
		ps.close();
		System.out.println("Finished!");
	}
}