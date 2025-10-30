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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class pianoinstrumentos extends AppCompatActivity implements View.OnClickListener {

    private SoundPool soundPoolI;
    private int soundGuitar, soundDrum, soundFlauta, soundViolin, soundArmonica, soundTrompeta, soundHarp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pianoinstrumentos);

        iniciarSonidos();
        iniciarBotones();
    }

    private void iniciarBotones() {
        findViewById(R.id.btnGuitar).setOnClickListener(this);
        findViewById(R.id.btntambor).setOnClickListener(this);
        findViewById(R.id.btntompeta).setOnClickListener(this);
        findViewById(R.id.btnflauta).setOnClickListener(this);
        findViewById(R.id.btnviolin).setOnClickListener(this);
        findViewById(R.id.btnArmonica).setOnClickListener(this);
        findViewById(R.id.btnharp).setOnClickListener(this);
    }

    private void iniciarSonidos() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPoolI = new SoundPool.Builder()
                .setMaxStreams(7)
                .setAudioAttributes(audioAttributes)
                .build();

        // Cargar sonidos
        soundGuitar = safeLoad(R.raw.guitar);
        soundDrum = safeLoad(R.raw.drum);
        soundArmonica = safeLoad(R.raw.armonica);
        soundTrompeta = safeLoad(R.raw.trompeta);
        soundHarp = safeLoad(R.raw.harp);
        soundViolin = safeLoad(R.raw.violin);
        soundFlauta = safeLoad(R.raw.flute);
    }

    private int safeLoad(int resId) {
        try {
            return soundPoolI.load(this, resId, 1);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnGuitar) tocarNota("Guitarra", soundGuitar);
        else if (id == R.id.btntambor) tocarNota("Tambor", soundDrum);
        else if (id == R.id.btntompeta) tocarNota("Trompeta", soundTrompeta);
        else if (id == R.id.btnviolin) tocarNota("Violin", soundViolin);
        else if (id == R.id.btnflauta) tocarNota("Flauta", soundFlauta);
        else if (id == R.id.btnArmonica) tocarNota("Armonica", soundArmonica);
        else if (id == R.id.btnharp) tocarNota("Arpa", soundHarp);

        if (view instanceof Button) aplicarEfectoVisual((Button) view);
    }

    private void tocarNota(String nombre, int soundId) {
        if (soundId != 0) {
            soundPoolI.play(soundId, 1f, 1f, 1, 0, 1f);
        } else {
            Toast.makeText(this, "♪ " + nombre + " ♪", Toast.LENGTH_SHORT).show();
        }
    }

    private void aplicarEfectoVisual(Button boton) {
        boton.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() -> boton.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPoolI != null) {
            soundPoolI.release();
            soundPoolI = null;
        }
    }

    // -------------------- MENU --------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.piano) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.intrumentos) {
            Toast.makeText(this, "Modo Instrumentos activado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.animales) {
            startActivity(new Intent(this, pianoselva.class));
            finish();
        } else if (id == R.id.grupo) {
            startActivity(new Intent(this, Participantes.class));
            finish();
        } else if (id == R.id.salir) {
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
