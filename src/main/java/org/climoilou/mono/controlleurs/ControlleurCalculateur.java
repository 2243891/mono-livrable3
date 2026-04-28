package org.climoilou.mono.controlleurs;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.climoilou.mono.models.EtatApplication;
/// Montant max
public class ControlleurCalculateur extends Application {
    @FXML
    private Button ajouterFactureBouton;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calculateur de dons");
        EtatApplication etatApplication = new EtatApplication();

        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("ControlleurGraphiqueCalculateur.fxml"));
        fxmlLoader1.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return new ControlleurGraphiqueCalculateur(etatApplication);
            }
        });

        Scene scene = new Scene(fxmlLoader1.load(), 1920, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
