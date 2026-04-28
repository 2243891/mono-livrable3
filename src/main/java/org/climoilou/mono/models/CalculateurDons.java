package org.climoilou.mono.models;

import java.math.BigDecimal;

public class CalculateurDons {
    public static final double TAUX_DONATIONS = 0.02;
    public static final double FRAIS_CREDIT = 0.01;
    public static final double FRAIS_DEBIT = 0.03;
    public static final double FRAIS_COMPTANT = 0.00;

    /**
     * Calcule le montant d'une donation selon le total d'une facture
     * @param modePaiement le mode de paiement sélectionné
     * @param total le total de la transaction après taxes
     * @return le montant de la donation
     */
    public double calculerDonation(double total, ModePaiement modePaiement) {
        return switch (modePaiement) {
            case DEBIT -> TAUX_DONATIONS * (total - total*FRAIS_DEBIT);
            case CREDIT -> TAUX_DONATIONS * (total - total*FRAIS_CREDIT);
            case COMPTANT -> TAUX_DONATIONS * (total - total*FRAIS_COMPTANT);
        };
    }
}
