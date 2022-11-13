package model.mo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Utente {

    private String email;
    private String nome;
    private String cognome;
    private String sesso;
    private String password;
    private Date annoNascita;
    private String cellulare;
    private boolean isAdmin;
    private boolean deleted;
    private boolean isBlocked;

    private List<Map> carello;

    private Carrello carrello_Carello;
    private List<Carrello> carrello_CarrelloList;

    /* 1:N */
    private TestataOrdine[] testataOrdine;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getSesso() { return sesso; }
    public void setSesso(String sesso) { this.sesso = sesso; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Date getAnnoNascita() { return annoNascita; }
    public void setAnnoNascita(Date annoNascita) { this.annoNascita = annoNascita; }

    public String getCellulare() {return cellulare;}
    public void setCellulare(String cellulare) {this.cellulare = cellulare;}

    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted;}

    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }

    public TestataOrdine[] getTestataOrdine() { return testataOrdine; }
    public void setTestataOrdine(TestataOrdine[] testataOrdine) { this.testataOrdine = testataOrdine; }

    public TestataOrdine getTestataOrdine(int index) {
        return this.testataOrdine[index];
    }
    public void setTestataOrdine(int index, TestataOrdine testataOrdine) {
        this.testataOrdine[index] = testataOrdine;
    }

    public List<Map> getCarello() {
        return carello;
    }
    public void setCarello(List<Map> carello) {
        this.carello = carello;
    }

    public Carrello getCarello_Carello() {
        return carrello_Carello;
    }
    public void serCarello_Carello(Carrello carrello_Carello) {
        this.carrello_Carello = carrello_Carello;
    }

    public List<Carrello> getCarrello_CarrelloList() {
        return carrello_CarrelloList;
    }
    public void setCarello_CarelloList(List<Carrello> carrello_CarrelloList) {
        this.carrello_CarrelloList = carrello_CarrelloList;
    }
}
