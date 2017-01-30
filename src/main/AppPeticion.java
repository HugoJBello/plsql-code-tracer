package main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import plsql.structures.MethodListConstructor;
import plsql.structures.PLSQLMethod;
import plsql.structures.PackageData;
import tracers.TraceMethod;

public class AppPeticion {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Path pathInput = Paths.get("em_k_ld_agravante_trn.spb");
		//Path pathInput = Paths.get("package.spb");

//		PackageData data = new PackageData(pathInput);
//		data.findMethods();
//		for (PLSQLMethod mt : data.getListMethods()){
//			System.out.println("+ " +mt.getPackageName() + "." + mt.getMethodName() + " --num_entries-->  " + mt.getNumberEntries());
//		}
//		data.findMethods();

		//data.findSql();
		File file = new File("C:/Users/hjbello/wkspaces/ES_SSCC_TRON21_EMLN_4");
		//File file = new File("D:/hugo_documentos/otros_java/pl_code_para_rastreo_de_tablas");

		MethodListConstructor constr = new MethodListConstructor();
		constr.addPackagesToList(file);
		//constr.addSynonymsToPackages(file);
		constr.addMethodsToList(file);
		TraceMethod tr = new TraceMethod();
		tr.setListOfMethods(constr.getListOfMethods());
		tr.setListOfPackages(constr.getListOfPackages());

		// fp_tab_real, 707, em_k_ld_agravante_trn


		tr.trace("pp_tab_pol_gescom", "ld_em_esp_trn", 21);
		// 		System.out.println(tr.getListOfPackages().get(55).getPackageName());
		// 		System.out.println(tr.getListOfPackages().get(55).getPackageSynonym());
		// 		
		// 		int n = 377;
		// 		System.out.println(tr.getListOfMethods().get(n).getPackageName());
		// 		System.out.println(tr.getListOfMethods().get(n).getPackageSynonym());

		
	}

}
