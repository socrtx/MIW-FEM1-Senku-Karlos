package es.upm.miw.karlos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import es.upm.miw.karlos.models.Result;

public class MainActivity extends Activity {

    JuegoCelta juego;
    boolean isPristine = true;
    boolean needStop = false;
    private final String GRID_KEY = "GRID_KEY";
    Chronometer chronometer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        juego = new JuegoCelta();
        mostrarTablero();
        chronometer = (Chronometer) findViewById(R.id.chronometer);
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(View v) {
        if (isPristine) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isPristine = false;
        }
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        juego.jugar(i, j);

        mostrarTablero();
        if (juego.juegoTerminado()) {
            chronometer.stop();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String username = preferences.getString(getString(R.string.settingsUsername), getString(R.string.settingsUsernameDefault));
            int score = juego.numeroFichas();
            String time = chronometer.getText().toString();
            Result result = new Result(username + " ", score, time);
            try {
                String toFile = result.toString() + "\n";
                FileOutputStream fos = openFileOutput(getString(R.string.scores), Context.MODE_APPEND);
                fos.write(toFile.getBytes());
                fos.close();
            } catch (IOException e) {
                Toast.makeText(this,
                        getString(R.string.saveScoreException),
                        Toast.LENGTH_SHORT).show();
            }

            new AlertDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + Integer.toString(i) + Integer.toString(j);
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = (RadioButton) findViewById(idBoton);
                    button.setChecked(juego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }
        TextView barraEstado = (TextView) findViewById(R.id.barraEstado);
        barraEstado.setText("Quedan: " + juego.numeroFichas());
    }

    /**
     * Guarda el estado del tablero (serializado)
     *
     * @param outState Bundle para almacenar el estado del juego
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(GRID_KEY, juego.serializaTablero());
        super.onSaveInstanceState(outState);
    }

    /**
     * Recupera el estado del juego
     *
     * @param savedInstanceState Bundle con el estado del juego almacenado
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString(GRID_KEY);
        juego.deserializaTablero(grid);
        mostrarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            case R.id.opcReiniciarPartida:
                new AlertDialogRestartFragment().show(getFragmentManager(), "ALERT DIALOG");
                return true;
            case R.id.opcGuardarPartida:
                saveGame();
                return true;
            case R.id.opcRecuperarPartida:
                loadGame();
                return true;
            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, ShowResults.class));
                return true;
            default:
                Toast.makeText(
                        this,
                        getString(R.string.txtSinImplementar),
                        Toast.LENGTH_SHORT
                ).show();
        }
        return true;
    }

    public void saveGame() {
        String currentState = juego.serializaTablero();
        try {
            FileOutputStream fos = openFileOutput(getString(R.string.savedGame), Context.MODE_PRIVATE);
            fos.write(currentState.getBytes());
            fos.close();
            Toast.makeText(this,
                    getString(R.string.saveGameSuccess),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,
                    getString(R.string.saveGameException),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void loadGame() {
        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(openFileInput(getString(R.string.savedGame))));
            String game = fin.readLine();
            juego.deserializaTablero(game);
            mostrarTablero();
            Toast.makeText(this,
                    getString(R.string.loadGameSuccess),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,
                    getString(R.string.loadGameException),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
