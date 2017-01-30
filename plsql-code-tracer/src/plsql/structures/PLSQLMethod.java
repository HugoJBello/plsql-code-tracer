package plsql.structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PLSQLMethod {
	private String methodName = "";
	private ArrayList<String> tablesAccessed= new ArrayList<String>();
	private String packageName = "";
	private String packageSynonym = "";
	private ArrayList<String> methodCodeLines = new ArrayList<String>();
	private ArrayList<SqlQuerry> listOfSqlQuerry= new ArrayList<SqlQuerry>();
	private int lineStart = 0;
	private int lineEnd = 0;
	private String Heading = "";
	private int numberEntries = 0;
	private ArrayList<PLCall> listOfCalls = new ArrayList<PLCall>();
	private ArrayList<Integer> listOfNumberEntriesCalls = new ArrayList<>();

	public void findSql() {
		boolean tracing = false;
		boolean endOfSql = false;
		String sqlCode = "";
		for (String line: methodCodeLines){
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
				sql.setMethodContainer(methodName);
				sql.setPackageContainer(packageName);
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

	public void obtainCalls(){
		String contextCall = "";
		boolean tracingCall = false;

		for (String line: methodCodeLines){
			if (tracingCall == false){
				if (line.contains(":=")){	
					String lin = line.split(":=")[1];
					Pattern p31 = Pattern.compile("\\S+[(]");
					Matcher m1 = p31.matcher(line); 
					if (m1.find()) { 
						String call = m1.group(0);
						if (!( lin.contains("PACKAGE") || lin.contains("FUNCTION") || call.contains(".NEXT") || call.contains("--")|| call.contains(".next") || call.contains(".EXISTS") || call.contains(".exists"))){
							PLCall c = new PLCall();
							c.setMethodName(m1.group(0).replace("(", "").trim());
							listOfCalls.add(c); 
							tracingCall = true;
						}
					}

				} else  {
					Pattern p3 = Pattern.compile("\\S+[(]");
					Matcher m = p3.matcher(line); 
					if (m.find()) { 
						String call = m.group(0);
						if (!( line.contains("PACKAGE") || line.contains("FUNCTION") || call.contains(".NEXT") || call.contains("--")|| call.contains(".next") || call.contains(".EXISTS") || call.contains(".exists"))){
							PLCall c = new PLCall();
							String lineAux = line.split("\\(")[0].trim();
							c.setMethodName(lineAux.split(" ")[lineAux.split(" ").length -1]);
							listOfCalls.add(c); 
							tracingCall = true;
						}
					}
				}
			}

			if (tracingCall){
				contextCall = contextCall  + line;
			}
			if (tracingCall && line.contains(");")){
				tracingCall= false;
				int num = contextCall.split(",").length;
				listOfCalls.get(listOfCalls.size()-1).setNumEntries(num);
				listOfCalls.get(listOfCalls.size()-1).setContextCall(contextCall);
				contextCall="";
			}
			if ((tracingCall && line.contains("IF")&& line.contains(")"))|| (tracingCall && line.contains("if")&& line.contains(")"))){
				tracingCall= false;
				int num = contextCall.split(",").length;
				listOfCalls.get(listOfCalls.size()-1).setNumEntries(num);
				listOfCalls.get(listOfCalls.size()-1).setContextCall(contextCall);
				contextCall="";
			}

		}
	}

//	public void obtainEntries(){
//		boolean tracingHeading = true;
//		int count=0;
//		int i=0;
//
//		for (String line: methodCodeLines){
//
//			if (i>0){ 
//
//				if (tracingHeading) {
//					//#TODO
//					if( line.contains("RETURN")){
//						numberEntries = count;
//						tracingHeading=false;
//						count = 0;
//					}
//					if( line.contains("--")){
//						numberEntries = count;
//						tracingHeading=false;
//						
//					}
//				} else {
//					count++;
//				}
//
//				 
//			}
//			
//		}
//		i++;
//	}
	public ArrayList<String> getTablesAccessed() {
		return tablesAccessed;
	}
	public void setTablesAccessed(ArrayList<String> tablesAccessed) {
		this.tablesAccessed = tablesAccessed;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public ArrayList<String> getMethodCodeLines() {
		return methodCodeLines;
	}
	public void setMethodCodeLines(ArrayList<String> methodCodeLines) {
		this.methodCodeLines = methodCodeLines;
	}
	public ArrayList<SqlQuerry> getListOfSqlQuerry() {
		return listOfSqlQuerry;
	}
	public void setListOfSqlQuerry(ArrayList<SqlQuerry> listOfSqlQuerry) {
		this.listOfSqlQuerry = listOfSqlQuerry;
	}


	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getLineStart() {
		return lineStart;
	}
	public void setLineStart(int lineStart) {
		this.lineStart = lineStart;
	}
	public int getLineEnd() {
		return lineEnd;
	}
	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public String getPackageSynonym() {
		return packageSynonym;
	}

	public void setPackageSynonym(String packageSynonym) {
		this.packageSynonym = packageSynonym;
	}

	public int getNumberEntries() {
		return numberEntries;
	}

	public void setNumberEntries(int numberEntries) {
		this.numberEntries = numberEntries;
	}

	public ArrayList<Integer> getListOfNumberEntriesCalls() {
		return listOfNumberEntriesCalls;
	}

	public void setListOfNumberEntriesCalls(
			ArrayList<Integer> listOfNumberEntriesCalls) {
		this.listOfNumberEntriesCalls = listOfNumberEntriesCalls;
	}

	public ArrayList<PLCall> getListOfCalls() {
		return listOfCalls;
	}

	public void setListOfCalls(ArrayList<PLCall> listOfCalls) {
		this.listOfCalls = listOfCalls;
	}

	public String getHeading() {
		return Heading;
	}

	public void setHeading(String heading) {
		Heading = heading;
	}

}
