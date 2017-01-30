package plsql.structures;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageData {
	private String packageName = "";
	private String packageSynonym = "";
	private ArrayList<PLSQLMethod> listMethods = new ArrayList<PLSQLMethod>();

	private ArrayList<Integer> lineNumbersMethods = new ArrayList<Integer>();
	private ArrayList<String> packageCodeLines = new ArrayList<String>();
	private String encoding = "windows-1252";
	public String getPackageSynonym() {
		return packageSynonym;
	}



	private ArrayList<SqlQuerry> listOfSqlQuerry= new ArrayList<SqlQuerry>();

	public PackageData (Path pathInput) throws IOException{
		int i=0;
		boolean extrangeFirstLine = false;
		for (String line : Files.readAllLines(pathInput,Charset.forName(encoding))) {
			if (i==0){
				//if (line.trim().equals("CREATE OR REPLACE")){ extrangeFirstLine=true;}
				packageName = line.replace("create or replace package", "").replace("CREATE OR REPLACE PACKAGE", "").replace("create or replace", "").replace("CREATE OR REPLACE", "").replaceAll("body", "").replaceAll("BODY", "").replaceAll("as", "").replaceAll("AS", "").replaceAll("is", "").replaceAll("IS", "").trim(); 
				if (packageName.trim().equals(""))
					if (packageName.trim().equals("")){
						extrangeFirstLine=true;}	
			}
			if (i==1 && extrangeFirstLine) {
				packageName = line.replace("package body", "").replace("PACKAGE BODY", "").replaceAll("as", "").replaceAll("AS", "").replaceAll("is", "").replaceAll("IS", "").trim();    
			}

			packageCodeLines.add(line);

			i++;
		}
	}


	public void findSql() {
		//#TODO
		boolean tracing = false;
		boolean endOfSql = false;
		String sqlCode = "";
		for (String line: packageCodeLines){
			if (line.contains("SELECT")|| line.contains("select")
					|| line.contains("INSERT")||   line.contains("insert") 
					|| line.contains("DELETE") || line.contains("delete")){
				tracing = true;				
			} else if  (line.contains(";") && tracing){
				sqlCode = sqlCode + line + "\n";
				tracing=false;
				endOfSql=true;
				SqlQuerry sql = new SqlQuerry();
				sql.setCode(sqlCode);
				listOfSqlQuerry.add(sql);
			}
			if (tracing){
				sqlCode = sqlCode + line + "\n";
			}
			if (!tracing){
				sqlCode = "";
			}
		}
	}

	public void findMethods() {
		//#TODO
		boolean tracing = false;
		boolean tracingHeading = false;
		String methodName = "";
		ArrayList<String> listOfLines = new ArrayList<String>();
		int i=1;
		int count=0;
		int numEnt = 0;
		int[] lines = new int[2];
		String headAux = "";
		String heading = "";
		for (String line: packageCodeLines){
			if (line.contains("PROCEDURE")|| line.contains("procedure")
					|| line.contains("FUNCTION")||   line.contains("function")){
				tracing = true;	
				tracingHeading= true;
				count = 0;
				String aux = line.replace("PROCEDURE", "").replace("FUNCTION", "").replace("IS","").replace("AS","").replace("\\s+","");
				methodName = aux.split("\\(")[0];
				lines[0]=i;				

			} 
			if (tracingHeading){
				headAux = headAux + line;
			}

			if (tracingHeading && line.contains("RETURN")) {
				//#TODO
				numEnt =headAux.split(",").length;

				count=0;
				tracingHeading=false;
				heading = headAux.replaceAll("\\s+"," ");
				headAux= "";
			} else  {
				count++;
				if (tracingHeading && line.contains("IS")){
					numEnt =headAux.split(",").length;

				}
			}
			if (tracingHeading && line.contains("IS")) {
				//#TODO
				numEnt =headAux.split(",").length;
				count=0;
				tracingHeading=false;
				heading = headAux.replaceAll("\\s+"," ");
				headAux= "";
			} else  {
				count++;

			}

			if  (line.contains("END")&& line.contains(methodName.trim())){
				listOfLines.add(line);
				tracing=false;
				lines[1]=i;
				PLSQLMethod method = new PLSQLMethod();
				method.setMethodName(methodName.trim());
				method.setLineStart(lines[0]);
				method.setLineEnd(lines[1]);
				method.setMethodCodeLines(listOfLines);
				method.setPackageName(packageName);
				method.setPackageSynonym(packageSynonym);
				method.setHeading(heading);
				method.setNumberEntries(numEnt);
				//method.obtainEntries();
				listMethods.add(method);
				//System.out.println(method.getPackageName() +"."+ method.getMethodName() + " " + method.getLineStart()+ " " + method.getLineEnd());
			}
			if (tracing){
				listOfLines.add(line);
			}
			if (!tracing){
				listOfLines = new ArrayList<String>();
			}

			i++;
		}
	}


	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public ArrayList<String> getPackageCodeLines() {
		return packageCodeLines;
	}

	public void setPackageCodeLines(ArrayList<String> packageCodeLines) {
		this.packageCodeLines = packageCodeLines;
	}

	public ArrayList<PLSQLMethod> getListMethods() {
		return listMethods;
	}

	public void setListMethods(ArrayList<PLSQLMethod> listMethods) {
		this.listMethods = listMethods;
	}

	public ArrayList<Integer> getLineNumbersMethods() {
		return lineNumbersMethods;
	}

	public void setLineNumbersMethods(ArrayList<Integer> lineNumbersMethods) {
		this.lineNumbersMethods = lineNumbersMethods;
	}

	public ArrayList<SqlQuerry> getListOfSqlQuerry() {
		return listOfSqlQuerry;
	}

	public void setListOfSqlQuerry(ArrayList<SqlQuerry> listOfSqlQuerry) {
		this.listOfSqlQuerry = listOfSqlQuerry;
	}

	public void setPackageSynonym(String packageSynonym) {
		this.packageSynonym = packageSynonym;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}


