package main.java.com.task.model.therapy;

import java.util.Objects;

public abstract class Therapy {
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Therapy(){};

    public Therapy(String name) {
        if (name == null) throw new NullPointerException();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Therapy therapy = (Therapy) o;
        return id == therapy.id &&
                Objects.equals(name, therapy.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Therapy{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
