package ui;

import model.Recipe;
import model.RecipeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Console-based Cookbook application
public class CookbookApp {
    private static final String JSON_STORE = "./data/recipeList.json";
    private Scanner input;
    private RecipeList newRecipeList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs cookbook and runs application
    public CookbookApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCookbook();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runCookbook() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();

        System.out.println("Welcome to your cookbook! How would you like to get started?");

        while (keepGoing) {
            displayOptions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("8")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nYou have closed your cookbook. See you next time!");
    }

    // MODIFIES: this
    // EFFECTS: initializes new instance of an empty recipe list
    private void init() {
        newRecipeList = new RecipeList();
    }

    // EFFECTS: displays menu of options for user to select from
    public void displayOptions() {
        System.out.println("\nPlease select from:");
        System.out.println("\t1: Add a recipe");
        System.out.println("\t2: Remove a recipe");
        System.out.println("\t3: View page number of recipe");
        System.out.println("\t4: View a recipe in detail");
        System.out.println("\t5: Print all recipes");
        System.out.println("\t6: Save cookbook to file");
        System.out.println("\t7: Load cookbook from file");
        System.out.println("\t8: Quit my cookbook");
    }

    // EFFECTS: processes user command
    public void processCommand(String command) {
        if (command.equals("1")) {
            requestRecipe();
        } else if (command.equals("2")) {
            removeRecipe();
        } else if (command.equals("3")) {
            getRecipePageNumber();
        } else if (command.equals("4")) {
            viewRecipeInDetail();
        } else if (command.equals("5")) {
            printAllRecipes();
        } else if (command.equals("6")) {
            saveRecipeList();
        } else if (command.equals("7")) {
            loadRecipeList();
        } else {
            System.out.println("Invalid selection, please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to type recipe title
    public void requestRecipe() {
        System.out.println("What is the title of the recipe you would like to add?");
        String title = input.next();

        newRecipeList.addRecipe(new Recipe(title));

        System.out.println("Your cookbook now contains the following recipe: " + title);
    }

    // MODIFIES: this
    // EFFECTS: if found, removes recipe from cookbook
    public void removeRecipe() {
        System.out.println("Would is the title of the recipe you would like to remove?");
        String title = input.next();
        Recipe r = newRecipeList.retrieveRecipe(title);

        if (!(r == null)) {
            newRecipeList.removeRecipe(r);
            System.out.println("You have just removed the recipe for " + r.getTitle());
            System.out.println("Your cookbook now contains " + newRecipeList.length() + " recipes." + "\n");
        } else {
            System.out.println("It appears your cookbook does not contain a recipe for " + title + "\n");
        }
    }

    // EFFECTS: if found, returns page number of recipe given the recipe title
    public void getRecipePageNumber() {
        System.out.println("What is the title of the recipe you would like to see the page number of?");
        String title = input.next();
        Recipe r = newRecipeList.retrieveRecipe(title);

        if (!(r == null)) {
            System.out.println("Your recipe for " + title + " is found on page "
                    + newRecipeList.getPageNumberOfRecipe(title));
        } else {
            System.out.println("It appears your cookbook does not contain a recipe for " + title + "\n");
        }
    }

    // EFFECTS: returns ingredients and instructions of recipe given the recipe title
    public void viewRecipeInDetail() {
        System.out.println("What is the title of the recipe you would like to view in detail?");
        String title = input.next();
        Recipe r = newRecipeList.retrieveRecipe(title);

        if (!(r == null)) {
            System.out.println("These are the ingredients needed for " + title + ":" + "\t");
            r.getIngredients();
            System.out.println("These are the instructions on how to make " + title + ":" + "\t");
            r.getInstructions();

        } else {
            System.out.println("It appears your cookbook does not contain a recipe for " + title + "\n");
        }
    }

    // EFFECTS: prints all the recipe titles of recipes in the cookbook
    private void printAllRecipes() {
        List<Recipe> recipes = newRecipeList.getRecipes();

        System.out.println("Here are all of the recipes in your cookbook!");
        for (Recipe r : recipes) {
            System.out.println(r.getTitle());
        }
    }

    // EFFECTS: saves the cookbook to file
    public void saveRecipeList() {
        try {
            jsonWriter.open();
            jsonWriter.write(newRecipeList);
            jsonWriter.close();
            System.out.println("You have saved your file to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the cookbook from file
    public void loadRecipeList() {
        try {
            newRecipeList = jsonReader.read();
            System.out.println("You have loaded your cookbook from + " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}




