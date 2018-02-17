package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        /* Also known as */
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        String aka = TextUtils.join(", ", sandwich.getAlsoKnownAs());
        alsoKnownAs.setText(aka.isEmpty() ? getString(R.string.na) : aka);

        /* Place of origin */
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin().isEmpty() ? getString(R.string.unknown) : sandwich.getPlaceOfOrigin());

        /* Description */
        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        /* Ingredients */
        TextView ingredients = findViewById(R.id.ingredients_tv);
        ingredients.setText(getAsBulletList(sandwich.getIngredients()));
    }

    private String getAsBulletList(List<String> list){
        if(list == null || list.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for(String str : list){
            sb.append(String.format("\u2022 %s\n", str));
        }

        return sb.toString();
    }
}
