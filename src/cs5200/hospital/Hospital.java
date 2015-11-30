package cs5200.hospital;

import java.util.List;

public class Hospital {
	public int id;
	public String name;
	public String address;
	public String phoneNumber;
	public String Introduction;
	public String password;
	
	private List<Comment> comments;
	
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Hospital(int id, String name, String address, String phoneNumber, String introduction, String password) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		Introduction = introduction;
		this.password = password;
	}
	public Hospital() {
		super();
	}
}
