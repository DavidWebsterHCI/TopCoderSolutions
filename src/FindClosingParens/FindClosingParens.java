package FindClosingParens;

public class FindClosingParens {

	/*
	 * I like parentheticals (a lot).
	 * 
	 * "Sometimes (when I nest them (my parentheticals) too much (like this (and this))) they get confusing."
	 * 
	 * Write a function that, given a sentence like the above, along with the
	 * position of an opening parenthesis, finds the corresponding closing
	 * parenthesis.
	 * 
	 * Example: if the example string above is input with the number 10
	 * (position of the first parenthesis), the output should be 79 (position of
	 * the last parenthesis).
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out
				.println("Recursive:   Expecting 79: "
						+ findClosingParenRecursively(
								"Sometimes (when I nest them (my parentheticals) too much (like this (and this))) they get confusing.",
								10));
		System.out
				.println("Iteratively: Expecting 79: "
						+ findClosingParenIteratively(
								"Sometimes (when I nest them (my parentheticals) too much (like this (and this))) they get confusing.",
								10));

		System.out.println("Recursive:   Expecting -1: "
				+ findClosingParenRecursively("No paren!", 3));
		System.out.println("Iteratively: Expecting -1: "
				+ findClosingParenIteratively("No paren!", 3));

		System.out.println("Recursive:   Expecting -1: "
				+ findClosingParenRecursively("", 0));
		System.out.println("Iteratively: Expecting -1: "
				+ findClosingParenIteratively("", 0));

		System.out.println("Recursive:   Expecting -1: "
				+ findClosingParenRecursively("((((", 0));
		System.out.println("Iteratively: Expecting -1: "
				+ findClosingParenIteratively("((((", 0));
	}

	/**
	 * StartingParenLoc is a 0-based counting system.
	 * 
	 * @param s
	 * @param startingParenLoc
	 * @return
	 */
	public static int findClosingParenRecursively(String s, int startingParenLoc) {
		// check for valid parens location
		int returnVal = -1;

		if (errorCheck(s, startingParenLoc))
			return -1;

		for (int i = startingParenLoc + 1; i < s.length(); i++) {

			if (s.charAt(i) == '(') {
				i = findClosingParenRecursively(s, i);

				if (i == -1) {
					// no ) was found, so propagate up the error flag.
					return -1;
				}

				returnVal = i;
				i = i + 1; // push i past the current ")"
			} else if (s.charAt(i) == ')') {
				return i;
			}
		}

		return returnVal;
	}

	/**
	 * StartingParenLoc is a 0-based counting system.
	 * 
	 * @param s
	 * @param startingParenLoc
	 * @return
	 */
	public static int findClosingParenIteratively(String s, int startingParenLoc) {
		// check for valid parens location
		int openParenCounter = 1;
		int returnVal = -1;

		if (errorCheck(s, startingParenLoc))
			return -1;

		for (int i = startingParenLoc + 1; i < s.length(); i++) {

			if (s.charAt(i) == '(') {
				openParenCounter++;
			} else if (s.charAt(i) == ')') {
				openParenCounter--;
			}

			if (openParenCounter == 0) {
				returnVal = i;
				break;
			}
		}

		return returnVal;
	}

	/**
	 * returns true if there was an error detected
	 * 
	 * @param s
	 * @param startingParenLoc
	 * @return
	 */
	public static boolean errorCheck(String s, int startingParenLoc) {
		// error checking
		if (s == null)
			return true; // null object passed
		else if (s.length() <= 1)
			return true; // cant possibly have an open and a close
		else if (s.charAt(startingParenLoc) != '(')
			return true; // starting location is off.
		else
			return false;
	}

}
