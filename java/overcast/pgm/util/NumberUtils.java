package overcast.pgm.util;

import java.util.List;

import com.google.common.collect.Lists;

public class NumberUtils {

	public static int parseInteger(String input) {
		int integer = Integer.parseInt(input);
		return integer;
	}

	public static double parseDouble(String input) {
		if (input.equals("-oo")) {
			return Double.NEGATIVE_INFINITY;
		} else if (input.equals("oo")) {
			return Double.POSITIVE_INFINITY;
		}else{
			return Double.parseDouble(input);
		}
	}
	

	public static short parseShort(String input){
		 return Short.parseShort(input);
	}

	public static float parseFloat(String textContent) {
		return Float.parseFloat(textContent);
	}
	
	
	
	public static List<Double> parseDoubleList(String text){
		 if(text == null){
			 return null;
		 }else{
			 List<Double> doubles = Lists.newLinkedList();
			 
			 String[] split =text.split("[^o0-9\\.-]");
			  
			 for(String splits : split){
				  doubles.add(parseDouble(splits));
			 }
			 
			 return doubles;
		 }  
	}
}
