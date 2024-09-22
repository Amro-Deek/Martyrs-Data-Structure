package application;

public class MDateNode {
	private MDateNode left, right;
	private DateData data;

	public MDateNode(DateData data) {
		super();
		this.data = data;
	}

	public MDateNode getLeft() {
		return left;
	}

	public void setLeft(MDateNode left) {
		this.left = left;
	}

	public MDateNode getRight() {
		return right;
	}

	public void setRight(MDateNode right) {
		this.right = right;
	}

	public DateData getData() {
		return data;
	}

	public void setData(DateData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "["+ data +"]";
	}

}
