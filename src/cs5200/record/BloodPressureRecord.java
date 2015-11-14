package cs5200.record;

import java.sql.Date;

public class BloodPressureRecord extends Records{
	public double bloodPressure;

	public BloodPressureRecord(int id, Date date, int patientId, int doctorId, double bloodPressure) {
		super(id, date, patientId, doctorId);
		this.bloodPressure = bloodPressure;
	}

	public BloodPressureRecord() {
		super();
	}
	
}
