package cs5200.doctor;

import java.sql.Date;

import cs5200.BasicInfo;
import cs5200.Enum.Gender;
import cs5200.Enum.Specialties;

public class Doctor extends BasicInfo{
	public Date startDate;
	public Specialties specialties;
	public int belongs;
	public String hospitalName; // this info based on belongs
	
	public Doctor(int id, String name, int age, String ssn, Gender gender, String driverLisense, String phoneNumber, String address,
			String password, Date startDate, Specialties specialties, int belongs) {
		super(id, name, age, ssn, gender, driverLisense, phoneNumber, address, password);
		this.startDate = startDate;
		this.specialties = specialties;
		this.belongs = belongs;
	}
	public Doctor() {
		super();
	}
	
	
}
