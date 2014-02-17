package ShortProblems;

import java.util.Stack;

public class TwoStackQueue {

	/*
	 * Implement a queue with 2 stacks. Your queue should have an enqueue and a
	 * dequeue function and it should be "first in first out" (FIFO). Optimize
	 * for the time cost of m function calls on your queue. These can be any mix
	 * of enqueue and dequeue calls. Assume you already have a stack
	 * implementation and it gives O(1) time push and pop.
	 */
	private static Stack<Object> inStack = new Stack<Object>();
	private static Stack<Object> outStack = new Stack<Object>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		enqueue("1");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("2");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("3");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("4");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("5");
		System.out.println("Popping: [" + dequeue() + "]");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("1");
		System.out.println("Popping: [" + dequeue() + "]");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("2");
		System.out.println("Popping: [" + dequeue() + "]");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("3");
		System.out.println("Popping: [" + dequeue() + "]");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("4");
		System.out.println("Popping: [" + dequeue() + "]");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("5");
		System.out.println("Popping: [" + dequeue() + "]");
		System.out.println("OS: " + outStack + "IS: " + inStack);
		enqueue("9");
	}

	public static void enqueue(Object obj) {
		inStack.push(obj);
	}

	public static Object dequeue() {

		if (outStack.isEmpty()) {
			flipStack();
		}

		if (!outStack.isEmpty())
			return outStack.pop();
		else
			return null;
	}

	private static void flipStack() {
		while (!inStack.isEmpty()) {
			outStack.push(inStack.pop());
		}
	}

}
