package nta_chat_controllers;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import nta_chat.*;

import java.io.IOException;

public class Register_controller {

    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private Button btn_annuler;
    @FXML private TextField name;
    @FXML private TextField password;
    @FXML private TextField password2;
    @FXML private Label flash_msg;
    @FXML private Button btn_register;

    @FXML
    void go_login(ActionEvent event) throws IOException {
        View view=new View();
        view.login_view();

    }

    @FXML
    void register(ActionEvent event) {

        User user = new User(username.getText(), name.getText(), password.getText(), password2.getText(), email.getText(), "true");
        HTTP res = user.Register();
        Gson gson = new Gson();
        Erreur erreurs[] = new Erreur[8];
        if (res.getResponseCode() == 200) {
            flash_msg.setText("Invalid Username, choose another please.");
        }else{
            if (res.getResponseCode() == 202) {
                erreurs = gson.fromJson(res.getResponse(), erreurs.getClass());
                for (Erreur e : erreurs) {
                    if (e.getParam().equals("email")){
                        email.setText(e.getMsg()+".");
                    }
                    if (e.getParam().equals("name")){
                        name.setText(e.getMsg()+".");
                    }
                    if (e.getParam().equals("username")){
                        username.setText(e.getMsg()+".");
                    }
                    if (e.getParam().equals("password")){
                        password.setText(e.getMsg()+".");
                    }
                    if (e.getParam().equals("password2")){
                        password2.setText(e.getMsg()+".");
                    }
                }
            }else{
                if (res.getResponseCode() == 201) {
                    user = gson.fromJson(res.getResponse(), User.class);
                    View view=new View();
                    try {
                        view.chat_view(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.print("erreur");
                }
            }

        }



    }
}
