package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    // sandwich_description views references
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mAlsoKnownTextView;
    private TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Obtaining all references to sandwich_description views
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);

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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(final Sandwich sandwich) {
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());

        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (!alsoKnownList.isEmpty()) {
            mAlsoKnownTextView.setText(alsoKnownList.get(0));
            for(int i = 1; i < alsoKnownList.size(); ++i) {
                mAlsoKnownTextView.append("\n" + alsoKnownList.get(i));
            }
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (!ingredientsList.isEmpty()) {
            mIngredientsTextView.setText(ingredientsList.get(0));
            for(int i = 1; i < ingredientsList.size(); ++i) {
                mIngredientsTextView.append("\n" + ingredientsList.get(i));
            }
        }
    }
}
