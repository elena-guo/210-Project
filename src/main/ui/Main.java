package ui;

import java.io.FileNotFoundException;

// Code referenced from TellerApp, Lab5, and JSONSerializationDemo

public class Main {
    public static void main(String[] args) {
        try {
            new CookbookApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}

