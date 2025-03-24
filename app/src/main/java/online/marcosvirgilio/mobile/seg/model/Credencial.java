package online.marcosvirgilio.mobile.seg.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Credencial {
    //atributos
    private String grantType;
    private String endpoint;
    private String clientID;
    private String clientScret;

    //CONSTRUTOR - inicializa atributos de um arquivo JSon
    public Credencial (JSONObject jp) {
        try {
            this.setGrantType(jp.getString("grant_type"));
            this.setEndpoint(jp.getString("token_endpoint"));
            this.setClientID(jp.getString("client_id"));
            this.setClientScret(jp.getString("client_secret"));
        } catch (Exception e) {
             Log.e("Crendencial", Objects.requireNonNull(e.getMessage()));
        }
    }
    //CONSTRUTOR - Inicializa os atributos para gerar Objeto Json
    public Credencial () throws Exception {
        this.setGrantType("client_credentials");
        this.setEndpoint("");
        this.setClientID("");
        this.setClientScret("");
    }
    //Metodo retorna o objeto com dados no formato JSON
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("grant_type", this.grantType);
            json.put("token_endpoint", this.endpoint);
            json.put("client_id", this.clientID);
            json.put("client_secret", this.clientScret);

        } catch (JSONException e) {
            Log.e("Crendencial", Objects.requireNonNull(e.getMessage()));
        }
        return json;
    }


    //métodos
    public String getGrantType(){
        return this.grantType;
    }

    public void setGrantType(String gt)  {
        if (gt.length() > 4 && gt.contains("_")) {
            this.grantType = gt;
        } else {
            this.grantType = "client_credentials";
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) throws Exception {
        if (endpoint.contains("http")){
            this.endpoint = endpoint;
        } else {
            throw new Exception("Endpoint inválido");
        }
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientScret() {
        return clientScret;
    }

    public void setClientScret(String clientScret) {
        this.clientScret = clientScret;
    }
}
