package model.mo;

public class Prodotto {

    private long idProdotto;
    private String nomeProdotto;
    private int prezzoUnitario;
    private String urlImage;
    private int quantita;
    private String descrizione;
    private boolean vetrina;
    private boolean blocked;
    private boolean deleted;
    private String annata;

    public long getIdProdotto() {return idProdotto;}
    public void setIdProdotto(Long idProdotto) {this.idProdotto = idProdotto;}

    public String getNomeProdotto() {return nomeProdotto;}
    public void setNomeProdotto(String nomeProdotto) {this.nomeProdotto = nomeProdotto;}

    public int getPrezzoUnitario() {return prezzoUnitario;}
    public void setPrezzoUnitario(int prezzoUnitario) {this.prezzoUnitario = prezzoUnitario;}

    public String getUrlImage() {return urlImage;}
    public void setUrlImage(String urlImage) {this.urlImage = urlImage;}

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public boolean isVetrina() { return vetrina; }
    public void setVetrina(boolean vetrina) { this.vetrina = vetrina; }

    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getAnnata() { return annata; }
    public void setAnnata(String annata) { this.annata = annata; }
}
