package main.java.com.task.daolayer;

import java.util.Collection;

public interface DaoCrud<Entity> {
    Collection<Entity> select();
    boolean update(Entity o);
    boolean delete(int id);
    boolean insert(Entity o);
}
