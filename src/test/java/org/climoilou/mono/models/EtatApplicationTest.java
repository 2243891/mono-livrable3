package org.climoilou.mono.models;

import org.climoilou.mono.exceptions.TransactionDejaExistante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtatApplicationTest {
    EtatApplication etatApplication;
    CalculateurDons calculateurDons;

    @BeforeEach
    public void setUp() {
        etatApplication = new EtatApplication();
        calculateurDons = new CalculateurDons();
    }

    @Test
    public void etantDonneNouvelleTransaction_quandAjouteTransactionAlorsTotalDonsMisAJour() {
        Transaction nouvelleTransaction = new Transaction("BOB", 1.0, 0.15, ModePaiement.CREDIT, "Bob");

        etatApplication.ajouterTransaction(nouvelleTransaction);
        double resultat = calculateurDons.calculerDonation(nouvelleTransaction.getMontantTotalApresTaxes(), nouvelleTransaction.getModePaiement());

        assertEquals(etatApplication.getTotalDons(), resultat);
    }

    @Test
    public void etantDonneDeuxTransactionAvecNumeroTransactionDifferentes_quandAjouteTransaction_alorsTotalDonsMisAJour(){
        Transaction transaction1 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, "E1");
        Transaction transaction2 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, "E2");

        etatApplication.ajouterTransaction(transaction1);
        etatApplication.ajouterTransaction(transaction2);
        double resultat1 = calculateurDons.calculerDonation(transaction1.getMontantTotalApresTaxes(), transaction1.getModePaiement());
        double resultat2 = calculateurDons.calculerDonation(transaction2.getMontantTotalApresTaxes(), transaction2.getModePaiement());

        assertEquals(etatApplication.getTotalDons(), resultat1 + resultat2);

    }

    @Test
    public void etantDonneDeuxTransactionAvecMemeTransactionDifferentes_quandAjouteTransaction_alorsExceptionTransactionDejaExistanteLance(){
        Transaction transaction1 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, "E1");
        Transaction transaction2 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, "E1");

        etatApplication.ajouterTransaction(transaction1);
        assertThrows(TransactionDejaExistante.class, ()->{
            etatApplication.ajouterTransaction(transaction2);
        });
    }

}