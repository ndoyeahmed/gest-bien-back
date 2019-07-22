package main.java.com.m1gl.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Client extends Personne implements Serializable {

    @Column(nullable = false,length = 15)
    private String CIN;

    @Column(nullable = false,length = 15)
    private String telephone;

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
