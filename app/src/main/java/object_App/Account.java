package object_App;


import java.util.regex.Pattern;

public class Account {
    private int ID;
    private String userName;
    private String password;
    private String fullName;
    private String dateOfBirth;
    private String sex;
    private String phone;
    private String question;
    private String answer;


    public Account(int ID, String userName, String password, String fullName, String dateOfBirth, String phone, String question, String answer) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.question = question;
        this.answer = answer;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
