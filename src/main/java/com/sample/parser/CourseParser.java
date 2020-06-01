package com.sample.parser;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
@Component
public class CourseParser {

    private static final Gson GSON_OBJ = new Gson();
    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

    public <T> T getObjectsfromJson(final String json, final Class<T> classOfT) throws JsonSyntaxException {

        return GSON_OBJ.fromJson(json, classOfT);
    }

    public Gson getGsonObj() {
        return GSON_OBJ;
    }

    public GsonBuilder getGsonBuilderObj() {
        return GSON_BUILDER;
    }
}
