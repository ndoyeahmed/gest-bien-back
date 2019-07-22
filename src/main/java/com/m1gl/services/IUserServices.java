package main.java.com.m1gl.services;


import main.java.com.m1gl.models.User;
import main.java.com.m1gl.models.Utilisateur;

import java.util.List;

//@Local
public interface IUserServices {

    List<Utilisateur> getAllUsers();

    boolean addUser(Utilisateur user);


    Utilisateur getUserByUsername(String username);

    Utilisateur getUserByEmail(String email);

    Utilisateur login(String username,String password);

    Utilisateur getUserById(Long id);
}
