/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.wrapper;

import com.google.gson.Gson;
import nhunnq.Users.UsersDTO;
import nhunnq.utilities.NetUtils;

/**
 *
 * @author USER
 */
public class APIWrapper {
    private static String appID ="2724172921186403";
    private static String appSecret = "aa781cad33589a7a10f0a146a82477df";
    private static String redirectUrl = "http://localhost:8084/DreamTravelling/LoginFacebook";
    private String accessToken;
    private Gson gson;

    public APIWrapper() {
        gson = new Gson();
    }
    
    public static String getDialogLink(){
        String dialogLink = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s";
        return String.format(dialogLink, appID,redirectUrl);
    }
    
    public String getAcessToken(String code){
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=%s"
                + "&client_secret=%s"
                + "&redirect_uri=%s"
                + "&code=%s";
        accessTokenLink = String.format(accessTokenLink, appID, appSecret, redirectUrl, code);
        String result = NetUtils.GetResult(accessTokenLink);
        System.out.println(result);
        String token = result.substring(result.indexOf(":") + 1, result.indexOf(","));
        return token;
    }
    
    public UsersDTO getUserDTO(){
        String accessTokenReal = accessToken.replaceAll("\"", "");
        
        String infoUrl = "https://graph.facebook.com/me?access_token=" + accessTokenReal;
        String result = NetUtils.GetResult(infoUrl);
        UsersDTO user = gson.fromJson(result, UsersDTO.class);
        return user;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
