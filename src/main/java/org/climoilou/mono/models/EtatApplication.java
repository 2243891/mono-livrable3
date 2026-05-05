package org.climoilou.mono.models;

import org.climoilou.mono.exceptions.TransactionDejaExistante;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EtatApplication {
    private Set<Transaction> historiqueTransactions;
    private CalculateurDons calculateurDons = new CalculateurDons();
    private double totalDons;

    public EtatApplication() {
        historiqueTransactions = new HashSet<>();
        totalDons = 0.0;
    }

    public void ajouterTransaction(Transaction t) {

        if (historiqueTransactions.add(t)) {
            totalDons += calculateurDons.calculerDonation(t.getMontantTotalApresTaxes(), t.getModePaiement());
        } else {
            throw new TransactionDejaExistante("Le numéro de transaction que vous essayez de rentrez existe déjà.\n La transaction ne sait dont pas crée");
        }

    }

    public double getTotalDons() {
        return totalDons;
    }
}
