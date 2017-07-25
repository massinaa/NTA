package nta_chat_controllers;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import nta_chat.HTTP;
import nta_chat.User;
import nta_chat.View;

import java.io.IOException;

public class Login_controller {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button btn_login;
    @FXML private Hyperlink link_register;
    @FXML private Label flash_msg;
          private User user=null;

    @FXML void go_register(ActionEvent event) {
        View view=new View();
        try {
            view.Register_view();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML void login(ActionEvent event) throws Exception {
        Gson gson=new Gson();
        View view=new View();
        User util=new User(username.getText(),"",password.getText(),"","","");
        HTTP res=util.Login();
        if (res.getResponse().equals("Invalid username")){
            flash_msg.setText("Invalid Username, Enter a valid username.");
        }else{
            if (res.getResponse().equals("Invalid Password")){
                flash_msg.setText("Invalid Password... Try Again please.");

            }else{

                User user=new User();
                user= gson.fromJson(res.getResponse(),User.class);
                System.out.println("reussi :  "+user.toString());
                view.chat_view(user);

            }
        }
    }

}


