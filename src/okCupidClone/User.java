package okCupidClone;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class User {

	private int userId;
	
	// Key question ID, value question answer
	private HashMap<Integer, Integer> questionAnswerMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, HashSet<Integer>> questionExpectedAnswerMap = new HashMap<Integer, HashSet<Integer>>();
	private HashMap<Integer, Integer> questionImportanceMap = new HashMap<Integer, Integer>();
	
	// TODO space optimization, include all hashmaps in a nested hashmap so
	// only one copy of question id is kept.
	//

	User() {
	}

	public void setUserId(int id){
		userId = id;
	}
	
	public void setAnswer(int questionId, int answer) {
		questionAnswerMap.put(questionId, answer);
	}

	public void setExpectedAnswer(int questionId, HashSet<Integer> expectedAnswer) {
		questionExpectedAnswerMap.put(questionId, expectedAnswer);
	}

	public void setImportance(int questionId, int importance) {
		questionImportanceMap.put(questionId, importance);
	}

	public Set<Integer> getQuestionsAnswered() {
		return questionAnswerMap.keySet();
	}

	public int getId(){
		return userId;
	}
	
	public int getAnswer(int questionId) {
		return questionAnswerMap.get(questionId);
	}

	public HashSet<Integer> getExpectedAnswer(int questionId) {
		return questionExpectedAnswerMap.get(questionId);
	}

	public int getImportance(int questionId) {
		return questionImportanceMap.get(questionId);
	}

	public String toString() {
		return "ID: " + userId + " " + questionAnswerMap.toString()
				+ questionExpectedAnswerMap.toString()
				+ questionImportanceMap.toString();
	}
}
