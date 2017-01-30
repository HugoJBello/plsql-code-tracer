package plsql.structures;

public class PLCall {
	private String methodName = "";
	private int numEntries = 1;
	private String contextCall = "";
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getNumEntries() {
		return numEntries;
	}
	public void setNumEntries(int numEntries) {
		this.numEntries = numEntries;
	}
	public String getContextCall() {
		return contextCall;
	}
	public void setContextCall(String contextCall) {
		this.contextCall = contextCall;
	}

}
