import java.util.Calendar;

public class Birthday {
	private Calendar date;
	private int id;
	private String description;
	
	Birthday(int id, Calendar date, String description) {
		this.id = id;
		this.date = date;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
