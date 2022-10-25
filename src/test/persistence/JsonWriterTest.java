package persistence;

import model.Recipe;
import model.RecipeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            RecipeList rl = new RecipeList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyRecipeList() {
        try {
            RecipeList rl = new RecipeList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecipeList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecipeList.json");
            rl = reader.read();
            assertEquals(0, rl.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralRecipeList() {
        try {
            RecipeList rl = new RecipeList();
            rl.addRecipe(new Recipe("Salad"));
            rl.addRecipe(new Recipe("Udon"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipeList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRecipeList.json");
            rl = reader.read();
            List<Recipe> recipes = rl.getRecipes();
            assertEquals(2, recipes.size());
            checkRecipe("Salad", recipes.get(0));
            checkRecipe("Udon", recipes.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}


