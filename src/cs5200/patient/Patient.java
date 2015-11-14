package cs5200.patient;

import cs5200.BasicInfo;
import cs5200.Enum.BloodType;
import cs5200.Enum.Gender;

public class Patient extends BasicInfo {
	public String emergencyNumber;
	public BloodType bloodType;
	public Patient() {
		super();
	}
	public Patient(int id, String name, int age, String ssn, Gender gender, String driverLisense, String phoneNumber, String address,
			String password, String emergencyNumber, BloodType bloodType) {
		super(id, name, age, ssn, gender, driverLisense, phoneNumber, address, password);
		this.emergencyNumber = emergencyNumber;
		this.bloodType = bloodType;
	}
	
}
