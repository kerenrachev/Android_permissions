package com.example.permissiona_ex1;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class FragmentSuccess extends Fragment {
    private AppCompatButton success_BTN_back;
    public FragmentSuccess() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        findViews(view);
        setClickListeners();
        return view;
    }

    private void setClickListeners() {
        success_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_main fragment_main = new Fragment_main();
                getParentFragmentManager() .beginTransaction().replace(R.id.panel_FRAME_content, fragment_main).commit();
            }
        });
    }

    private void findViews(View view) {
        success_BTN_back = view.findViewById(R.id.success_BTN_back);
    }


}