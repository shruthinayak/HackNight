package hackday.com.carhack;

import android.support.v7.widget.RecyclerView;
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
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
