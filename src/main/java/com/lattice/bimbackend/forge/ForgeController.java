package com.lattice.bimbackend.forge;

import com.autodesk.client.auth.OAuth2TwoLegged;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/forge")
public class ForgeController {

    @Autowired
    private Oauth oauth;

    @GetMapping(value = "")
    @CrossOrigin
    protected ResponseEntity doGet() {
        JSONObject obj = new JSONObject();
        try {
            // get oAuth of public, in order to get the token with limited permission
            OAuth2TwoLegged forgeOAuth = oauth.getOAuthPublic();
            String token = forgeOAuth.getCredentials().getAccessToken();
            // this is a timestamp, not the exact value of expires_in, so calculate back
            // client side will need this. though not necessary
            long expire_time_from_SDK = forgeOAuth.getCredentials().getExpiresAt();
            // because we do not know when the token is got, align to current time
            // which will be a bit less than what Forge sets (say 3599 seconds). This makes
            // sense.
            long expires_in = (long) (expire_time_from_SDK - DateTime.now().toDate().getTime()) / 1000;
            // send to client
            obj.put("access_token", token);
            obj.put("expires_in", expires_in);
            return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
        } catch (Exception var2) {
            System.out.print("get token exception: " + var2.toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
