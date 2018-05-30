package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView integrated, description, placeoforgin, alsoKnownAs;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         final ImageView ingredientsIv = findViewById(R.id.image_iv);
         integrated = findViewById(R.id.ingredients_tv);
         description = findViewById(R.id.description_tv);
         placeoforgin = findViewById(R.id.origin_tv);
         alsoKnownAs = findViewById(R.id.also_known_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Toast.makeText(this, "No Sandwich data", Toast.LENGTH_SHORT).show();
            closeOnError();
            return;
        }

        populateUI();

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(this.getResources()
                        .getDrawable(R.drawable.ic_error_outline_black_720dp))
                .error(this.getResources()
                        .getDrawable(R.drawable.ic_error_outline_black_720dp))
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        alsoKnownAs.setText(TextUtils.join("\n", sandwich.getAlsoKnownAs()));
        integrated.setText(TextUtils.join("\n", sandwich.getIngredients()));
        description.setText(sandwich.getDescription());
        placeoforgin.setText(sandwich.getPlaceOfOrigin());

    }
}
