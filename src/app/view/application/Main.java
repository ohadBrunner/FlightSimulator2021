package app.view.application;

import app.model.TheModel;
import app.viewModel.ViewModel;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

 
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxl = new FXMLLoader();
            BorderPane root = fxl.load(getClass().getResource("./Sample.fxml").openStream());
            TheModel m = new TheModel();
            ViewModel vm = new ViewModel(m);
            MainController view = fxl.getController();
            view.init(vm);

            Scene scene = new Scene(root, 1400, 680);
            scene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());//check app
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
