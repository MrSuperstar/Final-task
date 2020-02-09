package main.java.com.task.daolayer;

import main.java.com.task.model.person.User;

import java.sql.SQLException;

public interface UserCrud extends DaoCrud<User>{
    User getUserById(int id);
    User authentication(String login, String password);
    void registration(User user);
}
