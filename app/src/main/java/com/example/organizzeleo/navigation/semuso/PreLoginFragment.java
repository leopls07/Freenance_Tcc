package com.example.organizzeleo.navigation.semuso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.organizzeleo.R;

public class PreLoginFragment extends Fragment {

    Animation animation;

    Button botaoLogin, botaoCadastro;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pre_login, container, false);

       animation = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);

       botaoCadastro.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               botaoCadastro.startAnimation(animation);
           }
       });
       


        return root;

    }
}