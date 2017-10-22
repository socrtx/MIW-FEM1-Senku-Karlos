package es.upm.miw.karlos;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import es.upm.miw.karlos.models.Result;

public class ShowResults extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        TextView textView = (TextView) findViewById(R.id.scores);
        ArrayList<Result> results = new ArrayList<Result>();
        String finalText = "";
        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(openFileInput(getString(R.string.scores))));
            String linea = fin.readLine();
            while (linea != null) {
                results.add(new Result(linea));
                linea = fin.readLine();
            }
            Collections.sort(results, new Comparator<Result>() {
                @Override
                public int compare(Result lhs, Result rhs) {
                    return lhs.getScore() < rhs.getScore() ? -1 : (lhs.getScore() > rhs.getScore()) ? 1 : 0;
                }
            });
            for (int i = 0; i < results.size(); i++) {
                finalText += results.get(i).toString() + "\n";
            }
            fin.close();
        } catch (IOException e) {
            Toast.makeText(this,
                    getString(R.string.loadScoreException),
                    Toast.LENGTH_SHORT).show();
        }
        textView.setText(finalText);
    }
}
