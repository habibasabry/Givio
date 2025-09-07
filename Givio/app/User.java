public class User {
    public String name, email, phone;

    // Empty constructor required for Firebase
    public User() {}

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
