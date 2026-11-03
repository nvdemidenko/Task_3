package models;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String emailData) {
        this.email = emailData;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordData) {
        this.password = passwordData;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameData) {
        this.name = nameData;
    }
}