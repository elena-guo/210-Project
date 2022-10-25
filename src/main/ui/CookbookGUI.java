package ui;

import model.Event;
import model.EventLog;
import model.Recipe;
import model.RecipeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Code referenced from ListDemo.java, MenuDemo.java

// Graphical user interface for Cookbook application
public class CookbookGUI extends JPanel implements ActionListener, ItemListener, ListSelectionListener {
    private JList<String> list;
    private DefaultListModel<String> listModel;

    private static final String addString = "Add Recipe";
    private static final String removeString = "Remove Recipe";
    private static final String pageString = "View Page Number";
    private JButton addButton;
    private JButton removeButton;
    private JButton pageButton;
    private JTextField nameTextField;

    private static final String JSON_STORE = "./data/recipeList.json";
    private RecipeList newRecipeList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: creates new instance of a list model and scroll pane
    public CookbookGUI() {
        super(new BorderLayout());

        newRecipeList = new RecipeList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // creates the recipe list and put it in a scroll pane
        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        createButtons();
        createPanel();

        add(listScrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates and sets functionality of buttons
    public void createButtons() {
        // creates add button
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        // creates remove button
        removeButton = new JButton(removeString);
        RemoveListener removeListener = new RemoveListener(removeButton);
        removeButton.setActionCommand(removeString);
        addButton.setEnabled(false);
        removeButton.addActionListener(removeListener);
        removeButton.setEnabled(false);

        // creates page number button
        pageButton = new JButton(pageString);
        PageListener pageListener = new PageListener(pageButton);
        pageButton.setActionCommand(pageString);
        pageButton.addActionListener(pageListener);
        pageButton.setEnabled(false);

        // creates text field
        nameTextField = new JTextField(10);
        nameTextField.addActionListener(addListener);
        nameTextField.getDocument().addDocumentListener(addListener);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds panel with buttons
    public void createPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(removeButton);
        buttonPane.add(nameTextField);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(addButton);
        buttonPane.add(pageButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // Class to help add a recipe to the cookbook
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // EFFECTS: constructs add listener
        public AddListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: adds new recipe given by user into recipe list
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameTextField.getText();

            if (name.equals("") || alreadyInList(name)) { // if user typed an already-existing name or empty
                Toolkit.getDefaultToolkit().beep();
                nameTextField.requestFocusInWindow();
                nameTextField.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); // get selected index from text bar
            if (index == -1) {                   // if nothing selected, insert item at beginning
                index = 0;
            } else {                             // add after the selected item
                index++;
            }

            // insert text at index
            newRecipeList.addRecipe(new Recipe(name));
            listModel.insertElementAt(nameTextField.getText(), index);
            // reset the text field
            nameTextField.requestFocusInWindow();
            nameTextField.setText("");
            // select new item and make it visible
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // EFFECTS: return true if list already contains user-typed name, otherwise false
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        // EFFECTS: [required by ListSelectionListener];
        //          calls enableButton();
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: [required by ListSelectionListener];
        //          calls handleEmptyTextField(e);
        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // EFFECTS: [required by ListSelectionListener];
        //          if text field is not empty, turns on button
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this, button
        // EFFECTS: if button not already enabled, turns on button, allowing button to be clicked
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this, button
        // EFFECTS: turns button off and returns true if text field is empty, false otherwise
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // Class to help remove a recipe from the cookbook
    class RemoveListener implements ActionListener {

        // EFFECTS: construct remove listener
        public RemoveListener(JButton button) {
            removeButton = button;
        }

        // MODIFIES: this
        // EFFECTS: removes selected recipe from recipe list
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String name = listModel.getElementAt(index);

            Recipe r = newRecipeList.retrieveRecipe(name);

            newRecipeList.removeRecipe(r);
            listModel.remove(index);

            int size = listModel.getSize();

            // nothing left in list, disable remove recipe button
            if (size == 0) {
                removeButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--; // remove item in last position
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // Class to help retrieve the page number of a selected recipe in the cookbook
    class PageListener implements ActionListener {

        // EFFECTS: construct page listener
        public PageListener(JButton button) {
            pageButton = button;
        }

        // EFFECTS: returns message stating page number (index) of selected item in list
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String name = listModel.getElementAt(index);
            int page = newRecipeList.getPageNumberOfRecipe(name);

            JOptionPane.showMessageDialog(pageButton, "Your recipe for " + name + " is on page " + page);

            int size = listModel.getSize();

            if (size == 0) {
                pageButton.setEnabled(false);
            } else {
                pageButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this, removeButton, pageButton
    // EFFECTS: [required by ListSelectionListener];
    //          if no selection is made, disable remove and page button;
    //          otherwise, if a selection has been made, enable remove and page button
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);
                pageButton.setEnabled(false);
            } else {
                removeButton.setEnabled(true);
                pageButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a menu bar for user to save and load data from JSON file
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        // adding save item option to menu bar
        JMenuItem saveItem = new JMenuItem("Save cookbook to file", KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        saveItem.setActionCommand("Save");
        saveItem.addActionListener(this);
        menu.add(saveItem);

        // adding load item option to menu bar
        JMenuItem loadItem = new JMenuItem("Load cookbook from file", KeyEvent.VK_L);
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        loadItem.setMnemonic(KeyEvent.VK_B);
        loadItem.setActionCommand("Load");
        loadItem.addActionListener(this);
        menu.add(loadItem);

        return menuBar;
    }

    // EFFECTS: [required by ActionListener];
    //          determines if action performed should save or load data from JSON file
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            saveCookbook();
        } else if (e.getActionCommand().equals("Load")) {
            loadCookbook();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves recipes in cookbook to JSON destination file
    private void saveCookbook() {
        try {
            jsonWriter.open();
            jsonWriter.write(newRecipeList);
            jsonWriter.close();
            System.out.println("You have saved your file to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads recipes in cookbook from JSON destination file
    private void loadCookbook() {
        try {
            newRecipeList = jsonReader.read();
            listModel.clear();
            createListModel(listModel);
            System.out.println("You have loaded your cookbook from + " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new list model with recipe names added to it
    private void createListModel(DefaultListModel<String> listModel) {
        for (Recipe r : newRecipeList.getRecipes()) {
            listModel.addElement(r.getTitle());
        }
    }

    // EFFECTS: [required by ItemListener];
    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    // EFFECTS: prints description and date of each event that occurred throughout duration of execution
    public static void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.getDescription() + " on " + e.getDate());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new JFrame and displays frame to window
    public static void createAndDisplayGUI() {
        // Create and set up window
        JFrame frame = new JFrame();
        frame.setTitle("My Cookbook");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });

        // Create and set up content pane
        CookbookGUI newContentPane = new CookbookGUI();
        frame.setJMenuBar(newContentPane.createMenuBar());
        frame.setContentPane(newContentPane);

        // Display window
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndDisplayGUI();
            }
        });
    }
}


