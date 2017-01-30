package main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import plsql.structures.*;
import tracers.*;
public class App {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Path pathInput = Paths.get("em_k_ld_agravante_trn.spb");
		Path pathInput = Paths.get("package.spb");

//		PackageData data = new PackageData(pathInput);
//		data.findMethods();
//		for (PLSQLMethod mt : data.getListMethods()){
//			System.out.println("+ " +mt.getPackageName() + "." + mt.getMethodName() + " --num_entries-->  " + mt.getNumberEntries());
//		}
//		data.findMethods();

		//data.findSql();
		File file = new File("D:/hugo_documentos/otros_java/pl_code_para_rastreo_de_tablas/TP-2016-000051_copia");
		//File file = new File("D:/hugo_documentos/otros_java/pl_code_para_rastreo_de_tablas");

		MethodListConstructor constr = new MethodListConstructor();
		constr.addPackagesToList(file);
		//constr.addSynonymsToPackages(file);
		constr.addMethodsToList(file);
		TraceMethod tr = new TraceMethod();
		tr.setListOfMethods(constr.getListOfMethods());
		tr.setListOfPackages(constr.getListOfPackages());

		// fp_tab_real, 707, em_k_ld_agravante_trn


		tr.trace("fp_tab_real", "em_k_ld_agravante_trn", 14);
		// 		System.out.println(tr.getListOfPackages().get(55).getPackageName());
		// 		System.out.println(tr.getListOfPackages().get(55).getPackageSynonym());
		// 		
		// 		int n = 377;
		// 		System.out.println(tr.getListOfMethods().get(n).getPackageName());
		// 		System.out.println(tr.getListOfMethods().get(n).getPackageSynonym());

		System.out.println("/////////////////////////////---------------------/////////////////////////////--------------------------------------------");
		tr = new TraceMethod();
		tr.setListOfMethods(constr.getListOfMethods());
		tr.setListOfPackages(constr.getListOfPackages());
		tr.trace("fp_t_registro", "em_k_ld_agravante_trn", 1);

		System.out.println("/////////////////////////////---------------------/////////////////////////////--------------------------------------------");
		tr = new TraceMethod();
		tr.setListOfMethods(constr.getListOfMethods());
		tr.setListOfPackages(constr.getListOfPackages());
		tr.trace("finding fp_tab_max_spto_a2001232", "em_k_ld_inf_adicional_spto_trn", 12);

		System.out.println("/////////////////////////////---------------------/////////////////////////////--------------------------------------------");
		tr = new TraceMethod();
		tr.setListOfMethods(constr.getListOfMethods());
		tr.setListOfPackages(constr.getListOfPackages());
		tr.trace("fp_inf_adicional_ocu", "em_k_ld_inf_adicional_spto_trn", 6);

		System.out.println("/////////////////////////////---------------------/////////////////////////////--------------------------------------------");
		tr = new TraceMethod();
		tr.setListOfMethods(constr.getListOfMethods());
		tr.setListOfPackages(constr.getListOfPackages());
		tr.trace("p_v_fec_validez", "em_k_ln_ramo_trn", 3);
	}

}
