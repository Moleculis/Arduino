package ua.nure.moleculis.emulator;


import ua.nure.moleculis.emulator.http.MoleculisClient;
import ua.nure.moleculis.emulator.http.models.UserResponse;
import ua.nure.moleculis.emulator.http.models.responses.MessageDTO;
import ua.nure.moleculis.emulator.http.models.responses.PeopleResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
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
                println("1 - Verify access token");
                println("2 - Find people");
                println("0 - Exit");

                final int result = Integer.parseInt(in.readLine());
                print(result);
                switch (result) {
                    case 0:
                        working = false;
                        break;
                    case 1:
                        println("\nVerifying token...");
                        final boolean valid = moleculisClient.verifyToken(accessToken).getResult();
                        println("Token valid: " + valid);
                        break;
                    case 2:
                        println("\nLooking for people nearby...");
                        println("Found:\n");
                        final PeopleResponse peopleNearby = moleculisClient.finPeople(accessToken);
                        for (UserResponse user : peopleNearby.getUsers()) {
                            println("Id: " + user.getId() + "; Username: " + user.getUsername());
                        }
                        println("\nChoose ids of users you want to add: ");
                        final String idsString = in.readLine();
                        List<Long> ids = new ArrayList<>();
                        for (String id : idsString.split(",")) {
                            ids.add(Long.parseLong(id));
                        }
                        List<String> usernames = new ArrayList<>();
                        for (Long id : ids) {
                            final String username = getUserUsernameById(peopleNearby.getUsers(), id);
                            usernames.add(username);
                        }
                        final MessageDTO response = moleculisClient.sendContactRequests(usernames, accessToken);
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

    static String getUserUsernameById(List<UserResponse> userResponses, Long id) {
        for (UserResponse userResponse : userResponses) {
            if (userResponse.getId().equals(id)) {
                return userResponse.getUsername();
            }
        }
        return null;
    }

    public static void print(Object message) {
        System.out.print(message);
    }

    public static void println(Object message) {
        System.out.println(message);
    }

}
