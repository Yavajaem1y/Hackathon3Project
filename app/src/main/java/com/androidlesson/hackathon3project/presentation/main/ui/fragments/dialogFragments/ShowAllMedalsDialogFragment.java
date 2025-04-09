package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.adapters.MedalAdapter;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;


public class ShowAllMedalsDialogFragment extends DialogFragment {

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private RecyclerView rv_medal_holder;
    private ImageView b_back;
    private MedalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_show_all_medals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((App) requireActivity().getApplication()).appComponent.injectShowAllMedals(this);

        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);

        initialization(view);

        setOnClickListener();

        observe();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Dialog dialog = getDialog();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);

                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }
        }
    }


    private void initialization(View view){
        b_back=view.findViewById(R.id.b_back);
        rv_medal_holder =view.findViewById(R.id.rv_medal_holder);
        setAdapter(view);
    }

    private void setAdapter(View view){
        adapter=new MedalAdapter(getContext());

        rv_medal_holder.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_medal_holder.setAdapter(adapter);
    }

    private void setOnClickListener(){
        b_back.setOnClickListener(v->{
            dismiss();
        });

    }

    private void observe(){
        sharedVM.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (userData.getMedalsId()!=null){
                    adapter.setMedals(userData.getMedalsId());
                }
            }
        });

    }
}

