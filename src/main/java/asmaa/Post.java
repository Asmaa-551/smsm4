package asmaa;

public class Post {
    private String post;
    private int likes;

    public Post(String post) {
        this.post = post;
        this.likes = 0;
    }

    public void likePost() {
        likes++;
    }

    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return post + " (Likes:" + likes + ")";
    }
}
