package com.anonimeact.utility;

import java.util.regex.Pattern;

/**
 * Created by Didi Yulianto (anonimeact) (anonimeact) on 11/07/2017.
 * author email didiyuliantos@gmail.com
 */

public class DoRegEx {
    public static boolean email(String e){
        final Pattern pattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,100}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9]{0,10}" +
                        ")+");
        return !pattern.matcher(e).matches();
    }

    public static boolean nama(String e){
        final Pattern pattern = Pattern.compile(
                "[A-Za-z\\s]+");
        return !pattern.matcher(e).matches();
    }

    public static boolean phone(String e){
        final Pattern pattern = Pattern.compile(
                "[+0-9\\s]+");
        return !pattern.matcher(e).matches();
    }
}
