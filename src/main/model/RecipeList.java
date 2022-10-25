package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of recipes in the cookbook
public class RecipeList implements Writable {
    private final ArrayList<Recipe> recipeList;

    // EFFECTS: constructs new instance of RecipeList as an empty list
    public RecipeList() {
        recipeList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if the list does not already contain the Recipe we are trying to add
    //            - add Recipe title to end of list
    //            - returns true
    //          otherwise, returns false
    public boolean addRecipe(Recipe r) {
        if (!recipeList.contains(r)) {
            recipeList.add(r);
            EventLog.getInstance().logEvent(new Event("Added recipe for " + r.getTitle() + " to cookbook"));
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes the Recipe from the list
    public void removeRecipe(Recipe r) {
        recipeList.remove(r);
        EventLog.getInstance().logEvent(new Event("Removed recipe for " + r.getTitle() + " from cookbook"));
    }

    // EFFECTS: returns recipe if recipeList contains recipe title, null otherwise
    public Recipe retrieveRecipe(String t) {
        for (Recipe r : recipeList) {
            if (r.getTitle().equals(t)) {
                return r;
            }
        }
        return null;
    }

    // EFFECTS: if there is a Recipe in the list with the given recipe title
    //            - return the page number of the Recipe with the given title
    //          otherwise, return -1
    public int getPageNumberOfRecipe(String t) {
        for (Recipe r : recipeList) {
            if (r.getTitle().equals(t)) {
                EventLog.getInstance().logEvent(new Event("Retrieved page number of recipe for " + r.getTitle()));
                return recipeList.indexOf(r) + 1;
            }
        }
        return -1;
    }

    // EFFECTS: returns true if list is empty, false otherwise
    public boolean isEmpty() {
        return recipeList.isEmpty();
    }

    // EFFECTS: returns the current number of recipes in the list
    public int length() {
        return recipeList.size();
    }

    // EFFECTS: returns an unmodifiable list of recipes in the recipe list
    public List<Recipe> getRecipes() {
        return Collections.unmodifiableList(recipeList);
    }

    // EFFECTS: returns recipes in recipe list as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes", recipesToJson());
        return json;
    }

    // EFFECTS: returns recipes in recipe list as a JSON array
    public JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe r : recipeList) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}

