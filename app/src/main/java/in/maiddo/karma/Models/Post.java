package in.maiddo.karma.Models;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public Post(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
