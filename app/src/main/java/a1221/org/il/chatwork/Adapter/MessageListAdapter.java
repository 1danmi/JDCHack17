package a1221.org.il.chatwork.Adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import a1221.org.il.chatwork.Entities.Message;
import a1221.org.il.chatwork.Libraries.RoundBitmap;
import a1221.org.il.chatwork.R;

import static a1221.org.il.chatwork.constants.Constansts.DARK_MODE;

/**
 * Created by nadav on 1/4/2017.
 * Package: a1221.org.il.chatwork
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private List<Message> mDataset;
    public Bitmap botBmp = null;
    public Bitmap userBmp = null;
    public final static int MSG_OUT =0;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        // each data item is just a string in this case
        public TextView text;
        public TextView date;
        public ImageView img;
        public MessageViewHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date_view);
            text = (TextView)itemView.findViewById(R.id.text_view);
            img = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageListAdapter(List<Message> myDataset, Bitmap botmap,Bitmap usermap) {
        botBmp = botmap;
        userBmp = usermap;
        mDataset = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).isUserSender() ? 1 : 0;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        if(DARK_MODE) {
            if (viewType == MSG_OUT)
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_message_out_dark, parent, false);
            else
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_message_in_dark, parent, false);
        }else{
            if (viewType == MSG_OUT)
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_message_out, parent, false);
            else
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_message_in, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters
        MessageViewHolder vh = new MessageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.text.setText(mDataset.get(position).getText());
        Calendar time = mDataset.get(position).getDate();
        SimpleDateFormat df;
        if (Calendar.getInstance().get(Calendar.DATE) == time.get(Calendar.DATE))
            df = new SimpleDateFormat("yyyy MMMM d", Locale.ENGLISH);
        else
            df = new SimpleDateFormat("HH:mm:ss");
        holder.date.setText(df.format(time.getTime()));
        Bitmap bmp=null;
        if(!mDataset.get(position).isUserSender()) {
            bmp = RoundBitmap.getRoundedCornerBitmap(userBmp, userBmp.getWidth() / 2);
        }
        else
            bmp = RoundBitmap.getRoundedCornerBitmap(botBmp, botBmp.getWidth()/2);
        holder.img.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
