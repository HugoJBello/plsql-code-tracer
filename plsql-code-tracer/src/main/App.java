package main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import plsql.structures.*;
import tracers.*;
public class App {

	public static void main(String[] args) throws IOException {
		Path pathInput = Paths.get("package.spb");

		File file = new File("D:/hugo_documentos/otros_java/pl_code_para_rastreo_de_tablas/sample");
		MethodListConstructor constr = new MethodListConstructor();
		constr.addPackagesToList(file);
		//constr.addSynonymsToPackages(file);
		constr.addMethodsToList(file);
		TraceMethod tr = new TraceMethod();
		tr.setListOfMethods(constr.getListOfMethods());
		tr.setListOfPackages(constr.getListOfPackages());


		tr.trace("fp_tab_real", "em_k_ld_agravante_trn", 14);
		
		
	}

}
