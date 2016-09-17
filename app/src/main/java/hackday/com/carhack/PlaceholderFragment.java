package hackday.com.carhack;

/**
 * Created by shruthi on 17/9/16.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
                    ArrayList<String> s = new ArrayList<>();
                    s.add("http://172.24.1.1:8080/action");
                    for(String i: mAdapter.dataset){
                        s.add(i);
                    }
                    new ServerAsyncTask().execute(s);
                }
            });

            Button add = (Button)rootView.findViewById(R.id.btn_add);
            final Switch swit = (Switch) rootView.findViewById(R.id.switch_obs);


            final TextView txtLeftProgress = (TextView) rootView.findViewById(R.id.txt_left_progress);
            final TextView txtRightProgress = (TextView) rootView.findViewById(R.id.txt_right_progress);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int left = Integer.parseInt(txtLeftProgress.getText().toString());
                    int right = Integer.parseInt(txtRightProgress.getText().toString());
                    float time;
                    if(numberOfSecs.getText().toString().isEmpty())
                        time = 0;
                    else
                        time = Float.parseFloat(numberOfSecs.getText().toString().split("s")[0]);
                    String check_obs = getObstacle(swit);
                    mAdapter.dataset.add(String.valueOf(left) + "," + String.valueOf(right) + "," + String.valueOf(time) + "," + check_obs);
                    mAdapter.notifyDataSetChanged();

                }
            });

            seekLeft.setMax(190);
            seekRight.setMax(190);

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
            Button front_left = (Button) rootView.findViewById(R.id.btn_for_left);
            front_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapter.dataset.add("35,95,2.5,"+getObstacle(swit));
                    mAdapter.notifyDataSetChanged();
                }
            });
            Button front_right = (Button) rootView.findViewById(R.id.btn_for_right);
            front_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAdapter.dataset.add("99,29,2.5,"+getObstacle(swit));
                    mAdapter.notifyDataSetChanged();
                }
            });

            final ImageView camera = (ImageView) rootView.findViewById(R.id.img_camera_pic);
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*ArrayList<String> n = new ArrayList<String>();
                    n.add("http://172.24.1.1:8080/image");
                    new ServerAsyncTask().execute(n);*/
                    Picasso.with(getActivity()).load("http://172.24.1.1:8080/image").into(camera);
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

    @NonNull
    private String getObstacle(Switch swit) {
        String check_obs = "0";
        if (swit.isChecked()) {
            check_obs = "1";
        }
        return check_obs;
    }
}
