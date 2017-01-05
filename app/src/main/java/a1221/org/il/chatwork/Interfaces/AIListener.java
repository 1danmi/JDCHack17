package a1221.org.il.chatwork.Interfaces;

import ai.api.model.AIError;
import ai.api.model.AIResponse;

/**
 * Created by nadav on 1/5/2017.
 * Package: a1221.org.il.chatwork.Interfaces
 */

public interface AIListener {
    void onResult(AIResponse result); // here process response
    void onError(AIError error); // here process error
    void onAudioLevel(float level); // callback for sound level visualization
    void onListeningStarted(); // indicate start listening here
    void onListeningCanceled(); // indicate stop listening here
    void onListeningFinished(); // indicate stop listening here
}
