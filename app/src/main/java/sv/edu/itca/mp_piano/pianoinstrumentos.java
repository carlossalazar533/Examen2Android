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

public class pianoinstrumentos extends AppCompatActivity implements View.OnClickListener{
    private SoundPool soundPoolI;

    private int soundGuitar;
    private int soundDrum;
    private int soundFlauta;
    private int soundViolin;
    private int soundArmonica;
    private int soundTrompeta;
    private int soundHarp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pianoinstrumentos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inciarSonidos();
        iniciarBonotnes();
    }

    private void iniciarBonotnes()
    {
        findViewById(R.id.btnGuitar).setOnClickListener(this);
        findViewById(R.id.btntambor).setOnClickListener(this);
        findViewById(R.id.btntompeta).setOnClickListener(this);
        findViewById(R.id.btnflauta).setOnClickListener(this);
        findViewById(R.id.btnviolin).setOnClickListener(this);
        findViewById(R.id.btnArmonica).setOnClickListener(this);
        findViewById(R.id.btnharp).setOnClickListener(this);

    }

    private void inciarSonidos()
    {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPoolI = new SoundPool.Builder()
                .setMaxStreams(7)
                .setAudioAttributes(audioAttributes)
                .build();

        // Cargar los archivos de sonido desde la carpeta raw
        soundGuitar = soundPoolI.load(this, R.raw.guitar, 1);
        soundDrum = soundPoolI.load(this, R.raw.drum, 1);
        soundArmonica = soundPoolI.load(this, R.raw.armonica, 1);
        soundTrompeta = soundPoolI.load(this, R.raw.trompeta, 1);
        soundHarp = soundPoolI.load(this, R.raw.harp, 1);
        soundViolin = soundPoolI.load(this, R.raw.violin, 1);
        soundFlauta = soundPoolI.load(this, R.raw.flute, 1);
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        if (id == R.id.btnGuitar) {
            tocarNota("Guitarra", soundGuitar);
        } else if (id == R.id.btntambor) {
            tocarNota("Tambor", soundDrum);
        } else if (id == R.id.btntompeta) {
            tocarNota("Trompeta", soundTrompeta);
        } else if (id == R.id.btnviolin) {
            tocarNota("Violin", soundViolin);
        } else if (id == R.id.btnflauta) {
            tocarNota("Flauta", soundFlauta);
        } else if (id == R.id.btnArmonica) {
            tocarNota("Armonica", soundArmonica);
        } else if (id == R.id.btnharp) {
            tocarNota("Arpa", soundHarp);
        }

        aplicarEfectoVisual((Button) view);


    }

    private void tocarNota(String nombreNota, int soundId) {
        // Reproducir sonido si está cargado
        if (soundId != 0) {
            soundPoolI.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
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
            mostrarMensaje("Modo Instrumentos activado");
            return true;
        } else if (itemId == R.id.animales) {

            Intent intent = new Intent(this, pianoselva.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (itemId == R.id.grupo)
        {
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
        if (soundPoolI != null) {
            soundPoolI.release();
            soundPoolI = null;
        }
    }
}