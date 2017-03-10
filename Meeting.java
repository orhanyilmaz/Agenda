import java.util.ArrayList;
import java.util.Calendar;

public class Meeting {
	private Calendar date;
	private int id;
	ArrayList<Integer> persons = new ArrayList<Integer>();

	Meeting(int id, Calendar date, ArrayList<Integer> ids) {
		this.id = id;
		this.date = date;
		for (int i = 0; i < ids.size(); i++) {
			persons.add(ids.get(i));
		}
	}

	public ArrayList<Integer> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<Integer> persons) {
		this.persons = persons;
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
}
