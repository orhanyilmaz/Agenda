import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReadAndWrite {

	ArrayList<User> user = new ArrayList<User>();
	ArrayList<Course> course = new ArrayList<Course>();
	ArrayList<Appointment> appointment = new ArrayList<Appointment>();
	ArrayList<Integer> idCount = new ArrayList<Integer>();
	ArrayList<Concert> concert = new ArrayList<Concert>();
	ArrayList<Sport> sport = new ArrayList<Sport>();
	ArrayList<Theater> theater = new ArrayList<Theater>();
	ArrayList<Meeting> meeting = new ArrayList<Meeting>();

	public void readInWriteOut(String pathIn, String pathOut) {
		try {
			File file = new File(pathOut);
			FileWriter writer = new FileWriter(file);
			BufferedWriter write = new BufferedWriter(writer);
			BufferedReader reader = new BufferedReader(new FileReader(pathIn));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.length() > 0) {
					String[] word = line.split(" ");
					if (word[0].equals("SAVE")) {
						if (word[1].equals("USER")) {
							String name = word[3];
							for (int i = 4; i < word.length; i++) {
								name = name + " " + word[i];
							}
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + name);
							try {
								for (int i = 0; i < user.size(); i++) { // count for all saved users
									if (user.get(i).getId() == Integer.parseInt(word[2])) {
										throw new SameIdException(); // if same user want to save
									}
								}
								user.add(new User(Integer.parseInt(word[2]), name)); // save new user
								write.newLine();
								write.write(user.get(user.size() - 1).getFullname() + " SAVED");
								write.newLine();
							} catch (SameIdException e) {
								write.newLine();
								write.write("DUPLICATED USER ID: " + word[2] + " ALREADY EXIST");
								write.newLine();
							}
						} else if (word[1].equals("ANNIVERSARY")) {
							String description = word[5];
							for (int i = 6; i < word.length; i++) {
								description = description + " " + word[i];
							}
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + description);
							write.newLine();
							int checkId = 0;
							int id = -1;
							try {
								for (int i = 0; i < user.size(); i++) {
									if (user.get(i).getId() == Integer.parseInt(word[3])) {
										checkId = 1;
										id = i;
									}
								}
								if (checkId == 0) {
									throw new UserNotFoundException(); // if there is no agenda owner
								}
								for (int i = 0; i < user.get(id).anniversary.size(); i++) { // count for all saved anniversaries
									if (user.get(id).anniversary.get(i).getId() == Integer.parseInt(word[4])) {
										throw new SameIdException(); // if same anniversary want to save
									}
								}
								Calendar date = Calendar.getInstance();
								SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
								try {
									date.setTime(format.parse(word[2]));
								} catch (ParseException ex) {
									ex.fillInStackTrace();
								}

								user.get(id).saveAnniversary(Integer.parseInt(word[4]), date, description); // saving new anniversary
								write.write(description + " ADDED TO " + user.get(id).getFullname() + "'S AGENDA");
								write.newLine();

							} catch (UserNotFoundException e) {
								write.write("USER NOT FOUND: " + word[3]);
								write.newLine();
							} catch (SameIdException e) {
								write.write("DUPLICATED ANNIVERSARY ID: " + word[3] + " ALREADY EXIST");
								write.newLine();
							}
						} else if (word[1].equals("BIRTHDAY")) {
							String description = word[5];
							for (int i = 6; i < word.length; i++) {
								description = description + " " + word[i];
							}
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + description);
							write.newLine();
							int checkId = 0;
							int id = -1;
							try {
								for (int i = 0; i < user.size(); i++) {
									if (user.get(i).getId() == Integer.parseInt(word[3])) {
										checkId = 1;
										id = i;
									}
								}
								if (checkId == 0) {
									throw new UserNotFoundException();
								}
								for (int i = 0; i < user.get(id).birthday.size(); i++) { // count for all saved birthdays
									if (user.get(id).birthday.get(i).getId() == Integer.parseInt(word[4])) {
										throw new SameIdException(); // check if want or not same birthday
									}
								}
								Calendar date = Calendar.getInstance();
								SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
								try {
									date.setTime(format.parse(word[2]));
								} catch (ParseException ex) {
									ex.fillInStackTrace();
								}

								user.get(id).saveBirthday(Integer.parseInt(word[4]), date, description); // saving new birthday
								write.write(description + " ADDED TO " + user.get(id).getFullname() + "'S AGENDA");
								write.newLine();

							} catch (UserNotFoundException e) {
								write.write("USER NOT FOUND: " + word[3]);
								write.newLine();
							} catch (SameIdException e) {
								write.write("DUPLICATED BIRTHDAY ID: " + word[4] + " ALREADY EXIST");
								write.newLine();
							}
						}
					} else if (word[0].equals("ARRANGE")) {
						if (word[1].equals("APPOINTMENT")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + word[5] + " " + word[6]);
							write.newLine();
							int checkId1 = 0;
							int checkId2 = 0;
							int id1 = -1;
							int id2 = -1;
							int userId = -1;
							String[] word2 = word[5].split(","); // split for all users

							String s = word[2] + " " + word[3];
							Calendar date = Calendar.getInstance();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							try {
								date.setTime(format.parse(s));
							} catch (ParseException ex) {
								ex.fillInStackTrace();
							}

							Date end;
							int duration = Integer.parseInt(word[4]);
							int hour = duration / 60;
							int minute = duration - (hour * 60);
							date.add(Calendar.HOUR_OF_DAY, hour);
							date.add(Calendar.MINUTE, minute);
							end = date.getTime();
							date.add(Calendar.HOUR_OF_DAY, -hour);
							date.add(Calendar.MINUTE, -minute);

							try {
								for (int i = 0; i < user.size(); i++) { // count for all users
									if (user.get(i).getId() == Integer.parseInt(word2[0])) { // if first user found
										checkId1 = 1;
										id1 = i;
									}
								}
								for (int i = 0; i < user.size(); i++) { // count for all users
									if (user.get(i).getId() == Integer.parseInt(word2[1])) { // if second user found
										checkId2 = 1;
										id2 = i;
									}
								}
								if (checkId1 == 0) {
									userId = Integer.parseInt(word2[0]);
									throw new UserNotFoundException();
								}
								if (checkId2 == 0) {
									userId = Integer.parseInt(word2[1]);
									throw new UserNotFoundException();
								}
								idCount.add(id1);
								idCount.add(id2);
								for (int j = 0; j < word2.length; j++) {
									for (int i = 0; i < user.get(idCount.get(j)).start.size(); i++) {
										if (!(user.get(idCount.get(j)).start.get(i)).after(end)
												&& !user.get(idCount.get(j)).end.get(i).before(date.getTime())) { // if user is incomptiable in appointment time
											if (!user.get(idCount.get(j)).start.get(i).equals(end)
													&& !user.get(idCount.get(j)).end.get(i).equals(date.getTime())) {
												userId = user.get(idCount.get(j)).getId();
												idCount.removeAll(idCount);
												throw new IncompatibleUserException(); // throw exception
											}
										}
									}
								}
								idCount.removeAll(idCount);
								for (int i = 0; i < appointment.size(); i++) { // count for all appointments
									if (appointment.get(i).getId() == Integer.parseInt(word[6])) { // check if want or not same appointment
										throw new SameIdException();
									}
								}
								appointment.add(new Appointment(Integer.parseInt(word[6]), date, user.get(id1).getId(), // add new appointment
										user.get(id2).getId()));
								user.get(id1).arrangeAppointment(Integer.parseInt(word[6]), date, user.get(id1).getId(), // add new appointment to user1
										user.get(id2).getId(), end);
								user.get(id2).arrangeAppointment(Integer.parseInt(word[6]), date, user.get(id2).getId(), // add new appointment to user2
										user.get(id1).getId(), end);
								write.write("AN APPOINTMENT ARRANGED FOR " + user.get(id1).getId() + ","
										+ user.get(id2).getId() + " ON " + s);
								write.newLine();

							} catch (UserNotFoundException e) {
								write.write("USER NOT FOUND: " + userId);
								write.newLine();
							} catch (IncompatibleUserException e) {
								write.write("INCOMPATIBLE USER: " + userId + "’S AGENDA IS NOT COMPATIBLE");
								write.newLine();
							} catch (SameIdException e) {
								write.write("DUPLICATED APPOINTMENT ID: " + word[6] + " ALREADY EXIST");
								write.newLine();
							}
						} else if (word[1].equals("COURSE")) {
							String description = word[6];
							for (int i = 7; i < word.length; i++) {
								description = description + " " + word[i];
							}
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + word[5] + " " + description);
							write.newLine();
							String s = word[3] + " " + word[4];
							Calendar date = Calendar.getInstance();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							try {
								date.setTime(format.parse(s));
							} catch (ParseException ex) {
								ex.fillInStackTrace();
							}

							int hourOfMeeting = date.get(Calendar.HOUR_OF_DAY); // i try to convert calendar type to day ,hour,minute
							int minuteOfMeeting = date.get(Calendar.MINUTE);
							int timeAsMinutes = hourOfMeeting * 60;
							timeAsMinutes = timeAsMinutes + minuteOfMeeting; // and i wrote the course time as minutes
							int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
							int checkWorkHour = -1;
							if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
								checkWorkHour = 1;
							}
							if (checkWorkHour == -1) { // 9.00 equal as minute 540 , 18.00 equal as minute 1080
								if (timeAsMinutes > 540 && timeAsMinutes < 1080) { // so i compared time . and checked if is in working time or not ..
								} else {
									checkWorkHour = 1; // and i sent this information to new course object.
								} // not throwing exception because i throw exception while course ATTEND..
							}
							try {
								for (int i = 0; i < course.size(); i++) { // count for all courses
									if (course.get(i).getId() == Integer.parseInt(word[5])) { // check if want or not same course
										throw new SameIdException();
									}
								}
								DateFormat df = new SimpleDateFormat("EEEE", Locale.ENGLISH);
								Date day = date.getTime();
								course.add(new Course(Integer.parseInt(word[5]), date, description, // add new course with in working hour or not information
										Integer.parseInt(word[2]), checkWorkHour));
								write.write(
										description + " ARRANGED ON " + df.format(day).toUpperCase() + " " + word[4]);
								write.newLine();

							} catch (SameIdException e) {
								write.write("DUPLICATED COURSE ID: " + word[5] + " ALREADY EXIST");
								write.newLine();
							}
						} else if (word[1].equals("MEETING")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + word[5] + " " + word[6]);
							write.newLine();
							String[] word2 = word[5].split(",");
							int userId = -1;
							ArrayList<Integer> checkId = new ArrayList<Integer>();
							ArrayList<Integer> index = new ArrayList<Integer>();
							String s = word[2] + " " + word[3];
							Calendar date = Calendar.getInstance();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							try {
								date.setTime(format.parse(s));
							} catch (ParseException ex) {
								ex.fillInStackTrace();
							}
							Date end;
							int duration = Integer.parseInt(word[4]); // in there i tried to add duration to starting time and have ending time
							int hour = duration / 60;
							int minute = duration - (hour * 60);
							date.add(Calendar.HOUR_OF_DAY, hour);
							date.add(Calendar.MINUTE, minute);
							end = date.getTime();
							date.add(Calendar.HOUR_OF_DAY, -hour);
							date.add(Calendar.MINUTE, -minute);

							int hourOfMeeting = date.get(Calendar.HOUR_OF_DAY); // again try to convert calendar type to day ,hour ,minute
							int minuteOfMeeting = date.get(Calendar.MINUTE);
							int timeAsMinutes = hourOfMeeting * 60;
							timeAsMinutes = timeAsMinutes + minuteOfMeeting; // and i wrote the meeting time as minutes
							int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
							int checkWorkHour = 0;
							if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
								checkWorkHour = -1;
							}
							if (checkWorkHour == 0) {
								if (timeAsMinutes > 540 && timeAsMinutes < 1080) {
								} else {
									checkWorkHour = -1;
								}
							}
							try {
								if (checkWorkHour == -1) { // and i use working time information in there and throw exception..
									throw new NotWorkHoursException();
								}
								for (int j = 0; j < word2.length; j++) {
									for (int i = 0; i < user.size(); i++) {
										if (user.get(i).getId() == Integer.parseInt(word2[j])) {
											checkId.add(j); // i assign all ids in checkId variable
											index.add(i);
										}
									}
								}
								for (int i = 0; i < word2.length; i++) {
									if (!checkId.contains(i)) { // then if this id is not in user's array (word2)
										userId = Integer.parseInt(word2[i]); // then i throw this user as exception (userId)
										throw new UserNotFoundException(); // in this exception i write to ouyput userId varible
									}
								}

								for (int j = 0; j < word2.length; j++) {
									for (int i = 0; i < user.get(index.get(j)).start.size(); i++) {
										if (!(user.get(index.get(j)).start.get(i)).after(end) // check if users incompatible in meeting time
												&& !user.get(index.get(j)).end.get(i).before(date.getTime())) {
											if (!user.get(index.get(j)).start.get(i).equals(end)
													&& !user.get(index.get(j)).end.get(i).equals(date.getTime())) {
												userId = user.get(index.get(j)).getId();
												throw new IncompatibleUserException(); // throw exception
											}
										}
									}
								}
								for (int i = 0; i < meeting.size(); i++) { // count for all meetings
									if (meeting.get(i).getId() == Integer.parseInt(word[6])) { // check if want add same meeting
										throw new SameIdException();
									}
								}
								ArrayList<Integer> id = new ArrayList<Integer>(); // meeting users are not fixed size and array usage is forbbiden so i create an ArrayList for users 
								for (int i = 0; i < index.size(); i++) {
									id.add(user.get(index.get(i)).getId());
								}
								meeting.add(new Meeting(Integer.parseInt(word[6]), date, id)); // add new meeting

								for (int i = 0; i < index.size(); i++) {
									user.get(index.get(i)).arrangeMeeting(Integer.parseInt(word[6]), date, id, end); // add new meeting to allattendance..

								}
								write.write("A MEETING ARRANGED FOR " + word[5] + " ON " + s);
								write.newLine();

							} catch (UserNotFoundException e) {
								write.write("USER NOT FOUND: " + userId);
								write.newLine();
							} catch (IncompatibleUserException e) {
								write.write("INCOMPATIBLE USER: " + userId + "’S AGENDA IS NOT COMPATIBLE");
								write.newLine();
							} catch (NotWorkHoursException e) {
								write.write("INCOMPATIBLE USER: " + word2[0] + "’S AGENDA IS NOT COMPATIBLE");
								write.newLine();
							} catch (SameIdException e) {
								write.write("DUPLICATED MEETING ID: " + word[6] + " ALREADY EXIST");
								write.newLine();
							}
						} else if (word[1].equals("CONCERT")) { 
							String description = word[6];
							for (int i = 7; i < word.length; i++) {
								description = description + " " + word[i];
							}
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + word[5] + " " + description);
							write.newLine();
							String s = word[3] + " " + word[4];
							Calendar date = Calendar.getInstance();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							try {
								date.setTime(format.parse(s));
							} catch (ParseException ex) {
								ex.fillInStackTrace();
							}
							int hourOfMeeting = date.get(Calendar.HOUR_OF_DAY);
							int minuteOfMeeting = date.get(Calendar.MINUTE);
							int timeAsMinutes = hourOfMeeting * 60;
							timeAsMinutes = timeAsMinutes + minuteOfMeeting;
							int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
							int checkWorkHour = -1;
							if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
								checkWorkHour = 1;
							}
							if (checkWorkHour == -1) {
								if (timeAsMinutes > 540 && timeAsMinutes < 1080) {
								} else {
									checkWorkHour = 1;
								}
							}
							try {
								for (int i = 0; i < concert.size(); i++) {
									if (concert.get(i).getId() == Integer.parseInt(word[5])) {
										throw new SameIdException();
									}
								}
								concert.add(new Concert(Integer.parseInt(word[5]), date, description,
										Integer.parseInt(word[2]), checkWorkHour));
								write.write(description + " ARRANGED ON " + word[3] + " " + word[4]);
								write.newLine();

							} catch (SameIdException e) {
								write.write("DUPLICATED EVENT ID: " + word[5] + " ALREADY EXIST");
								write.newLine();
							}
						} else if (word[1].equals("SPORT")) {
							String description = word[6];
							for (int i = 7; i < word.length; i++) {
								description = description + " " + word[i];
							}
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + word[5] + " " + description);
							write.newLine();
							String s = word[3] + " " + word[4];
							Calendar date = Calendar.getInstance();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							try {
								date.setTime(format.parse(s));
							} catch (ParseException ex) {
								ex.fillInStackTrace();
							}
							int hourOfMeeting = date.get(Calendar.HOUR_OF_DAY);
							int minuteOfMeeting = date.get(Calendar.MINUTE);
							int timeAsMinutes = hourOfMeeting * 60;
							timeAsMinutes = timeAsMinutes + minuteOfMeeting;
							int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
							int checkWorkHour = -1;
							if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
								checkWorkHour = 1;
							}
							if (checkWorkHour == -1) {
								if (timeAsMinutes > 540 && timeAsMinutes < 1080) {
								} else {
									checkWorkHour = 1;
								}
							}
							try {
								for (int i = 0; i < sport.size(); i++) {
									if (sport.get(i).getId() == Integer.parseInt(word[5])) {
										throw new SameIdException();
									}
								}
								sport.add(new Sport(Integer.parseInt(word[5]), date, description,
										Integer.parseInt(word[2]), checkWorkHour));
								write.write(description + " ARRANGED ON " + word[3] + " " + word[4]);
								write.newLine();

							} catch (SameIdException e) {
								write.write("DUPLICATED EVENT ID: " + word[5] + " ALREADY EXIST");
								write.newLine();
							}
						} else if (word[1].equals("THEATER")) {
							String description = word[6];
							for (int i = 7; i < word.length; i++) {
								description = description + " " + word[i];
							}
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3] + " "
									+ word[4] + " " + word[5] + " " + description);
							write.newLine();
							String s = word[3] + " " + word[4];
							Calendar date = Calendar.getInstance();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							try {
								date.setTime(format.parse(s));
							} catch (ParseException ex) {
								ex.fillInStackTrace();
							}
							int hourOfMeeting = date.get(Calendar.HOUR_OF_DAY);
							int minuteOfMeeting = date.get(Calendar.MINUTE);
							int timeAsMinutes = hourOfMeeting * 60;
							timeAsMinutes = timeAsMinutes + minuteOfMeeting;
							int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
							int checkWorkHour = -1;
							if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
								checkWorkHour = 1;
							}
							if (checkWorkHour == -1) {
								if (timeAsMinutes > 540 && timeAsMinutes < 1080) {
								} else {
									checkWorkHour = 1;
								}
							}
							try {
								for (int i = 0; i < theater.size(); i++) {
									if (theater.get(i).getId() == Integer.parseInt(word[5])) {
										throw new SameIdException();
									}
								}
								theater.add(new Theater(Integer.parseInt(word[5]), date, description,
										Integer.parseInt(word[2]), checkWorkHour));
								write.write(description + " ARRANGED ON " + word[3] + " " + word[4]);
								write.newLine();

							} catch (SameIdException e) {
								write.write("DUPLICATED EVENT ID: " + word[5] + " ALREADY EXIST");
								write.newLine();
							}
						}
					} else if (word[0].equals("ATTEND")) {
						if (word[1].equals("EVENT")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3]);
							write.newLine();
							String[] word2 = word[3].split(",");
							ArrayList<Integer> unavailableUsers = new ArrayList<Integer>();
							Calendar eventDate = Calendar.getInstance();
							int eventId = -1;
							String eventName = null;
							int eventQuota = -1;
							int eventDuration = -1;
							int eventWorkingTime = -1;
							Date eventEnd = new Date();
							String type = null;
							int workHour = 0;
							int info = -1;
							ArrayList<Integer> checkId = new ArrayList<Integer>();
							ArrayList<Integer> index = new ArrayList<Integer>();
							try {
								for (int i = 0; i < sport.size(); i++) {
									if (sport.get(i).getId() == Integer.parseInt(word[2])) {
										eventId = sport.get(i).getId();
										eventName = sport.get(i).getName();
										eventDate = sport.get(i).getDate();
										eventQuota = sport.get(i).getQuota();
										eventDuration = sport.get(i).getDuration();
										eventWorkingTime = sport.get(i).getWorkingTime();
										eventDate.add(Calendar.HOUR_OF_DAY, eventDuration);
										eventEnd = eventDate.getTime();
										eventDate.add(Calendar.HOUR_OF_DAY, -eventDuration);
										type = "sport";
										info = i;
										workHour = sport.get(i).getWorkingTime();
										break;
									}
								}
								for (int i = 0; i < concert.size(); i++) {
									if (concert.get(i).getId() == Integer.parseInt(word[2])) {
										eventId = concert.get(i).getId();
										eventName = concert.get(i).getName();
										eventDate = concert.get(i).getDate();
										eventQuota = concert.get(i).getQuota();
										eventDuration = concert.get(i).getDuration();
										eventWorkingTime = concert.get(i).getWorkingTime();
										eventDate.add(Calendar.HOUR_OF_DAY, eventDuration);
										eventEnd = eventDate.getTime();
										eventDate.add(Calendar.HOUR_OF_DAY, -eventDuration);
										type = "concert";
										info = i;
										workHour = concert.get(i).getWorkingTime();
										break;
									}
								}
								for (int i = 0; i < theater.size(); i++) {
									if (theater.get(i).getId() == Integer.parseInt(word[2])) {
										eventId = theater.get(i).getId();
										eventName = theater.get(i).getName();
										eventDate = theater.get(i).getDate();
										eventQuota = theater.get(i).getQuota();
										eventDuration = theater.get(i).getDuration();
										eventWorkingTime = theater.get(i).getWorkingTime();
										eventDate.add(Calendar.HOUR_OF_DAY, eventDuration);
										eventEnd = eventDate.getTime();
										eventDate.add(Calendar.HOUR_OF_DAY, -eventDuration);
										type = "theater";
										info = i;
										workHour = theater.get(i).getWorkingTime();
										break;
									}
								}
								if (eventId == -1) {
									throw new NotFoundException();
								}
								if (workHour == -1) {
									throw new InWorkHoursException();
								}

								for (int j = 0; j < word2.length; j++) {
									for (int i = 0; i < user.size(); i++) {
										if (user.get(i).getId() == Integer.parseInt(word2[j])) {
											checkId.add(j);
											index.add(i);
										}
									}
								}
								int check = 0;
								for (int i = 0; i < word2.length; i++) {
									if (!checkId.contains(i)) {
										write.write("USER NOT FOUND: " + word2[i]);
										write.newLine();
										word2[i] = null;
										check++;
									}
								}
								ArrayList<Integer> listOfUsers = new ArrayList<Integer>();
								for (int i = 0; i < word2.length; i++) {
									if (word2[i] == null) {
										for (int j = i; j < word2.length; j++) {
											if (word2[j] != null) {
												word2[i] = word2[j];
												word2[j] = null;
												break;
											}
										}
									}
								}
								for (int i = 0; i < word2.length - check; i++) {
									listOfUsers.add(Integer.parseInt(word2[i]));
								}

								for (int j = 0; j < index.size(); j++) {
									for (int i = 0; i < user.get(index.get(j)).start.size(); i++) {
										if (!(user.get(index.get(j)).start.get(i)).after(eventEnd)
												&& !user.get(index.get(j)).end.get(i).before(eventDate.getTime())) {
											if (!user.get(index.get(j)).start.get(i).equals(eventEnd)
													&& !user.get(index.get(j)).end.get(i).equals(eventDate.getTime())) {
												if (unavailableUsers.contains(user.get(index.get(j)).getId())) {
													continue;
												} else {
													unavailableUsers.add(user.get(index.get(j)).getId());
												}
											}
										}
									}
								}
								for (int i = 0; i < unavailableUsers.size(); i++) {
									write.write("UNAVAILABLE USER: " + unavailableUsers.get(i));
									write.newLine();
								}
								for (int i = 0; i < listOfUsers.size(); i++) {
									for (int j = i + 1; j < listOfUsers.size(); j++) {
										int temp1 = listOfUsers.get(i);
										int temp2 = listOfUsers.get(j);
										if (temp1 == temp2) {

											write.write("UNAVAILABLE USER: " + listOfUsers.get(i));
											write.newLine();
											listOfUsers.remove(i);
											index.remove(i);
										}
									}
								}
								for (int i = 0; i < listOfUsers.size(); i++) {
									for (int j = 0; j < unavailableUsers.size(); j++) {
										int temp1 = listOfUsers.get(i);
										int temp2 = unavailableUsers.get(j);
										if (temp1 == temp2) {
											listOfUsers.remove(i);
											index.remove(i);
										}
									}
								}

								int number = listOfUsers.size();
								if (eventQuota < number) {
									for (int i = eventQuota; i < number; i++) {
										write.write("QUOTA FULL: " + listOfUsers.get(eventQuota));
										write.newLine();
										listOfUsers.remove(eventQuota);
										index.remove(eventQuota);
									}
								}

								write.write(listOfUsers.size() + " USERS ADDED TO ATTEDANCE LIST OF " + eventName);
								write.newLine();

								for (int i = 0; i < listOfUsers.size(); i++) {
									user.get(index.get(i)).attendEvent(eventId, eventDate, eventEnd);
								}
								if (type == "sport") {
									sport.get(info).setPersons(index, listOfUsers.size());
									for (int i = 0; i < index.size(); i++) {
										user.get(index.get(i)).sport.add(
												new Sport(eventId, eventDate, eventName, eventQuota, eventWorkingTime));
									}
								} else if (type == "concert") {
									concert.get(info).setPersons(index, listOfUsers.size());
									for (int i = 0; i < index.size(); i++) {
										user.get(index.get(i)).concert.add(new Concert(eventId, eventDate, eventName,
												eventQuota, eventWorkingTime));
									}
								} else if (type == "theater") {
									theater.get(info).setPersons(index, listOfUsers.size());
									for (int i = 0; i < index.size(); i++) {
										user.get(index.get(i)).theater.add(new Theater(eventId, eventDate, eventName,
												eventQuota, eventWorkingTime));
									}
								}

							} catch (NotFoundException e) {
								write.write("EVENT NOT FOUND: " + word[2]);
								write.newLine();
							} catch (InWorkHoursException e) {
								for (int i = 0; i < word2.length; i++) {
									write.write("UNAVAILABLE USER: " + word2[i]);
									write.newLine();
								}
								write.write("0 USERS ADDED TO ATTEDANCE LIST OF " + eventName);
								write.newLine();
							}
						} else if (word[1].equals("COURSE")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3]);
							write.newLine();
							String[] word2 = word[3].split(",");
							ArrayList<Integer> unavailableUsers = new ArrayList<Integer>();
							Calendar date = Calendar.getInstance();
							int id = -1;
							String name = null;
							int quota = -1;
							int workHour = 0;
							Date end = new Date();
							int info = -1;
							ArrayList<Integer> checkId = new ArrayList<Integer>();
							ArrayList<Integer> index = new ArrayList<Integer>();
							try {
								for (int i = 0; i < course.size(); i++) {
									if (course.get(i).getId() == Integer.parseInt(word[2])) {
										id = course.get(i).getId();
										name = course.get(i).getName();
										date = course.get(i).getDate();
										quota = course.get(i).getQuota();
										date.add(Calendar.HOUR_OF_DAY, 2);
										end = date.getTime();
										date.add(Calendar.HOUR_OF_DAY, -2);
										info = i;
										workHour = course.get(i).getWorkingTime();
										break;
									}
								}
								if (id == -1) {
									throw new NotFoundException();
								}
								if (workHour == -1) {
									throw new InWorkHoursException();
								}
								for (int j = 0; j < word2.length; j++) {
									for (int i = 0; i < user.size(); i++) {
										if (user.get(i).getId() == Integer.parseInt(word2[j])) {
											checkId.add(j);
											index.add(i);
										}
									}
								}
								int check = 0;
								for (int i = 0; i < word2.length; i++) {
									if (!checkId.contains(i)) {
										write.write("USER NOT FOUND: " + word2[i]);
										write.newLine();
										word2[i] = null;
										check++;
									}
								}
								ArrayList<Integer> listOfUsers = new ArrayList<Integer>();
								for (int i = 0; i < word2.length; i++) {
									if (word2[i] == null) {
										for (int j = i; j < word2.length; j++) {
											if (word2[j] != null) {
												word2[i] = word2[j];
												word2[j] = null;
												break;
											}
										}
									}
								}
								for (int i = 0; i < word2.length - check; i++) {
									listOfUsers.add(Integer.parseInt(word2[i]));
								}
								Date courseDate;
								Date courseEnd;
								for (int j = 0; j < index.size(); j++) {
									for (int i = 0; i < user.get(index.get(j)).start.size(); i++) {
										for (int k = 0; k < 200; k++) {
											date.add(Calendar.DATE, k * 7);
											date.add(Calendar.HOUR_OF_DAY, 2);
											courseEnd = date.getTime();
											date.add(Calendar.HOUR_OF_DAY, -2);
											courseDate = date.getTime();
											date.add(Calendar.DATE, -k * 7);
											if (!(user.get(index.get(j)).start.get(i)).after(courseEnd)
													&& !user.get(index.get(j)).end.get(i).before(courseDate)) {
												if (!user.get(index.get(j)).start.get(i).equals(courseEnd)
														&& !user.get(index.get(j)).end.get(i).equals(courseDate)) {
													if (unavailableUsers.contains(user.get(index.get(j)).getId())) {
														continue;
													} else {
														unavailableUsers.add(user.get(index.get(j)).getId());
													}
												}
											}
										}
									}
								}
								for (int i = 0; i < unavailableUsers.size(); i++) {
									write.write("UNAVAILABLE USER: " + unavailableUsers.get(i));
									write.newLine();
								}
								for (int i = 0; i < listOfUsers.size(); i++) {
									for (int j = i + 1; j < listOfUsers.size(); j++) {
										int temp1 = listOfUsers.get(i);
										int temp2 = listOfUsers.get(j);
										if (temp1 == temp2) {
											write.write("UNAVAILABLE USER: " + listOfUsers.get(i));
											write.newLine();
											listOfUsers.remove(i);
											index.remove(i);
										}
									}
								}
								for (int i = 0; i < listOfUsers.size(); i++) {
									for (int j = 0; j < unavailableUsers.size(); j++) {
										int temp1 = listOfUsers.get(i);
										int temp2 = unavailableUsers.get(j);
										if (temp1 == temp2) {
											listOfUsers.remove(i);
											index.remove(i);
										}
									}
								}
								int number = listOfUsers.size();
								if (quota < number) {
									for (int i = quota; i < number; i++) {
										write.write("QUOTA FULL: " + listOfUsers.get(quota));
										write.newLine();
										listOfUsers.remove(quota);
										index.remove(quota);
									}
								}
								write.write(listOfUsers.size() + " USERS ADDED TO ATTEDANCE LIST OF " + name);
								write.newLine();

								for (int i = 0; i < index.size(); i++) {
									user.get(index.get(i)).course.add(new Course(id, date, name, quota, workHour));
								}
								for (int i = 0; i < listOfUsers.size(); i++) {
									user.get(index.get(i)).attendCourse(id,date, end);
								}
								course.get(info).setPersons(index, listOfUsers.size());

							} catch (NotFoundException e) {
								write.write("COURSE NOT FOUND: " + word[2]);
								write.newLine();
							} catch (InWorkHoursException e) {
								for (int i = 0; i < word2.length; i++) {
									write.write("UNAVAILABLE USER: " + word2[i]);
									write.newLine();
								}
								write.write("0 USERS ADDED TO ATTEDANCE LIST OF " + name);
								write.newLine();
							}
						}
					} else if (word[0].equals("CANCEL")) {
						if (word[1].equals("APPOINTMENT")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2]);
							write.newLine();
							int checkApp = -1;
							int index = -1;
							int user1 = -1;
							int user2 = -1;
							for (int i = 0; i < appointment.size(); i++) { // count for all appointment 
								if (appointment.get(i).getId() == Integer.parseInt(word[2])) { // check if app is exist or not
									checkApp = 0;
									index = i;
									break;
								}
							}

							try {
								if (checkApp == -1) {
									throw new NotFoundException();
								}
								user1 = appointment.get(index).getPerson1();   // get info about appointment user1
								user2 = appointment.get(index).getPerson2(); // get info about appointment user1
								
								appointment.remove(index);		// remove this appointment

								for (int i = 0; i < user.size(); i++) {
									if (user.get(i).getId() == user1) {
										for (int j = 0; j < user.get(i).appointment.size(); j++) {
											if(user.get(i).appointment.get(j).getId()==Integer.parseInt(word[2])){
												user.get(i).appointment.remove(j);			// remove this appointment from user1
												user.get(i).cancelAppointment(Integer.parseInt(word[2]));
											}
										}
									}
								}
								for (int i = 0; i < user.size(); i++) {
									if (user.get(i).getId() == user2) {
										for (int j = 0; j < user.get(i).appointment.size(); j++) {
											if(user.get(i).appointment.get(j).getId()==Integer.parseInt(word[2])){
												user.get(i).appointment.remove(j);			// remove this appointment from user2
												user.get(i).cancelAppointment(Integer.parseInt(word[2]));												
											}
										}
									}
								}
								write.write("APPOINTMENT CANCELLED: " + word[2]);
								write.newLine();
							} catch (NotFoundException e) {
								write.write("APPOINTMENT NOT FOUND: " + word[2]);
								write.newLine();
							}

						} else if (word[1].equals("MEETING")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2]);
							write.newLine();
							int checkMeeting = -1;
							int index = -1;
							ArrayList<Integer> users = new ArrayList<Integer>();
							ArrayList<Integer> usersIndex = new ArrayList<Integer>();
							for (int i = 0; i < meeting.size(); i++) { // count for all meeting 
								if (meeting.get(i).getId() == Integer.parseInt(word[2])) { // check if meeting is exist or not
									checkMeeting = 0;
									index = i;
									break;
								}
							}
							try {
								if (checkMeeting == -1) {
									throw new NotFoundException();
								}
								users = meeting.get(index).getPersons(); // get users informatin from meeting ArrayList
								meeting.remove(index);
								for (int i = 0; i < users.size(); i++) {  // count for all users
									for (int j = 0; j < user.size(); j++) {
										if (users.get(i) == user.get(j).getId()) {
											usersIndex.add(j);    // getting info about users index
										}
									}
								}								
								for (int i = 0; i < users.size(); i++) { // count for all users
									for (int j = 0; j < user.get(usersIndex.get(i)).meeting.size(); j++) { // count for all user's meeting
										if (user.get(usersIndex.get(i)).meeting.get(j).getId() == Integer.parseInt(word[2])) { 
											user.get(usersIndex.get(i)).meeting.remove(j);   // remove meeting from user
											user.get(usersIndex.get(i)).cancelMeeting(Integer.parseInt(word[2]));
										}
									}
								}								
								write.write("MEETING CANCELLED: " + word[2]);
								write.newLine();
							} catch (NotFoundException e) {
								write.write("MEETING NOT FOUND: " + word[2]);
								write.newLine();
							}
						} else if (word[1].equals("EVENT")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2]);
							write.newLine();
							int checkEvent = -1;
							int index = -1;
							String type=null;
							ArrayList<Integer> usersIndex = new ArrayList<Integer>();
							
							for (int i = 0; i < sport.size(); i++) { // count for all sport 
								if (sport.get(i).getId() == Integer.parseInt(word[2])) { // check if sport is exist or not
									checkEvent = 0;
									index = i;
									type = "sport";
									break;
								}
							}
							for (int i = 0; i < concert.size(); i++) { // count for all concert 
								if (concert.get(i).getId() == Integer.parseInt(word[2])) { // check if concert is exist or not
									checkEvent = 0;
									index = i;
									type = "concert";
									break;
								}
							}
							for (int i = 0; i < theater.size(); i++) { // count for all theater 
								if (theater.get(i).getId() == Integer.parseInt(word[2])) { // check if theater is exist or not
									checkEvent = 0;
									index = i;
									type = "theater";
									break;
								}
							}
							try {
								if (checkEvent == -1) {
									throw new NotFoundException();
								}
								
								if (type == "sport") {
									usersIndex = sport.get(index).getPersons(); // get users informatin from sport ArrayList
									sport.remove(index);
									for (int i = 0; i < usersIndex.size(); i++) { // count for all users
										for (int j = 0; j < user.get(usersIndex.get(i)).sport.size(); j++) { // count for all user's sport
											if (user.get(usersIndex.get(i)).sport.get(j).getId() == Integer.parseInt(word[2])) { 											
												user.get(usersIndex.get(i)).sport.remove(j);   // remove sport from user
												user.get(usersIndex.get(i)).cancelEvent(Integer.parseInt(word[2]));
											}
										}
									}
								} else if (type == "concert") {
									usersIndex = concert.get(index).getPersons(); // get users informatin from concert ArrayList
									concert.remove(index);
									for (int i = 0; i < usersIndex.size(); i++) { // count for all users
										for (int j = 0; j < user.get(usersIndex.get(i)).concert.size(); j++) { // count for all user's concert
											if (user.get(usersIndex.get(i)).concert.get(j).getId() == Integer.parseInt(word[2])) { 											
												user.get(usersIndex.get(i)).concert.remove(j);   // remove concert from user
												user.get(usersIndex.get(i)).cancelEvent(Integer.parseInt(word[2]));
											}
										}
									}
								} else if (type == "theater") {
									usersIndex = theater.get(index).getPersons(); // get users informatin from theater ArrayList
									theater.remove(index);
									for (int i = 0; i < usersIndex.size(); i++) { // count for all users
										for (int j = 0; j < user.get(usersIndex.get(i)).theater.size(); j++) { // count for all user's theater
											if (user.get(usersIndex.get(i)).theater.get(j).getId() == Integer.parseInt(word[2])) { 											
												user.get(usersIndex.get(i)).theater.remove(j);   // remove theater from user
												user.get(usersIndex.get(i)).cancelEvent(Integer.parseInt(word[2]));
											}
										}
									}
								}
								
								write.write("EVENT CANCELLED: " + word[2]);
								write.newLine();
							} catch (NotFoundException e) {
								write.write("EVENT NOT FOUND: " + word[2]);
								write.newLine();
							}
						

						} else if (word[1].equals("COURSE")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2]);
							write.newLine();
							int checkCourse = -1;
							int index = -1;						
							ArrayList<Integer> usersIndex = new ArrayList<Integer>();
							for (int i = 0; i < course.size(); i++) { // count for all course 
								if (course.get(i).getId() == Integer.parseInt(word[2])) { // check if course is exist or not
									checkCourse = 0;
									index = i;
									break;
								}
							}
						try {
							if (checkCourse == -1) {
								throw new NotFoundException();
							}
							usersIndex = course.get(index).getPersons(); // get users information from course ArrayList
							course.remove(index);
							for (int i = 0; i < usersIndex.size(); i++) { // count for all users
								for (int j = 0; j < user.get(usersIndex.get(i)).course.size(); j++) { // count for all user's course
									if (user.get(usersIndex.get(i)).course.get(j).getId() == Integer.parseInt(word[2])) {
										user.get(usersIndex.get(i)).course.remove(j); // remove course from user
										user.get(usersIndex.get(i)).cancelCourse(Integer.parseInt(word[2]));
									}
								}
							}
							write.write("COURSE CANCELLED: " + word[2]);
							write.newLine();
						} catch (NotFoundException e) {
							write.write("COURSE NOT FOUND: " + word[2]);
							write.newLine();
						}	
					}
					} else if (word[0].equals("LIST")) {
						if (word[1].equals("WEEKLY")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3]);
							write.newLine();
							int checkUser = -1;
							int index = -1;
							for (int i = 0; i < user.size(); i++) {
								if (user.get(i).getId() == Integer.parseInt(word[3])) {
									checkUser = 0;
									index = i;
								}
							}
							try {
								if (checkUser == -1) {
									throw new UserNotFoundException();
								}
								Calendar date = Calendar.getInstance();
								SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
								try {
									date.setTime(format.parse(word[2]));
								} catch (ParseException ex) {
									ex.fillInStackTrace();
								}

								Date tempD;
								for (int l = 0; l < 7; l++) {     // add 7 day for traversal on week
									date.add(Calendar.DATE, l);
									tempD = date.getTime();
									@SuppressWarnings("deprecation")
									int monthD = tempD.getMonth() + 1;
									@SuppressWarnings("deprecation")
									int dayD = tempD.getDate();
									@SuppressWarnings("deprecation")
									int yearD = tempD.getYear();
									yearD = yearD + 1900;

									for (int i = 0; i < user.get(index).anniversary.size(); i++) {
										Date dateAnn = user.get(index).anniversary.get(i).getDate().getTime();

										if (dateAnn.equals(date.getTime())) {
											write.write("ANNIVERSARY: "
													+ user.get(index).anniversary.get(i).getDescription() + " " + dayD
													+ "/" + monthD + "/" + yearD);
											write.newLine();
										}

									}

									for (int i = 0; i < user.get(index).birthday.size(); i++) {
										Date dateBirth = user.get(index).birthday.get(i).getDate().getTime();
										if (dateBirth.equals(date.getTime())) {
											write.write("BIRTHDAY: " + user.get(index).birthday.get(i).getDescription()
													+ " " + dayD + "/" + monthD + "/" + yearD);
											write.newLine();
										}
									}

									for (int i = 0; i < user.get(index).appointment.size(); i++) {
										Calendar dateApp = user.get(index).appointment.get(i).getDate();

										int appYear = dateApp.get(Calendar.YEAR);
										int appMonth = dateApp.get(Calendar.MONTH);
										appMonth++;
										int appDay = dateApp.get(Calendar.DAY_OF_MONTH);
										int hour = dateApp.get(Calendar.HOUR_OF_DAY);
										int minute = dateApp.get(Calendar.MINUTE);
										String name = null;

										int id = -1;
										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (appYear == year && appMonth == month && appDay == day) {
											if (user.get(index).appointment.get(i).getPerson1() == Integer
													.parseInt(word[3])) {
												id = user.get(index).appointment.get(i).getPerson2();
											} else if (user.get(index).appointment.get(i).getPerson2() == Integer
													.parseInt(word[3])) {
												id = user.get(index).appointment.get(i).getPerson1();
											}

											for (int j = 0; j < user.size(); j++) {
												if (user.get(j).getId() == id) {
													name = user.get(j).getFullname();
												}
											}
											write.write("APPOINTMENT WITH " + name + " ON " + dayD + "/" + monthD + "/"
													+ yearD + " " + hour + ":" + minute);
											write.newLine();
										}
									}

									for (int i = 0; i < user.get(index).meeting.size(); i++) {
										Calendar dateMeeting = user.get(index).meeting.get(i).getDate();

										int meetingYear = dateMeeting.get(Calendar.YEAR);
										int meetingMonth = dateMeeting.get(Calendar.MONTH);
										meetingMonth++;
										int meetingDay = dateMeeting.get(Calendar.DAY_OF_MONTH);
										int hour = dateMeeting.get(Calendar.HOUR_OF_DAY);
										int minute = dateMeeting.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);
										ArrayList<Integer> ids = new ArrayList<Integer>();
										ArrayList<String> names = new ArrayList<String>();

										if (meetingYear == year && meetingMonth == month && meetingDay == day) {
											ids = user.get(index).meeting.get(i).getPersons();

											for (int j = 0; j < ids.size(); j++) {
												for (int k = 0; k < user.size(); k++) {
													if (ids.get(j) == user.get(k).getId()) {
														names.add(user.get(k).getFullname());
													}
												}
											}
											write.write("MEETING WITH ");
											for (int j = 0; j < ids.size(); j++) {
												if (ids.get(j) != Integer.parseInt(word[3])) {
													if (j != ids.size() - 1) {
														write.write(names.get(j) + ",");
													} else {
														write.write(names.get(j));
													}
												}
											}
											write.write(" ON " + dayD + "/" + monthD + "/" + yearD + " " + hour + ":"
													+ minute);
											write.newLine();
										}

									}

									for (int i = 0; i < user.get(index).sport.size(); i++) {
										Calendar dateSport = user.get(index).sport.get(i).getDate();
										int sportYear = dateSport.get(Calendar.YEAR);
										int sportMonth = dateSport.get(Calendar.MONTH);
										sportMonth++;
										int sportDay = dateSport.get(Calendar.DAY_OF_MONTH);
										int hour = dateSport.get(Calendar.HOUR_OF_DAY);
										int minute = dateSport.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (sportYear == year && sportMonth == month && sportDay == day) {

											write.write(user.get(index).sport.get(i).getName() + " ON " + dayD + "/"
													+ monthD + "/" + yearD + " " + hour + ":" + minute);
											write.newLine();
										}

									}
									for (int i = 0; i < user.get(index).concert.size(); i++) {
										Calendar dateConcert = user.get(index).concert.get(i).getDate();
										int concertYear = dateConcert.get(Calendar.YEAR);
										int concertMonth = dateConcert.get(Calendar.MONTH);
										concertMonth++;
										int concertDay = dateConcert.get(Calendar.DAY_OF_MONTH);
										int hour = dateConcert.get(Calendar.HOUR_OF_DAY);
										int minute = dateConcert.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (concertYear == year && concertMonth == month && concertDay == day) {

											write.write(user.get(index).concert.get(i).getName() + " ON " + dayD + "/"
													+ monthD + "/" + yearD + " " + hour + ":" + minute);
											write.newLine();
										}

									}
									for (int i = 0; i < user.get(index).theater.size(); i++) {
										Calendar dateTheater = user.get(index).theater.get(i).getDate();
										int theaterYear = dateTheater.get(Calendar.YEAR);
										int theaterMonth = dateTheater.get(Calendar.MONTH);
										theaterMonth++;
										int theaterDay = dateTheater.get(Calendar.DAY_OF_MONTH);
										int hour = dateTheater.get(Calendar.HOUR_OF_DAY);
										int minute = dateTheater.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (theaterYear == year && theaterMonth == month && theaterDay == day) {

											write.write(user.get(index).theater.get(i).getName() + " ON " + dayD + "/"
													+ monthD + "/" + yearD + " " + hour + ":" + minute);
											write.newLine();
										}

									}
									for (int i = 0; i < user.get(index).course.size(); i++) {

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);
										Calendar dateCourse = user.get(index).course.get(i).getDate();
										Calendar temp;
										for (int j = 0; j < 100; j++) {
											dateCourse.add(Calendar.DATE, j * 7);
											temp = dateCourse;
											int courseYear = temp.get(Calendar.YEAR);
											int courseMonth = temp.get(Calendar.MONTH);
											courseMonth++;
											int courseDay = temp.get(Calendar.DAY_OF_MONTH);
											int hour = temp.get(Calendar.HOUR_OF_DAY);
											int minute = temp.get(Calendar.MINUTE);

											if (courseYear == year && courseMonth == month && courseDay == day) {

												write.write(user.get(index).course.get(i).getName() + " ON " + dayD
														+ "/" + monthD + "/" + yearD + " " + hour + ":" + minute);
												write.newLine();
											}

											dateCourse.add(Calendar.DATE, -j * 7);
										}

									}
									date.add(Calendar.DATE, -l);
								}

							} catch (UserNotFoundException e) {
								write.write("USER NOT FOUND: " + word[3]);
								write.newLine();
							}

						}
						if (word[1].equals("DAILY")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3]);
							write.newLine();
							int checkUser = -1;
							int index = -1;
							for (int i = 0; i < user.size(); i++) {
								if (user.get(i).getId() == Integer.parseInt(word[3])) {
									checkUser = 0;
									index = i;
								}
							}
							try {
								if (checkUser == -1) {
									throw new UserNotFoundException();
								}
								Calendar date = Calendar.getInstance();
								SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
								try {
									date.setTime(format.parse(word[2]));
								} catch (ParseException ex) {
									ex.fillInStackTrace();
								}

								for (int i = 0; i < user.get(index).anniversary.size(); i++) {
									Date dateAnn = user.get(index).anniversary.get(i).getDate().getTime();
									if (dateAnn.equals(date.getTime())) {
										write.write("ANNIVERSARY: "
												+ user.get(index).anniversary.get(i).getDescription() + " " + word[2]);
										write.newLine();
									}
								}

								for (int i = 0; i < user.get(index).birthday.size(); i++) {
									Date dateBirth = user.get(index).birthday.get(i).getDate().getTime();
									if (dateBirth.equals(date.getTime())) {
										write.write("BIRTHDAY: " + user.get(index).birthday.get(i).getDescription()
												+ " " + word[2]);
										write.newLine();
									}
								}

								for (int i = 0; i < user.get(index).appointment.size(); i++) {
									Calendar dateApp = user.get(index).appointment.get(i).getDate();
									int id = -1;
									int year = date.get(Calendar.YEAR);
									int month = date.get(Calendar.MONTH);
									month++;
									int day = date.get(Calendar.DAY_OF_MONTH);

									int appYear = dateApp.get(Calendar.YEAR);
									int appMonth = dateApp.get(Calendar.MONTH);
									appMonth++;
									int appDay = dateApp.get(Calendar.DAY_OF_MONTH);
									int hour = dateApp.get(Calendar.HOUR_OF_DAY);
									int minute = dateApp.get(Calendar.MINUTE);
									String name = null;

									if (appYear == year && appMonth == month && appDay == day) {
										if (user.get(index).appointment.get(i).getPerson1() == Integer
												.parseInt(word[3])) {
											id = user.get(index).appointment.get(i).getPerson2();
										} else if (user.get(index).appointment.get(i).getPerson2() == Integer
												.parseInt(word[3])) {
											id = user.get(index).appointment.get(i).getPerson1();
										}

										for (int j = 0; j < user.size(); j++) {
											if (user.get(j).getId() == id) {
												name = user.get(j).getFullname();
											}
										}
										write.write("APPOINTMENT WITH " + name + " ON " + word[2] + " " + hour + ":"
												+ minute);
										write.newLine();
									}
								}

								for (int i = 0; i < user.get(index).meeting.size(); i++) {
									Calendar dateMeeting = user.get(index).meeting.get(i).getDate();
									int year = date.get(Calendar.YEAR);
									int month = date.get(Calendar.MONTH);
									month++;
									int day = date.get(Calendar.DAY_OF_MONTH);

									int meetingYear = dateMeeting.get(Calendar.YEAR);
									int meetingMonth = dateMeeting.get(Calendar.MONTH);
									meetingMonth++;
									int meetingDay = dateMeeting.get(Calendar.DAY_OF_MONTH);
									int hour = dateMeeting.get(Calendar.HOUR_OF_DAY);
									int minute = dateMeeting.get(Calendar.MINUTE);
									ArrayList<Integer> ids = new ArrayList<Integer>();
									ArrayList<String> names = new ArrayList<String>();

									if (meetingYear == year && meetingMonth == month && meetingDay == day) {
										ids = user.get(index).meeting.get(i).getPersons();

										for (int j = 0; j < ids.size(); j++) {
											for (int k = 0; k < user.size(); k++) {
												if (ids.get(j) == user.get(k).getId()) {
													names.add(user.get(k).getFullname());
												}
											}
										}
										write.write("MEETING WITH ");
										for (int j = 0; j < ids.size(); j++) {
											if (ids.get(j) != Integer.parseInt(word[3])) {
												if (j != ids.size() - 1) {
													write.write(names.get(j) + ",");
												} else {
													write.write(names.get(j));
												}
											}
										}
										write.write(" ON " + word[2] + " " + hour + ":" + minute);
										write.newLine();
									}
								}

								for (int i = 0; i < user.get(index).sport.size(); i++) {
									Calendar dateSport = user.get(index).sport.get(i).getDate();
									int year = date.get(Calendar.YEAR);
									int month = date.get(Calendar.MONTH);
									month++;
									int day = date.get(Calendar.DAY_OF_MONTH);

									int sportYear = dateSport.get(Calendar.YEAR);
									int sportMonth = dateSport.get(Calendar.MONTH);
									sportMonth++;
									int sportDay = dateSport.get(Calendar.DAY_OF_MONTH);
									int hour = dateSport.get(Calendar.HOUR_OF_DAY);
									int minute = dateSport.get(Calendar.MINUTE);

									if (sportYear == year && sportMonth == month && sportDay == day) {

										write.write(user.get(index).sport.get(i).getName() + " ON " + word[2] + " "
												+ hour + ":" + minute);
										write.newLine();
									}
								}
								for (int i = 0; i < user.get(index).concert.size(); i++) {
									Calendar dateConcert = user.get(index).concert.get(i).getDate();
									int year = date.get(Calendar.YEAR);
									int month = date.get(Calendar.MONTH);
									month++;
									int day = date.get(Calendar.DAY_OF_MONTH);

									int concertYear = dateConcert.get(Calendar.YEAR);
									int concertMonth = dateConcert.get(Calendar.MONTH);
									concertMonth++;
									int concertDay = dateConcert.get(Calendar.DAY_OF_MONTH);
									int hour = dateConcert.get(Calendar.HOUR_OF_DAY);
									int minute = dateConcert.get(Calendar.MINUTE);

									if (concertYear == year && concertMonth == month && concertDay == day) {

										write.write(user.get(index).concert.get(i).getName() + " ON " + word[2] + " "
												+ hour + ":" + minute);
										write.newLine();
									}
								}
								for (int i = 0; i < user.get(index).theater.size(); i++) {
									Calendar dateTheater = user.get(index).theater.get(i).getDate();
									int year = date.get(Calendar.YEAR);
									int month = date.get(Calendar.MONTH);
									month++;
									int day = date.get(Calendar.DAY_OF_MONTH);

									int theaterYear = dateTheater.get(Calendar.YEAR);
									int theaterMonth = dateTheater.get(Calendar.MONTH);
									theaterMonth++;
									int theaterDay = dateTheater.get(Calendar.DAY_OF_MONTH);
									int hour = dateTheater.get(Calendar.HOUR_OF_DAY);
									int minute = dateTheater.get(Calendar.MINUTE);

									if (theaterYear == year && theaterMonth == month && theaterDay == day) {

										write.write(user.get(index).theater.get(i).getName() + " ON " + word[2] + " "
												+ hour + ":" + minute);
										write.newLine();
									}
								}
								for (int i = 0; i < user.get(index).course.size(); i++) {
									int year = date.get(Calendar.YEAR);
									int month = date.get(Calendar.MONTH);
									month++;
									int day = date.get(Calendar.DAY_OF_MONTH);
									Calendar dateCourse = user.get(index).course.get(i).getDate();
									Calendar temp;
									for (int j = 0; j < 100; j++) {
										dateCourse.add(Calendar.DATE, j * 7);
										temp = dateCourse;
										int courseYear = temp.get(Calendar.YEAR);
										int courseMonth = temp.get(Calendar.MONTH);
										courseMonth++;
										int courseDay = temp.get(Calendar.DAY_OF_MONTH);
										int hour = temp.get(Calendar.HOUR_OF_DAY);
										int minute = temp.get(Calendar.MINUTE);

										if (courseYear == year && courseMonth == month && courseDay == day) {

											write.write(user.get(index).course.get(i).getName() + " ON " + word[2] + " "
													+ hour + ":" + minute);
											write.newLine();
										}

										dateCourse.add(Calendar.DATE, -j * 7);
									}

								}

							} catch (UserNotFoundException e) {
								write.write("USER NOT FOUND: " + word[3]);
								write.newLine();
							}

						}
						if (word[1].equals("MONTHLY")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3]);
							write.newLine();
							int checkUser = -1;
							int index = -1;
							for (int i = 0; i < user.size(); i++) {
								if (user.get(i).getId() == Integer.parseInt(word[3])) {
									checkUser = 0;
									index = i;
								}
							}
							try {
								if (checkUser == -1) {
									throw new UserNotFoundException();
								}
								Calendar date = Calendar.getInstance();
								SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
								try {
									date.setTime(format.parse(word[2]));
								} catch (ParseException ex) {
									ex.fillInStackTrace();
								}

								Date tempD;
								for (int l = 0; l < 30; l++) { // add 30 day for traversal on week
									date.add(Calendar.DATE, l);
									tempD = date.getTime();
									@SuppressWarnings("deprecation")
									int monthD = tempD.getMonth() + 1;
									@SuppressWarnings("deprecation")
									int dayD = tempD.getDate();
									@SuppressWarnings("deprecation")
									int yearD = tempD.getYear();
									yearD = yearD + 1900;

									for (int i = 0; i < user.get(index).anniversary.size(); i++) {
										Date dateAnn = user.get(index).anniversary.get(i).getDate().getTime();

										if (dateAnn.equals(date.getTime())) {
											write.write("ANNIVERSARY: "
													+ user.get(index).anniversary.get(i).getDescription() + " " + dayD
													+ "/" + monthD + "/" + yearD);
											write.newLine();
										}

									}

									for (int i = 0; i < user.get(index).birthday.size(); i++) {
										Date dateBirth = user.get(index).birthday.get(i).getDate().getTime();
										if (dateBirth.equals(date.getTime())) {
											write.write("BIRTHDAY: " + user.get(index).birthday.get(i).getDescription()
													+ " " + dayD + "/" + monthD + "/" + yearD);
											write.newLine();
										}
									}

									for (int i = 0; i < user.get(index).appointment.size(); i++) {
										Calendar dateApp = user.get(index).appointment.get(i).getDate();

										int appYear = dateApp.get(Calendar.YEAR);
										int appMonth = dateApp.get(Calendar.MONTH);
										appMonth++;
										int appDay = dateApp.get(Calendar.DAY_OF_MONTH);
										int hour = dateApp.get(Calendar.HOUR_OF_DAY);
										int minute = dateApp.get(Calendar.MINUTE);
										String name = null;

										int id = -1;
										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (appYear == year && appMonth == month && appDay == day) {
											if (user.get(index).appointment.get(i).getPerson1() == Integer
													.parseInt(word[3])) {
												id = user.get(index).appointment.get(i).getPerson2();
											} else if (user.get(index).appointment.get(i).getPerson2() == Integer
													.parseInt(word[3])) {
												id = user.get(index).appointment.get(i).getPerson1();
											}

											for (int j = 0; j < user.size(); j++) {
												if (user.get(j).getId() == id) {
													name = user.get(j).getFullname();
												}
											}
											write.write("APPOINTMENT WITH " + name + " ON " + dayD + "/" + monthD + "/"
													+ yearD + " " + hour + ":" + minute);
											write.newLine();
										}
									}

									for (int i = 0; i < user.get(index).meeting.size(); i++) {
										Calendar dateMeeting = user.get(index).meeting.get(i).getDate();

										int meetingYear = dateMeeting.get(Calendar.YEAR);
										int meetingMonth = dateMeeting.get(Calendar.MONTH);
										meetingMonth++;
										int meetingDay = dateMeeting.get(Calendar.DAY_OF_MONTH);
										int hour = dateMeeting.get(Calendar.HOUR_OF_DAY);
										int minute = dateMeeting.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);
										ArrayList<Integer> ids = new ArrayList<Integer>();
										ArrayList<String> names = new ArrayList<String>();

										if (meetingYear == year && meetingMonth == month && meetingDay == day) {
											ids = user.get(index).meeting.get(i).getPersons();

											for (int j = 0; j < ids.size(); j++) {
												for (int k = 0; k < user.size(); k++) {
													if (ids.get(j) == user.get(k).getId()) {
														names.add(user.get(k).getFullname());
													}
												}
											}
											write.write("MEETING WITH ");
											for (int j = 0; j < ids.size(); j++) {
												if (ids.get(j) != Integer.parseInt(word[3])) {
													if (j != ids.size() - 1) {
														write.write(names.get(j) + ",");
													} else {
														write.write(names.get(j));
													}
												}
											}
											write.write(" ON " + dayD + "/" + monthD + "/" + yearD + " " + hour + ":"
													+ minute);
											write.newLine();
										}

									}

									for (int i = 0; i < user.get(index).sport.size(); i++) {
										Calendar dateSport = user.get(index).sport.get(i).getDate();
										int sportYear = dateSport.get(Calendar.YEAR);
										int sportMonth = dateSport.get(Calendar.MONTH);
										sportMonth++;
										int sportDay = dateSport.get(Calendar.DAY_OF_MONTH);
										int hour = dateSport.get(Calendar.HOUR_OF_DAY);
										int minute = dateSport.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (sportYear == year && sportMonth == month && sportDay == day) {

											write.write(user.get(index).sport.get(i).getName() + " ON " + dayD + "/"
													+ monthD + "/" + yearD + " " + hour + ":" + minute);
											write.newLine();
										}

									}
									for (int i = 0; i < user.get(index).concert.size(); i++) {
										Calendar dateConcert = user.get(index).concert.get(i).getDate();
										int concertYear = dateConcert.get(Calendar.YEAR);
										int concertMonth = dateConcert.get(Calendar.MONTH);
										concertMonth++;
										int concertDay = dateConcert.get(Calendar.DAY_OF_MONTH);
										int hour = dateConcert.get(Calendar.HOUR_OF_DAY);
										int minute = dateConcert.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (concertYear == year && concertMonth == month && concertDay == day) {

											write.write(user.get(index).concert.get(i).getName() + " ON " + dayD + "/"
													+ monthD + "/" + yearD + " " + hour + ":" + minute);
											write.newLine();
										}

									}
									for (int i = 0; i < user.get(index).theater.size(); i++) {
										Calendar dateTheater = user.get(index).theater.get(i).getDate();
										int theaterYear = dateTheater.get(Calendar.YEAR);
										int theaterMonth = dateTheater.get(Calendar.MONTH);
										theaterMonth++;
										int theaterDay = dateTheater.get(Calendar.DAY_OF_MONTH);
										int hour = dateTheater.get(Calendar.HOUR_OF_DAY);
										int minute = dateTheater.get(Calendar.MINUTE);

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);

										if (theaterYear == year && theaterMonth == month && theaterDay == day) {

											write.write(user.get(index).theater.get(i).getName() + " ON " + dayD + "/"
													+ monthD + "/" + yearD + " " + hour + ":" + minute);
											write.newLine();
										}

									}
									for (int i = 0; i < user.get(index).course.size(); i++) {

										int year = date.get(Calendar.YEAR);
										int month = date.get(Calendar.MONTH);
										month++;
										int day = date.get(Calendar.DAY_OF_MONTH);
										Calendar dateCourse = user.get(index).course.get(i).getDate();
										Calendar temp;
										for (int j = 0; j < 100; j++) {
											dateCourse.add(Calendar.DATE, j * 7);
											temp = dateCourse;
											int courseYear = temp.get(Calendar.YEAR);
											int courseMonth = temp.get(Calendar.MONTH);
											courseMonth++;
											int courseDay = temp.get(Calendar.DAY_OF_MONTH);
											int hour = temp.get(Calendar.HOUR_OF_DAY);
											int minute = temp.get(Calendar.MINUTE);

											if (courseYear == year && courseMonth == month && courseDay == day) {

												write.write(user.get(index).course.get(i).getName() + " ON " + dayD
														+ "/" + monthD + "/" + yearD + " " + hour + ":" + minute);
												write.newLine();
											}

											dateCourse.add(Calendar.DATE, -j * 7);
										}

									}
									date.add(Calendar.DATE, -l);
								}

							} catch (UserNotFoundException e) {
								write.write("USER NOT FOUND: " + word[3]);
								write.newLine();
							}
						}
						if (word[1].equals("ATTENDANCE")) {
							write.write("COMMAND:" + word[0] + " " + word[1] + " " + word[2] + " " + word[3]);
							write.newLine();

							if (word[2].equals("COURSE")) {
								int checkId = -1;
								int index = -1;
								try {
									for (int i = 0; i < course.size(); i++) {
										if (course.get(i).getId() == Integer.parseInt(word[3])) {
											checkId = 1;
											index = i;
											break;
										}
									}
									if (checkId == -1)
										throw new NotFoundException();

									for (int i = 0; i < course.get(index).persons.size(); i++) {
										if (i != course.get(index).persons.size() - 1) {
											write.write(user.get(course.get(index).persons.get(i)).getFullname() + ",");
										} else {
											write.write(user.get(course.get(index).persons.get(i)).getFullname());
											write.newLine();
										}
									}
								} catch (NotFoundException e) {
									write.write("COURSE NOT FOUND: " + word[3]);

								}
							}
							if (word[2].equals("APPOINTMENT")) {
								int checkId = -1;
								int index = -1;
								try {
									for (int i = 0; i < appointment.size(); i++) {
										if (appointment.get(i).getId() == Integer.parseInt(word[3])) {
											checkId = 1;
											index = i;
											break;
										}
									}
									int indexOfUser1 = -1;
									int indexOfUser2 = -1;
									if (checkId == -1)
										throw new NotFoundException();
									for (int i = 0; i < user.size(); i++) {
										if (user.get(i).getId() == appointment.get(index).getPerson1()) {
											indexOfUser1 = i;
										} else if (user.get(i).getId() == appointment.get(index).getPerson2()) {
											indexOfUser2 = i;
										}
									}

									write.write(user.get(indexOfUser1).getFullname() + ",");
									write.write(user.get(indexOfUser2).getFullname());
									write.newLine();

								} catch (NotFoundException e) {
									write.write("APPOINTMENT NOT FOUND: " + word[3]);
								}
							}
							if (word[2].equals("MEETING")) {
								int checkId = -1;
								int index = -1;
								try {
									for (int i = 0; i < meeting.size(); i++) {
										if (meeting.get(i).getId() == Integer.parseInt(word[3])) {
											checkId = 1;
											index = i;
										}
									}
									if (checkId == -1)
										throw new NotFoundException();
									for (int i = 0; i < meeting.get(index).persons.size(); i++) {
										if (i != meeting.get(index).persons.size() - 1) {
											write.write(
													user.get(meeting.get(index).persons.get(i)).getFullname() + ",");
										} else {
											write.write(user.get(meeting.get(index).persons.get(i)).getFullname());
											write.newLine();
										}
									}

								} catch (NotFoundException e) {
									write.write("MEETING NOT FOUND: " + word[3]);

								}
							}
							if (word[2].equals("EVENT")) {
								int checkId = -1;
								int index = -1;
								String type = null;
								try {
									for (int i = 0; i < sport.size(); i++) {
										if (sport.get(i).getId() == Integer.parseInt(word[3])) {
											checkId = 1;
											index = i;
											type = "sport";
										}
									}
									for (int i = 0; i < concert.size(); i++) {
										if (concert.get(i).getId() == Integer.parseInt(word[3])) {
											checkId = 1;
											index = i;
											type = "concert";
										}
									}
									for (int i = 0; i < theater.size(); i++) {
										if (theater.get(i).getId() == Integer.parseInt(word[3])) {
											checkId = 1;
											index = i;
											type = "theater";
										}
									}
									if (checkId == -1)
										throw new NotFoundException();
									if (type == "sport") {
										for (int i = 0; i < sport.get(index).persons.size(); i++) {
											if (i != sport.get(index).persons.size() - 1) {
												write.write(
														user.get(sport.get(index).persons.get(i)).getFullname() + ",");
											} else {
												write.write(user.get(sport.get(index).persons.get(i)).getFullname());
												write.newLine();
											}
										}
									} else if (type == "concert") {
										for (int i = 0; i < concert.get(index).persons.size(); i++) {
											if (i != concert.get(index).persons.size() - 1) {
												write.write(user.get(concert.get(index).persons.get(i)).getFullname()
														+ ",");
											} else {
												write.write(user.get(concert.get(index).persons.get(i)).getFullname());
												write.newLine();
											}
										}
									} else if (type == "theater") {
										for (int i = 0; i < theater.get(index).persons.size(); i++) {
											if (i != theater.get(index).persons.size() - 1) {
												write.write(user.get(theater.get(index).persons.get(i)).getFullname()
														+ ",");
											} else {
												write.write(user.get(theater.get(index).persons.get(i)).getFullname());
												write.newLine();
											}
										}
									}
								} catch (NotFoundException e) {
									write.write("EVENT NOT FOUND: " + word[3]);
								}
							}
						}
					}
				}
			}
			write.close();  // close output file
			reader.close(); // close input file
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
