package org.climoilou.mono.models;

import org.climoilou.mono.exceptions.TaxesInvalideException;
import org.climoilou.mono.exceptions.TotalAvantTaxesInvalideException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        transaction = new Transaction("Joe", 1000.0, 50.0, ModePaiement.CREDIT);
    }

    @Test
    void donneMontantAvantTaxesPlusHautLimite_setTotalAvantTaxes_DoncErreurTotalAvantTaxesInvalideException() {
        double montantPlusHautQueLimite = 10000000.00;

        assertThrows(TotalAvantTaxesInvalideException.class, () -> {
            transaction.setTotalAvantTaxes(montantPlusHautQueLimite);
        });
    }
    @Test
    void donneMontantValide_setTotalAvantTaxes_DoncMontantEstValide() {
        double montantPlusHautQueLimite = 140.00;
        transaction.setTotalAvantTaxes(montantPlusHautQueLimite);
        assertEquals(montantPlusHautQueLimite, transaction.getTotalAvantTaxes());
    }

    @Test
    void donneMontantTaxeApplicablesPlusHautLimite_QuandSetTaxesApplicables_DoncErreurTotalAvantTaxesInvalideException() {
        double montantPlusHautQueLimite = 10000000.00;

        assertThrows(TaxesInvalideException.class, () -> {
            transaction.setTaxesApplicables(montantPlusHautQueLimite);
        });
    }
    @Test
    void donneMontantValide_QuandSetTaxesApplicables_DoncMontantEstValide() {
        double montantPlusHautQueLimite = 140.00;
        transaction.setTaxesApplicables(montantPlusHautQueLimite);
        assertEquals(montantPlusHautQueLimite, transaction.getTaxesApplicables());
    }



}