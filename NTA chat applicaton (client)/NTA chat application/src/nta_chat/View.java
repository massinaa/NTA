package nta_chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nta_chat_controllers.Chat_controller;
import nta_chat_controllers.Login_controller;
import nta_chat_controllers.Register_controller;

import java.io.IOException;

/**
 * Created by massina on 11/07/2017.
 */
public class View {
    private static Stage window=new Stage();

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void login_view() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/login_view.fxml"));
        window.setTitle("Nta Chat application - login");
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        Login_controller controller = loader.getController();
        window.show();
    }

    public void Register_view() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/register_view.fxml"));
        window.setTitle("Nta Chat application - Register ");

        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        Register_controller controller = loader.getController();
        window.show();
    }

    public void chat_view(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/chat_view.fxml"));
        window.setTitle("Nta Chat application - chat");

        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        Chat_controller controller = loader.getController();
        controller.setUser(user);
        try {
            controller.init_users();
            controller.set_hello();
            controller.init_discussion(controller.selectedUser());

        } catch (Exception e) {
            e.printStackTrace();
        }
        window.show();
    }
}
