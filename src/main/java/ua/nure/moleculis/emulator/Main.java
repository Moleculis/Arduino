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
        boolean working = true;
        String accessToken = null;

        while (!loggedIn) {
            println("Logging in...");
            final String login = "stalker";
            final String password = "Stalkerkryt1";
            accessToken = moleculisClient.login(login, password).getToken();
            if (accessToken != null) {
                loggedIn = true;
            } else {
                println("Log in failed, retrying...");
            }
        }
        while (working) {
            try {
                println("1 - Verify token");
                println("0 - Exit");

                final int result = Integer.parseInt(in.readLine());
                print(result);
                switch (result) {
                    case 0:
                        working = false;
                        break;
                    case 1:
                        println("\nVerifying Token...");
                        break;
                    default:
                        println("\nUnknown command");
                        break;
                }
            } catch (Exception e) {
                println("Wrong input");
            }
        }
    }

    public static void print(Object message) {
        System.out.print(message);
    }

    public static void println(Object message) {
        System.out.println(message);
    }

}
