package com.anonimeact.utility;

import java.util.regex.Pattern;

/**
 * Created by Didi Yulianto (anonimeact) on 27/07/2017.
 * author email didiyuliantos@gmail.com
 */

public class DoSplit {
    public static String exe(int i, String by, String param) {
        String[] dataArray = param.split(Pattern.quote(by));
        return dataArray[i];
    }
}
