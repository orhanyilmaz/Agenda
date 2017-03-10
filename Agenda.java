import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public interface Agenda {

	void saveAnniversary(int id, Calendar date, String description);

	void saveBirthday(int id, Calendar date, String description);

	void arrangeAppointment(int id, Calendar date, int user1, int user2, Date endDate);

	void arrangeMeeting(int id, Calendar date, ArrayList<Integer> ids, Date endate);

	void attendCourse(int id, Calendar date, Date endate);

	void attendEvent(int id, Calendar date, Date endate);

	void cancelMeeting(int id);

	void cancelAppointment(int id);

	void cancelCourse(int id);

	void cancelEvent(int id);

}
