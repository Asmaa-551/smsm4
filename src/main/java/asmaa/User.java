package asmaa;
import java.util.*;

public class User {
    private String id;
    private String name;
    private ArrayList<Post> posts;
    private ArrayList<chat> chatHistories;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.posts = new ArrayList<>();
        this.chatHistories = new ArrayList<>();

    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPost(String content) {
        posts.add(new Post(content));
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addChatMessage(String userId, String message) {
        chat chatHistory = findChatHistory(userId);
        if (chatHistory == null) {
            chatHistory = new chat(userId);
            chatHistories.add(chatHistory);
        }
        chatHistory.addMessage(message);
    }

    public ArrayList<String> getChatHistory(String userId) {
        chat chatHistory = findChatHistory(userId);
        return chatHistory != null ? chatHistory.getMessages() : new ArrayList<>();
    }

    private chat findChatHistory(String userId) {
        for (chat chatHistory : chatHistories) {
            if (chatHistory.getUserId().equals(userId)) {
                return chatHistory;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
