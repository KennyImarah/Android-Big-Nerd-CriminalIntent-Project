package com.example.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    //instance of RecyclerView
    private RecyclerView mCrimeRecyclerView;
    //instance of CrimeAdapter
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //call inflater on View object
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        //assign find view to mCrimeRecyclerView
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //set new LayoutManager on mCrimeRecyclerView
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;

    }

    //when CrimeListActivity is resumed
    //call onResume
    @Override
    public void onResume() {
        super.onResume();
        //call updateUI
        updateUI();
    }
    //updateUI method
    private void updateUI () {
        //CrimeLab object
            CrimeLab crimeLab = CrimeLab.get(getActivity());

            //assign crimeLab getCrimes to list object from Crime
            List<Crime> crimes = crimeLab.getCrimes();
            //check mAdapter and assign new CrimeAdapter with crimes
            if (mAdapter == null){
                mAdapter = new CrimeAdapter(crimes);
                //call setAdapter method on mCrimeRecyclerView and assign mAdapter
                mCrimeRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }

        private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private Crime mCrime;
            private TextView mTitleTextView;
            private TextView mDateTextView;
            private ImageView mSolvedImageView;

            public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_crime, parent, false));
                itemView.setOnClickListener(this);

                mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
                mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
                mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            }

            public void bind(Crime crime) {
                mCrime = crime;
                mTitleTextView.setText(mCrime.getTitle());
                mDateTextView.setText(mCrime.getDate().toString());
                mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
            }

            public void onClick(View view) {

                //stash crimeId in intent that belong to the class MainActivity
                //and start the intent
                Intent intent = MainActivity.newIntent(getActivity(), mCrime.getId());
                startActivity(intent);

            }
        }

        //CrimeAdapter class
        private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        //list object mCrimes
        private List<Crime> mCrimes;

            //CrimeAdapter constructor
            public CrimeAdapter(List<Crime> crimes) {
                //mCrimes object assigned to crimes
                mCrimes = crimes;
            }

            @NonNull
            @Override
            public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                return new CrimeHolder(layoutInflater, parent);
            }

            @Override
            public void onBindViewHolder(CrimeHolder holder, int position) {
                Crime crime = mCrimes.get(position);
                holder.bind(crime);
            }

            @Override
            public int getItemCount() {

                return mCrimes.size();
            }
        }
    }

