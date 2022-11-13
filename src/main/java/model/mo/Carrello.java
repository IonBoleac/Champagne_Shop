package model.mo;

public class Carrello {
    private String email;
    private long idProdotto;
    private String nomeProdotto;
    private int quantita;
    private int prezzo;
    private boolean deleted;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getIdProdotto() { return idProdotto; }
    public void setIdProdotto(long idProdotto) { this.idProdotto = idProdotto; }

    public String getNomeProdotto() { return nomeProdotto; }
    public void setNomeProdotto(String nomeProdotto) { this.nomeProdotto = nomeProdotto; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public int getPrezzo() { return prezzo; }
    public void setPrezzo(int prezzo) { this.prezzo = prezzo; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    
}
