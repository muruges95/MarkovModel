package sg.edu.nus.cs2020;

import java.io.BufferedReader;
import java.io.FileReader;

public class TextGenerator {
	
	
	public static void main(String[] args){
		try {
			int order = Integer.parseInt(args[0]);
			int n = Integer.parseInt(args[1]);
			if (n < 0 || order <= 0){
				throw new IllegalArgumentException("One or more arguments are negative or order is zero.");
			} else if (n == 0){
				System.out.println("No output as number of chars to output is zero");
				return;
			}
			
			String fileName = args[2];
			
			FileReader file = new FileReader(fileName);
			BufferedReader bufferedFile = new BufferedReader(file);
			String line = bufferedFile.readLine();
			StringBuilder sb = new StringBuilder(); // for building the text
			
			while (line != null){ // append all lines to string builder line by line
				sb.append(line).append('\n'); // append \n as readLine strips it out
				line = bufferedFile.readLine();
			}
//			file.close();
//			bufferedFile.close();
			String text = sb.toString();
//			String text = "gagggagaggcgagaaa";
			if (text.length() < order){
				throw new IllegalArgumentException("Order larger than size of text.");					
			}
			else if (text.length() == order){ // order needs to be at least 1 less than size of input text. Otherwise model will be empty.
				System.out.println("No output as length equals to order.");
				return;
			}
			MarkovModel model = new MarkovModel(text, order);
//			model.setRandomSeed(1); // EDIT THIS TO SOME GOOD SEED.
			sb = new StringBuilder(text.substring(0,order)); // initialize the kgram to first order characters
			String kgram;
			char generatedChar;
			for (int i = 0; i < n; i++){ // generate n characters
				kgram = sb.toString();
				generatedChar = model.nextCharacter(kgram);
				if (generatedChar == MarkovModel.INVALIDCHAR){ // in case nextCharacter behaves unexpectedly; Should never return true
					throw new Exception("Error with generating character.");
				} else if (generatedChar == MarkovModel.NOCHARACTER){ // if we encounter this redo the step using first order chars
					sb = new StringBuilder(text.substring(0,order));
					kgram = sb.toString();
					generatedChar = model.nextCharacter(kgram);
				}
				System.out.print(generatedChar);
				sb.deleteCharAt(0); // remove first char
				sb.append(generatedChar); // append generated character
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}
}
