package nta_chat;

import com.sun.deploy.net.HttpResponse;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by massina on 09/07/2017.
 */
public class HTTP {
                private String response;
                private int responseCode;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public  void sendGet(String url) throws Exception {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        this.responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + this.responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        this.response=response.toString();


    }

    public void SendPOST(String myURL, String content) throws IOException {
        URL url = null;
        url = new URL(myURL);
        HttpURLConnection urlConn = null;
        urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setRequestMethod("POST");
        urlConn.setRequestProperty("Content-Type", "application/json");
        urlConn.connect();

        DataOutputStream output = null;
        DataInputStream input = null;
        output = new DataOutputStream(urlConn.getOutputStream());

                /*Construct the POST data.*/


    /* Send the request data.*/
        output.writeBytes(content);
        output.flush();
        output.close();

    /* Get response data.*/
         this.responseCode = urlConn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
       this.response=response.toString();

    }
}

