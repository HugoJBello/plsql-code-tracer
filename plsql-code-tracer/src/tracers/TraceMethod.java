package tracers;

import java.util.ArrayList;
import java.util.regex.Pattern;

import plsql.structures.PLCall;
import plsql.structures.PLSQLMethod;
import plsql.structures.PackageData;

public class TraceMethod {
	private ArrayList<PLSQLMethod> listOfMethods = new ArrayList<PLSQLMethod>();
	private ArrayList<PLSQLMethod> listOfTracedMethods = new ArrayList<PLSQLMethod>();

	private ArrayList<PackageData> listOfPackages = new ArrayList<PackageData>();

	public void trace(String method, String pk, int numEntries){
		PLSQLMethod ourMethod = new PLSQLMethod();
		System.out.println("...........finding " +pk + " ----- " + method + " ---- " + numEntries);
		for (PLSQLMethod m: listOfMethods){
			if (m.getMethodName().contains(method)){
				if (m.getPackageName().contains(pk) || m.getPackageSynonym().contains(pk)){
					if (m.getNumberEntries() == numEntries){
						ourMethod = m;
						System.out.println("............found " + m.getPackageName() + "." + m.getPackageSynonym()+ "." +m.getMethodName());
						break;	
					}
				}
			}
		}
		trace(ourMethod);

	}

	public void trace(PLSQLMethod method){
		boolean foundMethod = false;
		if (!method.getMethodName().equals("")){
			listOfTracedMethods.add(method);
			method.obtainCalls();
			System.out.println("---");
			System.out.println("tracing: ");
			System.out.println(method.getPackageName() + "." + method.getMethodName() + " " + method.getNumberEntries()+ "------>");

			if (method.getListOfCalls().size()==0){
				System.out.println(method.getPackageName() + "." + method.getMethodName()+ " " + method.getNumberEntries()+ "||");
			} else {
				for (PLCall c : method.getListOfCalls()){ 
					System.out.println("* " + c.getMethodName() + " " + c.getNumEntries());
				}
				for (PLCall call: method.getListOfCalls()){
					if (call.getMethodName().contains(".")){
						int numEntries = call.getNumEntries();
						String pkName = call.getMethodName().split(Pattern.quote("."))[0]; 
						String mtName = call.getMethodName().split(Pattern.quote("."))[1]; 
						boolean alreadyTraced = false;
						for (PLSQLMethod mt : listOfTracedMethods){
							if ((pkName.equals(mt.getPackageName()) && mtName.equals(mt.getMethodName()) && numEntries == mt.getNumberEntries())
									|| (pkName.equals(mt.getPackageSynonym()) && mtName.equals(mt.getMethodName()) && numEntries ==mt.getNumberEntries())){
								alreadyTraced=true;
								System.out.println("Already traced ||");
								foundMethod = true;
								break;	
							}
						}
						if (!(pkName.equals(method.getPackageName()) && !alreadyTraced && mtName.equals(method.getMethodName())&& numEntries == method.getNumberEntries())
								|| !(pkName.equals(method.getPackageSynonym()) && !alreadyTraced && mtName.equals(method.getMethodName()) && numEntries == method.getNumberEntries())){
							foundMethod = true;
 								trace(mtName,pkName,numEntries);							 
						}
					} else {
						int numEntries = call.getNumEntries();
						String  mtName= call.getMethodName().split(Pattern.quote("."))[0]; 
						String pkName = method.getPackageName();
						boolean alreadyTraced = false;
						for (PLSQLMethod mt : listOfTracedMethods){
							if ((pkName.equals(mt.getPackageName()) && mtName.equals(mt.getMethodName()) && numEntries ==method.getNumberEntries())
									|| (pkName.equals(mt.getPackageSynonym()) && mtName.equals(mt.getMethodName()) && numEntries ==method.getNumberEntries())){
								alreadyTraced=true;
								foundMethod = true;
								break;	
							}
						}
						if (!(pkName.equals(method.getPackageName()) && !alreadyTraced && mtName.equals(method.getMethodName()) && numEntries ==method.getNumberEntries())
								|| !(pkName.equals(method.getPackageSynonym()) && !alreadyTraced && mtName.equals(method.getMethodName()) && numEntries ==method.getNumberEntries())){
							foundMethod = true;
							 
								trace(mtName,pkName,numEntries);	
							 
						}
					}
				}
			}
		}
		System.out.println(foundMethod);
	}

	public ArrayList<PLSQLMethod> getListOfMethods() {
		return listOfMethods;
	}

	public void setListOfMethods(ArrayList<PLSQLMethod> listOfMethods) {
		this.listOfMethods = listOfMethods;
	}

	public ArrayList<PackageData> getListOfPackages() {
		return listOfPackages;
	}

	public void setListOfPackages(ArrayList<PackageData> listOfPackages) {
		this.listOfPackages = listOfPackages;
	}

}
