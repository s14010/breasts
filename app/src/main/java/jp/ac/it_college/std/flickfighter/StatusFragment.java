package jp.ac.it_college.std.flickfighter;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class StatusFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences playerStatus;
    private static final String ATTACK = "attackLevel";
    private static final String DEFENCE = "defenceLevel";
    private static final String LIFE = "lifeLevel";
    private static final String POINT = "point";

    private TextView pointView;

    private SoundPool soundPool;
    private int buttonClickSoundId;
    private int cancelSoundId;
    private int levelUpSoundId;
    private int errorSoundId;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_status, container, false);

        rootView.findViewById(R.id.button_stage_select).setOnClickListener(this);

        playerStatus = getActivity().getSharedPreferences("status", Context.MODE_PRIVATE);

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        soundPool.play(buttonClickSoundId, 1.0f, 1.0f, 0, 0, 1.0f);

        switch (view.getId()) {
            case R.id.button_stage_select:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new StageSelectFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //ボタン押下時の効果音を読み込み
        buttonClickSoundId = soundPool.load(getActivity(), R.raw.se_button_click01, 1);
        cancelSoundId = soundPool.load(getActivity(), R.raw.se_cancel01, 1);
        levelUpSoundId = soundPool.load(getActivity(), R.raw.se_levelup01, 1);
        errorSoundId = soundPool.load(getActivity(), R.raw.se_error01, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }
}
