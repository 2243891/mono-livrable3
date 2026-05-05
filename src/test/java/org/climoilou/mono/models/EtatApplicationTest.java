package org.climoilou.mono.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtatApplicationTest {
    EtatApplication etatApplication;

    @BeforeEach
    public void setUp() {
        etatApplication = new EtatApplication();
    }

    @Test
    public void etantDonneNouvelleTransaction_quandAjouteTransactionAlorsTotalDonsMisAJour() {
        Transaction nouvelleTransaction = new Transaction("BOB", 1.0, 0.15, ModePaiement.CREDIT, 0);

        etatApplication.ajouterTransaction(nouvelleTransaction);

        assertTrue(etatApplication.getTotalDons() > 0);
    }

}