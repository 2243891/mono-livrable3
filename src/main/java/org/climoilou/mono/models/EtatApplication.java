package org.climoilou.mono.models;

import java.util.ArrayList;
import java.util.Collection;

public class EtatApplication {
    private Collection<Transaction> transactions;
    private CalculateurDons calculateurDons = new CalculateurDons();
    private double totalDons;

    public EtatApplication() {
        transactions = new ArrayList<>();
        totalDons = 0.0;
    }

    public void ajouterTransaction(Transaction t) {
        totalDons += calculateurDons.calculerDonation(t.getMontantTotalApresTaxes(), t.getModePaiement());
        transactions.add(t);
    }

    public double getTotalDons() {
        return totalDons;
    }
}
