package a1221.org.il.chatwork;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class BotsActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bots);


        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.conversations_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawable d = getDrawable(R.drawable.android);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        Bot[] bots = new Bot[]{
                  new Bot(1,"Header","Hey! This is a test message. I'm just making this longer now", Calendar.getInstance(),bitmapdata)
                , new Bot(2,"Header","Hey! This is a test message. I'm just making this longer now", Calendar.getInstance(),bitmapdata)
                , new Bot(3,"Header","Hey! This is a test message. I'm just making this longer now",Calendar.getInstance(),bitmapdata)
                , new Bot(4,"Header","Hey! This is a test message. I'm just making this longer now",Calendar.getInstance(),bitmapdata)
                , new Bot(5,"Header","Hey! This is a test message. I'm just making this longer now",Calendar.getInstance(),bitmapdata)
                , new Bot(6,"Header","Hey! This is a test message. I'm just making this longer now",Calendar.getInstance(),bitmapdata)
        };
        mRecyclerView.setAdapter(new MessageListAdapter(bots));
    }
}
