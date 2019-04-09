package com.example.venky.tictactoe;

import android.util.Log;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

/**
 * Created by root on 2/7/18.
 */

public class PubNubUtil {

    private static PNConfiguration pnConfiguration;
    private static PubNub pubnub;
    private static String utilUser; //Ipal or Google Glass

    public PubNubUtil(final String initUtilUser) {
        pnConfiguration = new PNConfiguration();
        utilUser = initUtilUser;
    }

    /**
     * Set Subscribe Key
     * @param key
     */
    public void setSubscribeKey(final String key){
        pnConfiguration.setSubscribeKey(key);
    }

    /**
     * Set Publish Key
     * @param key
     */
    public void setPublishKey(final String key){
        pnConfiguration.setPublishKey(key);
    }

    /**
     * Configure api object
     */
    public void buildConfig(){
        pubnub = new PubNub(pnConfiguration);
    }


    /**
     * Add a listener to the api object .. acts as a receiver
     */
    public void buildChannel(){

        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {
                switch (status.getOperation()) {
                    // let's combine unsubscribe and subscribe handling for ease of use
                    case PNSubscribeOperation:
                    case PNUnsubscribeOperation:
                        // note: subscribe statuses never have traditional
                        // errors, they just have categories to represent the
                        // different issues or successes that occur as part of subscribe
                        switch (status.getCategory()) {
                            case PNConnectedCategory:
                                Log.i(utilUser,"Connection Successful");
                                break;
                                // this is expected for a subscribe, this means there is no error or issue whatsoever
                            case PNReconnectedCategory:
                                // this usually occurs if subscribe temporarily fails but reconnects. This means
                                // there was an error but there is no longer any issue
                            case PNDisconnectedCategory:
                                // this is the expected category for an unsubscribe. This means there
                                // was no error in unsubscribing from everything
                            case PNUnexpectedDisconnectCategory:
                                // this is usually an issue with the internet connection, this is an error, handle appropriately
                            case PNAccessDeniedCategory:
                                // this means that PAM does allow this client to subscribe to this
                                // channel and channel group configuration. This is another explicit error
                            default:
                                Log.e(utilUser,"PubNub Connection Unsuccessful");
                                // More errors can be directly specified by creating explicit cases for other
                                // error categories of `PNStatusCategory` such as `PNTimeoutCategory` or `PNMalformedFilterExpressionCategory` or `PNDecryptionErrorCategory`
                        }

                    case PNHeartbeatOperation:
                        // heartbeat operations can in fact have errors, so it is important to check first for an error.
                        // For more information on how to configure heartbeat notifications through the status
                        // PNObjectEventListener callback, consult <link to the PNCONFIGURATION heartbeart config>
                        if (status.isError()) {
                            // There was an error with the heartbeat operation, handle here
                        } else {
                            // heartbeat operation was successful
                        }
                    default: {
                        // Encountered unknown status type
                    }
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

                Log.d("Msg Recieved by"+utilUser,message.getMessage().getAsString());

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });
    }

    /**
     * Subscribe to channel
     * @param channelName
     */
    public void subscribeToChannel(final String channelName){
        pubnub.subscribe()
                .channels(Arrays.asList(channelName)) // subscribe to channels
                .execute();
    }

    /**
     * Send a broadcast message to everyone subscribed to channel
     * @param channelName
     * @param msg
     */
    public void publishToChannel(final String channelName, final String msg){

        pubnub.publish().channel(channelName).message(msg).async(new PNCallback<PNPublishResult>() {
            @Override
            public void onResponse(PNPublishResult result, PNStatus status) {
                // Check whether request successfully completed or not.
                if (!status.isError()) {

                    Log.i("Publish Successful",msg);
                    // Message successfully published to specified channel.
                }
                // Request processing failed.
                else {

                    Log.e("Publish Failed Category",status.getCategory().toString());
                    // Handle message publish error. Check 'category' property to find out possible issue
                    // because of which request did fail.
                    //
                    // Request can be resent using: [status retry];
                }
            }
        });

    }


}
