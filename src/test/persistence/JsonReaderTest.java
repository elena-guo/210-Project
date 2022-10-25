package persistence;

import model.Recipe;
import model.RecipeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RecipeList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyRecipeList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRecipeList.json");
        try {
            RecipeList rl = reader.read();
            List<Recipe> recipes = rl.getRecipes();
            assertEquals(0, recipes.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralRecipeList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecipeList.json");
        try {
            RecipeList rl = reader.read();
            List<Recipe> recipes = rl.getRecipes();
            assertEquals(2, recipes.size());
            checkRecipe("Salad", recipes.get(0));
            checkRecipe("Udon", recipes.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
