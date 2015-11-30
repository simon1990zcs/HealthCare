package cs5200.hospital;

import java.sql.Date;

public class Comment {
	public int hospitalId;
	public int patientId;
	public String patientName; // fetch from id;
	public String comment;
	public Date date;
	public Comment(int hospitalId, int patientId, String comment,Date date) {
		super();
		this.hospitalId = hospitalId;
		this.patientId = patientId;
		this.comment = comment;
		this.date=date;
	}
	
}
