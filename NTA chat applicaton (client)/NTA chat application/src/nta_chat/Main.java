package nta_chat;


import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nta_chat_controllers.*;



import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {

            View view=new View();
            view.login_view();

        }
        catch (IOException e) {
            e.printStackTrace();

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
