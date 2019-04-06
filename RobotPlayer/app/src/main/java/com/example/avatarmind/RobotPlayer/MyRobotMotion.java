package com.example.avatarmind.RobotPlayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.robot.hw.RobotDevices;
import android.robot.motion.RobotMotion;
import android.robot.motion.RobotPlayer;
import android.util.Log;

import java.io.InputStream;
import java.util.Random;


/**
 * Created by venky on 3/27/18.
 */

public class MyRobotMotion {

    //Keywords for communication
    private static final String IPAL_WIN = "win";
    private static final String PLAY = "play";
    private static final String IPAL_LOSE = "lose";
    private static final String CALM = "calm";

    private static final String IS_BLOCKED = "is_blocked";
    private static final String HAS_BLOCKED = "has_blocked";

    private static final String IPAL_SCORE = "iPal_score";
    private static final String HUMAN_SCORE = "human_score";

    private final static String TAG = MyRobotMotion.class.getSimpleName();

    private static MyRobotMotion mrm = null;
    private RobotMotion mRobotMotion;


    private RobotPlayer mRobotPlayer;

    private Context context;

    private Random rand = new Random();

    private MediaPlayer mediaPlayer;



    //Make only one instance of Robot Motion Object
    private MyRobotMotion(Context context){
        mRobotMotion = new RobotMotion();
        this.context = context;


        mRobotMotion.doAction(mRobotMotion.emoji(RobotMotion.Emoji.DEFAULT));
    }

    private String getString(int i){
        return context.getResources().getString(i);
    }

    /**
     *Singleton object access
     */
    public static MyRobotMotion getInstanceOfMyRobotMotion(Context context){
        if(mrm ==null)mrm = new MyRobotMotion(context);

        return mrm;
    }

    /*
    Pass without context.
     */
    public static MyRobotMotion getInstanceOfMyRobotMotion(){
        return mrm;
    }


    /**
     * Function called from pubnub listener
     * @param msg from the listener
     */
    public void pubNubCallBack(String msg){

        msg = msg.trim();
        //Log.i("TRIMMED",msg);


        if(msg.contains(PLAY)){

            Log.i("PLAY MSG","");
            // The breathing after every play was weird...
            //playSong(R.raw.breath);
            //makeRobotMoveWithCustomMotion(0);
            //new AwsPollyUtil(context).play(getString(R.string.hmm));
        }

        else if (msg.contains(CALM)) {
            makeRobotCalm();

            Log.i("CALM MSG","");
        }


        if(msg.contains(IPAL_WIN)){
            int id = Integer.parseInt(msg.split(" ")[1]);
            Log.i(IPAL_WIN,id+"");
            Log.i("IPAL_WIN MSG","");
            makeRobotWin(id);
            playSong(R.raw.yay_won);
            //new AwsPollyUtil(context).play(getString(R.string.won));
        }

        else if(msg.contains(IPAL_LOSE)){
            int id = Integer.parseInt(msg.split(" ")[1]);
            Log.i(IPAL_LOSE,id+"");
            makeRobotLose(id);
            playSong(R.raw.oh_no);
            //new AwsPollyUtil(context).play(getString(R.string.lost));
            Log.i("IPAL_LOSE MSG","");


        }

        else if(msg.contains(IS_BLOCKED) || msg.contains(HUMAN_SCORE)){
            int id = Integer.parseInt(msg.split(" ")[1]);
            makeRobotMoveWithCustomSadMotion(id);
            //LEILIIIIIII
            playSong(R.raw.no);
            mRobotMotion.emoji(RobotMotion.Emoji.SAD);
            Log.i("MSG",msg);
        }

        else if(msg.contains(HAS_BLOCKED) || msg.contains(IPAL_SCORE)){
            int id = Integer.parseInt(msg.split(" ")[1]);
            makeRobotMoveWithCustomHappyMotion(id);
            //LEILIIIIIII
            playSong(R.raw.yay);
            mRobotMotion.emoji(RobotMotion.Emoji.SMILE);
            Log.i("MSG",msg);
        }


        else if(msg.equals(HAS_BLOCKED)){
            makeRobotMoveWithCustomMotion(0);
            playSong(R.raw.yay);
        }


    }


    public  void makeRobotCalm(){

        mRobotMotion.reset((int) RobotDevices.Units.ALL_MOTORS);
        //LEILIIIII
        mRobotMotion.emoji(RobotMotion.Emoji.DEFAULT);
    }

    public  void makeRobotWin(int id){

        //mRobotMotion.doAction(RobotMotion.Action.OH_YEAH);
        makeRobotMoveWithCustomHappyMotion(id);

    }

    public  void makeRobotLose(int id){

        //mRobotMotion.doAction(RobotMotion.Action.WORRY);
        makeRobotMoveWithCustomSadMotion(id);
    }

    public RobotMotion getmRobotMotion() {
        return mRobotMotion;
    }


    public void makeRobotMoveWithCustomMotion(int emotion) {

        String filename = "";

        switch (emotion) {
            case 0:
                filename = "happy3.arm";
                break;

            case 1:
                filename = "sad3.arm";
                break;
        }


        // Load binary ARM data stream
        byte[] movement = getFromAssets(filename);
        mRobotPlayer = new RobotPlayer();
        mRobotPlayer.setDataSource(movement, 0, movement.length);
        mRobotPlayer.prepare();
        mRobotPlayer.start();
    }
    public void makeRobotMoveWithCustomSadMotion(int id) {

        // Load binary ARM data stream
        //Leili
        int n = id;//rand.nextInt(5) +1 ;
        byte[] movement = getFromAssets("sad"+ String.valueOf(n)+".arm");

        if( n == 1 || n == 4) {
            mRobotMotion.doAction(RobotMotion.Emoji.CRY);
        }
        else{
            mRobotMotion.doAction(RobotMotion.Emoji.SAD);
        }
        // play ARM frame sequence
        mRobotPlayer = new RobotPlayer();
        mRobotPlayer.setDataSource(movement, 0, movement.length);
        mRobotPlayer.prepare();
        mRobotPlayer.start();

        //mRobotPlayer.stop();

        //mRobotMotion.emoji(RobotMotion.Emoji.DEFAULT);
        Log.i("Sad Gesture ",""+n);


    }

    public void makeRobotMoveWithCustomHappyMotion(int id){

        // Load binary ARM data stream
        //Leili
        int n = id;//rand.nextInt(5) +1 ;
        byte[] movement = getFromAssets("happy"+ String.valueOf(n)+".arm");


        mRobotMotion.doAction(RobotMotion.Emoji.SMILE);
        // play ARM frame sequence
        mRobotPlayer = new RobotPlayer();
        mRobotPlayer.setDataSource(movement, 0, movement.length);
        mRobotPlayer.prepare();
        mRobotPlayer.start();

        Log.i("Happy Gesture ",""+n);

        //mRobotPlayer.stop();
        //mRobotMotion.emoji(RobotMotion.Emoji.DEFAULT);
    }


    public void makeRobotMoveWithCustomMotion(){

        //Leili
        mRobotMotion.emoji(RobotMotion.Emoji.INDIFFERENT);

    }

    /**
     * Get byte stream from asset
     * @param fileName of asset
     * @return byte array
     */
    private byte[] getFromAssets(String fileName) {
        int mArmLen = 0;
        try {
            InputStream in = this.context.getResources().getAssets().open(fileName.trim());
            // get file's byte count
            mArmLen = in.available();
            // new byte array
            byte[] buffer = new byte[mArmLen];
            in.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * Play song from Raw file
     * @param id of song in raw file
     */
    private void playSong(int id){
     mediaPlayer = MediaPlayer.create(context,id);
     mediaPlayer.start();

    }


}
