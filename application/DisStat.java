package application;

public class DisStat {
	private String name;
	private String NoOfMartyrs;

	public DisStat(String name, String noOfMartyrs) {
		super();
		this.name = name;
		this.NoOfMartyrs = noOfMartyrs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNoOfMartyrs() {
		return NoOfMartyrs;
	}

	public void setNoOfMartyrs(String noOfMartyrs) {
		this.NoOfMartyrs = noOfMartyrs;
	}

	@Override
	public String toString() {
		return "DisStat [name=" + name + ", NoOfMartyrs=" + NoOfMartyrs + "]";
	}
	
}
