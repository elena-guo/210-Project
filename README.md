# CPSC 210: My Personal Project

## A Guide to Small Apartment Cooking: University Edition

I propose to design a simple cookbook catered towards university students living away from home. 
This application will allow users to view existing recipes, 
add delicious new recipes, remove recipes they don't enjoy, select a recipe and view the needed 
ingredients and instructions, and sort their recipes by type of cuisine.
Although this cookbook will contain easy-to-make recipes designed for students cooking in small spaces,
I am hopeful that aspiring young chefs and busy parents alike can all appreciate the 
flavourful yet effortless recipes comprising this cookbook.


Cookbooks are of interest to me as I recently moved into my own apartment at the beginning of July. 
Ever since, I have independently grocery shopping and cooking meals with the limited pots, pans, 
and sauces I own. While sometimes I follow recipes, more often times than not,
I simply look at what I have in the fridge and see what dishes I can create from them.
In doing so, I have discovered:

- grilled chicken salad tastes great with *green goddess* dressing
- napa cabbage with basa fillet tastes better with *medium-soft* tofu
- salmon can be marinated with *either* teriyaki sauce or *oisin sauce 
- room temperature rice causes *very* sticky stir-fry

With this cookbook, I hope to inspire comforting, nourishing homemade meals 
that can be made from the kitchens of university dorms everywhere.

## User Stories

- As a user, I want to be able to add recipes to my cookbook
- As a user, I want to be able to remove recipes from my cookbook
- As a user, I want to be able to view the page number of a chosen recipe
- As a user, I want to be able to select a recipe from my cookbook and view it in detail 
- As a user, I want to be able to view a list of all the recipe titles I have
- As a user, I want to be given the option to save my entire cookbook to file
- As a user, I want to be given the option to reload my entire cookbook from file 
and resume exactly where I left off at an earlier time

# Instructions for Grader
- You can generate the first required event by typing a recipe name into the text field and 
pressing the "return" key on your keyboard. You can also click the "Add Recipe" button displayed in the window.
- You can generate the second required event by selecting a recipe in the cookbook and clicking the 
"View Page Number" button.
- You can locate my visual component by selecting a recipe in the cookbook and clicking the
  "View Page Number" button.
- You can save the state of my application by going to "Menu" tab in the top left-hand corner, and clicking 
"Save cookbook to file", OR by typing option+S on the keyboard.
- You can reload the state of my application by going to "Menu" tab in the top left-hand corner, and clicking
  "Load cookbook from file", OR by typing option+L on the keyboard.

# Phase 4: Task 2

- Added recipe for noodles to cookbook on Wed Aug 10 11:17:07 PDT 2022
- Added recipe for salad to cookbook on Wed Aug 10 11:17:08 PDT 2022
- Added recipe for teriyaki to cookbook on Wed Aug 10 11:17:11 PDT 2022
- Retrieved page number of recipe for teriyaki on Wed Aug 10 11:17:13 PDT 2022
- Retrieved page number of recipe for noodles on Wed Aug 10 11:17:16 PDT 2022
- Removed recipe for noodles from cookbook on Wed Aug 10 11:17:19 PDT 2022
- Removed recipe for salad from cookbook on Wed Aug 10 11:17:19 PDT 2022
- Removed recipe for teriyaki from cookbook on Wed Aug 10 11:17:19 PDT 2022

# Phase 4: Task 3

Regarding the design presented in my UML class diagram for my cookbook application, 
I noticed that both the console-based CookbookApp Class and the graphical user interface 
CookbookGUI class had associations with the classes RecipeList, JsonReader and JsonWriter. 
The higher coupling between the two UI classes and the three Model classes means that any changes 
I make to RecipeList, JsonReader or JsonWriter would potentially be reflected in both CookbookApp and CookbookGUI,
causing a bug or compile error. If I were to refactor my design, I would consider removing the 
CookbookApp class entirely if there was no strict need to maintain a console-based UI. This would reduce the coupling
associated with the Model and UI classes, helping my design adhere more to the single point of control principle. 

Furthermore, certain methods that I created for the setup of my GUI in the CookbookGUI class were 
quite long and exhibited low cohesion. Specifically, my createButtons() method for my JPanel involved the creation of a JTextField along with 3 separate JButtons:
an add button, a remove button, and a page button. To improve code readability and ensure high method cohesion,
I would implement a separate method for the creation of my JTextField, and extract the creation and implementation of each JButton into its own separate method. 
I would then individually call those methods from within the createButtons() methods, allowing developers and anyone reading my code to immediately recognize
what buttons I am creating for my GUI. I would follow a similar extraction process for my createMenuBar() and createAndDisplayGUI() methods
in my CookbookGUI class. 

