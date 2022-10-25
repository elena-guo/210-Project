package persistence;

import model.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRecipe(String title, Recipe recipe) {
        assertEquals(title, recipe.getTitle());
    }
}
