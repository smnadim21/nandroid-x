package com.smnadim21.nadx.tools.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    List<JSONObject> jsonObjects;

    public JsonHandler() {

    }

    public JsonHandler(List<JSONObject> jsonObjects) {
        this.jsonObjects = jsonObjects;
    }

    public List<JSONObject> toList(JSONArray jsonArray) throws JSONException {
        List<JSONObject> jObj = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jObj.add(jsonArray.getJSONObject(i));
        }
        return jObj;
    }

    public JsonHandler fromList(List<JSONObject> jsonObjects) {
        this.jsonObjects = jsonObjects;
        return this;
    }

    public List<String> extract(String name) {
        List<String> extraction = new ArrayList<>();
        for (JSONObject object : jsonObjects) {
            extraction.add(object.optString(name));
        }
        return extraction;
    }
}
