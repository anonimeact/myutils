package com.anonimeact.dopermission;

import android.Manifest;

/**
 * Created by Didi Yulianto (anonimeact) on 31/07/2017.
 * author email didiyuliantos@gmail.com
 */

public interface Permissions {
    String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    String CALL_PHONE = Manifest.permission.CALL_PHONE;
    String CAMERA = Manifest.permission.CAMERA;
    String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    String READ_SMS = Manifest.permission.READ_SMS;
    String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    String SEND_SMS = Manifest.permission.SEND_SMS;
    String USE_SIP = Manifest.permission.USE_SIP;
    String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;

    String GROUP_CALENDAR = Manifest.permission_group.CALENDAR;
    String GROUP_CAMERA = Manifest.permission_group.CAMERA;
    String GROUP_CONTACTS = Manifest.permission_group.CONTACTS;
    String GROUP_LOCATION = Manifest.permission_group.LOCATION;
    String GROUP_MICROPHONE = Manifest.permission_group.MICROPHONE;
    String GROUP_PHONE = Manifest.permission_group.PHONE;
    String GROUP_SENSORS = Manifest.permission_group.SENSORS;
    String GROUP_SMS = Manifest.permission_group.SMS;
    String GROUP_STORAGE = Manifest.permission_group.STORAGE;
}
