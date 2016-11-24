package com.redis.cache.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GsonBuilderUtil {

    public static Gson create() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(java.util.Date.class, new DateSerializer());
        gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer());
        Gson gson = gb.create();
        return gson;
    }
}
