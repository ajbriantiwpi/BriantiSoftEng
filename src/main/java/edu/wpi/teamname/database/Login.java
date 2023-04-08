package edu.wpi.teamname.database;

import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

public class Login {
    @Getter @Setter private String username;
    @Getter @Setter private String password;

    public Login(String username, String password)
    {

    }

}
