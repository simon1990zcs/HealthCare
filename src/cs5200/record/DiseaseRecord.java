package cs5200.record;

import java.sql.Date;

public class DiseaseRecord extends Records {
	public String disease;

	public DiseaseRecord(int id, Date date, int patientId, int doctorId, String disease) {
		super(id, date, patientId, doctorId);
		this.disease = disease;
	}

	public DiseaseRecord() {
		super();
	}
	
}
