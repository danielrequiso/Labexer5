package com.requiso.labexer5;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] company, nationality, type, head;
    int[] logo = {R.drawable.icbc, R.drawable.jpmorgan, R.drawable.chinaconstructionbank, R.drawable.agriculturalbank, R.drawable.bankofamerica, R.drawable.apple, R.drawable.pingan, R.drawable.bankofchina, R.drawable.shell, R.drawable.wellsfargo, R.drawable.exxon, R.drawable.at, R.drawable.samsungelectronics, R.drawable.citigroup};
    ArrayList<AndroidContent> contents = new ArrayList<>();
    ListView lstContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        company = getResources().getStringArray(R.array.companyName);
        nationality = getResources().getStringArray(R.array.country);
        type = getResources().getStringArray(R.array.industry);
        head = getResources().getStringArray(R.array.ceo);
        for(int i=0; i < company.length; i++) {
            contents.add(new AndroidContent(logo[i], company[i], nationality[i], type[i], head[i]));
        }

        AndroidAdapter adapter = new AndroidAdapter(this, R.layout.content,contents);
        lstContents = findViewById(R.id.IvAndroid);
        lstContents.setAdapter(adapter);
        lstContents.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, "companylist.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String choice = contents.get(i).getName();
            fos.write(choice.getBytes());
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(contents.get(i).getLogo());
            dialog.setTitle(contents.get(i).getName());
            dialog.setMessage(contents.get(i).getCountry());
            dialog.setMessage(contents.get(i).getIndustry());
            dialog.setMessage(contents.get(i).getCeo());

            dialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, folder.toString(), Toast.LENGTH_LONG).show();
                }
            });
            dialog.create().show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "file not found",Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(this, "cannot write...", Toast.LENGTH_LONG);
        }

    }
}
