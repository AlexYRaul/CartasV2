package com.example.cartasvshumanidad.ui.lobby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cartasvshumanidad.databinding.FragmentLobbyBinding;

public class LobbyFragment extends Fragment {

    private LobbyViewModel lobbyViewModel;
    private FragmentLobbyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lobbyViewModel =
                new ViewModelProvider(this).get(LobbyViewModel.class);

        binding = FragmentLobbyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        lobbyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}