package org.climoilou.mono.models;

import org.climoilou.mono.controlleurs.InvalidNameException;
import org.climoilou.mono.exceptions.TaxesInvalideException;
import org.climoilou.mono.exceptions.TotalAvantTaxesInvalideException;

import java.util.Objects;

public class Transaction {
    private final Double MONTANT_MAXIMUM_TOTAL_AV_TAXES = 1000000.00;
    private final Double MONTANT_MAXIMUM_TAXES_APPLICABLES = 500000.00;
    private String nomAcheteur;
    private double totalAvantTaxes;
    private double taxesApplicables;
    private ModePaiement modePaiement;
    private int numeroTransaction;

    public Transaction(String nomAcheteur, double totalAvantTaxes, double taxesApplicables, ModePaiement modePaiement) {
        setNomAcheteur(nomAcheteur);
        setTotalAvantTaxes(totalAvantTaxes);
        setTaxesApplicables(taxesApplicables);
        setModePaiement(modePaiement);
        numeroTransaction = 0;
    }

    public void setNomAcheteur(String nomAcheteur) {
        if(nomAcheteur.isBlank()) throw new InvalidNameException("Le nom de l'acheteur de la transaction ne peut pas être vide");
        this.nomAcheteur = nomAcheteur;
    }

    public void setTotalAvantTaxes(double totalAvantTaxes) {
        if (!totalAvantTaxesEstValide(totalAvantTaxes)) throw new TotalAvantTaxesInvalideException("Le total avant taxes est invalide");
        this.totalAvantTaxes = totalAvantTaxes;
    }

    public void setTaxesApplicables(double taxesApplicables) {
        if (!taxesApplicablesEstValides(taxesApplicables) ) throw new TaxesInvalideException("Les taxes applicables sont invalides");
        this.taxesApplicables = taxesApplicables;
    }

    private void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getNomAcheteur() {
        return nomAcheteur;
    }

    public double getTotalAvantTaxes() {
        return totalAvantTaxes;
    }

    public double getTaxesApplicables() {
        return taxesApplicables;
    }

    public double getMontantTotalApresTaxes() {
        return totalAvantTaxes + taxesApplicables;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }


    private boolean totalAvantTaxesEstValide(double totalAvantTaxes) {
        return totalAvantTaxes >= 0 && totalAvantTaxes <= MONTANT_MAXIMUM_TOTAL_AV_TAXES;
    }

    private boolean taxesApplicablesEstValides(double taxesApplicables) {
        return taxesApplicables >= 0 && taxesApplicables <= MONTANT_MAXIMUM_TAXES_APPLICABLES;
    }

    public int getNumeroTransaction() {
        return numeroTransaction;
    }

    public void setNumeroTransaction(int numeroTransaction) {
        this.numeroTransaction = numeroTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return numeroTransaction == that.numeroTransaction;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroTransaction);
    }
}
