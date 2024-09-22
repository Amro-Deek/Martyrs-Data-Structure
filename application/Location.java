package application;

public class Location {
	private String name;
	private MartyrDateTree dateTree;

	public Location(String name, MartyrDateTree dateTree) {
		super();
		this.name = name;
		this.dateTree = dateTree;
	}
	
	public Location(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MartyrDateTree getDateTree() {
		return dateTree;
	}

	public void setDateTree(MartyrDateTree dateTree) {
		this.dateTree = dateTree;
	}

	@Override
	public String toString() {
		return "Location [name=" + name + ", dateTree=" + dateTree + "]";
	}

}
