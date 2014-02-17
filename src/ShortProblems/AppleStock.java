package ShortProblems;

public class AppleStock {

	/*
	 * Problem statement:
	 * 
	 * Writing interview questions hasn't made me rich. Maybe trading Apple
	 * stocks will.
	 * 
	 * I have an array stockPricesYesterday where the keys are the number of
	 * minutes into the day (starting with midnight) and the values are the
	 * price of Apple stock at that time. For example, the stock cost $500 at
	 * 1am, so stockPricesYesterday[60] = 500.
	 * 
	 * Write an efficient algorithm for computing the best profit I could have
	 * made from 1 purchase and 1 sale of an Apple stock yesterday.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int stockPricesYesterday[] = { 5, 5, 20, 3, 5, 20, 5, 8, 7 };

		int results[] = computeBuying(stockPricesYesterday);

		for (int i : results)
			System.out.print("[" + i + "]");
	}

	/**
	 * This function returns an array of integers of size 3:
	 * 
	 * results[0] = the point one should have bought 
	 * results[1] = the point at which one should have sold 
	 * results[2] = the largest profit that was possible
	 * 
	 * @param arr
	 * @return results
	 */
	public static int[] computeBuying(int[] arr) {
		int[] results = { -1, -1, -1 };

		// error check - only one data point
		if (arr.length == 1) {
			results[0] = results[1] = results[2] = 0;
			return results;
		}

		int min = 0;
		int max = 0;
		int prof = 0;

		int tempMin = 0;
		int tempMax = 0;
		int tempProf = 0;

		for (int i = 1; i < arr.length; i++) {

			if (arr[i] > arr[i - 1]) {
				tempMax = i;
				tempProf = arr[tempMax] - arr[tempMin];
			} else if (arr[i] < arr[i - 1]) {
				tempMin = i;
				tempMax = i;
			}

			if (tempProf > prof) {
				max = tempMax;
				min = tempMin;
				prof = tempProf;
			}
		}

		results[0] = min;
		results[1] = max;
		results[2] = prof;

		return results;
	}
}
