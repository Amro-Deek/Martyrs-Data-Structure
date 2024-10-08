package application;

public class Martyr implements Comparable<Martyr> {
	private String name;
	private String date;
	private int age;
	private String location;
	private String district;
	private String gender;

	public Martyr(String name, String date, int age, String location, String district, String gender) {
		this(name);
		setName(name);
		setDate(date);
		setAge(age);
		setLocation(location);
		setDistrict(district);
		setGender(gender);	
	}
	
	public Martyr(String name) {
		super();
		this.name = name;
	}


	public Martyr(String name, int age, String gender) {
		this(name);
		this.name = name;
		this.age = age;
		this.gender = gender;
	}


	public Martyr() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Martyr [name=" + name + ", date=" + date + ", age=" + age + ", location=" + location + ", district="
				+ district + ", gender=" + gender + "]";
	}

	@Override
	public int compareTo(Martyr o) {
		int ageComparison = Integer.compare(this.age, o.age);

		// If ages are not equal, return the comparison result
		if (ageComparison != 0) {
			return ageComparison;
		}

		// If ages are equal, compare based on gender
		return this.gender.compareTo(o.gender);
	}

}
