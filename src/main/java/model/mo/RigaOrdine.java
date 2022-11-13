package model.mo;

public class RigaOrdine {

    private Long idRigaOrdine;
    private int quantita;
    private int prezzo;
    private boolean deleted;
    private boolean arrivato;

    /* N:1 */
    private TestataOrdine testataOrdine;
    private Prodotto prodotto;

    public Long getIdRigaOrdine() {return idRigaOrdine;}
    public void setIdRigaOrdine(Long idRigaOrdine) {this.idRigaOrdine = idRigaOrdine;}

    public int getQuantita() {return quantita;}
    public void setQuantita(int quantita) {this.quantita = quantita;}

    public int getPrezzo() {return prezzo;}
    public void setPrezzo(int prezzo) {this.prezzo = prezzo;}

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public boolean isArrivato() { return arrivato; }
    public void setArrivato(boolean arrivto) { this.arrivato = arrivato; }

    public Prodotto getProdotto() {return prodotto;}
    public void setProdotto(Prodotto prodotto) {this.prodotto = prodotto;}

    public TestataOrdine getTestataOrdine() {return testataOrdine;}
    public void setTestataOrdine(TestataOrdine testataOrdine) {this.testataOrdine = testataOrdine;}

}
