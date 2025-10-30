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

public class pianoselva extends AppCompatActivity implements View.OnClickListener {

    private SoundPool soundPoolA;
    private int soundCow, soundDog, soundCat, soundHorse, soundDuck, soundHen, soundPig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pianoselva);

        iniciarSonidos();
        iniciarBotones();
    }

    private void iniciarBotones() {
        findViewById(R.id.btncow).setOnClickListener(this);
        findViewById(R.id.btndog).setOnClickListener(this);
        findViewById(R.id.btncat).setOnClickListener(this);
        findViewById(R.id.btnhorse).setOnClickListener(this);
        findViewById(R.id.btnduck).setOnClickListener(this);
        findViewById(R.id.btnhen).setOnClickListener(this);
        findViewById(R.id.btnpig).setOnClickListener(this);
    }

    private void iniciarSonidos() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPoolA = new SoundPool.Builder()
                .setMaxStreams(7)
                .setAudioAttributes(audioAttributes)
                .build();

        soundCow = soundPoolA.load(this, R.raw.cow, 1);
        soundDog = soundPoolA.load(this, R.raw.dog, 1);
        soundCat = soundPoolA.load(this, R.raw.cat, 1);
        soundHorse = soundPoolA.load(this, R.raw.horse, 1);
        soundDuck = soundPoolA.load(this, R.raw.duck, 1);
        soundHen = soundPoolA.load(this, R.raw.hen, 1);
        soundPig = soundPoolA.load(this, R.raw.pig, 1);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btncow) tocarNota("Vaca", soundCow);
        else if (id == R.id.btndog) tocarNota("Perro", soundDog);
        else if (id == R.id.btncat) tocarNota("Gato", soundCat);
        else if (id == R.id.btnhorse) tocarNota("Caballo", soundHorse);
        else if (id == R.id.btnduck) tocarNota("Pato", soundDuck);
        else if (id == R.id.btnhen) tocarNota("Gallina", soundHen);
        else if (id == R.id.btnpig) tocarNota("Cerdo", soundPig);

        if (view instanceof Button) aplicarEfectoVisual((Button) view);
    }

    private void tocarNota(String nombre, int soundId) {
        if (soundId != 0) {
            soundPoolA.play(soundId, 1f, 1f, 1, 0, 1f);
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
        if (soundPoolA != null) {
            soundPoolA.release();
            soundPoolA = null;
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
            startActivity(new Intent(this, pianoinstrumentos.class));
            finish();
        } else if (id == R.id.animales) {
            Toast.makeText(this, "Modo Animales activado", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.grupo) {
            startActivity(new Intent(this, Participantes.class));
            finish();
        } else if (id == R.id.salir) {
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
