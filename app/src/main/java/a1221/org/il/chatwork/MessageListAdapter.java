package a1221.org.il.chatwork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by nadav on 1/4/2017.
 * Package: a1221.org.il.chatwork
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private Bot[] mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        // each data item is just a string in this case
        public TextView header;
        public TextView time;
        public TextView preview;
        public ImageView img;
        public MessageViewHolder(View itemView) {
            super(itemView);
            header = (TextView)itemView.findViewById(R.id.message_name);
            time = (TextView)itemView.findViewById(R.id.message_date);
            preview = (TextView)itemView.findViewById(R.id.message_preview);
            img = (ImageView) itemView.findViewById(R.id.message_icon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageListAdapter(Bot[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bot_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MessageViewHolder vh = new MessageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.preview.setText(mDataset[position].getTextPreview());
        holder.header.setText(mDataset[position].getHeader());
        Calendar time = mDataset[position].getLastDate();
        SimpleDateFormat df;
        if (Calendar.getInstance().get(Calendar.DATE) == time.get(Calendar.DATE))
            df = new SimpleDateFormat("yyyy MMMM d", Locale.ENGLISH);
        else
            df = new SimpleDateFormat("HH:mm");
        holder.time.setText(df.format(time.getTime()));
        Bitmap bm = BitmapFactory.decodeByteArray(mDataset[position].getImage(), 0, mDataset[position].getImage().length);
        bm = RoundBitmap.getRoundedCornerBitmap(bm,bm.getWidth()/2);
        holder.img.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
