package a1221.org.il.chatwork.Activities;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import a1221.org.il.chatwork.Adapter.MessageListAdapter;
import a1221.org.il.chatwork.Entities.Message;
import a1221.org.il.chatwork.R;
import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MessagesActivity extends AppCompatActivity {

    private String CONNECTION_KEY = null;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private List<Message> msgs;
    private  Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        String name = getIntent().getStringExtra("Name");
        if(name.equals("Waiter Bot")) {
            CONNECTION_KEY = "c31bca71763f4df0b1641a2ae72f99d0";
            bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.waiter);
        }
        else {
            CONNECTION_KEY = "28e8e65dde6c40dcb4ae9f0f27670c4c";
            bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.cnc);
        }
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.messages_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        msgs = new ArrayList<Message>();
        mRecyclerView.setAdapter(new MessageListAdapter(msgs,bmp
                , BitmapFactory.decodeResource(this.getResources(), R.drawable.profile)
                , this));

        ImageButton btn = (ImageButton)findViewById(R.id.add_message);
        final EditText txt = (EditText)findViewById(R.id.message_txt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgs.add(new Message(7,txt.getText().toString(),Calendar.getInstance(),false));
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount()-1);
                final AIConfiguration config = new AIConfiguration(CONNECTION_KEY,
                        AIConfiguration.SupportedLanguages.English);


                final AIDataService aiDataService = new AIDataService(config);

                final AIRequest aiRequest = new AIRequest();
                if(txt.getText().toString().equals(""))
                    return;
                aiRequest.setQuery(txt.getText().toString());
                txt.setText("");
                new AsyncTask<AIRequest, Void, AIResponse>() {

                    @Override
                    protected AIResponse doInBackground(AIRequest... requests) {
                        final AIRequest request = requests[0];
                        try {
                            final AIResponse response = aiDataService.request(aiRequest);
                            return response;
                        } catch (AIServiceException e) {
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(AIResponse aiResponse) {
                        if (aiResponse != null) {
                            String ans = aiResponse.getResult().getFulfillment().getSpeech();
                            msgs.add(new Message(7, ans , Calendar.getInstance(), true));
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                        }
                    }
                }.execute(aiRequest);
            }
        });

    }
}
