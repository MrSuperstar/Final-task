package main.java.com.task.model.person;

public class User extends Human {
    private String login;
    private String password;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

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

    public User(String name, Gender gender) {
        super(name, gender);
    }

    public enum Position {
        ADMIN(1), EMPLOYEE(2), PATIENT(3);
        private int code;

        Position(int i) {
            code = i;
        }

        @Override
        public String toString() {
            switch (code) {
                case 1: return "Admin";
                case 2: return "Employee";
                case 3: return "Patient";
                default: throw new EnumConstantNotPresentException(Position.class, "value " + code);
            }
        }
    }
}
