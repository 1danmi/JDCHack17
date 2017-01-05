package a1221.org.il.chatwork.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import a1221.org.il.chatwork.Entities.Bot;
import a1221.org.il.chatwork.R;
import a1221.org.il.chatwork.Libraries.RoundBitmap;

/**
 * Created by nadav on 1/4/2017.
 * Package: a1221.org.il.chatwork
 */
public class BotListAdapter extends RecyclerView.Adapter<BotListAdapter.BotViewHolder> {

    private Bot[] mDataset;
    private View.OnClickListener click;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BotViewHolder extends RecyclerView.ViewHolder{

        // each data item is just a string in this case
        public TextView header;
        public TextView time;
        public TextView preview;
        public ImageView img;
        public LinearLayout container;
        public BotViewHolder(View itemView) {
            super(itemView);
            header = (TextView)itemView.findViewById(R.id.message_name);
            time = (TextView)itemView.findViewById(R.id.message_date);
            preview = (TextView)itemView.findViewById(R.id.message_preview);
            img = (ImageView) itemView.findViewById(R.id.message_icon);
            container = (LinearLayout)itemView.findViewById(R.id.message_list_row);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BotListAdapter(Bot[] myDataset, View.OnClickListener click) {
        mDataset = myDataset;
        this.click = click;
    }

    @Override
    public BotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bot_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        BotViewHolder vh = new BotViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BotViewHolder holder, int position) {
        holder.preview.setText(mDataset[position].getTextPreview());
        holder.header.setText(mDataset[position].getHeader());
        Calendar time = mDataset[position].getLastDate();
        SimpleDateFormat df;
        if (Calendar.getInstance().get(Calendar.DATE) != time.get(Calendar.DATE))
            df = new SimpleDateFormat("yyyy MMMM d", Locale.ENGLISH);
        else
            df = new SimpleDateFormat("HH:mm");
        holder.time.setText(df.format(time.getTime()));
        Bitmap bm = BitmapFactory.decodeByteArray(mDataset[position].getImage(), 0, mDataset[position].getImage().length);
        bm = RoundBitmap.getRoundedCornerBitmap(bm,bm.getWidth()/2);
        holder.img.setImageBitmap(bm);
        holder.container.setOnClickListener(click);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
