//package com.example.leili.helloworld;
package com.example.avatarmind.RobotPlayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.robot.hw.RobotDevices;
import android.robot.motion.RobotMotion;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.robot.utils.LogUtils;
import android.util.Log;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
//import com.pubnub.api.PubNubUtil;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

public class MainActivity extends Activity {

    private RobotMotion mRobotMotion;

    //        PNConfiguration pnConfiguration = new PNConfiguration();

//        pnConfiguration.setSubscribeKey("my_subkey");

//        pnConfiguration.setPublishKey("my_pubkey");

//        pnConfiguration.setSecure(true);


//        PubNub pubnub = new PubNub(pnConfiguration);

    private PubNubUtil nubUtil = new PubNubUtil(ANDROID_DEVICE);
    private static final String PUBLISH_SUBSCRIBE_KEY = "demo";
    private static final String ANDROID_DEVICE = "Ipal_Robot";
    private static final String CHANNEL_NAME = "awesomeChannel";


    private SpeechManager mSpeechManager;
    private boolean isAsrEnabled;
    private boolean isTtsEnabled;
    private boolean isNluEnabled;
    private static final String TAG = "PollyDemo";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRobotMotion = MyRobotMotion.getInstanceOfMyRobotMotion(this).getmRobotMotion();
        myPubNubInit(); //pubNubInit();
        mRobotMotion.reset((int) RobotDevices.Units.ALL_MOTORS);

        mSpeechManager = (SpeechManager)getSystemService(SpeechService.SERVICE_NAME);
        Log.i(TAG,mSpeechManager+"");
        if (mSpeechManager == null) {
            mSpeechManager = new SpeechManager(this, new SpeechManager.OnConnectListener() {
                @Override
                public void onConnect(boolean status) {
                    if (status) {
                        LogUtils.d("speechManager init success!");
                        if (mSpeechManager.getAsrEnable()) {

                        }
                    } else {

                    }
                }
            }, "com.avatar.dialog");
        }
        Log.d(TAG, "onCreate");
        initSpeechManager();
        Log.d(TAG, "onPause  " + "getAsrStatus: " + mSpeechManager.getAsrEnable() + "  getTtsStatus: " + mSpeechManager.getTtsEnable() + "  getNluStatus: " + mSpeechManager.getNluEnable());


//        Button bkill = (Button) findViewById(R.id.bkill);
//        bkill.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                System.exit(0);
//            }
//        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        mRobotMotion.reset((int) RobotDevices.Units.ALL_MOTORS);
    }

    public void myPubNubInit(){

        nubUtil.setSubscribeKey(PUBLISH_SUBSCRIBE_KEY);
        nubUtil.setPublishKey(PUBLISH_SUBSCRIBE_KEY);
        nubUtil.buildConfig();
        nubUtil.buildChannel();
        nubUtil.subscribeToChannel(CHANNEL_NAME);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume  " + "getAsrStatus: " + isAsrEnabled + "  getTtsStatus: " + isTtsEnabled + "  getNluStatus: " + isNluEnabled);

        mSpeechManager.setAsrEnable(false);
        mSpeechManager.setTtsEnable(false);
        mSpeechManager.setNluEnable(false);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause  " + "getAsrStatus: " + mSpeechManager.getAsrEnable() + "  getTtsStatus: " + mSpeechManager.getTtsEnable() + "  getNluStatus: " + mSpeechManager.getNluEnable());
        restoreSpeechManager();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy  " + "getAsrStatus: " + isAsrEnabled + "  getTtsStatus: " + isTtsEnabled + "  getNluStatus: " + isNluEnabled);
        mRobotMotion.reset((int) RobotDevices.Units.ALL_MOTORS);
        restoreSpeechManager();
        super.onDestroy();
    }

    private void initSpeechManager() {
        isAsrEnabled = mSpeechManager.getAsrEnable();
        isTtsEnabled = mSpeechManager.getTtsEnable();
        isNluEnabled = mSpeechManager.getNluEnable();
    }

    private void restoreSpeechManager() {
        mSpeechManager.setAsrEnable(isAsrEnabled);
        mSpeechManager.setTtsEnable(isTtsEnabled);
        mSpeechManager.setNluEnable(isNluEnabled);
    }

    public void pubNubInit(){


        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("demo");
        pnConfiguration.setPublishKey("demo");

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {


                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    // This event happens when radio / connectivity is lost
                }

                else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {

                    // Connect event. You can do stuff like publish, and know you'll get it.
                    // Or just use the connected event to confirm you are subscribed for
                    // UI / internal notifications, etc

                    if (status.getCategory() == PNStatusCategory.PNConnectedCategory){
                        pubnub.publish().channel("awesomeChannel").message("hello!! from IPAL").async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                // Check whether request successfully completed or not.
                                if (!status.isError()) {

                                    // Message successfully published to specified channel.
                                }
                                // Request processing failed.
                                else {

                                    // Handle message publish error. Check 'category' property to find out possible issue
                                    // because of which request did fail.
                                    //
                                    // Request can be resent using: [status retry];
                                }
                            }
                        });
                    }
                }
                else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {

                    // Happens as part of our regular operation. This event happens when
                    // radio / connectivity is lost, then regained.
                }
                else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {

                    // Handle messsage decryption error. Probably client configured to
                    // encrypt messages and on live data feed it received plain text.
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // Handle new message stored in message.message
                if (message.getChannel() != null) {
                    // Message has been received on channel group stored in
                    // message.getChannel()
                }
                else {
                    // Message has been received on channel stored in
                    // message.getSubscription()
                }

            /*
                log the following items with your favorite logger
                    - message.getMessage()
                    - message.getSubscription()
                    - message.getTimetoken()
            */

                Log.d("Msg Recieved by IPAL:",message.getMessage().getAsString());
                if(message.getMessage().getAsString().toLowerCase().contains("glass")){
                    //makeRobotHighFive();
                }

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });

        pubnub.subscribe().channels(Arrays.asList("awesomeChannel")).execute();


    }

}


//package com.example.avatarmind.RobotPlayer;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.robot.hw.RobotDevices;
//import android.robot.motion.RobotMotion;
//import android.robot.motion.RobotPlayer;
//import android.robot.speech.SpeechManager;
//import android.robot.speech.SpeechService;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class MainActivity extends Activity {
//
////    private static final String TAG = "RunArmActivity";
//    private static final String TAG = "MainActivity";
//
//    private RobotPlayer mRobotPlayer;
//    private RobotMotion mRobotMotion;
//    private ImageView mTitleBack;
//    private Button mRunBtn;
//    private Button mPauseBtn;
//    private Button mResumeBtn;
//    private Button mStopBtn;
//    private int mArmLen = 0;
//    private int mPosition;
//    private SpeechManager mSpeechManager;
//
//    private SpeechManager.TtsListener mTtsListener = new SpeechManager.TtsListener() {
//        @Override
//        public void onBegin(int requestId) { }
//        @Override
//        public void onEnd(int requestId) { }
//        @Override
//        public void onError(int error) { }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getActionBar() != null) {
//            getActionBar().hide();
//        }
//
//        mRobotPlayer = new RobotPlayer();
//        mRobotMotion = new RobotMotion();
//        Intent intent = getIntent();
//        mPosition = intent.getIntExtra("Mode", -1);
//
////        mRobotPlayer.setDataSource("/sdcard/media/surprised.arm");
//        mRobotPlayer.setDataSource(getFromAssets("surprised.arm"), 0, mArmLen);
//        mRobotPlayer.prepare();
//        initView();
//        initListener();
//
//        String tts = "Hello there!";
//        mRobotPlayer.start();
//        mSpeechManager.startSpeaking(tts);
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mRobotMotion.reset(RobotDevices.Units.ALL_MOTORS);
//        mRobotPlayer.stop();
//
//        tts = "You're a star!";
////        mRobotPlayer.setDataSource(getFromAssets("surprised.arm"), 0, mArmLen);
////        mRobotPlayer.prepare();
//
//        mSpeechManager.startSpeaking(tts);
//        mRobotPlayer.start();
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        mRobotMotion.reset(RobotDevices.Units.ALL_MOTORS);
//        mRobotPlayer.stop();
//
////        tts = "Oh bother!";
////        mSpeechManager.startSpeaking(tts);
////        mRobotPlayer.start();
//
////        if (mPosition != -1) {
////            if (mPosition == 0) {
////                mRobotPlayer.setDataSource("/sdcard/media/surprised.arm");
////                System.out.println("mickey");
////
////            } else if (mPosition == 1) {
////                mRobotPlayer.setDataSource(getFromAssets("surprised.arm"), 0, mArmLen);
////                System.out.println("minnie");
////            }
////
////            mRobotPlayer.prepare();
////            initView();
////            initListener();
////        }
//    }
//
//    private void initView() {
//        setContentView(R.layout.activity_arm);
//
//        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
//        TextView title = (TextView) findViewById(R.id.common_title_text);
//
//        if (mPosition == 0) {
//            title.setText("Run .arm Files By File");
//        } else if (mPosition == 1) {
//            title.setText("Run .arm Files By Streams");
//        }
//
//        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
//
//        mRunBtn = (Button) findViewById(R.id.run);
//        mPauseBtn = (Button) findViewById(R.id.pause);
//        mResumeBtn = (Button) findViewById(R.id.resume);
//        mStopBtn = (Button) findViewById(R.id.stop);
//
//    }
//
//    private void initListener() {
//        mSpeechManager.setTtsListener(mTtsListener);
//    }
//
//    private byte[] getFromAssets(String fileName) {
//        try {
//            InputStream in = getResources().getAssets().open(fileName);
//            mArmLen = in.available();
//            byte[] buffer = new byte[mArmLen];
//            in.read(buffer);
//            return buffer;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new byte[0];
//    }
//
//}