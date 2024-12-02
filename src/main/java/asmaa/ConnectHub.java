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
        if (findUserById(id) != null) {
            System.out.println("User with ID " + id + " already exists.");
            return;
        }
        User member = new User(id, name);
        graph.addVertex(member);
        userMap.put(id, member);
        System.out.println("Member added Successfully");

    }

    public void addFriendship(String id1, String id2, double initialWeight) {
        User member1 = findUserById(id1);
        User member2 = findUserById(id2);

        if (member1 != null && member2 != null) {
            if (!graph.containsEdge(member1, member2)) {
                DefaultWeightedEdge edge = graph.addEdge(member1, member2);
                graph.setEdgeWeight(edge, initialWeight);
                System.out.println("Friendship added between " + member1.getName() + " and " + member2.getName());
            } else {
                System.out.println("Friendship already exists!");
            }
        } else {
            System.out.println("One or both members not found!");
        }
    }

    public User findUserById(String userId) {
        return userMap.get(userId);
    }

    public void displayTopInfluencers(int topN) {
        ArrayList<UserDegree> userDegrees = new ArrayList<>();

        for (User user : graph.vertexSet()) {
            int degree = graph.degreeOf(user);
            userDegrees.add(new UserDegree(user, degree));
        }

        Collections.sort(userDegrees);

        System.out.println("Top " + topN + " Influencers:");
        for (int i = 0; i < Math.min(topN, userDegrees.size()); i++) {
            UserDegree md = userDegrees.get(i);
            System.out.println((i + 1) + ". " + md.getUser().getName() + " - " + md.getDegree() + " connections");
        }

    }

    public ArrayList<ArrayList<User>> findFriendGroups() {
        ArrayList<ArrayList<User>> friendGroups = new ArrayList<>();
        ArrayList<User> visited = new ArrayList<>();

        for (User user : graph.vertexSet()) {
            if (!visited.contains(user)) {
                ArrayList<User> group = new ArrayList<>();
                exploreGroup(user, group, visited);
                friendGroups.add(group);
            }
        }

        return friendGroups;
    }

    private void exploreGroup(User user, ArrayList<User> group, ArrayList<User> visited) {
        visited.add(user);
        group.add(user);

        for (User neighbor : Graphs.neighborListOf(graph, user)) {
            if (!visited.contains(neighbor)) {
                exploreGroup(neighbor, group, visited);
            }
        }
    }

    public ArrayList<User> recommendFriends(String userId) {
        User user = findUserById(userId);
        if (user == null) {
            System.out.println("User with ID " + userId + " does not exist.");
            return new ArrayList<>();
        }
        ArrayList<User> directFriends = new ArrayList<>(Graphs.neighborListOf(graph, user));
        ArrayList<UserDegree> recommendations = new ArrayList<>();
        ArrayList<User> allSecondDegreeFriends = new ArrayList<>();

        for (User friend : directFriends) {
            for (User secondDegree : Graphs.neighborListOf(graph, friend)) {
                if (!secondDegree.equals(user) && !directFriends.contains(secondDegree)) {
                    allSecondDegreeFriends.add(secondDegree);
                }
            }
        }

        ArrayList<User> processed = new ArrayList<>();
        for (User candidate : allSecondDegreeFriends) {
            if (!processed.contains(candidate)) {
                int frequency = Collections.frequency(allSecondDegreeFriends, candidate);
                recommendations.add(new UserDegree(candidate, frequency));
                processed.add(candidate);
            }
        }

        Collections.sort(recommendations);

        ArrayList<User> sortedRecommendations = new ArrayList<>();
        for (UserDegree ud : recommendations) {
            sortedRecommendations.add(ud.getUser());
        }

        return sortedRecommendations;
    }

    public boolean isNetworkConnected() {
        ConnectivityInspector<User, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);
        return inspector.isConnected();
    }

    public void displayNetworkStructure() {
        for (User user : graph.vertexSet()) {
            System.out.print(user.getName() + " -> ");
            for (User friend : Graphs.neighborListOf(graph, user)) {
                System.out.print(friend.getName() + " ");
            }
            System.out.println();
        }
    }

    public void viewUserDetails(String userId) {
        User user = findUserById(userId);

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

        System.out.println("Posts by " + user.getName() + ":");
        ArrayList<Post> posts = user.getPosts();
        for (int i = 0; i < posts.size(); i++) {
            System.out.println((i+1) + ": " + posts.get(i));
        }
    }

    public void likePost(String likerId, String authorId, int postIndex) {
        User liker = userMap.get(likerId);
        User author = userMap.get(authorId);
    
        if (liker == null || author == null) {
            System.out.println("One or both users do not exist in the network.");
            return;
        }
    
        DefaultWeightedEdge edge = graph.getEdge(liker, author);
        if (edge == null) {
            System.out.println("No connection exists between " + liker.getName() + " and " + author.getName() + ".");
            return;
        }
    
        ArrayList<Post> posts = author.getPosts();
        int zeroBasedIndex = postIndex - 1; 
        if (zeroBasedIndex < 0 || zeroBasedIndex >= posts.size()) {
            System.out.println("Invalid post index for user " + author.getName() + ". Available posts: " + posts.size());
            return;
        }
    
        Post post = posts.get(zeroBasedIndex);
        post.likePost();
        System.out.println(liker.getName() + " liked the post: " + post);
    
        double currentWeight = graph.getEdgeWeight(edge);
        graph.setEdgeWeight(edge, currentWeight + 1.0);
    }
    

    public User findDearestFriend(String userId) {
        User user = userMap.get(userId);

        if (user == null) {
            System.out.println("User with ID " + userId + " does not exist.");
            return null;
        }

        ArrayList<User> neighbors = new ArrayList<>(Graphs.neighborListOf(graph, user));
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

}
