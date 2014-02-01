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
	public static void main(String[] args) {

		// String a[] = {"a01b98c01","a20b40c40"};
		// String b[] =
		// {"abbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbc"};

		// String a[] ={"a10b10c10d10e10f50"};
		// String b[] ={"abcde g"};

		// String a[] = {"a10b10c10d10e10f10g10h10i10j20"};
		// String b[] ={"abcdef ghij"};

		// String a[] ={"a30b30c40","a20b40c40"};
		// String b[] ={"aa bbbb cccc"};

		String a[] = { "a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01",
				"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01" };
		String b[] = { "this text is in english",
				"the letter counts should be close to", "that in the table" };

		// String a[] = {"a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01"
		// ,"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01"};
		//
		// String b[] = {"en esta es una oracion en castellano"
		// ,"las ocurrencias de cada letra"
		// ,"deberian ser cercanas a las dadas en la tabla"};

		// String a[] = {"z99y01", "z99y01", "z99y01", "z99y01", "z99y01",
		// "z99y01", "z99y01", "z99y01", "z99y01", "z99y01"};
		// String b[] = {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"};

		System.out.println("The least deviation from a key set was: " +language(a, b));
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
						System.out.println(charCountMap.get(ch));
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
