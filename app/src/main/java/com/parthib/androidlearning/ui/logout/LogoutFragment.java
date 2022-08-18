package com.parthib.androidlearning.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


import com.parthib.androidlearning.LoginActivity;
import com.parthib.androidlearning.StoreInfo;
import com.parthib.androidlearning.databinding.FragmentLogoutBinding;

public class LogoutFragment extends DialogFragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnCancel.setOnClickListener(view -> {
            if(getDialog() != null)
                getDialog().dismiss();
        });

        binding.btnExit.setOnClickListener(view -> {
            new StoreInfo(getActivity()).delete();
            startActivity( new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}