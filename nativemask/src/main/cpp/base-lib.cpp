/**
 * Created by Didi Yulianto (anonimeact) on 27/01/2017.
 * author email didiyuliantos@gmail.com
 */

/**
 * Contoh algoritma sederhana untuk mengacak atau menyembunyikan data menggunakan ndk
 */

#include <jni.h>
#include <string>
#include <sstream>
#include <algorithm>

using namespace std;

string toHex(string input) {
    static const char *const lut = "0123456789abcdef";
    size_t len = input.length();

    string output;
    output.reserve(2 * len);
    for (size_t i = 0; i < len; ++i) {
        const unsigned char c = (const unsigned char) input[i];
        output.push_back(lut[c >> 4]);
        output.push_back(lut[c & 15]);
    }

    return output;
}

unsigned char hexval(unsigned char c) {
    if ('0' <= c && c <= '9')
        return c - '0';
    else if ('a' <= c && c <= 'f')
        return (unsigned char) (c - 'a' + 10);
    else if ('A' <= c && c <= 'F')
        return (unsigned char) (c - 'A' + 10);
    else abort();
}

string toString(string fixO) {
    string output;
    output.reserve(fixO.length() / 2);
    for (string::const_iterator p = fixO.begin(); p != fixO.end(); p++) {
        unsigned char c = hexval((unsigned char) *p);
        p++;
        if (p == fixO.end()) break; // incomplete last digit - should report error
        c = (c << 4) + hexval((unsigned char) *p); // + takes precedence over <<
        output.push_back(c);
    }

    return output;
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_anonimeact_donativemask_Blind_eKey(
        JNIEnv *env,
        jobject /* this */, jstring input) {

    const char *inC = env->GetStringUTFChars(input, NULL);
    string fixO = string(inC);
    string fix = toHex(fixO);
    if (fix.length() > 11) {
        char x7 = fix.at(0);
        char x8 = fix.at(6);
        char x9 = fix.at(1);
        char x10 = fix.at(10);

        fix[0] = x8;
        fix[6] = x7;
        fix[1] = x10;
        fix[10] = x9;
    }

    if (fix.length() > 1) {
        char x1 = fix.at(0);
        char x2 = fix.at(fix.length() - 1);
        fix[0] = x2;
        fix[fix.length() - 1] = x1;

        if (fix.length() > 4) {
            char x5 = fix.at(1);
            char x6 = fix.at(fix.length() - 2);
            fix[1] = x6;
            fix[fix.length() - 2] = x5;

            if (fix.length() > 7) {
                char x3 = fix.at(3);
                char x4 = fix.at(fix.length() - 3);

                fix[3] = x4;
                fix[fix.length() - 3] = x3;
            }
        }
    }

    return env->NewStringUTF(fix.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_anonimeact_donativemask_Blind_dKey(
        JNIEnv *env,
        jobject /* this */, jstring input) {

    const char *inC = env->GetStringUTFChars(input, NULL);
    string output = string(inC);

    if (output.length() > 1) {
        char x1 = output.at(0);
        char x2 = output.at(output.length() - 1);
        output[0] = x2;
        output[output.length() - 1] = x1;

        if (output.length() > 4) {
            char x3 = output.at(1);
            char x4 = output.at(output.length() - 2);

            output[1] = x4;
            output[output.length() - 2] = x3;

            if (output.length() > 7) {
                char x5 = output.at(3);
                char x6 = output.at(output.length() - 3);

                output[3] = x6;
                output[output.length() - 3] = x5;
            }
        }
    }

    if (output.length() > 11) {
        char x7 = output.at(0);
        char x8 = output.at(6);
        char x9 = output.at(1);
        char x10 = output.at(10);

        output[0] = x8;
        output[6] = x7;
        output[1] = x10;
        output[10] = x9;
    }

    string fix = toString(output);

    return env->NewStringUTF(fix.c_str());
}

