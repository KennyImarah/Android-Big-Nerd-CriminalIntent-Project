package com.example.criminalintent;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    //sCrimeLab object
    private static CrimeLab sCrimeLab;
    //mCrimes list object
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
        private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // select every other crime in the list
            mCrimes.add(crime);
        }

        }

        public List<Crime> getCrimes(){

        return mCrimes;
        }
        //getCrime method with UUID id
        public Crime getCrime(UUID id){
        //assign mCrimes to crime
        for (Crime crime : mCrimes) {
            //check to match id and return crime
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
        }
}