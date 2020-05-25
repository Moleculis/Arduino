package ua.nure.moleculis.emulator;


import ua.nure.moleculis.emulator.http.MoleculisClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        final MoleculisClient moleculisClient = new MoleculisClient();
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        boolean loggedIn = false;
        String accessToken = null;

        while (!loggedIn) {
            print("Your login: ");
            var login = in.readLine();
            print("Your password: ");
            var password = in.readLine();
            accessToken = moleculisClient.login(login, password).getToken();
            if (accessToken != null) {
                loggedIn = true;
            } else {
                println("Log in failed, please try again.");
            }
        }
        print("Your token: " + accessToken);
    }

    public static void print(Object message) {
        System.out.print(message);
    }

    public static void println(Object message) {
        System.out.println(message);
    }

}
