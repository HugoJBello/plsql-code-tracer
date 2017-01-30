package plsql.structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodListConstructor {
	private ArrayList<PLSQLMethod> listOfMethods = new ArrayList<PLSQLMethod>();
	private ArrayList<PackageData> listOfPackages = new ArrayList<PackageData>();
	private String encoding = "windows-1252";

	public String[] obtainSynonym (Path pathInput) throws IOException{
		String[] sy = new String[2];
		int i=0;
		String synonym = "";
		String pkName = "";
		for (String line : Files.readAllLines(pathInput,Charset.forName(encoding))) {
			if (line.contains("SYNONYM")){
				//System.out.println(line);
				Pattern p3 = Pattern.compile("SYNONYM\\s+\\S+");
				Matcher m = p3.matcher(line); 
				if (m.find()) {  
					synonym = m.group(0).replace("SYNONYM", "").trim().replaceAll("\\S+\\.", ""); 

					//System.out.println("synony---" + synonym);
				}
			}

			if (line.contains("FOR")){
				//System.out.println(line);
				Pattern p3 = Pattern.compile("FOR\\s+\\S+");
				Matcher m = p3.matcher(line); 
				if (m.find()) {  

					String aux = m.group(0).replace("FOR", "").trim().replace(";", "").replaceAll("\\S+\\.", "").replaceAll("\\@\\S+", ""); 

					pkName = aux;
					//System.out.println("pkname---" + pkName);
				}
			}

			i++;
		}
		if (synonym.equals("")){
			int i1=0;
			for (String line : Files.readAllLines(pathInput,Charset.forName(encoding))) {
				if (line.contains("SYNONYM")){
					//System.out.println(line);
					Pattern p3 = Pattern.compile("CREATE SYNONYM\\s+\\S+\\s+ FOR \\s+\\S+");
					Matcher m = p3.matcher(line); 
					if (m.find()) {  
						synonym = m.group(0).replace("CREATE SYNONYM", "").replace("FOR", "").trim().replace("\\s+", " ").split(" ")[0]; 
						synonym = obtainExtension(synonym);
						String[] aux = m.group(0).replace("CREATE SYNONYM", "").replace("FOR", "").trim().replace("\\s+", " ").split(" "); 
						pkName = aux[aux.length-1];
						pkName = pkName.replaceAll("\\S+\\.","").replaceAll(";","");
						System.out.println("---" + pkName);

						//synonym = synonym.split(".")[synonym.split(".").length];
					}
				}

				i1++;
			}

		}
		sy[0]= synonym;
		sy[1]= pkName;
		return sy;
	}



	public String obtainExtension (File file){
		//here we find out the extension of the file
		String extension = obtainExtension(file.getName());
		return extension;
	}

	public String obtainExtension (String str){
		//here we find out the extension of the file
		String extension = "";

		int i = str.lastIndexOf('.');
		if (i > 0) {
			extension = str.substring(i+1);
		}
		return extension;
	}

	public void addPackagesToList(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			// we make sure that we are considering accessible folders only. We are using recursion here
			if (fileEntry.isDirectory() && fileEntry.canRead() ) {

				addPackagesToList(fileEntry);
			} else if (obtainExtension(fileEntry).contains("spb")){
				PackageData pk = new PackageData(fileEntry.toPath());
				//				pk.findMethods();
				//				for (PLSQLMethod method : pk.getListMethods()){
				//					method.findSql();
				//					this.listOfMethods.add(method);					
				//				}
				this.listOfPackages.add(pk);
			}
		}

	}

	public void addSynonymsToPackages(File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			// we make sure that we are considering accessible folders only. We are using recursion here
			if (fileEntry.isDirectory() && fileEntry.canRead() ) {
				addSynonymsToPackages(fileEntry);
			} else if (obtainExtension(fileEntry).contains("pdc")){
				String fileNameWithoutExt = fileEntry.getName().replaceAll(".pdc","");
				int i = 0;
				String[] sy = obtainSynonym(fileEntry.toPath());
				String synonym = sy[0].toLowerCase();
				String pkNameFromPdcFile = sy[1].toLowerCase();
				for (PackageData pk: listOfPackages){

					if (pkNameFromPdcFile.toLowerCase().contains(pk.getPackageName().toLowerCase())){
						//						System.out.println(pkNameFromPdcFile);
						//						System.out.println(pk.getPackageName());
						//						
						listOfPackages.get(i).setPackageSynonym(synonym);
						//listOfPackages.set(i, pk);

					}
					i++;
				}
				//System.out.println(obtainSynonym(fileEntry.toPath()));



			}
		}

	}

	public void addMethodsToList(File folder) throws IOException {
		addSynonymsToPackages(folder);
		for (PackageData pk : listOfPackages){
			pk.findMethods();
			for (PLSQLMethod m: pk.getListMethods()){
				m.setPackageSynonym(pk.getPackageSynonym());
				listOfMethods.add(m);
			}
		}

	}
	public void addSynonymsToMethods (File folder) throws IOException {
		addSynonymsToPackages(folder);

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



	public String getEncoding() {
		return encoding;
	}



	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
