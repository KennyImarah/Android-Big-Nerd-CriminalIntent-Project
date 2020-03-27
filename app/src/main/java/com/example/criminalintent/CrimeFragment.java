package com.example.criminalintent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import static android.widget.CompoundButton.*;

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Use getActivity method to fetch MainActivity's intent  extra
        UUID crimeId = (UUID) getActivity().getIntent()
                .getSerializableExtra("MainActivity.EXTRA_CRIME_ID");
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);  //fetch Crime from CrimeLab
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_crime, container, false);
       mTitleField = (EditText) v.findViewById(R.id.crime_title);
       mTitleField.setText(mCrime.getTitle());
       mTitleField.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               //space intentionally left blank
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
            mCrime.setTitle(s.toString());
           }

           @Override
           public void afterTextChanged(Editable s) {
               //space also left blank on purpose
           }
       });

       mDateButton = (Button) v.findViewById(R.id.crime_date);

       //remember to refer back to this assignment
       mDateButton.setText(mCrime.getDate().toString());


       mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
       mSolvedCheckBox.setChecked(mCrime.isSolved());
       mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               mCrime.setSolved(isChecked);
           }
       });

        return v;
    }
}
