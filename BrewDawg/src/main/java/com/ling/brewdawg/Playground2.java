package com.ling.brewdawg;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.os.Handler;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Playground2 extends ActionBarActivity {

    // For progressbar
    private ProgressBar beerProgressbar;

    // Tekstfelt

    private TextView beerText;
    private TextView beerProgressText;
    private EditText DateEditText;

    // Datoer

    SimpleDateFormat dateFormat;
    Calendar dateBrewStart;
    Calendar dateBrewEnd;
    Calendar dateNow;


    // Ny kalender hvor vi kan sette inn plukket dato
    Calendar myCalendar = Calendar.getInstance();


    // Hva gjøres med datoen som ble valgt. Oppdater Calendar.
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTextField();
        }

    };

    // Oppdater tekstfeltet med verdi satt i kalender
    private void updateDateTextField() {

        String myFormat = "dd-MMMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        DateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground2);

        // Finn widgets
        beerText = (TextView) findViewById(R.id.beerDescription);
        beerProgressText = (TextView) findViewById(R.id.beerProgressText);
        beerProgressbar = (ProgressBar) findViewById(R.id.beerProgressBar);
        DateEditText = (EditText) findViewById(R.id.DateEditText);


        // Klikk inne i tekstboks, fyr opp en datepicker.

        DateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(Playground2.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        beerText.setText("Pale Ale Test");

        // Sett dato for start og slutt
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        dateBrewStart = Calendar.getInstance();
        dateBrewStart.add(Calendar.MINUTE, -1);

        dateBrewEnd = Calendar.getInstance();
        //dateBrewEnd.add(Calendar.DATE, 1); // days
        dateBrewEnd.add(Calendar.MINUTE, 2);

        dateNow = Calendar.getInstance();




        beerProgressbar.setMax(100);


        // Fyr opp timer
        // gettime -> Returns this Date as a millisecond value.
        CountDownTimer countDown = new CountDownTimer(dateBrewEnd.getTimeInMillis() - dateNow.getTimeInMillis(), 1000) {

            int secondsRemaining;

            public void onTick(long millisecondsRemaining) {
                secondsRemaining = (int) (millisecondsRemaining / 1000);

                float NowMinusStart = (float) ((System.currentTimeMillis() - dateBrewStart.getTimeInMillis()) / 100 );
                float EndMinusStart = (float) ((dateBrewEnd.getTimeInMillis() - dateBrewStart.getTimeInMillis()) / 100);
                float percent = (NowMinusStart / EndMinusStart) * 100;

                beerProgressText.setText("Remaining: " + secondsRemaining );

                beerProgressbar.setProgress((int) percent);


                Log.d("d", "current: " + System.currentTimeMillis() + " " + percent);


            }


            public void onFinish() {
                 beerProgressText.setText("Done!");
            }
        }.start();

        // end ling

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    // ling




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.playground2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_playground2, container, false);
            return rootView;
        }
    }

}


