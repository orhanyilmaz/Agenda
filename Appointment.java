import java.util.Calendar;

public class Appointment {
	private Calendar date;
	private int id;
	private int person1;
	private int person2;

	Appointment(int id, Calendar date, int id1, int id2) {
		this.id = id;
		this.date = date;
		this.person1 = id1;
		this.person2 = id2;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPerson1() {
		return person1;
	}

	public void setPerson1(int person1) {
		this.person1 = person1;
	}

	public int getPerson2() {
		return person2;
	}

	public void setPerson2(int person2) {
		this.person2 = person2;
	}

}
