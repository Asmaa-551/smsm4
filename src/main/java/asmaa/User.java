package asmaa;
import java.util.*;

public class User {
    private String id;
    private String name;
    private ArrayList<Post> posts;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.posts = new ArrayList<>();
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

    @Override
    public String toString() {
        return name;
    }
}
