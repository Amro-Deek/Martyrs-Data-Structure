package application;

public class DateData {
	private String date;
	private MLinkList list;

	public DateData(String date, MLinkList list) {
		super();
		this.date = date;
		this.list = list;
	}

	public DateData(String date) {
		super();
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public MLinkList getList() {
		return list;
	}

	public void setList(MLinkList list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "DateData [date=" + date + ", list=" + list + "]";
	}

}
