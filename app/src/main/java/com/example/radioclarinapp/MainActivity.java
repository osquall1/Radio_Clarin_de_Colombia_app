package com.example.radioclarinapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView playButton, share;
    private boolean isPlaying = false;
    private Intent radioServiceIntent;
    private List<ObjectAnimator> animators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        ImageView menuIcon = findViewById(R.id.icon_menu);

        ImageView youtube = findViewById(R.id.youtube_icon);
        ImageView facebook = findViewById(R.id.facebook_icon);
        ImageView telegram = findViewById(R.id.telegram_icon);
        ImageView twitter = findViewById(R.id.twitter_icon);
        ImageView vk = findViewById(R.id.vk_icon);

        youtube.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@CLARINdeColombia1/videos"));
            startActivity(intent);
        });

        facebook.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/clarindecolombia1984"));
            startActivity(intent);
        });

        telegram.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/CLARIN_de_Colombia"));
            startActivity(intent);
        });

        twitter.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/CLARIN_Colombia"));
            startActivity(intent);
        });

        vk.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/clarindecolombia"));
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playButton = findViewById(R.id.play_btn);
        share = findViewById(R.id.share_c);
        radioServiceIntent = new Intent(this, RadioService.class);

        menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inicio) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.clarindecolombia.co/"));
                startActivity(intent);
            } else if (id == R.id.nav_contacto) {
                // Acción para Contacto
            } else if (id == R.id.nav_acerca) {
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Acerca de")
                        .setMessage("RADIO CLARIN de Colombia  creado el 26 de julio de 2020 https://www.radio.clarindecolombia.co/ \n" +
                                "es un departamento comunicacional de CLARIN de Colombia fundado en diciembre 14 de 1984 https://www.clarindecolombia.co/ \n" +
                                "conforme a sus estatutos y perfil editorial en la información y la formación en defensa de los Derechos Humanos \n" +
                                "la soberanía Nacional y la Autodeterminación de los pueblos,\n con corresponsales y enviados especiales por los diferentes continentes")
                        .setIcon(R.drawable.ic_info)
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });




        // Configurar animaciones de las barras
        int[] barsId = {R.id.bar1, R.id.bar2, R.id.bar3, R.id.bar4, R.id.bar5, R.id.bar6, R.id.bar7, R.id.bar8};
        for (int id : barsId) {
            View bar = findViewById(id);
            ObjectAnimator animator = ObjectAnimator.ofFloat(bar, "scaleY", 1f, 3f);
            animator.setDuration(400);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animators.add(animator);
        }

        // Acción del botón Play/Pause
        playButton.setOnClickListener(v -> {
            if (isPlaying) {
                stopService(radioServiceIntent);
                playButton.setImageResource(R.drawable.play_ico);
                stopEqualizer();
            } else {
                startService(radioServiceIntent);
                playButton.setImageResource(R.drawable.pause_ico);
                startEqualizer();
            }
            isPlaying = !isPlaying;
        });

        // Acción del botón Compartir
        share.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Música y noticias en RADIO CLARIN de Colombia: https://play.google.com/store/apps/details?id=com.example.radioclarinapp");
            intent.setType("text/plain");
            startActivity(intent);
        });

        // 🔹 Iniciar automáticamente la música al abrir la app
        startService(radioServiceIntent);
        playButton.setImageResource(R.drawable.pause_ico);
        startEqualizer();
        isPlaying = true;
    }

    private void startEqualizer() {
        int delay = 0;
        for (ObjectAnimator animator : animators) {
            animator.setStartDelay(delay);
            animator.start();
            delay += 150; // diferencia de ritmo entre barras
        }
    }

    private void stopEqualizer() {
        for (ObjectAnimator animator : animators) {
            animator.cancel();
        }
    }
}


