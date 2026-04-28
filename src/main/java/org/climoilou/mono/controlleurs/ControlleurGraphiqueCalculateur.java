package org.climoilou.mono.controlleurs;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.climoilou.mono.exceptions.MethodeDePaiementInconnueException;
import org.climoilou.mono.exceptions.TaxesInvalideException;
import org.climoilou.mono.exceptions.TotalAvantTaxesInvalideException;
import org.climoilou.mono.models.EtatApplication;
import org.climoilou.mono.models.ModePaiement;
import org.climoilou.mono.models.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

public class ControlleurGraphiqueCalculateur {
    private final Double MONTANT_MAXIMUM_TOTAL_AV_TAXES = 1000000.00;
    private final Double MONTANT_MAXIMUM_TAXES_APPLICABLES = 500000.00;

    private final EtatApplication etatApplication;
    @FXML
    private Button ajouterFactureBouton;

    @FXML
    private ToggleGroup methodePaiement;

    @FXML
    private Group modePaiementButtonGroup;

    @FXML
    private TextField nomAcheuteurInputField;

    @FXML
    private TextField taxesApplicablesInputField;

    @FXML
    private Text texteSucces;

    @FXML
    private Text totalAvecTaxesText;

    @FXML
    private Text totalDonsText;

    @FXML
    private TextField totalSansTaxesInputField;

    @FXML
    private StackPane informationFactureStackPane;

    public ControlleurGraphiqueCalculateur(EtatApplication etatApplication) {
        this.etatApplication = etatApplication;
    }

    UnaryOperator<TextFormatter.Change> validateurMontantAvantTaxes = change -> {

        String inputText = change.getControlNewText();
        if (inputText.isBlank()) return change;
        if(inputText.contains("d")) return null;
        try {
            double montant = Double.parseDouble(inputText);
            double montantMax = MONTANT_MAXIMUM_TOTAL_AV_TAXES;
            if (montant <= montantMax) {
                return change;
            } else {
                createErrorDialogBox("Erreur","Erreur", "Le montant ne peut pas être plus grand que 1 000 000.00$");
                return null;
            }
        } catch (NumberFormatException ex) {
            return null;
        }
    };

    UnaryOperator<TextFormatter.Change> validateurMontantTaxesApplicables = change -> {

        String inputText = change.getControlNewText();
        if (inputText.isBlank()) return change;
        if(inputText.contains("d")) return null;
        try {
            double montant = Double.parseDouble(inputText);
            double montantMax = MONTANT_MAXIMUM_TAXES_APPLICABLES;
            if (montant <= montantMax) {
                return change;
            } else {
                createErrorDialogBox("Erreur","Erreur", "Le montant ne peut pas être plus grand que 500 000.00$");
                return null;
            }
        } catch (NumberFormatException ex) {
            return null;
        }
    };



    @FXML
    public void initialize() {
        totalSansTaxesInputField.setOnKeyTyped(this::updateTotalFacture);
        taxesApplicablesInputField.setOnKeyTyped(this::updateTotalFacture);
        totalSansTaxesInputField.setTextFormatter(new TextFormatter<>(validateurMontantAvantTaxes));
        taxesApplicablesInputField.setTextFormatter(new TextFormatter<>(validateurMontantTaxesApplicables));
        ajouterFactureBouton.setOnAction(this::ajouterFacture);

        setAffichageTransactionValide(false);
    }


    private void updateTotalFacture(Event event) {
        setAffichageTransactionValide(false);
        double totalSansTaxes = 0.0;
        double taxesApplicables = 0.0;
        if (!totalSansTaxesInputField.getText().isEmpty())
            totalSansTaxes = Double.parseDouble(totalSansTaxesInputField.getText());
        if (!taxesApplicablesInputField.getText().isEmpty())
            taxesApplicables = Double.parseDouble(taxesApplicablesInputField.getText());

        double totalAvecTaxes = totalSansTaxes + taxesApplicables;
        if (totalAvecTaxes == 0) totalAvecTaxesText.setText("inconnu");
        else {
            BigDecimal totalFormate = BigDecimal.valueOf(totalAvecTaxes).setScale(2, RoundingMode.HALF_UP);

            totalAvecTaxesText.setText(totalFormate.toPlainString() + " $");
        }
    }


    @FXML
    void ajouterFacture(ActionEvent event) {
        try {
            Transaction transaction = new Transaction(
                    nomAcheuteurInputField.getText(),
                    Double.parseDouble(totalSansTaxesInputField.getText()),
                    Double.parseDouble(taxesApplicablesInputField.getText()),
                    determinerModePaiement()
            );
            etatApplication.ajouterTransaction(transaction);
            BigDecimal totalFormate = BigDecimal.valueOf(etatApplication.getTotalDons()).setScale(2, RoundingMode.HALF_UP);
            updateTotalDons(totalFormate);
            clearFormulaireFacture();
            updateTotalFacture(event);
            setAffichageTransactionValide(true);
        }
         catch (TaxesInvalideException | TotalAvantTaxesInvalideException | MethodeDePaiementInconnueException | InvalidNameException e) {
            System.out.println("Une erreur est survenue");
            System.err.println(e.getMessage());
            createErrorDialogBox("Erreur","Erreur", e.getMessage());
        } catch (NumberFormatException e) {
            // ne rien faire
            createErrorDialogBox("Erreur","Erreur", "Vous devez entrez un montant !");
        }

    }

    private void setAffichageTransactionValide(boolean visible) {
        texteSucces.setVisible(visible);
    }

    private void clearFormulaireFacture() {
        nomAcheuteurInputField.setText("");
        totalSansTaxesInputField.setText("");
        taxesApplicablesInputField.setText("");
    }

    void updateTotalDons(BigDecimal montant) {
        totalDonsText.setText(montant.toString() + " $");
    }

    private ModePaiement determinerModePaiement() {
        Toggle selectione = methodePaiement.getSelectedToggle();

        return switch (((RadioButton) selectione).getText()) {
            case "Crédit" -> ModePaiement.CREDIT;
            case "Débit" -> ModePaiement.DEBIT;
            case "Comptant" -> ModePaiement.COMPTANT;
            default -> throw new MethodeDePaiementInconnueException("La méthode de paiement est incconue");
        };
    }

    private void createErrorDialogBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
