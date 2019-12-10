package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Main extends Application {
    static public String[] hours = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13",
            "14","15","16","17","18","19","20","21","22","23"};
    static public String[] minutes = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13",
            "14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
            "32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50",
            "51","52","53","54","55","56","57","58","59"};
    static public String[] seconds = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13",
            "14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
            "32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50",
            "51","52","53","54","55","56","57","58","59"};


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("USRP B200 Start Configuration");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
