package com.example.viikko8pullomaatti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private SeekBar seekBar;
    protected TextView tuloste, rahaMaara;
    private Button lisaaRahaa;
    protected Spinner tuote, koko;

    Context context = null;

    BottleDispenser bd = BottleDispenser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.liukuvaraha);
        seekBar = findViewById(R.id.seekBar);
        tuloste = findViewById(R.id.tuloste);
        lisaaRahaa = findViewById(R.id.lisaaRahaa);
        rahaMaara = findViewById(R.id.rahaMaara);
        context = MainActivity.this;
        System.out.println(context.getFilesDir());

        tuote = findViewById(R.id.tuote);
        final List<String> tuotteet = new ArrayList<String>();
        tuotteet.add("Coca-Cola");
        tuotteet.add("Pepsi");
        tuotteet.add("Fanta");
        tuotteet.add("Sprite");

        koko = findViewById(R.id.koko);
        final List<String> koot = new ArrayList<String>();
        koot.add("0.5");
        koot.add("1.5");
        koot.add("2.0");

        ArrayAdapter<String> tuoteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tuotteet);
        tuote.setAdapter(tuoteAdapter);
        ArrayAdapter<String> kokoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, koot);
        koko.setAdapter(kokoAdapter);

 /*       tuote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Selected : "+ tuotteet.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        koko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Selected : "+ koot.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();
                textView.setText("" + progress + "€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void lisaaRahaa(View v) {
        rahaMaara.setText((bd.addMoney(seekBar.getProgress())) + "€");
        seekBar.setProgress(0);
        tuloste.setText("Klink! rahaa lisättiin laitteeseen!");
    }

    public void ostaPullo(View v) {
        Bottle pullo = bd.buyBottle(tuote.getSelectedItem().toString(), koko.getSelectedItem().toString());

        if (pullo == null) {
            tuloste.setText("Tuotetta ei ole saatavilla.");
        } else if (pullo.name == "eirahaa") {
            tuloste.setText("Lisää koneeseen rahaa!");
        } else {
            tuloste.setText("Plomps! Koneesta tuli ulos " + pullo.name + " " + pullo.volume + "!");
            rahaMaara.setText(String.format("%.2f€", bd.getMoney()));
        }
    }

    public void palautaRahat(View v) {
        tuloste.setText("Klink klink. Sinne menivät rahat! Rahaa tuli ulos " + String.format("%.2f", bd.getMoney()) + "€");
        bd.returnMoney();
        rahaMaara.setText(bd.getMoney() + "€");
    }

    public void tulostaKuitti(View v) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("kuitti.txt", Context.MODE_PRIVATE));

            String s;
            String nimi = BottleDispenser.getUusinnimi();
            String koko = BottleDispenser.getUusinkoko();
            float hinta = BottleDispenser.getUusinhinta();
            if (nimi == null) {
                tuloste.setText("Ei tulostettavia kauppoja.");
            } else {

                s = "*** KUITTI ***\n" + nimi + " " + koko + "l " + hinta + "€";

                osw.write(s);
                osw.close();
                tuloste.setText("Kuitti tulostettu");
            }
        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
        } finally {
            System.out.println("KIRJOITETTU");
        }
    }

}
