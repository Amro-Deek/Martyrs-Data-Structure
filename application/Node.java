package application;

public class Node {

	private Martyr data;
	private Node next;

	public Node(Martyr data) {
		setData(data);
	}

	public Martyr getData() {
		return data;
	}

	public void setData(Martyr data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return data + "";
	}

}
