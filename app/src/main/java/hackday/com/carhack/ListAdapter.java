package hackday.com.carhack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shruthi on 17/9/16.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    List<String> dataset;
    public ListAdapter(List<String> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_leg, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String item = dataset.get(position);
        holder.params.setText(item);
        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataset.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView params;
        ImageView clear;
        public ViewHolder(View itemView) {
            super(itemView);
            params = (TextView) itemView.findViewById(R.id.txt_info);
            clear = (ImageView) itemView.findViewById(R.id.btn_clear);
        }
    }
}
