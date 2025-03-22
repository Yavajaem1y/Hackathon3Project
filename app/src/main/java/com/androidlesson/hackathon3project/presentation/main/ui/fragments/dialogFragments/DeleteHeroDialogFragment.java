package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.viewModels.allUserHeroesViewModel.AllUserHeroesViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.deleteHeroFragmentViewModel.DeleteHeroFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.deleteHeroFragmentViewModel.DeleteHeroFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;


public class DeleteHeroDialogFragment extends DialogFragment {

    private TextView b_cancellation, b_delete;

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private DeleteHeroFragmentViewModel vm;
    @Inject
    DeleteHeroFragmentViewModelFactory vmFactory;

    private String heroId;

    public DeleteHeroDialogFragment(String heroId) {
        this.heroId = heroId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_delete_hero, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((App) requireActivity().getApplication()).appComponent.injectDeleteHeroDialogFragment(this);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);
        vm=new ViewModelProvider(requireActivity(),vmFactory).get(DeleteHeroFragmentViewModel.class);

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
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);

                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }
        }
    }


    private void initialization(View view){
        if (heroId!=null && !heroId.isEmpty()){
            vm.setHeroId(heroId);
        }

        b_cancellation=view.findViewById(R.id.b_cancellation);
        b_delete =view.findViewById(R.id.b_delete);
    }

    private void setOnClickListener(){
        b_delete.setOnClickListener(v->{
            vm.deleteHero();
        });

        b_cancellation.setOnClickListener(v->{
            dismiss();
        });
    }

    private void observe(){
        vm.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) Toast.makeText(getContext(), "Данные успешно удалены", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        sharedVM.getCurrentUserDataLiveData().observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                sharedVM.removeHeroData(vm.getHeroId());
                vm.setUserData(userData);
            }
        });
    }
}

