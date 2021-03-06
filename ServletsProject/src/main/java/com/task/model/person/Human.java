package main.java.com.task.model.person;

import java.util.Objects;

/**
 * The basic essence of a man.
 */
public abstract class Human implements Person {
    private String name;
    private Gender gender;
    private int id;

    public Human(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return id == human.id &&
                Objects.equals(name, human.name) &&
                gender == human.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public String getLogin() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", id=" + id +
                '}';
    }
}