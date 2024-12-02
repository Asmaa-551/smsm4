package asmaa;
public class UserDegree implements Comparable<UserDegree> {
    private User user;
    private int degree;

    public UserDegree(User user, int degree) {
        this.user = user;
        this.degree = degree;
    }

    public User getUser() {
        return user;
    }

    public int getDegree() {
        return degree;
    }

    @Override
    public int compareTo(UserDegree other) {
        return Integer.compare(other.degree, this.degree);
    }
}