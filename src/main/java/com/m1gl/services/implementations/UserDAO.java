package main.java.com.m1gl.services.implementations;

import main.java.com.m1gl.config.HibernateConfiguration;
import main.java.com.m1gl.models.Utilisateur;
import main.java.com.m1gl.services.IUserServices;
import org.hibernate.Session;

import java.util.List;

//@Stateless
public class UserDAO implements IUserServices {
    Session session = HibernateConfiguration.getSession();

    @Override
    public List<Utilisateur> getAllUsers() {
        try {
            return session.createQuery("SELECT u FROM Utilisateur u",Utilisateur.class).getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addUser(Utilisateur user) {
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }


    @Override
    public Utilisateur getUserByUsername(String username) {

        try {
            return session.createQuery("SELECT u FROM Utilisateur u Where u.username=:username",Utilisateur.class).
                    setParameter("username",username).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public Utilisateur getUserByEmail(String email) {

        try {
            return  session.createQuery("SELECT p FROM Utilisateur p Where p.email=:email", Utilisateur.class).
                    setParameter("email",email).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public Utilisateur login(String username, String password) {
        try {
            return session.createQuery("SELECT u FROM Utilisateur u Where u.username=:username AND u.password=:password",Utilisateur.class).
                    setParameter("username",username).setParameter("password",password).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public Utilisateur getUserById(Long id) {
        try {
            return  session.createQuery("SELECT u FROM Utilisateur u Where u.id=:id", Utilisateur.class).
                    setParameter("id",id).
                    getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }
}
