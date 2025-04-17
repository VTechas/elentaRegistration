package org.example;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String specialChars = "!@#$%^&*()_+-=[]{}|;:',.<>/?";
                int length = 1;
                StringBuilder randomString = new StringBuilder();

                Random rand = new Random();

                for (int i = 0; i < length; i++) {
                    int index = rand.nextInt(specialChars.length());
                    randomString.append(specialChars.charAt(index));
                }
                System.out.println("Random Special Character String: " + randomString);
    }
}