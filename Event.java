import java.util.ArrayList;
import java.util.Calendar;

public interface Event {

	Calendar getDate();

	public void setPersons(ArrayList<Integer> ids, int number);

	public ArrayList<Integer> getPersons();

	void setDate(Calendar Date);

	public int getWorkingTime();

	public void setWorkingTime(int workingTime);

	public int getDuration();

	public void setDuration(int duration);

	public int getId();

	public void setId(int id);

	public String getName();

	public void setName(String name);

	public int getQuota();

	public void setQuota(int quota);

}
