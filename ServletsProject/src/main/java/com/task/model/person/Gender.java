package main.java.com.task.model.person;

public enum Gender {
    MALE (1),
    FEMALE (2);

    private int code;

    Gender(int i) {
        this.code = i;
    }

    public int getCode() { return code; }
}
