package in.maiddo.karma.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import in.maiddo.karma.Adapters.KarmaDriveAdapter;
import in.maiddo.karma.R;

public class KarmaDrive extends AppCompatActivity {

    private CardView profile;
    private ImageView icon, bck;
    private RecyclerView recyclerView;
    private KarmaDriveAdapter karmaDriveAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karma_drive);
        getSupportActionBar().hide();

        profile = findViewById(R.id.btn_profile);
        icon = findViewById(R.id.logout_icon);
        bck = findViewById(R.id.logout_background);


        recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        karmaDriveAdapter = new KarmaDriveAdapter();
        recyclerView.setAdapter(karmaDriveAdapter);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ic = new Intent(getApplicationContext(),Profile.class);
                startActivity(ic);

            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });



    }

    private void logout(){
        showMessage("Logged Out!");
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Token", "Yes");
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}