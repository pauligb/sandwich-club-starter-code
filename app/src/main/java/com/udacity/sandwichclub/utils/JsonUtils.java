package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        // Properties from Sandwich
        String mainName;
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<String>();
        try {
            // JSON Objects
            JSONObject jSandwich = new JSONObject(json);
            JSONObject jName = jSandwich.getJSONObject("name");
            JSONArray jAlsoKnownAs = jName.getJSONArray("alsoKnownAs");
            JSONArray jIngredients = jSandwich.getJSONArray("ingredients");

            // Populating sandwich information
            mainName = jName.getString("mainName");
            for(int i = 0; i < jAlsoKnownAs.length();++i){
                alsoKnownAs.add(jAlsoKnownAs.getString(i));
            }
            placeOfOrigin = jSandwich.getString("placeOfOrigin");
            description = jSandwich.getString("description");
            image = jSandwich.getString("image");
            for(int i = 0; i < jIngredients.length(); ++i){
                ingredients.add(jIngredients.getString(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Return a new Sandwich
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
