package fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ap.cheerupdiary.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import activity.LoginActivity;
import activity.MainMenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private View v;

    Button signOutBtn;
    TextView nickname;
    ImageView profileImage;

    private FirebaseAuth mAuth ;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_setting, container,false);

        signOutBtn = (Button) v.findViewById(R.id.signOutBtn);
        nickname = (TextView) v.findViewById(R.id.userNameText);
        profileImage = (ImageView) v.findViewById(R.id.profileImage);


        mAuth = FirebaseAuth.getInstance();

        nickname.setText(mAuth.getCurrentUser().getDisplayName());

        Glide.with(getActivity()).load(mAuth.getCurrentUser().getPhotoUrl()).into(profileImage);


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });


        return v;
    }

    void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity().getApplication(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
