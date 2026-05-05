package org.climoilou.mono.models;

import org.climoilou.mono.exceptions.TransactionDejaExistante;
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

    @Test
    public void etantDonneDeuxTransactionAvecNumeroTransactionDifferentes_quandAjouteTransaction_alorsTotalDonsMisAJour(){
        Transaction transaction1 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, 1);
        Transaction transaction2 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, 22);

        etatApplication.ajouterTransaction(transaction1);
        etatApplication.ajouterTransaction(transaction2);
        assertTrue(etatApplication.getTotalDons() > 0);

    }

    @Test
    public void etantDonneDeuxTransactionAvecMemeTransactionDifferentes_quandAjouteTransaction_alorsExceptionTransactionDejaExistanteLance(){
        Transaction transaction1 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, 1);
        Transaction transaction2 = new Transaction("Georges de la jungle", 100.00, 10, ModePaiement.CREDIT, 1);

        etatApplication.ajouterTransaction(transaction1);
        assertThrows(TransactionDejaExistante.class, ()->{
            etatApplication.ajouterTransaction(transaction2);
        });


    }

}