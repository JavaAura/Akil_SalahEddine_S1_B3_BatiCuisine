package org.aura.models;

public abstract class Composants {
    protected int id;
    protected String nom;
    protected String typeComposant;
    protected double tauxTVA;

    public Composants(int id, String nom, String typeComposant, double tauxTVA) {
        this.id = id;
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(String typeComposant) {
        this.typeComposant = typeComposant;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }
}
