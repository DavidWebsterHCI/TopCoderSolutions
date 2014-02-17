package okCupidClone;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class okCupidClone {

	private static final int[] IMPORTANCE_POINTS = new int[]{0, 1, 10, 50, 250};
	
	public static void main(String[] args) {
		
		
		HashMap<Integer, User> userMap = buildUsersFromJsonData("C:\\Users\\Dia\\Desktop\\okCupidClone\\input.json");
		
		//Note the following function both calculates the user's top ten matches, and calls the helper functions to print to std out
		//as well as package up the results into a json object.  Currently the json object is not utilized as I have nowhere to send it
		//But it is ready for "shipping"!
		calculateUsersTopTenMatches(userMap);
		
	}

	/**
	 * Calculates the top ten matches for every user in the map 'userMap'  It is currently returned as std output text as well as a json object.
	 * 
	 * @param userMap - map of users created from a .json input file.
	 */
	private static void calculateUsersTopTenMatches(HashMap<Integer, User> userMap) {
		/*
		 * Note: Currently this function is hovering at a runtime of O(N^2).  All possible combinations of users are computed, with no cashing, i.e.
		 * user 1's potential relationship with user 2 is calculated, and later user 2's potential relationship with user 1 is calculated.
		 * This is one potential avenue of optimization that instantly pops into mind; however there are others methodologies as well, I am sure.
		 * This code should be viewed as an initial system functionality prototype.  If this were a production product, the next step would be 
		 * much more through testing and optimization code reviews.
		 */
		
		//Iterate through all users, and calculate their top 10 matches
		Iterator<Integer> i = userMap.keySet().iterator();
		while(i.hasNext()){
			User focusUser = userMap.get(i.next());
			LinkedList<Double[]> topTenList = new LinkedList<Double[]>();
			
			//Init topTenList to allow future operations
			Double[] init = {-1.0,-1.0};
			for(int x = 0; x <10; x ++){
				topTenList.add(init);
			}
			
			Iterator<Integer> i2 = userMap.keySet().iterator();
			while(i2.hasNext()){
			
				//Dont try to match yourself to yourself -- no vain people allowed!
				if(i.equals(i2)) continue;
				
				User targetUser = userMap.get(i2.next());
				
				//Note: This duple "Double[]" has userId at index [0] and % match with target at index [1]
				//Find the correct location for this result, and add it into the list of top ten
				double result = calculateTotalMatchPotential(focusUser, targetUser);
				for(int x=0; x<10; x++){
					Double[] idMatchPercentInQuestion = {(double)targetUser.getId(), result};
					Double[] idMatchPercentInList = topTenList.get(x);
					if(result > idMatchPercentInList[1])
					{
						topTenList.add(x, idMatchPercentInQuestion);
						break;
					}
				}
				
				//We only care about the top ten, so maintain the list length at 10.
				if(topTenList.size() > 10){
					topTenList.subList(10, topTenList.size()).clear();
				}
				
			}
			
			System.out.println("------------Top ten list for " + focusUser.getId() + "----------");
			int tempCount = 1;
			for(Double[] a : topTenList)
			{
				System.out.println("Match #" + tempCount++ + ": " + "User #" + a[0].intValue() + " at %" + a[1] *100);
			}
			
			packUpResultsIntoJson(topTenList, focusUser.getId());
		}
	}

	/**
	 * packages the top ten users matches into a json object for future shipment. 
	 * 
	 * @param topTenList - list of duples index[0] is the id of the potential lover, and index [1] is % match to user
	 * @param userId - id of a user.
	 */
	private static void packUpResultsIntoJson(LinkedList<Double[]> topTenList, int userId) {
		JSONObject resultsObj = new JSONObject();
		JSONObject profileIdObj = new JSONObject();
		JSONObject matchesObj = null;
		JSONArray matchesArr = new JSONArray();
		JSONArray resultsArr = new JSONArray();
		
		for(Double[] a : topTenList)
		{
			matchesObj = new JSONObject();
			matchesObj.put("profileId", a[0]);
			matchesObj.put("score", a[1]);
			matchesArr.add(matchesObj);
		}
		
		profileIdObj.put("profileId", userId);
		profileIdObj.put("matches", matchesArr);
		
		resultsArr.add(profileIdObj);
		resultsObj.put("results", resultsArr);
		
		//Printing to std out as per problem specifications
		System.out.println(resultsObj);
	}

	/**
	 * returns a decimal representation of match between two Users.
	 * 
	 * @param focus - person looking for love
	 * @param target - potential lover
	 * @return
	 */
	private static double calculateTotalMatchPotential(User focus, User target)
	{
		Set<Integer> questionSet = getSetOfAnsweredQuestions(focus, target);
		
		double resultOne = calculateMatchPotential(focus, target, questionSet);
		double resultTwo = calculateMatchPotential(target, focus, questionSet);

		return calculateTrueMatch(Math.sqrt(resultOne * resultTwo), questionSet.size());		
	}
	
	/**
	 * builds a hashmap of users <userId, User object> based on a .json file located at 'path'
	 * @param path location of .json file.
	 * @return hashmap representation of users is json file
	 */
	private static HashMap<Integer, User> buildUsersFromJsonData(String path) {
		
		HashMap<Integer, User> userMap = new HashMap<Integer, User>();
		JSONParser parser = new JSONParser();
		 
		try {
			Object obj = null;
			try {
				obj = parser.parse(new FileReader(path));
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray users = (JSONArray) jsonObject.get("profiles");
			
			for(Object o: users){
				User user = new User();
				int id = ((Long)((JSONObject)o).get("id")).intValue();

				JSONArray answers = (JSONArray) ((JSONObject)o).get("answers");
				
				for(int i = 0; i < answers.size(); i++){

					JSONArray acceptableAnswers = (JSONArray) ((JSONObject)answers.get(i)).get("acceptableAnswers");

					//translate the json based importance into the okcupid based importance
					int importance = IMPORTANCE_POINTS[((Long) ((JSONObject)answers.get(i)).get("importance")).intValue()];
				
					int questionId = ((Long) ((JSONObject)answers.get(i)).get("questionId")).intValue();
					int answer = ((Long) ((JSONObject)answers.get(i)).get("answer")).intValue();
			
					//Build set of acceptable answers, then add it to the user object
					HashSet<Integer> acceptableAnswerSet = new HashSet<Integer>();
					for(int k = 0; k < acceptableAnswers.size(); k++)
					{
						acceptableAnswerSet.add(((Long) acceptableAnswers.get(k)).intValue());
					}
					user.setExpectedAnswer(questionId, acceptableAnswerSet);
					user.setAnswer(questionId, answer);
					user.setImportance(questionId, importance);
					user.setUserId(id);
				}

				//Place the user that was built in the previous steps based on the json data into the user map
				userMap.put(id, user);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		return userMap;
	}

	/**
	 * modify match percentage based on size of question sets answered between two potential lovers.
	 * 
	 * @param match - decimal percentage of match between two users
	 * @param size - of question set.
	 * @return true match percentage.
	 */
	private static double calculateTrueMatch(double match, int size) {
		
		//These specifications are based on the okcupid .pdf methodology of calculating true match.
		if(size <=1)
			return 0;
		else if(size <=2)
			return match - 0.50;
		else if(size <=3)
			return match - 0.33;
		else if(size <=4)
			return match - 0.25;
		else if(size <=5)
			return match - 0.20;
		else if(size <=10)
			return match - 0.10;
		else if(size <=20)
			return match - 0.05;
		else if(size <=50)
			return match - 0.02;
		else if(size <=100)
			return match - 0.01;
		else if(size <=500)
			return match - 0.002;
		else if(size <=1000)
			return match - 0.001;
			
		//Default
		return match;
	}

	/**
	 * Given two users, returns the set of questions that have been answered that is common to both.
	 * 
	 * @param user a
	 * @param user b
	 * @return a set of answers common to both users
	 */
	public static Set<Integer> getSetOfAnsweredQuestions(User a, User b){
		
		HashSet<Integer> questionsInCommon = new HashSet<Integer>();
		a.getQuestionsAnswered();
		questionsInCommon.addAll(a.getQuestionsAnswered());
		questionsInCommon.retainAll(b.getQuestionsAnswered());
		
		return questionsInCommon;
	}
	
	/** 
	 * returns the % of a match user focus sees in user target
	 * @param focus - the user that is looking for a match
	 * @param target - the user that may or may not be a good match for focus
	 * @return the decimal percent of a match target is for focus
	 */
	public static double calculateMatchPotential(User focus, User target, Set<Integer> questionSet)
	{
		//What answered correct
		double pointsAcquired = 0;
		double totalPossiblePoints = 0;
		
		if(questionSet.size() <= 1)
			return 0;
		
		Iterator<Integer> i = questionSet.iterator();
		while(i.hasNext())
		{
			int questionId = i.next();
			
			//if target answered what the focus wanted then...
			if(focus.getExpectedAnswer(questionId).contains(target.getAnswer(questionId))){
				pointsAcquired += focus.getImportance(questionId);
				totalPossiblePoints += focus.getImportance(questionId);
			}
			else
			{
				totalPossiblePoints += focus.getImportance(questionId);				
			}
		}		
		return pointsAcquired / totalPossiblePoints;
	}
}
