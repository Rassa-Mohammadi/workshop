package com.tilldawn.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.client.User;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    public static final int playerMovementCoefficient = 70;
    public static final int friendlyBulletMovementCoefficient = 800;
    public static final int nonFriendlyBulletMovementCoefficient = 300;
    public static final int monsterMovementCoefficient = 20;
    private static boolean isFrench = false;
    private static boolean sfxEnabled = true;
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;
    private static ArrayList<Output> questions;

    static {
        loadUsers();
        questions = new ArrayList<>();
        questions.add(Output.FatherName);
        questions.add(Output.Turk);
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static void addUser(User User) {
        users.add(User);
    }

    public static void changeLanguage() {
        isFrench = !isFrench;
    }

    public static boolean isFrench() {
        return isFrench;
    }

    public static void setSfxEnabled(boolean sfxEnabled) {
        App.sfxEnabled = sfxEnabled;
    }

    public static boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static void deleteUser(User user) {
        users.remove(user);
    }

    public static ArrayList<User> getSortedUsers(String sortType) {
        ArrayList<User> result = new ArrayList<>(users);
        if (sortType.equals(Output.Score.getString())) {
            Collections.sort(result, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return user2.getPoints() - user1.getPoints();
                }
            });
        }
        else if (sortType.equals(Output.Username.getString())) {
            Collections.sort(result, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return user1.getUsername().compareTo(user2.getUsername());
                }
            });
        }
        else if (sortType.equals(Output.Kills.getString())) {
            Collections.sort(result, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return user2.getTotalKills() - user1.getTotalKills();
                }
            });
        }
        else if (sortType.equals(Output.MaxSurvivedTime.getString())) {
            Collections.sort(result, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return Float.compare(user2.getMaxSurvivedTime(), user1.getMaxSurvivedTime());
                }
            });
        }
        return result;
    }

    public static void loadUsers() {
        File file = new File("users.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, ArrayList<Object>> data = objectMapper.readValue(
                file,
                new TypeReference<Map<String, ArrayList<Object>>>() {}
            );
            for (String username : data.keySet()) {
                ArrayList<Object> userData = data.get(username);
                User user = new User();
                user.setUsername(username);
                user.setPassword((String) userData.get(0));
                SecurityQuestion securityQuestion = new SecurityQuestion(
                    Output.getPhrase((String) userData.get(1)),
                    (String) userData.get(2)
                );
                user.setSecurityQuestion(securityQuestion);
                user.setAvatar((String) userData.get(3));
                user.setAutoReload((boolean) userData.get(4));
                user.setPoints((int) userData.get(5));
                user.setTotalKills((int) userData.get(6));
                user.setMaxSurvivedTime(((Double) userData.get(7)).floatValue());
                users.add(user);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void saveUsers() {
        File file = new File("users.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, ArrayList<Object>> data = new HashMap<>();
        for (User user : users) {
            ArrayList<Object> userData = new ArrayList<>();
            userData.add(user.getPassword());
            userData.add(user.getSecurityQuestion().getQuestion().getString()); // security question output type
            userData.add(user.getSecurityQuestion().getAnswer());
            userData.add(user.getAvatarPath());
            userData.add(user.isAutoReload());
            userData.add(user.getPoints());
            userData.add(user.getTotalKills());
            userData.add(user.getMaxSurvivedTime());
            data.put(user.getUsername(), userData);
        }
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
