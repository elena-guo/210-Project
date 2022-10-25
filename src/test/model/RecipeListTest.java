package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeListTest {

    private RecipeList testRecipeList;
    Recipe r1 = new Recipe("Grilled Chicken Salad");
    Recipe r2 = new Recipe("Beef Teriyaki");
    Recipe r3 = new Recipe("Spinach Alfredo Pasta");
    Recipe r4 = new Recipe("Spring Rolls");
    Recipe r5 = new Recipe("Shrimp Scampi");

    @BeforeEach
    public void setup() {
        testRecipeList = new RecipeList();
    }

    @Test
    public void testRecipeList() {
        assertTrue(testRecipeList.isEmpty());
    }

    @Test
    public void testAddRecipe() {
        assertTrue(testRecipeList.addRecipe(r1));
        assertEquals(1, testRecipeList.length());

        assertTrue(testRecipeList.addRecipe(r2));
        assertEquals(2, testRecipeList.length());
    }

    @Test
    public void testAddSameRecipe() {
        assertTrue(testRecipeList.addRecipe(r1));
        assertEquals(1, testRecipeList.length());

        assertFalse(testRecipeList.addRecipe(r1));
        assertEquals(1, testRecipeList.length());
    }

    @Test
    public void testRemoveRecipeSuccess() {
        testRecipeList.addRecipe(r1);
        testRecipeList.addRecipe(r2);
        testRecipeList.addRecipe(r3);
        assertEquals(3, testRecipeList.length());

        testRecipeList.removeRecipe(r3);
        assertEquals(2, testRecipeList.length());

        testRecipeList.removeRecipe(r1);
        assertEquals(1, testRecipeList.length());
    }

    @Test
    public void testRemoveRecipeFail() {
        testRecipeList.addRecipe(r1);
        testRecipeList.addRecipe(r2);
        testRecipeList.addRecipe(r3);

        testRecipeList.removeRecipe(r4);
        assertEquals(3, testRecipeList.length());
    }

    @Test
    public void testRetrieveRecipe() {
        testRecipeList.addRecipe(r1);
        testRecipeList.addRecipe(r3);

        assertEquals(r1, testRecipeList.retrieveRecipe("Grilled Chicken Salad"));
        assertEquals(null, testRecipeList.retrieveRecipe("Beef Teriyaki"));
        assertEquals(r3, testRecipeList.retrieveRecipe("Spinach Alfredo Pasta"));
    }

    @Test
    public void testGetPageNumberOfRecipe() {
        testRecipeList.addRecipe(r1);
        testRecipeList.addRecipe(r2);
        testRecipeList.addRecipe(r3);
        testRecipeList.addRecipe(r4);

        assertEquals(2, testRecipeList.getPageNumberOfRecipe(r2.getTitle()));
        assertEquals(4, testRecipeList.getPageNumberOfRecipe(r4.getTitle()));
        assertEquals(-1, testRecipeList.getPageNumberOfRecipe(r5.getTitle()));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(testRecipeList.isEmpty());

        testRecipeList.addRecipe(r1);
        assertFalse(testRecipeList.isEmpty());
    }
}