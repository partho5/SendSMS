package xyz.getsoft.sendsms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton sendSmsBtn, selectFileBtn;
    private static final int READ_REQUEST_CODE = 42;
    private Intent intent ;
    TextView selectedFileNameTV;
    String selectedFileName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //external permission to send sms
        ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.SEND_SMS }, 1);

        //external request to read storage
        ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 1);







        final EditText msgEditText = findViewById(R.id.msgEditText);

        sendSmsBtn = findViewById(R.id.sendSmsBtn);
        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedFileName == null){
                    Toast.makeText(getApplicationContext(), "No File Selected", Toast.LENGTH_LONG).show();
                }else{
                    String filePath = Environment.getExternalStorageDirectory()+"/"+selectedFileName;
                    MyFileReader myFileReader = new MyFileReader(filePath);
                    ArrayList numbersGotFromFile = myFileReader.readLines();


                    final List<String> nums = new ArrayList();
                    for (Object line : numbersGotFromFile){
                        nums.add(line.toString());
                    }

                    String msg = msgEditText.getText().toString();
                    //new SmsSender(getApplicationContext(), nums, msg).send(sendingStatusTv);
                    new SmsSender(getApplicationContext(), nums, msg).send();
                }
            }
        });


        selectFileBtn = findViewById(R.id.selectFileBtn);



        intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        Uri startDir = Uri.fromFile(new File("/sdcard"));
// Title
        intent.putExtra("explorer_title", "Select a file");
// Optional colors
        intent.putExtra("browser_title_background_color", "440000AA");
        intent.putExtra("browser_title_foreground_color", "FFFFFFFF");
        intent.putExtra("browser_list_background_color", "00000066");
// Optional font scale
        intent.putExtra("browser_list_fontscale", "120%");
// Optional 0=simple list, 1 = list with filename and size, 2 = list with filename, size and date.
        intent.putExtra("browser_list_layout", "2");



        selectFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });



    }






    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == READ_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Uri uri = intent.getData();
                String type = intent.getType();
                if (uri != null)
                {
                    String path = uri.toString();
                    if (path.toLowerCase().startsWith("file://"))
                    {
                        // Selected file/directory path is below
                        File myFile = (new File(URI.create(path)));
                        selectedFileName = myFile.getName();

                        selectedFileNameTV = findViewById(R.id.selectedFileName);
                        selectedFileNameTV.setText(selectedFileName);

                        path = myFile.getAbsolutePath();
                        intent.putExtra("filePath", path);
                        Log.d("selected filePath", path);
                    }

                }
            }
            //else LogHelper.i(TAG,"Back from pick with cancel status");
        }
    }


}
