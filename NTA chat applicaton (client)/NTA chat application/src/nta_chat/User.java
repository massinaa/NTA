package nta_chat;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by massina on 09/07/2017.
 */
public class User {
    private String username;
    private String name;
    private String password;
    private String password2;
    private String email;
    private String connect;
    private String _id;

    public User(String username, String name, String password, String password2, String email, String connect) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.password2 = password2;
        this.email = email;
        this.connect = connect;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", email='" + email + '\'' +
                ", connect='" + connect + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }

    public HTTP Login() {
        Gson gson = new Gson();
        String user_json = gson.toJson(this);
        HTTP http = new HTTP();
        try {
            http.SendPOST("http://localhost:3000/users/login", user_json);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (http);

    }

    public HTTP Register() {
        Gson gson = new Gson();
        HTTP http = new HTTP();
        String user_json = gson.toJson(this);
        String response = "";
        try {
            http.SendPOST("http://localhost:3000/users/register", user_json);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return (http);
    }

    public User GetUserByUsername() {
        HTTP res = new HTTP();
        Gson gson = new Gson();
        try {
            res.sendGet("http://localhost:3000/users/find/" + this.username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res.getResponseCode() == 200) {
            return gson.fromJson(res.getResponse(), this.getClass());
        }else{
            return null;
        }

    }


}



