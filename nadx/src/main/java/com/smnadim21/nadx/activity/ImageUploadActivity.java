package com.smnadim21.nadx.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.smnadim21.nadx.tools.NandX;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUploadActivity extends BaseActivity {
    protected String mCurrentPhotoPath = null;
    protected File photoFile;
    protected Uri photoURI;
    protected static final int IMAGE_QUALITY = 50;

    protected int PERMISSION_ALL = 56;
    protected String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Bitmap rotateBitmap(InputStream is, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap source = BitmapFactory.decodeStream(is);
        if (source.getWidth() > source.getHeight()) {
            Log.e("BITMAP", "ROTATE TRUE" + angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        } else {
            Log.e("BITMAP", "ROTATE False");
            return source;
        }
    }

    protected File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("createdImageFile>>", mCurrentPhotoPath);
        return image;
    }

    protected void chooseImageFromCam(int req) {
        if (!hasPermissions(getRootAvtivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(ImageUploadActivity.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            } else {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) == null)

                    Tools.snackInfo(getRootAvtivity(),
                            "Getting Image Error",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    chooseImageFromCam(0);
                                }
                            });
                return;}
            */



            /*if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {*/
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                NandX.snackErr(getRootAvtivity(),
                        ex.getMessage(),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chooseImageFromCam(0);
                            }
                        });
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getRootAvtivity(),
                        getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, req);
            } else {
                NandX.snackErr(getRootAvtivity(),
                        "File Creation Failed!",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chooseImageFromCam(0);
                            }
                        });
            }
        }


    }

    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    protected File fromBitmap(Bitmap source) throws IOException {

        File temp = createImageFile();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = new FileOutputStream(temp);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return temp;

    }

    private void chooseImage(int req) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        startActivityForResult(getIntent, req);

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    protected Activity getRootAvtivity() {
        return ImageUploadActivity.this;
    }

/*    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.e("result Code", String.valueOf(resultCode) + "   " + String.valueOf(requestCode));

        if (*//*requestCode == PICK_IMAGE_REQUEST &&*//* resultCode == -1) {

            try {
                image[requestCode].setImageURI(photoURI);

                ExifInterface exif = new ExifInterface(mCurrentPhotoPath);
                exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                Log.e("Rotation x", exifToDegrees(exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) + "");
                Bitmap b_nid = rotateBitmap(getContentResolver().openInputStream(photoURI), exifToDegrees(exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)));
                files[requestCode] = fromBitmap(b_nid);
                Glide.with(MainActivity.this)
                        .load(b_nid)
                        .placeholder(R.drawable.ic_camera)
                        .into(image[requestCode]);
                // image[requestCode].setImageBitmap(b_nid);


            } catch (Exception e) {
                e.printStackTrace();
                Tools.snackErr(MainActivity.this
                        , "Image getting error " + requestCode, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chooseImageFromCam(requestCode);
                            }
                        });


            }


        }
    }*/
}