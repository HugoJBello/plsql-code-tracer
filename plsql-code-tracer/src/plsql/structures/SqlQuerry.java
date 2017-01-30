package plsql.structures;

import java.util.ArrayList;

public class SqlQuerry {
	private String Code="";
	private ArrayList<String> tablesAccessed= new ArrayList<String>();
	private Integer[] lines = new Integer[2];
	private String methodContainer = "";
	public String getMethodContainer() {
		return methodContainer;
	}
	public void setMethodContainer(String methodContainer) {
		this.methodContainer = methodContainer;
	}
	public String getPackageContainer() {
		return packageContainer;
	}
	public void setPackageContainer(String packageContainer) {
		this.packageContainer = packageContainer;
	}
	private String packageContainer = "";
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public ArrayList<String> getTablesAccessed() {
		return tablesAccessed;
	}
	public void setTablesAccessed(ArrayList<String> tablesAccessed) {
		this.tablesAccessed = tablesAccessed;
	}
	public Integer[] getLines() {
		return lines;
	}
	public void setLines(Integer[] lines) {
		this.lines = lines;
	}

}
