package com.lattice.bimbackend.forge;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ForgeConfiguartion {

    // set environment variables or hard-code here
    public static class credentials{
        public static String client_id = "zjgquwVfA6R5eQ7AyLKGz2kqCldGy83i";
        public static String client_secret = "pJOuNnVGamnATJ5T";
    };

    // Required scopes for your application on server-side
    public static ArrayList<String> scopeInternal = new ArrayList<String>() {{
        add("bucket:create");
        add("bucket:read");
        add("data:read");
        add("data:create");
        add("data:write");
    }};

    // Required scope of the token sent to the client
    public static ArrayList<String> scopePublic = new ArrayList<String>() {{
        add("viewables:read");
    }};

}
