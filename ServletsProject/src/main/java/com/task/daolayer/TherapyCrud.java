package main.java.com.task.daolayer;

import main.java.com.task.model.therapy.Therapy;

import java.util.Collection;

public interface TherapyCrud extends DaoCrud<Therapy> {
    Therapy getById(int id, String table);
    Collection<Therapy> select(String table);
}
