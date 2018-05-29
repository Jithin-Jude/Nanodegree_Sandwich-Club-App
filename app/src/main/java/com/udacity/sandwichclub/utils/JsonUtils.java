package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName;
        String placeOfOrigin;
        String description;
        String image;


        try {
            //Create JSONObject of whole json string
            JSONObject rootObject = new JSONObject(json);

            //Get name JsonObject
            JSONObject subObj = rootObject.getJSONObject("name");

            //Get mainName and aka values
            mainName = subObj.getString("mainName");
            JSONArray aka = subObj.getJSONArray("alsoKnownAs");

            //Get PlaceOfOrigin
            placeOfOrigin = rootObject.getString("placeOfOrigin");

            //Get Description
            description = rootObject.getString("description");

            //GET image path
            String imagePath = rootObject.getString("image");

            //Get ingredient array
            JSONArray ingredientArray = rootObject.getJSONArray("ingredients");
            List<String> alsoKnownAsList = new ArrayList<>();

            //Iterate through the array of aka and add it to list
            for (int i = 0; i < aka.length(); i++) {
                String alsoKnownAs = aka.getString(i);
                alsoKnownAsList.add(alsoKnownAs);
            }

            //Iterate through the array of ingredients and add it to list

            List<String> ingredientList = new ArrayList<>();
            for (int i = 0; i < ingredientArray.length(); i++) {
                ingredientList.add(ingredientArray.getString(i));
            }

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imagePath, ingredientList);
            return sandwich;

        }catch (JSONException e){
            Log.e("JSON","JSON fetch error: "+e);
            return null;
        }
    }
}
