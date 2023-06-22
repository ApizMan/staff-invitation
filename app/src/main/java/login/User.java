package login;

public class User {

    private String fullname, email, contactnum, password;

    public User() {
    }

    public User(String fullname, String email, String contactnum, String password) {
        this.fullname = fullname;
        this.email = email;
        this.contactnum = contactnum;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getContactnum() {
        return contactnum;
    }

    public String getPassword() {
        return password;
    }
}