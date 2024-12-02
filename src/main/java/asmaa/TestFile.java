package asmaa;

import java.io.*;
import java.util.Scanner;

public class TestFile {

    public static void main(String[] args) {
        ConnectHub connectHub = new ConnectHub();

        try (Scanner scanner = new Scanner(new File("test.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Split the line into parts
                String[] parts = line.split(" ");
                String command = parts[0];

                try {
                    switch (command) {
                        case "addUser":
                            if (parts.length < 3) {
                                System.out.println("Invalid command format for addUser: " + line);
                                break;
                            }
                            connectHub.addMember(parts[1], parts[2]);
                            break;

                        case "addFriendship":
                            if (parts.length < 3) {
                                System.out.println("Invalid command format for addFriendship: " + line);
                                break;
                            }
                            connectHub.addFriendship(parts[1], parts[2], 1.0);
                            break;

                        case "removeFriendship":
                            if (parts.length < 3) {
                                System.out.println("Invalid command format for removeFriendship: " + line);
                                break;
                            }
                            connectHub.removeFriendship(parts[1], parts[2]);
                            break;

                        case "addPost":
                            if (parts.length < 3) {
                                System.out.println("Invalid command format for addPost: " + line);
                                break;
                            }
                            String userId = parts[1];
                            String content = String.join(" ", parts).substring(parts[0].length() + parts[1].length() + 2);
                            connectHub.addPost(userId, content);
                            break;

                        case "likePost":
                            if (parts.length < 4) {
                                System.out.println("Invalid command format for likePost: " + line);
                                break;
                            }
                            String likerId = parts[1];
                            String authorId = parts[2];
                            int postIndex;
                            try {
                                postIndex = Integer.parseInt(parts[3]); // Parse post index
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid post index format: " + parts[3]);
                                break;
                            }
                            connectHub.likePost(likerId, authorId, postIndex);
                            break;

                        case "viewUserDetails":
                            if (parts.length < 2) {
                                System.out.println("Invalid command format for viewUserDetails: " + line);
                                break;
                            }
                            connectHub.viewUserDetails(parts[1]);
                            break;

                        case "displayNetworkStructure":
                            connectHub.displayNetworkStructure();
                            break;

                        case "findDearestFriend":
                            if (parts.length < 2) {
                                System.out.println("Invalid command format for findDearestFriend: " + line);
                                break;
                            }
                            connectHub.findDearestFriend(parts[1]);
                            break;

                        case "recommendFriends":
                            if (parts.length < 2) {
                                System.out.println("Invalid command format for recommendFriends: " + line);
                                break;
                            }
                            connectHub.recommendFriends(parts[1]);
                            break;

                        case "removeUser":
                            if (parts.length < 2) {
                                System.out.println("Invalid command format for removeUser: " + line);
                                break;
                            }
                            connectHub.removeUser(parts[1]);
                            break;

                        case "isNetworkConnected":
                            System.out.println("Network connected: " + connectHub.isNetworkConnected());
                            break;

                            case "findTopInfluencers":
                            if (parts.length < 2) {
                                System.out.println("Invalid command format for findTopInfluencers: " + line);
                                break;
                            }
                            int topN;
                            try {
                                topN = Integer.parseInt(parts[1]);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number format for top influencers: " + parts[1]);
                                break;
                            }
                            connectHub.displayTopInfluencers(topN);
                            break;
                        

                        default:
                            System.out.println("Unknown command: " + command);
                    }
                } catch (Exception e) {
                    System.out.println("Error executing command: " + line);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Test file not found: " + e.getMessage());
        }
    }
}
