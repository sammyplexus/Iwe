package com.samuelagbede.sammybobo.iwe;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Agbede on 14/10/2016.
 */

public class Fonts extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Italic.otf");

        Typeface raleway_regular = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Regular.ttf");
        Typeface raleway_bold = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Bold.ttf");
        Typeface raleway_italic = Typeface.createFromAsset(getAssets(),"fonts/Raleway-Italic.ttf");

        Typeface LobsterTwo_regular = Typeface.createFromAsset(getAssets(),"fonts/LobsterTwo-Regular.otf");
        Typeface LobsterTwo_bold = Typeface.createFromAsset(getAssets(),"fonts/LobsterTwo-Bold.otf");
        Typeface LobsterTwo_italic = Typeface.createFromAsset(getAssets(),"fonts/LobsterTwo-Regular.otf");
    }


}
