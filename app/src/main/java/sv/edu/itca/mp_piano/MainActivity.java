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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private SoundPool soundPool;

  // IDs de los sonidos (se cargarán cuando agregues los archivos de audio)
  private int soundDo;
  private int soundRe;
  private int soundMi;
  private int soundFa;
  private int soundSol;
  private int soundLa;
  private int soundSi;

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

    inicializarSonido();
    configurarBotones();
  }

  /**
   * Inicializa el sistema de sonido con SoundPool
   */
  private void inicializarSonido() {
    AudioAttributes audioAttributes = new AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build();

    soundPool = new SoundPool.Builder()
        .setMaxStreams(7)
        .setAudioAttributes(audioAttributes)
        .build();

    // Cargar los archivos de sonido desde la carpeta raw
    soundDo = soundPool.load(this, R.raw.do_note, 1);
    soundRe = soundPool.load(this, R.raw.re, 1);
    soundMi = soundPool.load(this, R.raw.mi, 1);
    soundFa = soundPool.load(this, R.raw.fa, 1);
    soundSol = soundPool.load(this, R.raw.sol, 1);
    soundLa = soundPool.load(this, R.raw.la, 1);
    soundSi = soundPool.load(this, R.raw.si, 1);
  }

  /**
   * Configura los listeners para todos los botones del piano
   */
  private void configurarBotones() {
    findViewById(R.id.btnDo).setOnClickListener(this);
    findViewById(R.id.btnRe).setOnClickListener(this);
    findViewById(R.id.btnMi).setOnClickListener(this);
    findViewById(R.id.btnFa).setOnClickListener(this);
    findViewById(R.id.btnSol).setOnClickListener(this);
    findViewById(R.id.btnLa).setOnClickListener(this);
    findViewById(R.id.btnSi).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();

    if (id == R.id.btnDo) {
      tocarNota("DO", soundDo);
    } else if (id == R.id.btnRe) {
      tocarNota("RE", soundRe);
    } else if (id == R.id.btnMi) {
      tocarNota("MI", soundMi);
    } else if (id == R.id.btnFa) {
      tocarNota("FA", soundFa);
    } else if (id == R.id.btnSol) {
      tocarNota("SOL", soundSol);
    } else if (id == R.id.btnLa) {
      tocarNota("LA", soundLa);
    } else if (id == R.id.btnSi) {
      tocarNota("SI", soundSi);
    }

    aplicarEfectoVisual((Button) v);
  }

  /**
   * Reproduce el sonido de la nota
   */
  private void tocarNota(String nombreNota, int soundId) {
    // Reproducir sonido si está cargado
    if (soundId != 0) {
      soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    } else {
      // Mientras no tengas los sonidos, muestra un toast
      Toast.makeText(this, "♪ " + nombreNota + " ♪", Toast.LENGTH_SHORT).show();
    }
  }


  /**
   * Aplica un efecto visual al presionar la tecla
   */
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
      mostrarMensaje("Modo Piano activado");
      return true;
    } else if (itemId == R.id.intrumentos) {
      Intent intent = new Intent(this, pianoinstrumentos.class);
      startActivity(intent);
      finish();
      return true;

    } else if (itemId == R.id.animales) {

      Intent intent = new Intent(this, pianoselva.class);
      startActivity(intent);
      finish();

      return true;
    } else if (itemId == R.id.grupo)
    {
      Intent intent = new Intent(this,Participantes.class);
      startActivity(intent);
      finish();
      return true;

    }
    else if (itemId == R.id.salir) {
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
    if (soundPool != null) {
      soundPool.release();
      soundPool = null;
    }
  }
}