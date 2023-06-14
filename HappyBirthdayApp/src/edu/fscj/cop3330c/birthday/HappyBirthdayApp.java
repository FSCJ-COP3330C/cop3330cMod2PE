// HappyBirthdayApp.java
// D. Singletary
// 1/22/23
// wish a user happy birthday

package edu.fscj.cop3330c.birthday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimeZone;

public class HappyBirthdayApp {
    private User user;
    private ArrayList<String> desiredTimezones = new ArrayList<>();

    public HappyBirthdayApp() {
        desiredTimezones = new ArrayList<>();
    }

    // accessor and mutator for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // get user's information
    public User getUserInfo() {
        String fName = "", lName = "";
        int year = 0, month = 0, day = 0;
        int zoneChoice = 0;
        User user = null;
        LocalDateTime date;
        ZoneId zone;
        ZonedDateTime zdt;

        // first and last name
        Scanner kbd = new Scanner(System.in);
        prompt("Please enter your first name");
        fName = kbd.nextLine();
        prompt("Please enter your last name");
        lName = kbd.nextLine();

        // birthday
        prompt("Please enter the year of your birthday");
        year = kbd.nextInt();
        prompt("Please enter the month of your birthday");
        month = kbd.nextInt();
        prompt("Please enter the day of your birthday");
        day = kbd.nextInt();
        date = LocalDateTime.of(year, month, day, 0, 0, 0);

        // timezone
        showTZMenu();
        prompt("Please select the number of your timezone from the menu");
        zoneChoice = kbd.nextInt();
        // get selected timezone from list (adjust choice for 0-based index)
        zone = ZoneId.of(desiredTimezones.get(zoneChoice - 1));
        zdt = ZonedDateTime.of(LocalDateTime.from(date), zone);
        user = new User(fName, lName, zdt);
        return user;

    }

    // show a numeric menu for selected timezones
    public void showTZMenu() {
        int menuCount = 1;
        for (String s : desiredTimezones)
            System.out.println(menuCount++ + ". " + s);
    }

    // show prompt msg with no newline
    public void prompt(String msg) {
        System.out.print(msg + ": ");
    }

    // compare current month and day to user's data
    // to see if it is their birthday
    public boolean isBirthday() {
        boolean result = false;

        LocalDate today = LocalDate.now();
        if (today.getMonth() == user.getBirthday().getMonth() &&
                today.getDayOfMonth() == user.getBirthday().getDayOfMonth())
            result = true;

        return result;
    }

    public static void main(String[] args) {

        HappyBirthdayApp hba = new HappyBirthdayApp();
        // extract some timezones to use for our app, we'll use the US/ zones
        String[] availableTimezones = TimeZone.getAvailableIDs();
        for (String s : availableTimezones) {
            if (s.length() >= 3 && s.substring(0, 3).equals("US/")) {
                hba.desiredTimezones.add(s);
            }
        }
        // get the user's information
        hba.setUser(hba.getUserInfo());
        // print the user's information
        System.out.println("Hello, here is your birthday information:");
        System.out.println(hba.getUser());

        // see if today is their birthday
        if (!hba.isBirthday())
            System.out.println("Sorry, today is not your birthday.");
        else
            System.out.println("Happy Birthday!");
    }

}

// user class
class User {
    private StringBuilder name;
    private ZonedDateTime birthday;

    public User(String fName, String lName, ZonedDateTime birthday) {
        this.name = new StringBuilder();
        this.name.append(fName).append(" ").append(lName);
        this.birthday = birthday;
    }

    public StringBuilder getName() {
        return name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        String s = this.name + "," + this.birthday;
        return s;
    }
}
