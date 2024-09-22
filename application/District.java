package application;

public class District {
	private String name;
	private LocTree locationTree;

	public District(String name, LocTree locationTree) {
		super();
		this.name = name;
		this.locationTree = locationTree;
	}
	

	public District(String name) {
		super();
		this.name = name;
	}


	public District() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocTree getLocationTree() {
		return locationTree;
	}

	public void setLocationTree(LocTree locationTree) {
		this.locationTree = locationTree;
	}

	@Override
	public String toString() {
		return "District [name=" + name + ", locationTree=" + locationTree + "]";
	}

}
