package com.staradmin.android.tasku.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.staradmin.android.tasku.Activities.Profile.ProfileActivity;
import com.staradmin.android.tasku.Callback.callback_read_profile;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileFragment extends Fragment {
    private ImageView profPic, addProfPic;
    private TextView name, email, username, birthDate, handphoneNumb;
    private String mName, mEmail, mUsername, mBirthDate, mHandphone, mId;
    final LocalStorage localStorage = new LocalStorage();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        return root;
    }
    public void initateElement(View view){
        name = view.findViewById(R.id.nama);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        birthDate = view.findViewById(R.id.birthDate);
        handphoneNumb = view.findViewById(R.id.phone);
    }

    public void getDataFromDatabase(){
        mId = localStorage.getCustomerId(getActivity());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(mId);
        for(int i=0; i<alBookPick.size();++i){
            mName = alBookPick.get(i).get("nama");
            mBirthDate = alBookPick.get(i).get("birth_date");
            mEmail = alBookPick.get(i).get("email");
            mHandphone = alBookPick.get(i).get("handphone_numb");
            mUsername = alBookPick.get(i).get("username");
        }
    }

    public void insertDatatoElement(){
        name.setText(mName);
        email.setText(mEmail);
        username.setText(mUsername);
        birthDate.setText(mBirthDate);
        handphoneNumb.setText(mHandphone);
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_profile read_profile = new callback_read_profile(getActivity().getApplication());
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_profile.execute(
                    v1
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }
}
