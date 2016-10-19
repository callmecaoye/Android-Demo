package com.caoye.fragmentdemo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 10/18/16.
 */
public class FragmentThree extends Fragment {
    private String content;

    public static FragmentThree newInstance(String info) {
        FragmentThree fg3 = new FragmentThree();
        Bundle args = new Bundle();
        args.putString("content", info);
        fg3.setArguments(args);
        return fg3;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            content = bundle.getString("content");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        TextView txt_Content = (TextView) view.findViewById(R.id.txt_content);
        txt_Content.setText(content);
        return view;
    }
}
