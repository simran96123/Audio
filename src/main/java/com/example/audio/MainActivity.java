package com.example.audio;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.BitmapDrawableDecoder;

import java.security.AccessController;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AdapterAudioFolder obj_adapter;
    ArrayList<Model_Audio> al_audio = new ArrayList<>();
    RecyclerView.LayoutManager recyclerViewLayoutManager_Audio;
    RecyclerView recyclerView_Audio;

    Button audiobtn;

    public static final int MY_READ_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audiobtn = findViewById(R.id.audioButton);
     
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, MY_READ_PERMISSION_CODE);
        } else {

            audiobtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    iniit();
                    fn_audio();
                }


            });
        }
    }


    private void iniit() {

        recyclerView_Audio = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewLayoutManager_Audio = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView_Audio.setLayoutManager(recyclerViewLayoutManager_Audio);

        obj_adapter = new AdapterAudioFolder(getApplicationContext(), al_audio, new AdapterAudioFolder.audiolistener() {
            @Override
            public void onaudioclick(String path) {
                Toast.makeText(MainActivity.this, "" + path, Toast.LENGTH_SHORT).show();
            }

        });
        recyclerView_Audio.setAdapter(obj_adapter);
    }

    public void fn_audio() {

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_id, thum;
       // thum = bmp= ThumbnailUtils.extractThumbnail(bmp,80,50);
        //                            mediaListRowHolder.thumbnail.setImageBitmap(bmp);
//        public String getThumbnailsPath(Uri uri){
//            String[] Proj ={
//                    MediaStore.Audio.Media.DATA
//            };
//
//        }


       // ContentResolver cr=thumbnails.getContext().getContentResolver();
      //  BitmapFactory.Options options=new BitmapFactory.Options();


        String absolutePathOfImage = null;
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
       // cursor = ThumbnailUtils.createAudioThumbnail(80 , 50);
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Audio.Media.BUCKET_DISPLAY_NAME, MediaStore.Audio.Media._ID , MediaStore.Audio.Media.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME  );
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
       // Bitmap bitmap = MediaStore.Audio.Thumnails.get
       // Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
       // Uri urii = ContentUris.withAppendedId(sArtworkUri , Long.parseLong(songsList.get(position).get("ID")));
       // bmp= ThumbnailUtils.extractThumbnail(bmp,80,50);
      //  mediaListRowHolder.thumbnail.setImageBitmap(bmp);
       // Bitmap bitmap = BitmapDrawableDecoder

       thum = cursor.getColumnIndexOrThrow(MediaStore.Audio.Thumbnails.DATA);

        al_audio.clear();
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            Log.e("column_id", cursor.getString(column_id));
           Log.e("thum", cursor.getString(thum));

            Model_Audio obj_model = new Model_Audio();
            obj_model.setBoolean_selected(false);

            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));


            al_audio.add(obj_model);

        }
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == MY_READ_PERMISSION_CODE) {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();


                    iniit();
                }
                else {

                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }



    }





