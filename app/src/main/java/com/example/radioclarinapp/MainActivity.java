package com.example.radioclarinapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.radioclarinapp.RadioService;

public class MainActivity extends AppCompatActivity {
    private ImageView playButton, share;
    private boolean isPlaying = false;
    private Intent radioServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playButton = findViewById(R.id.play_btn);

        share = findViewById(R.id.share_c);

        radioServiceIntent = new Intent(this, RadioService.class);

        playButton.setOnClickListener(v -> {
            if (isPlaying) {
                stopService(radioServiceIntent);
                playButton.setImageResource(R.drawable.playico);
            }
            else {
                startService(radioServiceIntent);
                playButton.setImageResource(R.drawable.pauseico);
            }
            isPlaying = !isPlaying;
        });

        share.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"musica y noticias en  RADIO CLARIN de Colombia  : https://play.google.com/store/apps/details?id=com.example.radioclarinapp");
            intent.setType("text/plain");
            startActivity(intent);
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPlaying) {


        }
    }

}
