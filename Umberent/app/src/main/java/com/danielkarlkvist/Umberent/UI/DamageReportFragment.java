package com.danielkarlkvist.Umberent.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.danielkarlkvist.Umberent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DamageReportFragment extends Fragment {

    public DamageReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_damage_report, container, false);
        return v;
    }
}
