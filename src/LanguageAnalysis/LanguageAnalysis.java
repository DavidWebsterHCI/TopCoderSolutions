package LanguageAnalysis;
import java.util.HashMap;
import java.util.Iterator;

public class LanguageAnalysis {

	/*
	 * PROBLEM STATEMENT: In written languages, some symbols may appear more
	 * often than others. Expected frequency tables have been defined for many
	 * languages. For each symbol in a language, a frequency table will contain
	 * its expected percentage in a typical passage written in that language.
	 * For example, if the symbol "a" has an expected percentage of 5, then 5%
	 * of the letters in a typical passage will be "a". If a passage contains
	 * 350 letters, then 'a' has an expected count of 17.5 for that passage
	 * (17.5 = 350 * 5%). Please note that the expected count can be a
	 * non-integer value. The deviation of a text with respect to a language
	 * frequency table can be computed in the following manner. For each letter
	 * ('a'-'z') determine the difference between the expected count and the
	 * actual count in the text. The deviation is the sum of the squares of
	 * these differences. Blank spaces (' ') and line breaks (each element of
	 * text is a line) are ignored when calculating percentages. Each frequency
	 * table will be described as a concatenation of up to 16 strings of the
	 * form "ANN", where A is a lowercase letter ('a'-'z') and NN its expected
	 * frequency as a two-digit percentage between "00" (meaning 0%) and "99"
	 * (meaning 99%), inclusive. Any letter not appearing in a table is not
	 * expected to appear in a typical passage (0%). You are given a String[]
	 * frequencies of frequency tables of different languages. Return the lowest
	 * deviation the given text has with respect to the frequency tables.
	 */
	
	/*Program output:
	 * 
	 * Note: results in the format  'Expected Deviation | Calculated Deviation'
	 * Test case 0 = 0.0 | 0.0
	 * Test case 1 = 2.0 | 2.0
	 * Test case 2 = 10.8 | 10.8
	 * Test case 3 = 130.6578 | 130.65779999999998
	 * Test case 4 = 114.9472 | 114.94719999999998
	 * Test case 5 = 495050.0 | 495050.0
	 */

	/* NOTE: Examples/test cases are at bottom! */

	public static void main(String[] args) {
		int testCaseCounter = 0;
		System.out.println("Note: results in the format  'Expected Deviation | Calculated Deviation'");
		
		// Test case 0
		String a0[] = { "a30b30c40", "a20b40c40" };
		String b0[] = { "aa bbbb cccc" };
		System.out.println("Test case " + testCaseCounter++ + " = 0.0 | " + language(a0, b0));

		// Test case 1
		String a1[] = { "a30b30c40", "a20b40c40" };
		String b1[] = { "aaa bbbb ccc" };
		System.out.println("Test case " + testCaseCounter++ + " = 2.0 | " + language(a1, b1));

		// test case 2
		String a2[] = { "a10b10c10d10e10f50" };
		String b2[] = { "abcde g" };
		System.out.println("Test case " + testCaseCounter++ + " = 10.8 | " + language(a2, b2));

		// test case 3
		String a3[] = { "a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01",
		"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01" };
		String b3[] = { "this text is in english",
				"the letter counts should be close to", "that in the table" };
		System.out.println("Test case " + testCaseCounter++ + " = 130.6578 | " + language(a3, b3));

		// test case 4
		String a4[] = { "a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01",
		"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01" };
		String b4[] = { "en esta es una oracion en castellano",
				"las ocurrencias de cada letra",
		"deberian ser cercanas a las dadas en la tabla" };
		System.out.println("Test case " + testCaseCounter++ + " = 114.9472 | " + language(a4, b4));

		// test case 5
		String a5[] = { "z99y01", "z99y01", "z99y01", "z99y01", "z99y01",
				"z99y01", "z99y01", "z99y01", "z99y01", "z99y01" };
		String b5[] = { "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" };
		System.out.println("Test case " + testCaseCounter++ + " = 495050.0 | " + language(a5, b5));
	}

	public static double language(String[] frequencies, String[] text) {

		HashMap<Integer, HashMap<Character, Integer>> languageFreqMap;
		HashMap<Character, Integer> charCountMap;

		// Build hashmaps of the character frequencies expected (based on the
		// keys held in 'String[] frequencies'
		languageFreqMap = initLanguagesMap(frequencies);

		// Create a map which counts all instances of a-z characters in 'text'
		charCountMap = initCharCountMap(text);

		// Analyze 'text' for accuracy to language frequency 'key' built in
		// initLanguagesMap
		return analyseTextForLanguageSet(languageFreqMap, charCountMap);
	}

	/**
	 * this function will sum up the value's in a hashmap
	 * 
	 * @param map
	 * @return
	 */
	public static double sumUpMapValues(HashMap<Character, Integer> map) {
		double total = 0;
		Iterator<Character> it = map.keySet().iterator();
		while (it.hasNext()) {
			char c = it.next();
			total += map.get(c);
		}

		return total;
	}

	/**
	 * this function will compare a text with a key of character probabilities
	 * as per the problem specification
	 * 
	 * @param languageFreqMap
	 * @param charCountMap
	 * @return
	 */
	public static double analyseTextForLanguageSet(
			HashMap<Integer, HashMap<Character, Integer>> languageFreqMap,
			HashMap<Character, Integer> charCountMap) {

		double expectedCount = 0;
		double tempSum = 0;
		double currentSum = Integer.MAX_VALUE;

		// iterate through the map created of character instances found in the
		// text being examined
		for (int k = 0; k < charCountMap.size(); k++) {
			double totalCharacterCountFromText = sumUpMapValues(charCountMap);

			// for each map of potential character expectations (i.e.
			// english/spanish/etc) examine the text's accuracy to the key.
			for (int i = 0; i < languageFreqMap.size(); i++) {
				double actualCount;
				HashMap<Character, Integer> expectedFreqChart = languageFreqMap
						.get(i);

				Iterator<Character> it = expectedFreqChart.keySet().iterator();
				while (it.hasNext()) {
					char c = it.next();
					if (charCountMap.get(c) != null)
						actualCount = charCountMap.get(c);
					else
						actualCount = 0;

					expectedCount = ((double) expectedFreqChart.get(c) / 100)
							* totalCharacterCountFromText;

					tempSum += Math.abs(expectedCount - actualCount)
							* Math.abs(expectedCount - actualCount);

				}

				// in the case where a character was expected, but not seen in
				// text, update the error
				Iterator<Character> itTwo = charCountMap.keySet().iterator();
				while (itTwo.hasNext()) {
					char ch = itTwo.next();
					if (expectedFreqChart.get(ch) == null) {
						tempSum += charCountMap.get(ch) * charCountMap.get(ch);
					}
				}

				// keep track of the current lowest deviation from an expected
				// set in order to decide which
				// of the language frequency charts is closest to the text at
				// hand.
				if (tempSum < currentSum) {
					currentSum = tempSum;
				}

				// Reset tempSum for next loop.
				tempSum = 0;
			}

		}

		return currentSum;
	}

	/**
	 * builds the hashmap of characters. key is a character from a-z and value
	 * is the amount of instances of the character in the text.
	 * 
	 * @param text
	 * @return
	 */
	public static HashMap<Character, Integer> initCharCountMap(String[] text) {

		HashMap<Character, Integer> CharFreqMap = new HashMap<Character, Integer>();

		for (int i = 0; i < text.length; i++) {
			for (int k = 0; k < text[i].length(); k++) {
				char c = text[i].charAt(k);

				if (CharFreqMap.containsKey(c)) {
					CharFreqMap.put(c, CharFreqMap.get(c) + 1);
				} else if (c != ' ') { // ignore spaces
					CharFreqMap.put(c, 1);
				}
			}
		}

		return CharFreqMap;
	}

	/**
	 * Builds a map of the language key. Input is required to be of the format
	 * "ANN" as in a20b40c10...etc Note: it is assumed that the string will not
	 * contain duplicate character "ANN" sets as per problem spec. i.e. a10a20
	 * is invalid.
	 * 
	 * @param frequencies
	 * @return
	 */
	public static HashMap<Integer, HashMap<Character, Integer>> initLanguagesMap(
			String[] frequencies) {

		HashMap<Integer, HashMap<Character, Integer>> mapOfLanguageFreq = new HashMap<Integer, HashMap<Character, Integer>>();

		// Build correct amount of maps
		for (int i = 0; i < frequencies.length; i++) {
			mapOfLanguageFreq.put(i, new HashMap<Character, Integer>());
		}

		// While there are more elements
		for (int i = 0; i < frequencies.length; i++) {
			int charCount = 0;
			String freqChart = frequencies[i];
			HashMap<Character, Integer> languageMap = mapOfLanguageFreq.get(i);

			// Go through string and build a language map out of the string
			// under examination
			while (charCount < freqChart.length()) {
				char c = freqChart.charAt(charCount);
				int value = Integer.parseInt(freqChart.substring(charCount + 1,
						charCount + 3));

				languageMap.put(c, value);

				charCount += 3;
			}
		}
		return mapOfLanguageFreq;
	}
}

/*
 * Examples 0)
 * 
 * {"a30b30c40","a20b40c40"}
 * 
 * {"aa bbbb cccc"}
 * 
 * Returns: 0.0
 * 
 * The first table indicates that 30% of the letters are expected to be 'a', 30%
 * to be 'b', and 40% to be 'c'. The second table indicates that 20% are
 * expected to be 'a', 40% to be 'b', and 40% to be 'c'. We consider the text to
 * have length 10, as blank spaces are ignored.
 * 
 * With respect to the first table, there are 2 'a' where 3 were expected (a
 * difference of 1), one more 'b' than expected (again a difference of 1) and as
 * many 'c' as expected. The sum of the squares of those numbers gives a
 * deviation of 2.0.
 * 
 * As for the second table, the text matches expected counts exactly, so its
 * deviation with respect to that language is 0.0. 1)
 * 
 * 
 * {"a30b30c40","a20b40c40"}
 * 
 * {"aaa bbbb ccc"}
 * 
 * Returns: 2.0
 * 
 * Here we use the same tables as in the previous example, but with a different
 * text. The counts for 'b' and 'c' each differ by 1 from the expected counts in
 * the first table, and the counts for 'a' and 'c' each differ by 1 from the
 * expected counts in the second table. The text has a deviation of 2.0 with
 * respect to both tables. 2)
 * 
 * 
 * {"a10b10c10d10e10f50"}
 * 
 * {"abcde g"}
 * 
 * Returns: 10.8
 * 
 * Here, each of the letters 'a' through 'e' is expected to make up 10% of the
 * letters (0.6 letters). Each of those letters actually appears once, so the
 * difference is 0.4, which becomes 0.16 when squared. 50% of the letters (3
 * letters) are expected to be 'f', but 'f' does not appear at all. The square
 * of this difference is 9.0. No 'g's are expected to appear, but there is one
 * in the text. This adds 1.0 to the deviation. The final deviation for this
 * table is: 0.16+0.16+0.16+0.16+0.16+9.0+1.0=10.8. 3)
 * 
 * 
 * {"a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01"
 * ,"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01"}
 * 
 * {"this text is in english" ,"the letter counts should be close to"
 * ,"that in the table"}
 * 
 * Returns: 130.6578
 * 
 * These two frequency tables correspond (roughly) to the frequencies found in
 * the English and Spanish languages, respectively. The English passage, as
 * expected, has a lower deviation in the first table than in the second one. 4)
 * 
 * 
 * {"a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01"
 * ,"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01"}
 * 
 * {"en esta es una oracion en castellano" ,"las ocurrencias de cada letra"
 * ,"deberian ser cercanas a las dadas en la tabla"}
 * 
 * Returns: 114.9472
 * 
 * The same tables again, but with Spanish passage. This time the second table,
 * which correspond to frequencies in Spanish, gives the lowest deviation. 5)
 * 
 * 
 * {"z99y01", "z99y01", "z99y01", "z99y01", "z99y01", "z99y01", "z99y01",
 * "z99y01", "z99y01", "z99y01"}
 * 
 * {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"}
 * 
 * Returns: 495050.0
 */
