package application;

public class DisNode {
	private DisNode left, right;
	private District data;

	public DisNode(District data) {
		super();
		this.data = data;
	}

	public DisNode getLeft() {
		return left;
	}

	public void setLeft(DisNode left) {
		this.left = left;
	}

	public DisNode getRight() {
		return right;
	}

	public void setRight(DisNode right) {
		this.right = right;
	}

	public District getData() {
		return data;
	}

	public void setData(District data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "[" + data + "]";
	}

}
