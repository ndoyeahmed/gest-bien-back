package main.java.com.m1gl.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Client extends Personne implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 15)
    private String CIN;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
