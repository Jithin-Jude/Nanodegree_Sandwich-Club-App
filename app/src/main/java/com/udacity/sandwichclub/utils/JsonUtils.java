package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        String mainName;
        String placeOfOrigin;
        String description;
        String image;


        try {
            //Create JSONObject of whole json string
            JSONObject rootObject = new JSONObject(json);

            //Get name JsonObject
            JSONObject subObj = rootObject.getJSONObject(KEY_NAME);

            //Get mainName and aka values
            mainName = subObj.getString(KEY_MAIN_NAME);
            JSONArray aka = subObj.getJSONArray(KEY_ALSO_KNOWN_AS);

            //Get PlaceOfOrigin
            placeOfOrigin = rootObject.getString(KEY_PLACE_OF_ORGIN);

            //Get Description
            description = rootObject.getString(KEY_DESCRIPTION);

            //GET image path
            String imagePath = rootObject.getString(KEY_IMAGE);

            //Get ingredient array
            JSONArray ingredientArray = rootObject.getJSONArray(KEY_INGREDIENTS);
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
