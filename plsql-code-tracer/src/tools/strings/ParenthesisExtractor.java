package tools.strings;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParenthesisExtractor {
	private ArrayList<String> substitutedLayer = new ArrayList<String>();
	private ArrayList<ArrayList<String>> layer = new ArrayList<ArrayList<String>>();
	private String SUBSTITUTIONSTRING = "**";
	private boolean withSubstitutionString = true;
	private String text = "";

	public  void extractLayers (String input){
		List<String> matchList = new ArrayList<String>();
		layer.add((ArrayList<String>) matchList);
		Pattern regex = Pattern.compile("\\((.+?)\\)");
		Matcher regexMatcher = regex.matcher(input);
		String output = input;
		Integer index = layer.size();
		while (regexMatcher.find()) {//Finds Matching Pattern in String
			String str = regexMatcher.group(1);//Fetching Group from String
			if (!str.contains("(")){
				matchList.add(str);
				if (withSubstitutionString) {
					output = output.replace("("+str+")" , SUBSTITUTIONSTRING + index);
				} else {
					output = output.replace("("+str+")" , "");
				}
			}else {
				String Aux = str.split("\\(")[str.split("\\(").length-1];
				matchList.add(Aux);
				if (withSubstitutionString) {
					output = output.replace("("+ Aux+")" , SUBSTITUTIONSTRING + index);
				} else {
					output = output.replace("("+Aux+")" , "");
				}
			}
		}
		substitutedLayer.add(output);
		if (output.contains("\\(")){ 
			extractLayers(output);
		} else {
			List<String> lastLayer = new ArrayList<String>();
			lastLayer.add(output);
			layer.add((ArrayList<String>) lastLayer);
		}
	}
	public ParenthesisExtractor (String input){
		this.setText(input);
	}
	public ArrayList<String> getSubstitutedLayer() {
		return substitutedLayer;
	}
	public void setSubstitutedLayer(ArrayList<String> substitutedLayer) {
		this.substitutedLayer = substitutedLayer;
	}
	public ArrayList<ArrayList<String>> getLayer() {
		return layer;
	}
	public void setLayer(ArrayList<ArrayList<String>> layer) {
		this.layer = layer;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSUBSTITUTIONSTRING() {
		return SUBSTITUTIONSTRING;
	}
	public void setSUBSTITUTIONSTRING(String sUBSTITUTIONSTRING) {
		SUBSTITUTIONSTRING = sUBSTITUTIONSTRING;
	}
	public boolean isWithSubstitutionString() {
		return withSubstitutionString;
	}
	public void setWithSubstitutionString(boolean withSubstitutionString) {
		this.withSubstitutionString = withSubstitutionString;
	}

}
