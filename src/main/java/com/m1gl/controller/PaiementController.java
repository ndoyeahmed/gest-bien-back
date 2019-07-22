package main.java.com.m1gl.controller;

import main.java.com.m1gl.models.MoisAnnee;
import main.java.com.m1gl.models.Paiement;
import main.java.com.m1gl.services.ILocationServices;
import main.java.com.m1gl.services.IPaiement;
import main.java.com.m1gl.services.IParametrage;
import main.java.com.m1gl.services.IUserServices;
import main.java.com.m1gl.utils.Utilitaire;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.time.LocalDate.*;

@Path("paiements")
public class PaiementController extends BaseController {

    @EJB
    ILocationServices ilocation;

    @EJB
    IPaiement ipaiement;

    @EJB
    IUserServices iuser;

    @EJB
    IParametrage iparam;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addPaiement(@FormParam("location_id") Long location_id,
            @FormParam("typeReglement_id") Long typeReglement_id,
            @FormParam("user_id") Long user_id,
            @FormParam("listeMoisAnnees") List<Long> mois){


        try{
            Paiement paiement=new Paiement();
            paiement.setDatePaiement(java.sql.Date.valueOf(LocalDate.now()));
            paiement.setNumPaiement(Utilitaire.generateNumpaiement());
            paiement.setUser(iuser.getUserById(user_id));
            paiement.setLocation(ilocation.getLocationById(location_id));
            paiement.setTypeReglement(iparam.getTypeReglementbyId(typeReglement_id));

            List<MoisAnnee> lm=new ArrayList<>();


            for(int i=0;i<mois.size();i++){
                lm.add(iparam.getMoisAnneeById(mois.get(i)));
            }
            boolean result = ipaiement.addPaiement(paiement,lm);

            if(result) {
                return sendSuccess("Insertion paiement reussie!", paiement);
            }
            return sendError(200,"Erreur insertion paiement");
        }catch (Exception ex){
            ex.printStackTrace();
            return sendError(500,"Erreur Serveur");
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaiements(){
        return sendSuccess("Tous les paiements",ipaiement.getAllPaiements());
    }

    @GET
    @Path("/client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaiementsByIdClient(@PathParam("id") Long id){
        return sendSuccess("Tous les paiements du client id="+id,ipaiement.getPaiementsByIdClient(id));
    }
}
