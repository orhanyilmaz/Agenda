import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User implements Agenda {
	private int id;
	private String fullname;
	public ArrayList<Date> start = new ArrayList<Date>();
	public ArrayList<Date> end = new ArrayList<Date>();
	public ArrayList<Integer> registryId = new ArrayList<Integer>();
	public ArrayList<Anniversary> anniversary = new ArrayList<Anniversary>();
	public ArrayList<Birthday> birthday = new ArrayList<Birthday>();
	public ArrayList<Meeting> meeting = new ArrayList<Meeting>();
	public ArrayList<Course> course = new ArrayList<Course>();
	public ArrayList<Appointment> appointment = new ArrayList<Appointment>();
	public ArrayList<Theater> theater = new ArrayList<Theater>();
	public ArrayList<Concert> concert = new ArrayList<Concert>();
	public ArrayList<Sport> sport = new ArrayList<Sport>();

	User(int id, String name) {
		this.id = id;
		fullname = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void saveAnniversary(int id, Calendar date, String description) {
		anniversary.add(new Anniversary(id, date, description));

	}

	public void saveBirthday(int id, Calendar date, String description) {
		birthday.add(new Birthday(id, date, description));
	}

	public void arrangeAppointment(int id, Calendar date, int user1, int user2, Date endate) {
		appointment.add(new Appointment(id, date, user1, user2));
		start.add(date.getTime());
		end.add(endate);
		registryId.add(id);
	}

	public void arrangeMeeting(int id, Calendar date, ArrayList<Integer> ids, Date endate) {
		meeting.add(new Meeting(id, date, ids));
		start.add(date.getTime());
		end.add(endate);
		registryId.add(id);
	}

	public void attendCourse(int id, Calendar date, Date endate) {
		Date temp;
		for (int i = 0; i < 100; i++) {
			date.add(Calendar.DATE, i * 7);
			date.add(Calendar.HOUR_OF_DAY, 2);
			endate = date.getTime();
			date.add(Calendar.HOUR_OF_DAY, -2);
			temp = date.getTime();
			start.add(temp);
			end.add(endate);
			registryId.add(id);
			date.add(Calendar.DATE, -i * 7);
		}
	}

	public void attendEvent(int id, Calendar date, Date endate) {
		start.add(date.getTime());
		end.add(endate);
		registryId.add(id);
	}

	public void cancelMeeting(int id) {
		for (int i = 0; i < registryId.size(); i++) {
			if (registryId.get(i) == id) {
				start.remove(i);
				end.remove(i);
				registryId.remove(i);
				break;
			}
		}

	}

	public void cancelAppointment(int id) {
		for (int i = 0; i < registryId.size(); i++) {
			if (registryId.get(i) == id) {
				start.remove(i);
				end.remove(i);
				registryId.remove(i);
				break;
			}
		}
	}

	public void cancelCourse(int id) {
		for (int i = 0; i < registryId.size(); i++) {
			if (registryId.get(i) == id) {
				start.remove(i);
				end.remove(i);
				registryId.remove(i);
				i = 0;
			}
		}
		if (registryId.get(0) == id) {
			start.remove(0);
			end.remove(0);
			registryId.remove(0);
		}
	}

	public void cancelEvent(int id) {
		for (int i = 0; i < registryId.size(); i++) {
			if (registryId.get(i) == id) {
				start.remove(i);
				end.remove(i);
				registryId.remove(i);
				break;
			}
		}
	}

}
