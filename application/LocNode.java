package application;

public class LocNode {
	private LocNode left, right;
	private Location data;

	
	public LocNode(Location data) {
		super();
		this.data = data;
	}


	public LocNode getLeft() {
		return left;
	}


	public void setLeft(LocNode left) {
		this.left = left;
	}


	public LocNode getRight() {
		return right;
	}


	public void setRight(LocNode right) {
		this.right = right;
	}


	public Location getData() {
		return data;
	}


	public void setData(Location data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "["+ data +"]";
	}

}
