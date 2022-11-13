package model.mo;

import java.sql.Date;
import java.util.ArrayList;

public class TestataOrdine {

    private Long idTestataOrdine;
    private String nome;
    private String cognome;
    private String indirizzoSpedizione;
    private String codicePostale;
    private String citta;
    private String provincia;
    private String regione;
    private Date dataOrdine;
    private String statoSpedizione;
    private boolean annullato;
    private boolean consegnato;

    /* N:1 */
    private Utente utente;

    /* 1:N */
    private ArrayList<RigaOrdine> rigaOrdine;


    public Long getIdTestataOrdine() {return idTestataOrdine;}
    public void setIdTestataOrdine(Long idTestataOrdine) {this.idTestataOrdine = idTestataOrdine;}

    public void setNome(String nome) { this.nome = nome; }
    public String getNome() { return nome; }

    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getCognome() { return cognome; }

    public String getIndirizzoSpedizione() {return indirizzoSpedizione;}
    public void setIndirizzoSpedizione(String indirizzoSpedizione) { this.indirizzoSpedizione = indirizzoSpedizione; }

    public void setCodicePostale(String codicePostale) { this.codicePostale = codicePostale; }
    public String getCodicePostale() { return codicePostale; }

    public void setCitta(String citta) { this.citta = citta; }
    public String getCitta() { return citta; }

    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getProvincia() { return provincia; }

    public void setRegione(String regione) { this.regione = regione; }
    public String getRegione() { return regione; }

    public Date getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Date dataOrdine) {this.dataOrdine = dataOrdine;}

    public String getStatoSpedizione() { return statoSpedizione; }
    public void setStatoSpedizione(String statoSpedizione) { this.statoSpedizione = statoSpedizione; }

    public boolean isAnnullato() { return annullato; }
    public void setAnnullato(boolean annullato) { this.annullato = annullato; }

    public Utente getUtente() {return utente;}
    public void setUtente(Utente utente) {this.utente = utente;}

    public ArrayList<RigaOrdine> getRigaOrdine() {return rigaOrdine;}
    public void setRigaOrdine(ArrayList<RigaOrdine> rigaOrdine) {this.rigaOrdine = rigaOrdine;}

    public boolean isConsegnato() { return consegnato; }
    public void setConsegnato(boolean consegnato) { this.consegnato = consegnato; }

    /*
    public ArrayList<RigaOrdine> getRigaOrdine(int index) { return rigaOrdine[index]; }
    public void setRigaOrdine(int index, RigaOrdine rigaOrdine){ this.rigaOrdine[index] = rigaOrdine; }
     */

}
