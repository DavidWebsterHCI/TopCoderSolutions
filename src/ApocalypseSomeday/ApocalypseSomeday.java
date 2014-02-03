package ApocalypseSomeday;

public class ApocalypseSomeday {

	/*
	 * Problem Statement:
	 * 
	 & number 666 is considered to be the occult
	 * "number of the beast" and is a well used number in all major apocalypse
	 * themed blockbuster movies. However the number 666 can't always be used in
	 * the script so numbers such as 1666 are used instead. Let us call the
	 * numbers containing at least three contiguous sixes beastly numbers. The
	 * first few beastly numbers are 666, 1666, 2666, 3666, 4666, 5666... Given
	 * a 1-based index n, your program should return the n-th beastly number.
	 * Definition ���� Class: ApocalypseSomeday Method: getNth Parameters: int
	 * Returns: int Method signature: int getNth(int n) (be sure your method is
	 * public) Limits ���
	 * 
	 * Time limit (s): 2.000 Memory limit (MB): 64 Constraints - n will be
	 * between 1 and 10000, inclusive
	 * 
	 * NOTE: Examples from problem specs at end of code.
	 */

	public static void main(String[] args) {
		// Test 1
		System.out.println("expected 1666: returned: " + getNth(2));

		// Test 2
		System.out.println("expected 2666: returned: " + getNth(3));

		// Test 3
		System.out.println("expected 5666: returned: " + getNth(6));

		// Test 4
		System.out.println("expected 66666: returned: " + getNth(187));

		// Test 5
		System.out.println("expected 166699: returned: " + getNth(500));
	}

	public static int getNth(int n) {
		int counter = 0;
		int foundDevilNumber = 0;
		do {
			counter++;
			String s = Integer.toString(counter);
			CharSequence cs = "666";
			if (s.contains(cs)) {
				if (++foundDevilNumber == n) {
					break;
				}
			}
		} while (true);
		return counter;
	}
}

/*
 * Examples 0)
 * 
 * ���� 2 Returns: 1666
 * 
 * 1)
 * 
 * ���� 3 Returns: 2666
 * 
 * 2)
 * 
 * ���� 6 Returns: 5666
 * 
 * 3)
 * 
 * ���� 187 Returns: 66666
 * 
 * 4)
 * 
 * ���� 500 Returns: 166699
 * 
 * This problem statement is the exclusive and proprietary property of TopCoder,
 * Inc. Any unauthorized use or reproduction of this information without the
 * prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003,
 * TopCoder, Inc. All rights reserved.
 */

