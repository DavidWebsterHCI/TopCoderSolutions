package ReverseCharArrayInPlace;

public class ReverseCharArrayInPlace {
	/*
	 * PROBLEM SPECIFICATION:
	 * 
	 * Write a function to reverse an array of characters in place. "In place"
	 * means "without creating a new string in memory."
	 */

	public static void main(String[] args) {

		// Driver
		char[] arr = "Muhahahaha".toCharArray();
		System.out.println(reverseInPlace(arr));

	}

	public static char[] reverseInPlace(char[] arr) {

		for (int i = 0; i < arr.length / 2; i++) {
			swap(arr, i, arr.length - i - 1);
		}
		return arr;
	}

	public static void swap(char[] arr, int indexOne, int indexTwo) {
		char temp = arr[indexOne];
		arr[indexOne] = arr[indexTwo];
		arr[indexTwo] = temp;
	}
}
