package cs5200;

import cs5200.Enum.Gender;

public class BasicInfo {

	public int id;
	public String name;
	public int age;
	public String ssn;
	public Gender gender;
	public String driverLisense;
	public String phoneNumber;
	public String address;
	public String password;
	public BasicInfo() {
		super();
	}
	public BasicInfo(int id, String name, int age, String ssn, Gender gender, String driverLisense, String phoneNumber, String address,
			String password) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.ssn = ssn;
		this.gender = gender;
		this.driverLisense = driverLisense;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.password = password;
	}
	
	
}
