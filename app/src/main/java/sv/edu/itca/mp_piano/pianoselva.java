package sv.edu.itca.mp_piano;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pianoselva extends AppCompatActivity implements View.OnClickListener {
    private SoundPool soundPoolA;

    private int soundCow;
    private int soundDog;
    private int soundCat;
    private int soundPig;
    private int soundDuck;
    private int soundHen;
    private int soundHorse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pianoselva);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });
        iniciarSonidos();
        configurarBotones();
    }

    private void configurarBotones()
    {
        findViewById(R.id.btndog).setOnClickListener(this);
        findViewById(R.id.btnduck).setOnClickListener(this);
        findViewById(R.id.btnhen).setOnClickListener(this);
        findViewById(R.id.btnhorse).setOnClickListener(this);
        findViewById(R.id.btncat).setOnClickListener(this);
        findViewById(R.id.btncow).setOnClickListener(this);
        findViewById(R.id.btnpig).setOnClickListener(this);



    }
    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        if (id == R.id.btndog) {
            tocarNota("Perro", soundDog);
        } else if (id == R.id.btnduck) {
            tocarNota("Pato", soundDuck);
        } else if (id == R.id.btnhen) {
            tocarNota("Gallina", soundHen);
        } else if (id == R.id.btnhorse) {
            tocarNota("Caballo", soundHorse);
        } else if (id == R.id.btncat) {
            tocarNota("Gato", soundCat);
        } else if (id == R.id.btncow) {
            tocarNota("Vaca", soundCow);
        } else if (id == R.id.btnpig) {
            tocarNota("Cerdo", soundPig);
        }
        aplicarEfectoVisual((Button) v);

    }
    private void tocarNota(String nombreNota, int soundId) {
        // Reproducir sonido si está cargado
        if (soundId != 0) {
            soundPoolA.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
        } else {
            // Mientras no tengas los sonidos, muestra un toast
            Toast.makeText(this, "♪ " + nombreNota + " ♪", Toast.LENGTH_SHORT).show();
        }
    }
    private void aplicarEfectoVisual(Button boton) {
        // Efecto de presión
        boton.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() -> {
                    boton.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(100)
                            .start();
                })
                .start();
    }

    private void iniciarSonidos()
    {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPoolA = new SoundPool.Builder()
                .setMaxStreams(7)
                .setAudioAttributes(audioAttributes)
                .build();

        // Cargar los archivos de sonido desde la carpeta raw
        soundDog = soundPoolA.load(this, R.raw.dog, 1);
        soundDuck = soundPoolA.load(this, R.raw.duck, 1);
        soundCow = soundPoolA.load(this, R.raw.cow, 1);
        soundCat = soundPoolA.load(this, R.raw.cat, 1);
        soundPig = soundPoolA.load(this, R.raw.pig, 1);
        soundHen = soundPoolA.load(this, R.raw.hen, 1);
        soundHorse = soundPoolA.load(this, R.raw.horse, 1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.piano) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (itemId == R.id.intrumentos) {
            Intent intent = new Intent(this, pianoinstrumentos.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.animales) {
            mostrarMensaje("Modo Animales activado");
            return true;

        } else if (itemId == R.id.grupo) {
            Intent intent = new Intent(this,Participantes.class);
            startActivity(intent);
            finish();
            return true;

        }
        else if (itemId == R.id.salir)
        {
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPoolA != null) {
            soundPoolA.release();
            soundPoolA = null;
        }
    }
}