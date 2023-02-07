package com.wisedrive.customerapp.commonclasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

public class BitmapUtility {
    public static class PictUtil{

        public static String saveImageasThumbs(String filePath, Pair<Integer, Integer> required, String SuffixName) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(filePath, options);

            Pair<Integer, Integer> size = actualSizeCalculator(new Pair<Integer, Integer>(options.outWidth,options.outHeight),required);
            Bitmap resized = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filePath),size.first,size.second);
            Uri imageUri = Uri.fromFile(new File(filePath));

            String thumbPath = getSavePath() + SuffixName + imageUri.getLastPathSegment();
            saveToFile(thumbPath,resized);

            return thumbPath;
        }

        public Uri getImageUri2(Context inContext, Bitmap inImage) {
            Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
            return Uri.parse(path);
        }

        public static Uri getImageUri(Context inContext, Bitmap inImage) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        }
        public static File getSavePath() {
            File path;
            if (hasSDCard()) { // SD card
                path = new File(getSDCardPath() + "/.DealerApp");
                path.mkdir();
            } else {
                path = Environment.getDataDirectory();
            }
            return path;
        }
        public static File getSavePath1() {
            File path;
            path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File file = new File(path, "Mechanic.jpg");


            // Make sure the Pictures directory exists.
            path.mkdirs();

            return path;
        }
        public static String saveToFile(String filename, Bitmap bmp) {
            try {
                FileOutputStream out = new FileOutputStream(filename);
                bmp.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();
                return filename;
            } catch(Exception e) {return "";}
        }
        public static Pair<Integer, Integer> actualSizeCalculator(Pair<Integer, Integer> actual, Pair<Integer, Integer> required) {

            float actualHeight = (float)actual.second;
            float actualWidth = (float)actual.first;

            float maxHeight = (float)required.second;
            float maxWidth = (float)required.first;

            float imgRatio = actualWidth/actualHeight;

            float maxRatio = maxWidth/maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if(imgRatio < maxRatio) {
                    //adjust width according to maxHeight
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = Math.round(imgRatio * actualWidth);
                    actualHeight = maxHeight;
                }
                else if(imgRatio > maxRatio) {
                    //adjust height according to maxWidth
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = Math.round(imgRatio * actualHeight);
                    actualWidth = maxWidth;
                }
                else{
                    actualHeight = maxHeight;
                    actualWidth = maxWidth;
                }
            }
            return new Pair<>((int)actualWidth,(int)actualHeight);
        }

        public static Uri saveImageasThumbs3(Context c, Uri galleryPath, String filePath, Pair<Integer,Integer> required, String SuffixName) throws MalformedURLException, FileNotFoundException {

            Bitmap mapbit = decodeUri(c,galleryPath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(c.getContentResolver().openInputStream(galleryPath), null, options);

            Pair<Integer,Integer> size = actualSizeCalculator(new Pair<Integer, Integer>(options.outWidth,options.outHeight),required);
            Bitmap resized = ThumbnailUtils.extractThumbnail(mapbit,size.first,size.second);

            saveToFile(filePath,resized);

            Uri imageUri = Uri.fromFile(new File(filePath));

            return imageUri;
        }
        public static String saveImageasThumbs4(Context c, Uri galleryPath, String filePath, Pair<Integer,Integer> required, String SuffixName) throws MalformedURLException, FileNotFoundException {

            Bitmap mapbit = decodeUri(c,galleryPath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(c.getContentResolver().openInputStream(galleryPath), null, options);

            Pair<Integer,Integer> size = actualSizeCalculator(new Pair<Integer, Integer>(options.outWidth,options.outHeight),required);
            Bitmap resized = ThumbnailUtils.extractThumbnail(mapbit,size.first,size.second);

            saveToFile(filePath,resized);

            Uri imageUri = Uri.fromFile(new File(filePath));
            String thumbPath = getSavePath() + SuffixName + imageUri.getLastPathSegment();
            saveToFile(thumbPath,resized);

            return thumbPath;
        }
        public static Bitmap decodeUri(Context c, Uri uri) throws FileNotFoundException {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = o.inSampleSize;
            return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
        }
        public static boolean hasSDCard() { // SD????????
            String status = Environment.getExternalStorageState();
            return status.equals(Environment.MEDIA_MOUNTED);
        }
        public static String getSDCardPath() {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            return path.getAbsolutePath();
        }
    }
}
