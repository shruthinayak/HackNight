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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView mRecyclerView;
    private static ListAdapter mAdapter = new ListAdapter(new ArrayList<String>());
    private TextView numberOfSecs;
    private TextView txtLeftProgress;
    private TextView txtRightProgress;
    private Switch swit;
    private SeekBar seekLeft;
    private SeekBar seekRight;
    private Button btnAdd;
    private Button btnLeft;
    private FloatingActionButton btnGo;
    private ImageView btnCamera;
    private Button btnRight;

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

            initMainUI(rootView);
            setUIListeners();

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

    private void setUIListeners() {
        btnGo.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnCamera.setOnClickListener(this);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = progress / 10;
                progress = progress * 10;
                progress = progress - 95;
                if (seekBar.getId() == R.id.seekLeft)
                    txtLeftProgress.setText(String.valueOf(progress));
                else
                    txtRightProgress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekLeft.setOnSeekBarChangeListener(listener);
        seekRight.setOnSeekBarChangeListener(listener);
    }

    private void initMainUI(View rootView) {
        seekLeft = (SeekBar) rootView.findViewById(R.id.seekLeft);
        seekRight = (SeekBar) rootView.findViewById(R.id.seekRight);
        numberOfSecs = (TextView) rootView.findViewById(R.id.txt_secs);
        btnAdd = (Button) rootView.findViewById(R.id.btn_add);
        swit = (Switch) rootView.findViewById(R.id.switch_obs);
        txtLeftProgress = (TextView) rootView.findViewById(R.id.txt_left_progress);
        txtRightProgress = (TextView) rootView.findViewById(R.id.txt_right_progress);
        btnLeft = (Button) rootView.findViewById(R.id.btn_for_left);
        btnRight = (Button) rootView.findViewById(R.id.btn_for_right);
        btnGo = (FloatingActionButton) rootView.findViewById(R.id.btn_go);
        btnCamera = (ImageView) rootView.findViewById(R.id.img_camera_pic);

        seekLeft.setMax(190);
        seekRight.setMax(190);
    }


    @NonNull
    private String getObstacle() {
        String check_obs = "0";
        if (swit.isChecked()) {
            check_obs = "1";
        }
        return check_obs;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add:
                int left = Integer.parseInt(txtLeftProgress.getText().toString());
                int right = Integer.parseInt(txtRightProgress.getText().toString());
                float time;
                if (numberOfSecs.getText().toString().isEmpty())
                    time = 0;
                else
                    time = Float.parseFloat(numberOfSecs.getText().toString().split("s")[0]);
                String check_obs = getObstacle();
                mAdapter.dataset.add(String.valueOf(left) + "," + String.valueOf(right) + "," + String.valueOf(time) + "," + check_obs);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_go:
                ArrayList<String> s = new ArrayList<>();
                s.add(Constants.BASE_URL + Constants.EndPoints.ACTION);
                for (String i : mAdapter.dataset) {
                    s.add(i);
                }
                new ServerAsyncTask().execute(s);
                break;

            case R.id.btn_for_left:
                mAdapter.dataset.add(Constants.Cheats.FORWARD_LEFT + getObstacle());
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_for_right:
                mAdapter.dataset.add(Constants.Cheats.FORWARD_RIGHT + getObstacle());
                mAdapter.notifyDataSetChanged();
                break;

        }
    }
}
