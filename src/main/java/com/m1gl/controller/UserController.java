package main.java.com.m1gl.controller;


import com.google.gson.Gson;
import main.java.com.m1gl.models.User;
import main.java.com.m1gl.services.implementations.ProfilDao;
import main.java.com.m1gl.services.implementations.UserDAO;
import main.java.com.m1gl.utils.Utilitaire;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Path("/users")
public class UserController extends BaseController {

    private static final String ERROR_CODE = "error";
    private static final String SUCCES_CODE = "success";

    private final UserDAO iuser = new UserDAO();

    private final ProfilDao profilDao = new ProfilDao();

    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listService() {
        List<User> users = iuser.getAllUsers();
        return sendSuccess("liste des utilisateurs", users);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(String body) {
        User utilisateur = gson.fromJson(body, User.class);
        utilisateur.setUsername(utilisateur.getEmail());
        utilisateur.setPassword("passer@123");
        utilisateur.setDate(Timestamp.valueOf(LocalDateTime.now()));
        utilisateur.setProfil(profilDao.getOneById(utilisateur.getProfil().getId()));
        utilisateur.setMatricule(Utilitaire.generateMatriculeUser());
        if (iuser.addUser(utilisateur))
            return Response.status(HttpServletResponse.SC_OK).entity(Collections.singletonMap(SUCCES_CODE, iuser.getAllUsers())).build();
        else
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity(Collections.singletonMap(ERROR_CODE, false)).build();
    }

    /**
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return Objet User en cas success et null en cas error
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response logine(@FormDataParam("username") String username, @FormDataParam("password") String password) {
        try {
            if (username.trim().equals("") ||
                    password.trim().equals("")) {
                return sendError(200, "Renseigner tous les champs!");
            }
            User resp = iuser.login(username, password);
            if (resp != null) {
                return sendSuccess("Connexion reussie", resp);
            } else {
                return sendError(200, "Login ou mot de passe incorrect!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return sendError(200, "Erreur server");
        }
    }
}
