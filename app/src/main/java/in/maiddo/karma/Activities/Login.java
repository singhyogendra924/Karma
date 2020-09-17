package in.maiddo.karma.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.maiddo.karma.APIs.Api;
import in.maiddo.karma.Models.Post;
import in.maiddo.karma.Models.Token;
import in.maiddo.karma.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {


    private EditText email,password;
    private TextView register;
    private Button loginBtn;
    private Api api;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().hide();
        register = findViewById(R.id.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        String text = "Not a member yet?<font color=#fc8608> Click Here</font>";
        register.setText(Html.fromHtml(text));

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(Api.class);

        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()){
                    showMessage("Email is empty!");
                }else if(password.getText().toString().isEmpty()){
                    showMessage("Password empty!");
                }else if (!isEmailValid(email.getText().toString())){
                    showMessage("Email format not valid");
                }else{
                    login();
                }
            }
        });

    }

    private void login(){
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
        Post post = new Post(email.getText().toString(), password.getText().toString());
        Call<Token> call = api.createPost(post);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()){
                    showMessage("Invalid email or password!");
                    loginBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                Token token = response.body();
                SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Token", "Yes");
                editor.apply();
                showMessage("Login Successful!");
                Intent intent = new Intent(getApplicationContext(), KarmaDrive.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                loginBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                showMessage(t.getMessage());
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}