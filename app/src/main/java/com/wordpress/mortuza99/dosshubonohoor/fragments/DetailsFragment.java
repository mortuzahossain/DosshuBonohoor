package com.wordpress.mortuza99.dosshubonohoor.fragments;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.mortuza99.dosshubonohoor.MYPDFReader;
import com.wordpress.mortuza99.dosshubonohoor.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    public DetailsFragment() {
        // Required empty public constructor
    }

    String name, image, downloadlink;
    CardView readOnline, openDownload, openFromFolder;

    File file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle bundle = getArguments();
        name = String.valueOf(bundle.getString("NAME"));
        image = String.valueOf(bundle.getString("IMAGE"));
        downloadlink = String.valueOf(bundle.getString("DOWNLOADURL"));
        file = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS + "/Books/" + name + ".pdf");


        TextView bookNameDetailPage = view.findViewById(R.id.bookNameDetailPage);
        ImageView bookImageDetailPage = view.findViewById(R.id.bookImageDetailPage);

        bookNameDetailPage.setText(name);

        Picasso.get()
                .load(image)
                .into(bookImageDetailPage);

        readOnline = view.findViewById(R.id.readOnline);
        openDownload = view.findViewById(R.id.download);
        openFromFolder = view.findViewById(R.id.openFromFolder);

        ChangeButton();

        readOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFromFolder("URL", downloadlink);
            }
        });

        openDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadMe();
            }
        });

        openFromFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFromFolder("FILENAME", name);
            }
        });

        return view;
    }

    private void ChangeButton() {
        if (!file.exists()) {
            readOnline.setVisibility(View.VISIBLE);
            openDownload.setVisibility(View.VISIBLE);
            openFromFolder.setVisibility(View.GONE);
        } else {
            readOnline.setVisibility(View.GONE);
            openDownload.setVisibility(View.GONE);
            openFromFolder.setVisibility(View.VISIBLE);
        }
    }

    private void DownloadMe() {
        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            name = name + ".pdf";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadlink));
            request.setTitle(name);
            request.setDescription(name + " Downloading..");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/Books/", name);
            DownloadManager downloadManager = (DownloadManager) getActivity().getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            TakeMeHome();
        }
    }

    private void TakeMeHome() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.mainframe, homeFragment);
        fragmentTransaction.commit();
    }

    private void OpenFromFolder(String key, String value) {
        Intent intent = new Intent(getActivity(), MYPDFReader.class);
        intent.putExtra(key, value);
        startActivity(intent);
    }

}
