package com.example.venky.tictactoe;

/**
 * Created by venky on 3/28/18.
 */

public class LogEntry {

    private long timestamp;
    private String deviceName;
    private String event;
    private String user;
    private String actionNumber;
    private String voiceNumber;
    private String difficultly;
    private String isOptimalMove;
    private String enocdedGameState;




    public LogEntry(long timestamp, String deviceName, String event, String user, String actionNumber, String voiceNumber, String difficultly, String isOptimalMove, String enocdedGameState) {
        this.timestamp = timestamp;
        this.deviceName = deviceName;
        this.event = event;
        this.user = user;
        this.actionNumber = actionNumber;
        this.voiceNumber = voiceNumber;
        this.difficultly = difficultly;
        this.isOptimalMove = isOptimalMove;
        this.enocdedGameState = enocdedGameState;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "timestamp=" + timestamp +
                ", deviceName='" + deviceName + '\'' +
                ", event='" + event + '\'' +
                ", user='" + user + '\'' +
                ", actionNumber='" + actionNumber + '\'' +
                ", voiceNumber='" + voiceNumber + '\'' +
                ", difficultly='" + difficultly + '\'' +
                ", isOptimalMove='" + isOptimalMove + '\'' +
                ", enocdedGameState='" + enocdedGameState + '\'' +
                '}';
    }
}
