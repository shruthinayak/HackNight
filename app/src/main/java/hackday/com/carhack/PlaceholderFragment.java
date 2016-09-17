package hackday.com.carhack;

/**
 * Created by shruthi on 17/9/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private List<String> dataset = new ArrayList<>();

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final SeekBar seekLeft = (SeekBar) rootView.findViewById(R.id.seekLeft);
            final SeekBar seekRight = (SeekBar) rootView.findViewById(R.id.seekRight);
            final TextView numberOfSecs = (TextView) rootView.findViewById(R.id.txt_secs);

            Button add = (Button) rootView.findViewById(R.id.btn_add);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int left = seekLeft.getProgress();
                    int right = seekRight.getProgress();
                    int time = Integer.parseInt(numberOfSecs.getText().toString().split("s")[0]);
                    dataset.add(String.valueOf(left) + ", " + String.valueOf(right) + ", " + String.valueOf(time));

                }
            });
            seekLeft.setMax(95);
            seekLeft.setProgress(35);
            seekLeft.incrementProgressBy(10);

            seekRight.setMax(95);
            seekRight.setProgress(35);
            seekRight.incrementProgressBy(10);

        } else {
            rootView = inflater.inflate(R.layout.fragment_items, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_tuples);
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new ListAdapter(dataset);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

        return rootView;
    }
}
