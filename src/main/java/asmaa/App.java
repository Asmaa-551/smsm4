package asmaa;
import java.util.*;

public class App {
    private static ConnectHub connectHub = new ConnectHub();
    public static void main(String[] args) {
        int userChoice;

        do {
            displayMenu();
            userChoice = getUserMenuChoice();

            switch (userChoice) {
                case 1: addUser(); break;
                case 2: addFriendship(); break;
                case 3: displayUserDetails(); break;
                case 4: displayNetworkStructure(); break;
                case 5: addPost(); break;
                case 6: viewPosts(); break;
                case 7: likePost(); break;
                case 8: findDearestFriend(); break;
                case 9: checkNetworkConnectivity(); break;
                case 10: recommendFriends(); break;
                case 11: removeUser(); break;
                case 12: removeFriendship(); break;
                case 13: findTopInfluencers(connectHub); break;
                case 14: loadData(); break;
                case 15: sendMessage(); break;
                case 0: System.out.println("Thank you for using ConnectHub. Goodbye!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (userChoice != 0);
    }

    public static void displayMenu() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Welcome to ConnectHub - Social Network Platform");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Add a new user");
        System.out.println("2. Add a friendship");
        System.out.println("3. View user details");
        System.out.println("4. Display the network structure");
        System.out.println("5. Add a post");
        System.out.println("6. View posts by a user");
        System.out.println("7. Like a post");
        System.out.println("8. Find the closest friend of a user");
        System.out.println("9. Check if the network is fully connected");
        System.out.println("10. Recommend friends");
        System.out.println("11. Remove a user from the network");
        System.out.println("12. Remove a friendship between two users");
        System.out.println("13. Find Top Influencers");
        System.out.println("14. Load Data");
        System.out.println("15. Send a message");
        System.out.println("0. Exit");
        System.out.println("------------------------------------------------------------");
    }

    public static int getUserMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        try {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); 
        }

        return choice;
    }

    public static void loadData(){
        TestFile.read(connectHub);
    }

    public static void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        connectHub.addMember(id, name);
    }

    public static void addFriendship() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the first user: ");
        String id1 = scanner.nextLine();
        System.out.print("Enter the ID of the second user: ");
        String id2 = scanner.nextLine();
        connectHub.addFriendship(id1, id2); 
    }

    public static void displayUserDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        connectHub.viewUserDetails(id);
    }

    public static void displayNetworkStructure() {
        connectHub.printGraph();
    }

    public static void addPost() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter post content: ");
        String content = scanner.nextLine();
        connectHub.addPost(id, content);
    }

    public static void viewPosts() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        connectHub.viewUserPosts(id);
    }

    public static void likePost() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your ID (liker): ");
        String likerId = scanner.nextLine();
        System.out.print("Enter the ID of the user whose post you want to like: ");
        String authorId = scanner.nextLine();
        connectHub.viewUserPosts(authorId);
        System.out.print("Enter the index of the post you want to like: ");
        int postIndex = scanner.nextInt();
        connectHub.likePost(likerId, authorId, postIndex);
    }

    public static void findDearestFriend() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        connectHub.findDearestFriend(id);
    }

    public static void checkNetworkConnectivity() {
        boolean isConnected = connectHub.isNetworkConnected();
        System.out.println("The network is " + (isConnected ? "fully connected." : "not fully connected."));
    }

    public static void recommendFriends() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        ArrayList<User> recommendations = connectHub.recommendFriends(id);
        System.out.println("Friend recommendations:");
        for (User user : recommendations) {
            System.out.println("- " + user.getName());
        }
    }

    public static void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the user to remove: ");
        String id = scanner.nextLine();
        connectHub.removeUser(id);
    }

    public static void removeFriendship() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the first user: ");
        String id1 = scanner.nextLine();
        System.out.print("Enter the ID of the second user: ");
        String id2 = scanner.nextLine();
        connectHub.removeFriendship(id1, id2);
    }
    public static void findTopInfluencers(ConnectHub connectHub) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of top influencers to display: ");
        int topN = scanner.nextInt();
        connectHub.displayTopInfluencers(topN);
    }

    public static void sendMessage() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter your user ID (sender): ");
        String senderId = scanner.nextLine();
    
        System.out.print("Enter the recipient user ID (receiver): ");
        String receiverId = scanner.nextLine();
    
        System.out.println("\nChat History:");
        connectHub.viewChatHistory(senderId, receiverId);
    
        System.out.print("\nEnter the message you want to send: ");
        String message = scanner.nextLine();
    
        connectHub.privateChat(senderId, receiverId, message);
    
        System.out.println("\nUpdated Chat History:");
        connectHub.viewChatHistory(senderId, receiverId);
    }
    
    
    
}