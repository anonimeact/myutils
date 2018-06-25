package com.anonimeact.utility;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Didi Yulianto (anonimeact) on 26/04/2017.
 * author email didiyuliantos@gmail.com
 */

public class DoTypeFace {
    public static Typeface set(Context context, String pathInAsset){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), pathInAsset);
        return typeface;
    }
}