package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG_NAME = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String jsonString) {
        try{
            JSONObject json = new JSONObject(jsonString);
            JSONObject name = json.getJSONObject("name");
            JSONArray aka = name.getJSONArray("alsoKnownAs");

            Sandwich sandwich = new Sandwich();
            sandwich.setMainName(name.getString("mainName"));
            sandwich.setAlsoKnownAs(toStringList(aka));
            sandwich.setPlaceOfOrigin(json.getString("placeOfOrigin"));
            sandwich.setDescription(json.getString("description"));
            sandwich.setImage(json.getString("image"));
            sandwich.setIngredients(toStringList(json.getJSONArray("ingredients")));

            return sandwich;
        } catch(JSONException e){
            Log.e(TAG_NAME, "Error parsing JSON", e);
        }

        return null;
    }

    private static List<String> toStringList(JSONArray jsonArray) throws JSONException{
        List<String> list = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
