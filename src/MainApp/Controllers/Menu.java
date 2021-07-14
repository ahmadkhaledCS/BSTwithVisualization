package MainApp.Controllers;

import MainApp.Main;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {

    public void Switch(Event event) throws IOException {
        Button b=(Button) event.getSource();
        String file="/MainApp/FXMLFiles/Numbers.fxml";
        if(b.getText().equals("letters")){
            file= "/MainApp/FXMLFiles/Letters.fxml";
        }else if(b.getText().equals("Custom binary tree")){
            file= "/MainApp/FXMLFiles/Custom.fxml";
        }
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(Main.class.getResource(file));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 720);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
