package main.java.com.task.model.person;

public class User extends Human {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public User(String name, Gender gender, String login, String password) {
        super(name, gender);

        this.login = login;
        this.password = password;
    }
}
