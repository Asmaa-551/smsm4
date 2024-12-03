package asmaa;

import java.util.ArrayList;

public class chat {
    private String withId; 
    private ArrayList<String> messages; 

    public chat(String withId) {
        this.withId = withId;
        this.messages = new ArrayList<>();
    }

    
    public void addMessage(String message) {
        messages.add(message);
    }

    
    public ArrayList<String> getMessages() {
        return messages;
    }

    public String getUserId() {
        return withId;
    }

    
    public int getMessageCount() {
        return messages.size();
    }

    @Override
    public String toString() {
        StringBuilder chatSummary = new StringBuilder("Chat with " + withId + ":\n");
        for (int i = 0; i < messages.size(); i++) {
            chatSummary.append((i + 1) + ". " + messages.get(i) + "\n");
        }
        return chatSummary.toString();
    }
}
