package object_App;


import java.util.regex.Pattern;

public class Account {
    private int ID;
    private String userName;
    private String password;
    private String fullName;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String homeTown;
    private int permission;

    public Account(int ID, String userName, String password, String fullName, String dateOfBirth, String phone, String email, String homeTown, int permission) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.homeTown = homeTown;
        this.permission = permission;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public boolean checkUserName() {
        String passPattern = "((?=.*\\d)(?=.*[a-z]).{6,})";
        if (userName.isEmpty()) {
            return false;
        } else if (Pattern.matches(passPattern, password)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword() {
        String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        if (password.isEmpty()) {
            return false;
        } else if (Pattern.matches(passPattern, password)) {
            return true;
        } else {
            return false;
        }
    }
}
