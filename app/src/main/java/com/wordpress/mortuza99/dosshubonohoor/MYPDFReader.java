package com.wordpress.mortuza99.dosshubonohoor;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MYPDFReader extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypdfreader);

        pdfView = findViewById(R.id.pdfView);

        if (getIntent().getStringExtra("FILENAME") != null) {
            setTitle(getIntent().getStringExtra("FILENAME"));
            String FILENAME = getIntent().getStringExtra("FILENAME") + ".pdf";
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Environment.DIRECTORY_DOWNLOADS + "/Books/" + FILENAME);
            pdfView.fromFile(file)
                    .load();
        } else {
            String URL = getIntent().getStringExtra("URL");
            new RetrievePDFBytes().execute(URL);
        }

    }

    class RetrievePDFBytes extends AsyncTask<String, Void, byte[]> {
        ProgressDialog progDailog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(MYPDFReader.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected byte[] doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            pdfView.fromBytes(bytes).load();
            progDailog.dismiss();
        }

    }

}
