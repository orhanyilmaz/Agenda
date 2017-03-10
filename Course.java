import java.util.ArrayList;
import java.util.Calendar;

public class Course {
	private Calendar date;
	private int id;
	private String name;
	private int quota;
	private int duration;
	ArrayList<Integer> persons = new ArrayList<Integer>();
	private int workingTime = -1;

	Course(int id, Calendar date, String name, int quota, int workingTime) {
		this.id = id;
		this.date = date;
		this.name = name;
		this.quota = quota;
		duration = 2;
		this.workingTime = workingTime;
	}

	public void setPersons(ArrayList<Integer> ids, int number) {
		for (int i = 0; i < number; i++) {
			persons.add(ids.get(i));
		}
	}

	public ArrayList<Integer> getPersons() {
		return persons;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(int workingTime) {
		this.workingTime = workingTime;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

}
