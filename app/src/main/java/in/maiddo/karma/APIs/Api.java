package in.maiddo.karma.APIs;

import in.maiddo.karma.Models.Post;
import in.maiddo.karma.Models.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("login")
    Call<Token> createPost(@Body Post post);

}
