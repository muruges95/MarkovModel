import java.util.HashMap;
import java.util.Random;

/**
 * For storing Markov model tables and for generating pseudorandom characters
 * based on frequency of patterns in text
 * @author muruges
 *
 */
public class MarkovModel {
	
	private int order;
	public final static char NOCHARACTER = 255;
	public final static char INVALIDCHAR = 254; // for the next character function.
	private Random rand; // for generating the characters
	private HashMap<String, int[]> hmap; // for storing frequencies of characters after a string
	
	/**
	 * Fills up hmap with every possible sequential substring of
	 * size order. Each entry has the number of appearances of
	 * each char after the substring. Note also that I have an 
	 * array of size 129 as the extra entry in each table is to 
	 * keep the total characters added into the array.
	 * 
	 * The checking of the values of order and text.length() are done
	 * in TextGenerator class
	 * @param text
	 * @param order
	 */
	public MarkovModel(String text, int order){
		this.order = order;
		rand = new Random();
		hmap = new HashMap<String, int[]>();
		int index = 0;
		int length = text.length();	
		String subStr;
		char nextChar;
		int[] charArray;
		while (index + order < length){ // as long as we still can form substring of size k from out current index
			subStr = text.substring(index, index + order);
			nextChar = text.charAt(index + order); // the char after the string
			charArray = hmap.get(subStr); // the char array for the string (if it exists)
			index++;
			
			// update the total by 1 and the frequency of the char by 1
			if (charArray == null){ // if it doesnt
				charArray = new int[129];
				charArray[128]++;
				charArray[nextChar]++;
				hmap.put(subStr, charArray);
			} else {
				charArray[128]++;
				charArray[nextChar]++;
			}
			
		}

		
		
	}
	public int order(){
		return this.order;
	}
	
	
	/**
	 * frequency of a string in text
	 * @param kgram
	 * @return number of appearances
	 */
	public int getFrequency(String kgram){
		if (kgram.length() != order){
			throw new IllegalArgumentException("Input string is of wrong size.");
		}
		return hmap.get(kgram)[128]; // return the value of the last element
	}
	
	
	/**
	 * frequency of a char after a string in text
	 * @param kgram
	 * @param c
	 * @return number of appearances
	 */
	public int getFrequency(String kgram, char c){
		if (kgram.length() != order){
			throw new IllegalArgumentException("Input string is of wrong size.");
		}
		return hmap.get(kgram)[c]; // return value of element c
	}
	
	
	
	/**
	 * generates new character based on kgram
	 * @param kgram
	 * @return randomly generated character
	 */
	public char nextCharacter(String kgram){
		if (kgram.length() != order){
			throw new IllegalArgumentException("Input string is of wrong size.");
		}
		int[] charArr = hmap.get(kgram);
		if (charArr == null){ // if it doesnt exist in our lookup table
			return NOCHARACTER;
		}
		int chosenInt = rand.nextInt(charArr[128]); // randomly chosen int from 0(inclusive) to frequency(exclusive)
		int totalTraversed = 0; // the number of characters passed (taking into account the characters' frequencies as well)
		int currCount = 0;
		// chose not to reuse the getFrequency functions as would unnecessarily need to get from the lookup table again
		for (char c = 0; c < 128; c++){
			currCount = charArr[c];
			if (currCount != 0){
				totalTraversed += currCount;
				if (totalTraversed > chosenInt){ // if the chosenInt is within the range for this char
					return c;
				}
			}
		}
		System.out.println("Error: Should not have reached this stage of nextCharacter method.");
		return INVALIDCHAR;
			
	}
	
	/**
	 * Set the seed of the pseudorandom generator
	 * @param s
	 */
	public void setRandomSeed(long s){
		rand.setSeed(s);
	}
	
	public static void main(String[] args){
		MarkovModel test = new MarkovModel("gagggagaggcgagaaa", 2);
//		System.out.println("gagagb".substring(4, 8));
	}
	
}
