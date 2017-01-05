package a1221.org.il.chatwork.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import a1221.org.il.chatwork.Adapter.BotListAdapter;
import a1221.org.il.chatwork.Entities.Bot;
import a1221.org.il.chatwork.R;

public class BotsActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bots);


        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.bots_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawable d = getDrawable(R.drawable.waiter);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        d = getDrawable(R.drawable.cnc);
        bitmap = ((BitmapDrawable)d).getBitmap();
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata2 = stream.toByteArray();

        Bot[] bots = new Bot[]{
                  new Bot(1,"Waiter Bot","Hey! This is a test message. I'm just making this longer now", Calendar.getInstance(),bitmapdata)
                , new Bot(2,"CNC Bot","Hey! This is a test message. I'm just making this longer now", Calendar.getInstance(),bitmapdata2)
        };
        mRecyclerView.setAdapter(new BotListAdapter(bots, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BotsActivity.this,MessagesActivity.class);
                TextView txt = (TextView)view.findViewById(R.id.message_name);
                intent.putExtra("Name",txt.getText());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(BotsActivity.this).toBundle());
            }
        }));
    }
}
