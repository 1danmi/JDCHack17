package a1221.org.il.chatwork.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import a1221.org.il.chatwork.Activities.MessagesActivity;
import a1221.org.il.chatwork.Entities.Message;
import a1221.org.il.chatwork.Libraries.RoundBitmap;
import a1221.org.il.chatwork.R;

/**
 * Created by nadav on 1/4/2017.
 * Package: a1221.org.il.chatwork
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private List<Message> mDataset;
    public Bitmap botBmp = null;
    public Bitmap userBmp = null;
    public Context context = null;
    public final static int MSG_OUT =0;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        // each data item is just a string in this case
        public TextView text;
        public TextView date;
        public ImageView img;
        public ImageView gif;
        public LinearLayout message_block;
        public LinearLayout footer;
        public MessageViewHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date_view);
            text = (TextView)itemView.findViewById(R.id.text_view);
            img = (ImageView) itemView.findViewById(R.id.avatar);
            gif = (ImageView) itemView.findViewById(R.id.gif);
            message_block = (LinearLayout) itemView.findViewById(R.id.message_block);
            footer = (LinearLayout) itemView.findViewById(R.id.footer);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageListAdapter(List<Message> myDataset, Bitmap botmap, Bitmap usermap, Context context) {
        botBmp = botmap;
        userBmp = usermap;
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).isUserSender() ? 1 : 0;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        if(viewType == MSG_OUT)
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_message_out, parent, false);
        else
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_message_in, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MessageViewHolder vh = new MessageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        final String txt = mDataset.get(position).getText();
        if(!isGifUrl(txt))
            holder.text.setText(txt);
        else
        {
            holder.message_block.setVisibility(View.GONE);
            holder.footer.setVisibility(View.GONE);
            holder.gif.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(txt)
                    .asGif()
                    .into(holder.gif);
        }
        Calendar time = mDataset.get(position).getDate();
        SimpleDateFormat df;
        if (Calendar.getInstance().get(Calendar.DATE) != time.get(Calendar.DATE))
            df = new SimpleDateFormat("yyyy MMMM d", Locale.ENGLISH);
        else
            df = new SimpleDateFormat("HH:mm");
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

    private boolean isGifUrl(String url){
        try{
            URL src = new URL(url);
            int index = url.lastIndexOf(".");
            if(url.substring(index+1,index+4).equals("gif"))
                return true;
            else
                return false;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
