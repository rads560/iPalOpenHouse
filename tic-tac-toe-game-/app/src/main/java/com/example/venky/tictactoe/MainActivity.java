package com.example.venky.tictactoe;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //For logging on the terminal
    private final static String TAG = MainActivity.class.getSimpleName();

    //For pubnub api
    private PubNubUtil pubNubUtil;
    private static final String PUBLISH_SUBSCRIBE_KEY = "demo";
    private static final String ANDROID_DEVICE = "Phone";
    private static final String CHANNEL_NAME = "awesomeChannel";

    //Keywords for communication
    private static final String WIN = "win";
    private static final String PLAY = "play";
    private static final String LOSE = "lose";
    private static final String CALM = "calm";

    private static final String IS_BLOCKED = "is_blocked";
    private static final String HAS_BLOCKED = "has_blocked";

    //For logging into a file
    private static final String logFileName = "log.txt";
    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Log";
    public File file;

    //Random number generatot for different difficulties
    private Random rand = new Random();


    ProgressDialog progressDialog;

    //Flags for gaming
    private static boolean allowAction = true;
    private static boolean gameOutcome = true;
    
    boolean PLAYER_X = false;

    //AI player with difficulty
    AIPlayer ai = new AIPlayer(3);

    //Game state user is playing
    private GameState State = new GameState();
    //Map index to button on screen
    HashMap<Integer, Integer> indexToButton;

    int TURN_COUNT = 0;

    static int difficulty = 3;

    Button b00;
    Button b01;
    Button b02;

    Button b10;
    Button b11;
    Button b12;

    Button b20;
    Button b21;
    Button b22;

    Button bReset;

    Button bStart;
    Button bStop;

    Button bIntro;
    Button bEnd;

    TextView tvInfo;



    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main);

        File dir = new File(path);
        Log.i(TAG,dir.mkdirs()+"");
        file = new File(path+"/"+logFileName);


        b00 = (Button) findViewById(R.id.b00);
        b01 = (Button) findViewById(R.id.b01);
        b02 = (Button) findViewById(R.id.b02);

        b10 = (Button) findViewById(R.id.b10);
        b11 = (Button) findViewById(R.id.b11);
        b12 = (Button) findViewById(R.id.b12);

        b20 = (Button) findViewById(R.id.b20);
        b21 = (Button) findViewById(R.id.b21);
        b22 = (Button) findViewById(R.id.b22);

        bReset = (Button) findViewById(R.id.bReset);
        bStop = (Button) findViewById(R.id.bStop);
        bStart = (Button) findViewById(R.id.bStart);
        bIntro = (Button) findViewById(R.id.bIntro);
        bEnd = (Button) findViewById(R.id.bEnd);
        tvInfo = (TextView) findViewById(R.id.tvInfo);

        bReset.setOnClickListener(this);
        bStart.setOnClickListener(this);
        bStop.setOnClickListener(this);

        bIntro.setOnClickListener(this);
        bEnd.setOnClickListener(this);

        bStop.setEnabled(false);

        b00.setOnClickListener(this);
        b01.setOnClickListener(this);
        b02.setOnClickListener(this);

        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);

        b20.setOnClickListener(this);
        b21.setOnClickListener(this);
        b22.setOnClickListener(this);

        //map index to button
        indexToButton = new HashMap<>();
        indexToButton.put(0,R.id.b00);
        indexToButton.put(1,R.id.b01);
        indexToButton.put(2,R.id.b02);
        indexToButton.put(3,R.id.b10);
        indexToButton.put(4,R.id.b11);
        indexToButton.put(5,R.id.b12);
        indexToButton.put(6,R.id.b20);
        indexToButton.put(7,R.id.b21);
        indexToButton.put(8,R.id.b22);

        //Initialise pubnub util
        myPubNubInit();

        ai.setDifficulty(difficulty);

    }

    /**
     * Initilaize pubnub api
     */
    public void myPubNubInit(){

        pubNubUtil = new PubNubUtil(ANDROID_DEVICE);
        pubNubUtil.setSubscribeKey(PUBLISH_SUBSCRIBE_KEY);
        pubNubUtil.setPublishKey(PUBLISH_SUBSCRIBE_KEY);
        pubNubUtil.buildConfig();
        pubNubUtil.buildChannel();
        pubNubUtil.subscribeToChannel(CHANNEL_NAME);

    }

    /**
     * Custom on click listener for buttons
     * @param view
     */
    @Override
    public void onClick(View view) {

        int optimalMove = -1;
        int actualMove = -1;
        String encoded_matrix = "";

        //Log.d(TAG, "Inside onClick: "+PLAYER_X);

        String displayChar = "X";
        int player = 2,enemy =1;

        //Switch type of player on every click
//        if (!PLAYER_X) {
//            displayChar = "O";
//            player = 1;
//            enemy = 2;
//            pubNubUtil.publishToChannel(CHANNEL_NAME,CALM);
//            FileUtil.Save(file,new LogEntry(
//                    new Date().getTime(),ANDROID_DEVICE,"Played","Human","","",difficulty+"","",State.getEncodedState()
//            ));
//        }
//        else {
//            FileUtil.Save(file,new LogEntry(
//                    new Date().getTime(),ANDROID_DEVICE,"Played","Ipal","","",difficulty+"","",State.getEncodedState()
//            ));
//        }
        //Find optimal move for current state
//        optimalMove = State.hasConcecutiveWithWinner(enemy);

        //Log.d(TAG, "Inside onClick: "+TURN_COUNT);
        //Log.d(TAG, "Inside onClick: "+view.getId());


        boolean resetButtonPressed = false;
        boolean stopButtonPressed = false;
        boolean startButtonPressed = false;
        boolean introButtonPressed = false;
        boolean endButtonPressed = false;
        //Act based on button pressed
        switch (view.getId()) {
//            case R.id.b00:
//                b00.setText(displayChar);
//                State.Update(new Tuple(player,0));
//                b00.setEnabled(false);
//                actualMove = 0;
//                break;
//
//            case R.id.b01:
//                b01.setText(displayChar);
//                State.Update(new Tuple(player,1));
//                b01.setEnabled(false);
//                actualMove = 1;
//                break;
//
//            case R.id.b02:
//                b02.setText(displayChar);
//                State.Update(new Tuple(player,2));
//                b02.setEnabled(false);
//                actualMove = 2;
//                break;
//
//            case R.id.b10:
//                b10.setText(displayChar);
//                State.Update(new Tuple(player,3));
//                b10.setEnabled(false);
//                actualMove = 3;
//                break;
//
//            case R.id.b11:
//                b11.setText(displayChar);
//                State.Update(new Tuple(player,4));
//                b11.setEnabled(false);
//                actualMove = 4;
//                break;
//
//            case R.id.b12:
//                b12.setText(displayChar);
//                State.Update(new Tuple(player,5));
//                b12.setEnabled(false);
//                actualMove = 5;
//                break;
//
//            case R.id.b20:
//                b20.setText(displayChar);
//                State.Update(new Tuple(player,6));
//                b20.setEnabled(false);
//                actualMove = 6;
//                break;
//
//            case R.id.b21:
//                b21.setText(displayChar);
//                State.Update(new Tuple(player,7));
//                b21.setEnabled(false);
//                actualMove = 7;
//                break;
//
//            case R.id.b22:
//                b22.setText(displayChar);
//                State.Update(new Tuple(player,8));
//                b22.setEnabled(false);
//                actualMove = 8;
//                break;
            case R.id.bIntro:
                introButtonPressed = true;
                actualMove = -10;
                break;

            case R.id.bEnd:
                endButtonPressed = true;
                actualMove = -10;
                break;

            case R.id.bReset:
                resetButtonPressed = true;
                actualMove = -10;
                break;


            case R.id.bStart:
                startButtonPressed = true;
//                int sad = rand.nextInt(5)+1;
//                pubNubUtil.publishToChannel(CHANNEL_NAME,IS_BLOCKED+" "+sad);
                actualMove = -10;
                break;

//            case R.id.bStop:
//                stopButtonPressed = true;
////                int happy = rand.nextInt(5)+1;
////                pubNubUtil.publishToChannel(CHANNEL_NAME,HAS_BLOCKED+" "+happy);
//                actualMove = -10;
//                break;

            default:
                break;

        }

        if (resetButtonPressed) {
            int happy = rand.nextInt(5)+1;
            pubNubUtil.publishToChannel(CHANNEL_NAME,HAS_BLOCKED+" "+happy);
            //Ipal blocked user
//                    Log.i(TAG,"Ipal has blocked player");
//                    int happy = rand.nextInt(5)+1;
//                    pubNubUtil.publishToChannel(CHANNEL_NAME,HAS_BLOCKED+" "+happy);
//                    FileUtil.Save(file,new LogEntry(
//                            new Date().getTime(),ANDROID_DEVICE,"Blocked","Ipal", ""+happy,"4",difficulty+"",true+"",State.getEncodedState()
//                    ));
//            stop();
        }
        else if(startButtonPressed){
            int sad = 3;
            pubNubUtil.publishToChannel(CHANNEL_NAME,IS_BLOCKED+" "+sad);
//            System.out.println("Started 1");
////            int happy = rand.nextInt(5)+1;
////            pubNubUtil.publishToChannel(CHANNEL_NAME,HAS_BLOCKED+" "+happy);
//            start();
        }
        else if(introButtonPressed){
            pubNubUtil.publishToChannel(CHANNEL_NAME,"intro");
        }
        else if(endButtonPressed){
            pubNubUtil.publishToChannel(CHANNEL_NAME,"end");
        }
//        else if(stopButtonPressed){
////            int happy = rand.nextInt(5)+1;
////            pubNubUtil.publishToChannel(CHANNEL_NAME,HAS_BLOCKED+" "+happy);
//            stop();
//        }
        //Else a normal move has been played
//        else {
//            TURN_COUNT++;
//            Log.i(optimalMove+"",actualMove+"");
//
//            //If optimal move is what player played .. check for blocking
//            if(optimalMove==actualMove){
//
//                //Log.i(optimalMove+"",actualMove+"");
//
//                if(PLAYER_X){
//                    //Ipal blocked user
//                    Log.i(TAG,"Ipal has blocked player");
//                    int happy = rand.nextInt(5)+1;
//                    pubNubUtil.publishToChannel(CHANNEL_NAME,HAS_BLOCKED+" "+happy);
//                    FileUtil.Save(file,new LogEntry(
//                            new Date().getTime(),ANDROID_DEVICE,"Blocked","Ipal", ""+happy,"4",difficulty+"",true+"",State.getEncodedState()
//                    ));
//
//                }
//                else {
//                    //User blocked Ipal
//                    Log.i(TAG,"Ipal was blocked by player ");
//                    int sad = rand.nextInt(5)+1;
//                    pubNubUtil.publishToChannel(CHANNEL_NAME,IS_BLOCKED+" "+sad);
//                    FileUtil.Save(file,new LogEntry(
//                            new Date().getTime(),ANDROID_DEVICE,"Blocked","Human",""+sad,"2",difficulty+"",true+"",State.getEncodedState()
//
//                    ));
//                    allowAction = false;
//                }
//
//            }
//            //else it is just a normal move
//            else if(PLAYER_X && allowAction){
//                //IPal just playing
//                Log.i(TAG,"Ipal playing");
//                pubNubUtil.publishToChannel(CHANNEL_NAME,PLAY);
//            }
//            /*else if(PLAYER_X){
//                Log.i(TAG,"Ipal was blocked by player ");
//                pubNubUtil.publishToChannel(CHANNEL_NAME,IS_BLOCKED);
//            }*/
//
//
//
//
//            //swap player for next move
//            PLAYER_X = !PLAYER_X;
//
//            if (PLAYER_X) {
//                setInfo("Player X turn");
//            } else {
//                setInfo("Player 0 turn");
//            }
//            //check winner for current move
//            checkWinner();
//
//            //make ai play
//            if(gameOutcome && PLAYER_X && TURN_COUNT<9) {
//                aiPlay();
//                allowAction = true;
//                checkWinner();
//            }
//        }
    }

    private void aiPlay(){
        //Log.e(TAG,ai.aiMove(State).getSectorValue()+"");
        //Log.e(TAG,indexToButton.get(ai.aiMove(State).getSectorValue())+"");

        loadingProgressDialog();
        //click button where ai wants to play
        Button temp = (Button) findViewById(indexToButton.get(ai.aiMove(State).getSectorValue()));
        this.onClick(temp);
    }

    /**
     * Loading progress dialog
     */
    private void loadingProgressDialog(){

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Ipal's Turn"); // Setting Message
        progressDialog.setTitle("Ipal is thinking.."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

    }

    /**
     * Function to check if current move has a winner.
     */
    private void checkWinner() {

        Log.d("D/Msg Recieved byPhone", "Inside "+gameOutcome);


        if(State.checkForWinner()==1){
            result("You Win!!");
            int sad = rand.nextInt(5)+1;
            pubNubUtil.publishToChannel(CHANNEL_NAME,LOSE+" "+sad);

            FileUtil.Save(file,new LogEntry(
                    new Date().getTime(),ANDROID_DEVICE,"Won","Human",""+sad,"3",difficulty+"","",State.getEncodedState()

            ));


            gameOutcome = false;
        }
        else if(State.checkForWinner()==2){
            result("IPAL Wins!!");
            int happy = rand.nextInt(5)+1;
            pubNubUtil.publishToChannel(CHANNEL_NAME,WIN+" "+happy);

            FileUtil.Save(file,new LogEntry(
                    new Date().getTime(),ANDROID_DEVICE,"Won","Ipal",""+happy,"5",difficulty+"","",State.getEncodedState()
            ));

            gameOutcome = false;
        }
        else if (TURN_COUNT == 9) {
            result("Game Draw");

            FileUtil.Save(file,new LogEntry(
                    new Date().getTime(),ANDROID_DEVICE,"Draw","Game","","",difficulty+"","",State.getEncodedState()
            ));
        }

        Log.d(TAG, "Inside "+gameOutcome);
    }


    /**
     * Set all boxes to clickable or not based on value
     * @param value true or false
     */
    private void enableAllBoxes(boolean value) {
        //Log.d(TAG, "Inside enableAllBoxes");
        b00.setEnabled(value);
        b01.setEnabled(value);
        b02.setEnabled(value);

        b10.setEnabled(value);
        b11.setEnabled(value);
        b12.setEnabled(value);

        b20.setEnabled(value);
        b21.setEnabled(value);
        b22.setEnabled(value);
    }

    /**
     * Print result on screen and make game ready for next round
     * @param winner
     */
    private void result(String winner) {
        //Log.d(TAG, "Inside result");

        setInfo(winner);
        enableAllBoxes(false);
        bStop.setEnabled(true);
    }

    /**
     * Clear board for next round.
     */
    private void resetBoard() {
        //Log.d(TAG, "Inside resetBoard");
        b00.setText("");
        b01.setText("");
        b02.setText("");

        b10.setText("");
        b11.setText("");
        b12.setText("");

        b20.setText("");
        b21.setText("");
        b22.setText("");

        enableAllBoxes(true);

        PLAYER_X = false;
        TURN_COUNT = 0;
        gameOutcome = true;
        allowAction = true;

        //initializeBoardStatus();
        State.resetGameState();

        int happy = rand.nextInt(5)+1;
        pubNubUtil.publishToChannel(CHANNEL_NAME,HAS_BLOCKED+" "+happy);

        setInfo("Start Again!!!");


        difficulty = 3;

        if(difficulty%2==0)difficulty++;

        ai.setDifficulty(difficulty);


        //Toast.makeText(this, "Board Reset", Toast.LENGTH_SHORT).show();

        Log.i(TAG,FileUtil.Load(file));

    }

    /**
     * Log in that the stop button has been pressed and reset board
     */
    private void stop(){
        pubNubUtil.publishToChannel(CHANNEL_NAME,"Stop");
        FileUtil.Save(file,new LogEntry(
                new Date().getTime(),ANDROID_DEVICE,"Stop","Human","","",difficulty+"","",State.getEncodedState()
        ));
        resetBoard();
        bStop.setEnabled(false);
        bStart.setEnabled(true);
    }

    /**
     * Log in that the start button has been pressed and start board
     */
    private void start(){
        pubNubUtil.publishToChannel(CHANNEL_NAME,"Start");
        FileUtil.Save(file,new LogEntry(
                new Date().getTime(),ANDROID_DEVICE,"Start","Human","","",difficulty+"","",State.getEncodedState()
        ));
        resetBoard();
        bStart.setEnabled(false);
    }

    private void setInfo(String text) {
        tvInfo.setText(text);
    }

}
