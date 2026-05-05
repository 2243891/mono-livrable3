package org.climoilou.mono.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CalculateurDonsTest {
    CalculateurDons calculateurDons;

    @BeforeEach
    public void setUp() {
        calculateurDons = new CalculateurDons();
    }

    @ParameterizedTest
    @CsvSource({
            "COMPTANT,57.99,1.16",
            "DEBIT,57.99,1.13",
            "CREDIT,57.99,1.15"
    })
    void etantDonneTotalValide_quandCalculeDonation_alorsRetourneMonantDonation(ModePaiement modePaiement, double totalValide, double resultatAttendu) {
        double donations = calculateurDons.calculerDonation(totalValide, modePaiement);
        donations = BigDecimal.valueOf(donations).setScale(2, RoundingMode.HALF_UP).doubleValue();
        assertEquals(resultatAttendu, donations);
    }
}