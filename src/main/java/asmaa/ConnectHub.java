package asmaa;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.*;
import java.util.*;

public class ConnectHub {
    private SimpleWeightedGraph<User, DefaultWeightedEdge> graph;
    private HashMap<String, User> userMap;

    public ConnectHub() {
        graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        userMap = new HashMap<>();
    }

    public void addMember(String id, String name) {
        if (findUser(id) != null) { //check if the user is already in the app
            System.out.println("User with ID " + id + " already exists");
            return;
        }
        User member = new User(id, name);
        graph.addVertex(member);
        userMap.put(id, member);
        System.out.println("Member added Successfully :)");

    }

    public void addFriendship(String id1, String id2) {
        User m1 = findUser(id1);
        User m2= findUser(id2);

        if (m1 != null && m2 != null) {
            if (!graph.containsEdge(m1, m2)) { // if the friendship exists then do nothing else add them in the weighted graph
                DefaultWeightedEdge edge = graph.addEdge(m1, m2);
                graph.setEdgeWeight(edge, 1);
                System.out.println("Friendship added between " + m1.getName() + " and " + m2.getName());
            } else {
                System.out.println("Friendship already exists!");
            }
        } else {
            System.out.println("One or both members not found!");
        }
    }

    public User findUser(String userId) {
        return userMap.get(userId);
    }

    public void displayTopInfluencers(int topN) {
        ArrayList<UserDegree> userDegree = new ArrayList<>();

        for (User user : graph.vertexSet()) { // calculate the degree for each user to check the top users degree
            int degree = graph.degreeOf(user);
            userDegree.add(new UserDegree(user, degree));
        }

        Collections.sort(userDegree); // sort them in descending to find out who got the highest degree 

        System.out.println("Top " + topN + " Influencers:");
        for (int i = 0; i < Math.min(topN, userDegree.size()); i++) {
            UserDegree md = userDegree.get(i);
            System.out.println((i + 1) + ". " + md.getUser().getName() + " - " + md.getDegree() + " connections");
        }

    }

    public ArrayList<ArrayList<User>> findFriendGroups() {
        ArrayList<ArrayList<User>> friendGroups = new ArrayList<>();
        ArrayList<User> visited = new ArrayList<>();

        for (User user : graph.vertexSet()) {
            if (!visited.contains(user)) { // find a group using dfs
                ArrayList<User> group = new ArrayList<>();
                find(user, group, visited);
                friendGroups.add(group);
            }
        }

        return friendGroups;
    }

    private void find(User user, ArrayList<User> group, ArrayList<User> visited) {
        visited.add(user);
        group.add(user);

        for (User neighbor : Graphs.neighborListOf(graph, user)) {
            if (!visited.contains(neighbor)) {
                find(neighbor, group, visited);
            }
        }
    }

    public ArrayList<User> recommendFriends(String userId) {
        User user = findUser(userId);
        if (user == null) {
            System.out.println("User with ID " + userId + " does not exist.");
            return new ArrayList<>();
        }
        ArrayList<User> directFriends = new ArrayList<>(Graphs.neighborListOf(graph, user));
        ArrayList<UserDegree> recommendations = new ArrayList<>();
        ArrayList<User> SDfriends = new ArrayList<>();

        for (User friend : directFriends) { // get a list for the first and second degree friends
            for (User secondDegree : Graphs.neighborListOf(graph, friend)) {
                if (!secondDegree.equals(user) && !directFriends.contains(secondDegree)) {
                    SDfriends.add(secondDegree);
                }
            }
        }

        ArrayList<User> processed = new ArrayList<>();
        for (User candidate : SDfriends) {
            if (!processed.contains(candidate)) {
                int frequency = Collections.frequency(SDfriends, candidate);
                recommendations.add(new UserDegree(candidate, frequency)); // add if it doesn't exists
                processed.add(candidate);
            }
        }

        Collections.sort(recommendations);

        ArrayList<User> sortedR = new ArrayList<>();
        for (UserDegree ud : recommendations) {
            sortedR.add(ud.getUser());
        }

        return sortedR;
    }
    // method from the jgrapht library to check if a network is connected
    public boolean isNetworkConnected() {
        ConnectivityInspector<User, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);
        return inspector.isConnected();
    }

    public void printGraph() {
        for (User user : graph.vertexSet()) {
            System.out.print(user.getName() + " -> ");
            for (User friend : Graphs.neighborListOf(graph, user)) {
                System.out.print(friend.getName() + " ");
            }
            System.out.println();
        }
    }

    public void viewUserDetails(String userId) {
        User user = findUser(userId);

        if (user == null) {
            System.out.println("User with ID " + userId + " not found in the network.");
            return;
        }

        System.out.println("User Details:");
        System.out.println("Name: " + user.getName());
        System.out.println("ID: " + user.getId());

        System.out.print("Friends: ");
        for (User friend : Graphs.neighborListOf(graph, user)) {
            System.out.print(friend.getName() + " ");
        }

        if (Graphs.neighborListOf(graph, user).isEmpty()) {
            System.out.print("No friends");
        }
        System.out.println();
    }

    public void removeUser(String userId) {
        User user = userMap.get(userId);

        if (user == null) {
            System.out.println("User with ID " + userId + " does not exist.");
            return;
        }

        graph.removeVertex(user);
        userMap.remove(userId);
        System.out.println("User with ID " + userId + " and all their connections have been removed.");
    }

    public void removeFriendship(String userId1, String userId2) {
        User user1 = userMap.get(userId1);
        User user2 = userMap.get(userId2);

        if (user1 == null || user2 == null) {
            System.out.println("One or both users do not exist in the network.");
            return;
        }

        if (!graph.containsEdge(user1, user2)) {
            System.out.println("No friendship exists between " + user1.getName() + " and " + user2.getName() + ".");
            return;
        }

        graph.removeEdge(user1, user2);
        System.out.println("Friendship removed between " + user1.getName() + " and " + user2.getName() + ".");
    }

    public void addPost(String userId, String content) {
        User user = userMap.get(userId);

        if (user == null) {
            System.out.println("User with ID " + userId + " does not exist.");
            return;
        }

        user.addPost(content);
        System.out.println("Post added successfully for " + user.getName() + ".");
    }

    public void viewUserPosts(String userId) {
        User user = userMap.get(userId);

        if (user == null) {
            System.out.println("User with ID " + userId + " does not exist.");
            return;
        }

        System.out.println("Posts by " + user.getName() + ":"); // show all of the user's posts
        ArrayList<Post> posts = user.getPosts();
        for (int i = 0; i < posts.size(); i++) {
            System.out.println((i+1) + ": " + posts.get(i));
        }
    }

    public void likePost(String likerId, String authorId, int index1) {
        User liker = userMap.get(likerId);
        User author = userMap.get(authorId);
    
        if (liker == null || author == null) {
            System.out.println("One or both users do not exist in the network.");
            return;
        }
    
        DefaultWeightedEdge edge = graph.getEdge(liker, author); // there should be a friendship between the two users 
        if (edge == null) {
            System.out.println("No connection exists between " + liker.getName() + " and " + author.getName() + ".");
            return;
        }
    
        ArrayList<Post> posts = author.getPosts();
        int index = index1 - 1;  // the list will start from 0
        if (index < 0 || index >= posts.size()) {
            System.out.println("Invalid post index for user " + author.getName() + ". Available posts: " + posts.size());
            return;
        }
    
        Post post = posts.get(index); 
        post.likePost(); 
        System.out.println(liker.getName() + " liked the post: " + post);
    
        double currentWeight = graph.getEdgeWeight(edge);
        graph.setEdgeWeight(edge, currentWeight + 1.0); // increase the weight 
    }
    

    public User findDearestFriend(String userId) {
        User user = userMap.get(userId);

        if (user == null) {
            System.out.println("User with ID " + userId + " does not exist.");
            return null;
        }

        ArrayList<User> neighbors = new ArrayList<>(Graphs.neighborListOf(graph, user)); // get all of the first degree friends
        if (neighbors.isEmpty()) {
            System.out.println(user.getName() + " has no connections.");
            return null;
        }

        User dearestFriend = null;
        double maxWeight = -1;

        for (User neighbor : neighbors) {
            double weight = graph.getEdgeWeight(graph.getEdge(user, neighbor));
            if (weight > maxWeight) {
                maxWeight = weight;
                dearestFriend = neighbor;
            }
        }

        System.out.println("The dearest friend of " + user.getName() + " is "
                + (dearestFriend != null ? dearestFriend.getName() : "none") + ".");
        return dearestFriend;
    }

    public void privateChat(String senderId, String receiverId, String message) {
        User sender = userMap.get(senderId);
        User receiver = userMap.get(receiverId);
    
        if (sender == null || receiver == null) { // the users should be friends
            System.out.println("The network does not contain one or both of the users.");
            return;
        }
    
        DefaultWeightedEdge edge = graph.getEdge(sender, receiver);
        if (edge == null) {
            System.out.println("No connection exists between " + sender.getName() + " and " + receiver.getName() + ".");
            return;
        }
    
        sender.addChatMessage(receiver.getId(), "You: " + message);
        receiver.addChatMessage(sender.getId(), sender.getName() + ": " + message);
    
        System.out.println("Message sent from " + sender.getName() + " to " + receiver.getName() + ": " + message);
    }
    

    public void viewChatHistory(String senderId, String receiverId) {
        User sender = userMap.get(senderId);
        User receiver = userMap.get(receiverId);
    
        if (sender == null || receiver == null) {
            System.out.println("One or both of the users are not found.");
            return;
        }
    
        System.out.println("Chat history between " + sender.getName() + " and " + receiver.getName() + ":");
    
        ArrayList<String> senderMessages = sender.getChatHistory(receiver.getId());
        for (String message : senderMessages) { // print all messages
            System.out.println(message);
        }
    }
    
    
    
    

}
