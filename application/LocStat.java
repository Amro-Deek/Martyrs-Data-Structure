package application;

public class LocStat {
	private String name;
	private String earliestDate ;
	private String latestDate ;
	private String dateOfMax;
	public LocStat(String name, String earliestDate, String latestDate, String dateOfMax) {
		super();
		this.name = name;
		this.earliestDate = earliestDate;
		this.latestDate = latestDate;
		this.dateOfMax = dateOfMax;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEarliestDate() {
		return earliestDate;
	}
	public void setEarliestDate(String earliestDate) {
		this.earliestDate = earliestDate;
	}
	public String getLatestDate() {
		return latestDate;
	}
	public void setLatestDate(String latestDate) {
		this.latestDate = latestDate;
	}
	public String getDateOfMax() {
		return dateOfMax;
	}
	public void setDateOfMax(String dateOfMax) {
		this.dateOfMax = dateOfMax;
	}
	@Override
	public String toString() {
		return "LocStat [name=" + name + ", earliestDate=" + earliestDate + ", latestDate=" + latestDate
				+ ", dateOfMax=" + dateOfMax + "]";
	}
	
}
