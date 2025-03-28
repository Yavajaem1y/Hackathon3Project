package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.models.EventDataFromDB;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.interfaces.VisibilityTopElement;
import com.androidlesson.hackathon3project.presentation.main.viewModels.newsFragmentViewModel.NewsFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.showEventViewModel.ShowEventFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.showEventViewModel.ShowEventFragmentViewModelFactory;
import com.bumptech.glide.Glide;

import javax.inject.Inject;


public class ShowEventDialogFragment extends DialogFragment {

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private ShowEventFragmentViewModel vm;
    @Inject
    ShowEventFragmentViewModelFactory vmFactory;

    private ImageView iv_back,iv_proud, iv_preview_image;
    private TextView tv_name, tv_info, tv_date;
    private LinearLayout ll_article_of_the_day;

    private String eventId;
    private VisibilityTopElement visibilityTopElement;

    public ShowEventDialogFragment(String eventId, VisibilityTopElement visibilityTopElement) {
        this.eventId = eventId;
        this.visibilityTopElement=visibilityTopElement;
    }

    public ShowEventDialogFragment() {
    }

    @Override
    public void onStop() {
        if (visibilityTopElement!=null)
            visibilityTopElement.getVisibility(true);
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_show_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((App) requireActivity().getApplication()).appComponent.injectShowEventDialogFragment(this);
        vm=new ViewModelProvider(requireActivity(),vmFactory).get(ShowEventFragmentViewModel.class);
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
                params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                window.setAttributes(params);
            }
        }
    }


    private void initialization(View view){
        if(eventId!=null && !eventId.isEmpty()){
            vm.setEventData(eventId);
        }

        iv_back=view.findViewById(R.id.iv_back);
        iv_proud=view.findViewById(R.id.iv_proud);
        iv_preview_image=view.findViewById(R.id.iv_preview_image);
        tv_date=view.findViewById(R.id.tv_date);
        tv_info=view.findViewById(R.id.tv_info);
        tv_name=view.findViewById(R.id.tv_name);
        ll_article_of_the_day=view.findViewById(R.id.ll_article_of_the_day);
    }

    private void setOnClickListener(){
        iv_back.setOnClickListener(v->{
            dismiss();
        });
    }

    private void observe(){
        vm.getEventDataMutableLiveData().observe(getViewLifecycleOwner(), new Observer<EventDataFromDB>() {
            @Override
            public void onChanged(EventDataFromDB data) {
                if (data!=null){
                    tv_name.setText(data.getEventName());
                    tv_info.setText(data.getEventInfo().replaceAll("  ","\n"));
                    tv_date.setText(data.getEventDate());
                    if (data.getEventAvatarImage()!=null && !data.getEventAvatarImage().isEmpty()){
                        Glide.with(getContext()).load(data.getEventAvatarImage()).centerCrop().into(iv_preview_image);
                    }
                    else Glide.with(getContext()).load("dada").centerCrop().into(iv_preview_image);
                }
            }
        });
    }

    private OnDialogDismissListener dismissListener;

    public interface OnDialogDismissListener {
        void onDialogDismiss();
    }

    public void setOnDialogDismissListener(OnDialogDismissListener listener) {
        this.dismissListener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDialogDismiss();
        }
    }

}

