package hackday.com.carhack;

/**
 * Created by shruthi on 17/9/16.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;


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
    private static ListAdapter mAdapter = new ListAdapter(new ArrayList<String>());

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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            final SeekBar seekLeft = (SeekBar) rootView.findViewById(R.id.seekLeft);
            final SeekBar seekRight = (SeekBar) rootView.findViewById(R.id.seekRight);
            final TextView numberOfSecs = (TextView) rootView.findViewById(R.id.txt_secs);
            FloatingActionButton btnGo = (FloatingActionButton) rootView.findViewById(R.id.btn_go);
            btnGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ServerAsyncTask().execute("https://172.24.1.54:8080/action");
                }
            });

            Button add = (Button) rootView.findViewById(R.id.btn_add);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int left = seekLeft.getProgress()-95;
                    int right = seekRight.getProgress()-95;
                    int time;
                    if(numberOfSecs.getText().toString().isEmpty())
                        time = 0;
                    else
                        time = Integer.parseInt(numberOfSecs.getText().toString().split("s")[0]);
                    mAdapter.dataset.add(String.valueOf(left) + ", " + String.valueOf(right) + ", " + String.valueOf(time));
                    mAdapter.notifyDataSetChanged();

                }
            });

            seekLeft.setMax(190);
            seekRight.setMax(190);
            final TextView txtLeftProgress = (TextView) rootView.findViewById(R.id.txt_left_progress);
            final TextView txtRightProgress = (TextView) rootView.findViewById(R.id.txt_right_progress);

            seekLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    progress = progress / 10;
                    progress = progress * 10;
                    txtLeftProgress.setText(String.valueOf(progress-95));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            seekRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    progress = progress / 10;
                    progress = progress * 10;
                    txtRightProgress.setText(String.valueOf(progress-95));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


        } else {
            rootView = inflater.inflate(R.layout.fragment_items, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_tuples);
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

        return rootView;
    }
}
