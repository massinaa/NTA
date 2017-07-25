package nta_chat_controllers;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import nta_chat.HTTP;
import nta_chat.Message;
import nta_chat.User;
import nta_chat.View;

import java.io.IOException;

public class Chat_controller {

    @FXML private MenuItem menu_informations;
    @FXML private MenuItem meu_deconnect;
    @FXML private MenuItem menu_delete;
    @FXML private ListView<String> list_users;
    @FXML private MenuItem menu_show_user;
    @FXML private MenuItem menu_delete_user;
    @FXML private Label hello_user;
    @FXML private ListView<String> list_msg;
    @FXML private Button btn_send;
    @FXML private TextArea txt_message;
          private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @FXML void deconnecter() {
        Gson gson=new Gson();
        HTTP http=new HTTP();
        String user;
        user=gson.toJson(this.user);
        try {
            http.SendPOST("http://localhost:3000/users/logout",user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (http.getResponseCode()==200){
            View view=new View();
            try {
                view.login_view();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML void Actualiser() {
        try {
            init_users();
            init_discussion(list_users.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public String selectedUser(){
        return (list_users.getSelectionModel().getSelectedItem());
    }

    @FXML
    void delete_user() {

    }

    @FXML
    void show_discuss() {
        init_discussion(list_users.getSelectionModel().getSelectedItem());


    }

    @FXML
    void send_message() throws IOException {


        Gson gson=new Gson();
        Message msg=new Message();
        String selectedUser = list_users.getSelectionModel().getSelectedItem();
        User user=new User();
        user.setUsername(selectedUser);
        User user2=user.GetUserByUsername();

        msg.setMsg(txt_message.getText());
        msg.setIdsender(this.user.get_id());
        msg.setIdreceiver(user2.get_id());

        System.out.print("id du sender "+this.user.get_id());
        //list_users.getSelectionModel().getSelectedIndex();
        HTTP http=new HTTP();
        http.SendPOST("http://localhost:3000/msg/send",gson.toJson(msg));
        System.out.println(gson.toJson(msg));
        if (http.getResponseCode()==201){
            list_msg.getItems().add(this.user.getUsername()+" : "+ msg.getMsg());
            txt_message.setText("");
        }else{
            System.out.println("Erreur");
        }

    }

    @FXML
    void show_information() {

    }

    @FXML
    void show_user() {

    }
    public User[] get_connected_users() throws Exception {
        HTTP http = new HTTP();
        User[] users=null;
        http.sendGet("http://localhost:3000/users/connect");
        if (http.getResponseCode()==200){
            Gson gson=new Gson();
            users=new User [300];
            users = gson.fromJson(http.getResponse(), users.getClass());
            return users;

        }else{
            return null;
        }
    }
    public void init_users() throws Exception {

       User[] users= this.get_connected_users();
       ObservableList pseudos = FXCollections.observableArrayList();


        for (User user:users) {
           pseudos.add(user.getUsername());
        }
        list_users.setItems(pseudos);
        list_users.getSelectionModel().select(0);

    }
    public void set_hello(){
        this.hello_user.setText("Hello "+ this.user.getUsername()+" !");
    }
    public void init_discussion(String username){

        User user=new User();
        HTTP http=new HTTP();
        Gson gson= new Gson();
        user.setUsername(username);
        User user2=user.GetUserByUsername();
        Message[] messages=new Message[3000];
        list_msg.getItems().removeAll();

        try {
            http.sendGet("http://localhost:3000/msg/send="+this.user.get_id()+"&rec="+user2.get_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (http.getResponseCode()==200){
            messages=gson.fromJson(http.getResponse(),messages.getClass());
            ObservableList discuss = FXCollections.observableArrayList();
            for (Message m:messages) {

                if(m.getIdsender().equals(user2.get_id())){
                    discuss.add(user2.getUsername()+" : "+m.getMsg());
                }else{
                    discuss.add(this.user.getUsername()+" : "+m.getMsg());
                }

            }
            list_msg.setItems(discuss);
        }



    }

}

