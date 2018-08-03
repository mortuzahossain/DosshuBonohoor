package com.wordpress.mortuza99.dosshubonohoor;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;


public class MYPDFReader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypdfreader);

        PDFView pdfView = findViewById(R.id.pdfView);

        if (getIntent().getStringExtra("FILENAME") == null) {
            String FILENAME = getIntent().getStringExtra("FILENAME");
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Environment.DIRECTORY_DOWNLOADS + "/Books/" + FILENAME);
            pdfView.fromFile(file)
                    .load();
        } else {
            String URL = getIntent().getStringExtra("URL");
            // TODO: LOAD USING INTERNET
        }

    }
}
