package com.example.shalisa.recipebox;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // grab recipe from intent
        final Recipe recipe = getIntent().getExtras().getParcelable("recipe_key");
        Log.d("DATABASE", recipe.toString());

        // set recipe header?
        TextView recipe_name = (TextView) findViewById(R.id.recipeTitle);
        recipe_name.setText(recipe.getName());

        // favorites button
        final ImageButton favBtn = (ImageButton) findViewById(R.id.favoriteStar);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipe.isFavorite()) {
                    recipe.setFavorite(false); //TODO: propagate favorites to database
                    favBtn.setImageDrawable(getResources().getDrawable(
                            R.drawable.btn_star_off_disabled_holo_light));
                } else {
                    recipe.setFavorite(true);
                    favBtn.setImageDrawable(getResources().getDrawable(
                            R.drawable.btn_star_on_pressed_holo_light));
                }
            }
        });

        // set recipe image
        ImageView recipe_img = (ImageView) findViewById(R.id.recipeImg);
        Picasso.with(this).load(recipe.getImgURL()).into(recipe_img);

        // set recipe ingredients
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            Log.d("DATABASE", ingredient.toString());
            LinearLayout item = (LinearLayout) findViewById(R.id.recipeIngredients);
            View ingView = getLayoutInflater().inflate(R.layout.ingredient_item, null);

            // display the quantity
            TextView quantView = (TextView) ingView.findViewById(R.id.quantityItem);
            String fractionQuantity = ingredient.getQuantityString();
            quantView.setText(fractionQuantity);

            // display the unit ingredient
            TextView ing_View = (TextView) ingView.findViewById(R.id.ingredientItem);
            ing_View.setText(ingredient.getUnitString() + " " + ingredient.getName());

            item.addView(ingView);
        }

        // set recipe instructions
        List<String> instructions = recipe.getInstructions();
        for (int i = 0; i < instructions.size(); i++) {
            LinearLayout item = (LinearLayout) findViewById(R.id.recipeInstructions);
            View instView = getLayoutInflater().inflate(R.layout.instruction_item, null);

            // number the instruction
            TextView num_View = (TextView) instView.findViewById(R.id.recipeNum);
            num_View.setText(Integer.toString(i+1) + ".");

            // display the instruction
            TextView inst_View = (TextView) instView.findViewById(R.id.instructionItem);
            inst_View.setText(instructions.get(i));

            item.addView(instView);

        }

    }
}
