package com.example.asyntask;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvResults;
    Button btnRollDice;
    EditText etNrTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNrTimes = (EditText) findViewById(R.id.etNrTimes);
        tvResults = (TextView) findViewById(R.id.tvResults);
        btnRollDice = (Button) findViewById(R.id.btnRollDice);

        tvResults.setVisibility(View.GONE);

        btnRollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int nrOfTimes = Integer.parseInt(etNrTimes.getText()
                        .toString()
                        .trim());
                new ProcessDiceInBackground()
                        .execute(nrOfTimes);

            }
        });

    }
    public class ProcessDiceInBackground extends AsyncTask < Integer, Integer, String > {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(Integer.parseInt(etNrTimes.getText()
                    .toString()
                    .trim()));
            dialog.show();
        }

        @Override
        protected String doInBackground(Integer...integers) {
            int ones = 0, two = 0, threes = 0, fours = 0, fives = 0, sixes = 0, randomNumber;

            Random random = new Random();
            String results;
            double currentProgress = 0;
            double previousProgress = 0;
            for (int i = 0; i < integers[0]; i++) {
                currentProgress = (double) i / integers[0];
                if (currentProgress - previousProgress >= 0.03) {
                    publishProgress(i);
                    previousProgress = currentProgress;

                }
                randomNumber = random.nextInt(6) + 1;

                switch (randomNumber) {
                    case 1:
                        ones++;
                        break;

                    case 2:
                        two++;
                        break;

                    case 3:
                        threes++;
                        break;

                    case 4:
                        fours++;
                        break;

                    case 5:
                        fives++;
                        break;

                    default:
                        sixes++;
                }
            }
            results = "Results: \n1: " + ones + "\n2: " + two + "\n3: " + threes + "\n4: " + fours +
                    "\n5: " + fives + "\n6: " + sixes;
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer...values) {
            super.onProgressUpdate(values);

            dialog.setProgressStyle(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();
            tvResults.setText(s);
            tvResults.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Process done!", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}