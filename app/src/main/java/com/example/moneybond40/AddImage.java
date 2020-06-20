package com.example.moneybond40;


import android.app.Activity;
import android.content.Intent;
;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class AddImage extends Activity {
   // private static int PERMISSION_REQUEST = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[] Manifest.permission.READ_EXTERNAL_STORAGE, PERMISSION_REQUEST);
//        }
        ImageView buttonLoadImage = findViewById(R.id.icon_button);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 256);
                intent.putExtra("outputY", 256);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 1);
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode != RESULT_OK) {

                if (requestCode == RESULT_LOAD_IMAGE && data != null) {
                    Uri imageUri = data.getData();
                    ImageView imageView =  findViewById(R.id.icon_button);
                    imageView.setImageURI(imageUri);}}}
}