package application;

public class Stack {
	private Object[] stack;
	private int top;
	private final static int SIZE=25;
	private int size;

	public Stack() {
		this(SIZE);
	}
	public Stack(int size) {
		this.size=size;
		stack = new Object[size];
		top=-1;
	}
	public boolean isFull() {
		return top+1==size;
	}
	public boolean isEmpty() {
		return top==-1;
	}
	public boolean push(Object obj) {
		if(isFull()) return false;
		stack[++top] = obj;
		return true;
	}
	public Object peek() {
		if(isEmpty()) return null;
		return stack[top];
	}
	public Object pop() {
		if(isEmpty()) return null;
		return stack[top--];
	}
	public void printStack() {
		Stack s2 = new Stack(this.getSize());
		while(!this.isEmpty()) {
			System.out.println(this.peek());
			s2.push(this.pop());
		}
		while(!s2.isEmpty()) this.push(s2.pop());
	}
	public boolean search(Object obj){
		Stack s2 = new Stack();
		while (size != 0) {
			if((char)this.peek()==(char)obj) {
				while (!s2.isEmpty())
					this.push(s2.pop());
				return true;
			}
			s2.push(this.pop());
		}
		while (!s2.isEmpty())
			this.push(s2.pop());
		return false;
	}
	public boolean remove(Object obj) {
		Stack s2 = new Stack();
		while (!this.isEmpty()) {
			if((char)this.peek()==(char)obj) {
				this.pop();
				while (!s2.isEmpty())
					this.push(s2.pop());
				return true;
			}
			else s2.push(this.pop());
		}
		while (!s2.isEmpty())
			this.push(s2.pop());
		return false;
	}
	public static boolean check(String s){
		Stack st = new Stack();
		for(int i=0; i<s.length(); i++){
			if(s.charAt(i)==')' && !st.remove('(')) return false;
			else if(s.charAt(i)=='}' && !st.remove('{')) return false;
			else if(s.charAt(i)==']' && !st.remove('[')) return false;
			else if(s.charAt(i)=='(' || s.charAt(i)=='{' || s.charAt(i)=='[') st.push(s.charAt(i));
		}
		if(!st.isEmpty()) return false;
		return true;
	}
//	public void sortStack() {
//		Stack s2 = new Stack(this.getSize());
//		s2.push(this.pop());
//		while(!this.isEmpty()) {
//			Object temp = this.pop();
//			while(!s2.isEmpty() && s2.peek().compareTo(temp)>0) {
//				this.push(s2.pop());
//			}
//			s2.push(temp);
//		}
//		while (!s2.isEmpty()) {
//            this.push(s2.pop());
//        }
//	}
//	public boolean insertSorted(Object obj) {
//		if(isFull()) return false;
//		Stack s2 = new Stack(this.getSize());
//		while(!this.isEmpty() && this.peek().compareTo(obj)<0) {
//			s2.push(this.pop());
//		}
//		while(!s2.isEmpty()) this.push(s2.pop());
//	}
//	public Stack mergeSorted(Stack s2) {
//		Stack mergedStack = new Stack(this.getSize()+s2.getSize());
//		while(!s2.isEmpty()) mergedStack.insertSorted(s2.pop());
//		while(!this.isEmpty()) mergedStack.insertSorted(this.pop());
//		return mergedStack;
//
//	}
	public Object[] getStack() {
		return stack;
	}
	public void setStack(Object[] stack) {
		this.stack = stack;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

}
