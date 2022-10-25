package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {

    private Recipe recipeTest;
    Recipe r1 = new Recipe("Grilled Chicken Salad");
    Recipe r2 = new Recipe("Beef Teriyaki");
    Recipe r3 = new Recipe("Spinach Alfredo Pasta");

    @BeforeEach
    public void setup() {
        r1 = new Recipe("Grilled Chicken Salad");
        r2 = new Recipe("Beef Teriyaki");
        r3 = new Recipe("Spinach Alfredo Pasta");
    }

    @Test
    public void testConstructor() {
        assertEquals("Grilled Chicken Salad", r1.getTitle());
    }

    @Test
    public void testGetCuisine() {
        assertEquals(null, r1.getCuisine());
    }

    @Test
    public void testGetIngredients() {
        assertEquals(null, r1.getIngredients());
    }

    @Test
    public void testGetInstructions() {
        assertEquals(null, r1.getInstructions());
    }
}
