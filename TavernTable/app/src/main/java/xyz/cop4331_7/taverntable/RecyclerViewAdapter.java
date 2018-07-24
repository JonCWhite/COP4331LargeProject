package xyz.cop4331_7.taverntable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private JSONArray mData;

    public RecyclerViewAdapter(Context mContext, JSONArray mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_sheet, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            holder.tvBookTitle.setText(mData.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.ivBookThumbnail.setImageResource(R.drawable.ic_charactersheet);

        // Set click listener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CharacterSheetActivity.class);
                try {
                    intent.putExtra("name", mData.getJSONObject(position).getString("name"));
                    intent.putExtra("characterID", mData.getJSONObject(position).getString("characterID"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.length();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle;
        ImageView ivBookThumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvBookTitle = (TextView) itemView.findViewById(R.id.book_title_id);
            ivBookThumbnail = (ImageView) itemView.findViewById(R.id.book_image_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
