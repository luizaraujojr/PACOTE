package br.unirio.odem.calc.revision;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.management.modelmbean.XMLParseException;

import br.unirio.odem.calc.loader.ProjectLoader;

public class MainVersionLog
{
	private String findVersion(String sdata, String project) throws XMLParseException, ParseException
	{
		ProjectLoader pl = new ProjectLoader();
		String version="";		
		Date date1 = null;		
		Date date2 = null;
		Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("9999-12-31");
		for (int i = 0; i < pl.PROJECT_INFO.length-1; i++)
		{
			if  (pl.PROJECT_INFO[i][0].equals(project)) {
//				if (sdata.compareToIgnoreCase(pl.PROJECT_INFO[i][2]) < 0) {
				date1=new SimpleDateFormat("yyyy-MM-dd").parse(sdata);
				date2=new SimpleDateFormat("ddMMyyyy").parse(pl.PROJECT_INFO[i][2]);
				 
				if(date1.compareTo(date2)<0) {
					if (date2.compareTo(date3)<0) {
						date3=date2;
						version=pl.PROJECT_INFO[i][1];	
					}
					
				}	
			}
		}
		return version;
	}
	
	private boolean hasPackage(List<String> packages, String packageName)
	{
		for (String name : packages)
			if (name.compareToIgnoreCase(packageName) == 0)
				return true;
		
		return false;
	}
	
	private void saveRevisionsByVersion(String filename, PrintStream ps, String project) throws IOException, XMLParseException, ParseException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		
		String lastVersion = "";
		String lastRevision = "";
		String lastAuthor = "";
		int lastClasses = 0;
		List<String> lastPackages = new ArrayList<String>();
		ps.println("version\tauthor\tclasses\tpackages");
		
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
					if (lastVersion.length() > 0)
						ps.println(lastVersion + "\t" + lastAuthor + "\t" + lastClasses + "\t" + lastPackages.size());
					
					lastVersion = findVersion(sdata,project);
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

		if (lastVersion.length() > 0)
			ps.println(lastVersion + "\t" + lastAuthor + "\t" + lastClasses + "\t" + lastPackages.size());

		br.close();		
	}
	
	public static void main(String[] args) throws IOException, ParseException, XMLParseException
	{		
//		FileOutputStream out = new FileOutputStream("results\\jeditlog_list.data");
////		FileOutputStream out = new FileOutputStream("results\\jhotdrawlog_list.data");
//		PrintStream ps = new PrintStream(out);
//		
////		loadLogFile("data\\log\\jhotdrawlog_raw.data", ps);
//		loadLogFile("data\\log\\jeditlog_raw.data", ps);
		
		//-----------------------------------------------------------
		
//		FileOutputStream out = new FileOutputStream("results\\jhotdraw_versions.data");
		FileOutputStream out = new FileOutputStream("results\\jedit_versions.data");

		PrintStream ps = new PrintStream(out);
		
		MainVersionLog me = new MainVersionLog();
//		me.saveRevisionsByVersion("data\\log\\jhotdrawlog_versions.data", ps, "jhotdraw");
		me.saveRevisionsByVersion("data\\log\\jeditlog_list.data", ps, "jedit");

		ps.close();
		System.out.println("Finished!");
	}
	
	private static void loadLogFile(String filename, PrintStream ps) throws IOException, ParseException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		
		String revision = "";
		String author = "";
		String date = "";
		String type = "";
		
		
		new ArrayList<String>();
		ps.println("version\tauthor\tclasses\tpackages");
		
		while ((line = br.readLine()) != null) 
		{
			if (line.length() > 0)
			{
				String tokens[] = line.split(":");
				if(tokens[0].equals("Revision")) {
					if (tokens.length>1) {
						revision = tokens[1];	
					}
				
				}
				
				if(tokens[0].equals("Author")) {
					if (tokens.length>1) {
						author = tokens[1].trim().length()==0 ? "N/A" : tokens[1];	
					}
					
				}
				
				if(tokens[0].equals("Date")) {
					if (tokens.length>1) {
						Date date1=new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH).parse(tokens[1].substring(1, tokens[1].length() -2));
//						Date date1=new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm:ss aaa", Locale.ENGLISH).parse(tokens[1]+":"+tokens[2]+":"+tokens[3]);
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
						date = dateFormat.format(date1);	
					}
				}
				
				if(tokens[0].equals("Added ")  || tokens[0].equals("Modified ") || tokens[0].equals("Deleted ") || tokens[0].equals("Replacing ")) {
					if (tokens.length>1) {
						if (tokens[1].endsWith(".java")) {
							type = tokens[1];
							ps.println(date + " R" + revision.trim() + " " + author.trim() + " " + type.trim());
						}	
					}
				}

			}
		}
			br.close();	
	}
}