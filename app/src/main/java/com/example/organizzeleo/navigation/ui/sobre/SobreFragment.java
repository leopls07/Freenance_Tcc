package com.example.organizzeleo.navigation.ui.sobre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.organizzeleo.R;

import mehdi.sakout.aboutpage.AboutPage;


public class SobreFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        String descricao = "Somos a aveta Software e esta é a freenance. \n \n " +
                "Com o objetivo de facilitar o aprendizado no mundo dos investimentos.  \n" +
                "Surgiu a ideia de que se criasse um aplicativo que simulasse-os.  \n" +
                "Assim fazendo o ensino ser prático e sem riscos";

    return new AboutPage(getActivity())
            .setDescription(descricao)
            .setImage(R.drawable.freenance_logo_160px)
            .addGroup("Entre em contato conosco")
            .addEmail("AvetaSoftware@gmail.com","Email de contato")
            .addGroup("Redes sociais")
            .addInstagram("avetasoftware","Visite nossa página")
            .addGitHub("leopls07/Freenance_Tcc","GitHub do nosso projeto!")
            .create();
    }
}