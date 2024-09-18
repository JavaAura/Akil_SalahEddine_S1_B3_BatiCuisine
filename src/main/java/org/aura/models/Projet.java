package org.aura.models;

public class Projet {
    private int id;
    private String nomProjet;
    private double margeBeneficiaire;
    private double coutTotal;
    private etat etatProjet;

    public Projet(int id, String nomProjet, double margeBeneficiaire, double coutTotal, etat etatProjet) {
        this.id = id;
        this.nomProjet = nomProjet;
        this.margeBeneficiaire = margeBeneficiaire;
        this.coutTotal = coutTotal;
        this.etatProjet = etatProjet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public etat getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(etat etatProjet) {
        this.etatProjet = etatProjet;
    }
}
