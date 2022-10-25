package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a recipe having a title, ingredients, and instructions
public class Recipe implements Writable {
    private final String title;
    private String cuisine;
    private String ingredients;
    private String instructions;

    // EFFECTS: constructs a recipe with a title, no ingredients and no instructions
    public Recipe(String title) {
        this.title = title;
        cuisine = null;
        ingredients = null;
        instructions = null;
    }

    // EFFECTS: returns title of recipe
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns type of cuisine of recipe
    public String getCuisine() {
        return cuisine;
    }

    // EFFECTS: returns list of ingredients needed for recipe
    public String getIngredients() {
        return ingredients;
    }

    // EFFECTS; returns set of instructions needed to make recipe
    public String getInstructions() {
        return instructions;
    }

    // EFFECTS: return recipe as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        return json;
    }
}
