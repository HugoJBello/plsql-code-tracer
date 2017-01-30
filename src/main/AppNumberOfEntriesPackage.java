package main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import plsql.structures.MethodListConstructor;
import plsql.structures.PLSQLMethod;
import plsql.structures.PackageData;
import tracers.TraceMethod;

public class AppNumberOfEntriesPackage {

	public static void main(String[] args) throws IOException {
		Path pathInput = Paths.get("package.spb");
		PackageData data = new PackageData(pathInput);
		data.findMethods();
		for (PLSQLMethod mt : data.getListMethods()){
			//mt.obtainEntries();
			System.out.println("+ " +mt.getPackageName() + "." + mt.getMethodName() + " " + mt.getNumberEntries()+ ", "   + mt.getLineStart()+ " " +mt.getLineEnd());
		}			
		//data.findSql();
		data.findMethods();



	}

}
