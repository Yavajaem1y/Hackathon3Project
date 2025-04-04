package com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments.MapFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments.NewsFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.bottomNavigationFragments.UserProfileFragment;

import java.util.HashMap;
import java.util.Map;

public class MainFragmentViewModel extends ViewModel {

    private final Map<Integer, Fragment> fragmentMap=new HashMap<>();

    private MutableLiveData<Fragment> fragmentContainerMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Integer> bottomElementMutableLiveData=new MutableLiveData<>(R.id.navigation_news);

    public MainFragmentViewModel() {
        setFragmentsInfo();
        Log.d("viewModel","Create");
    }

    private void setFragmentsInfo(){
        if (fragmentMap.isEmpty()){

            //Creating user profile fragment
            Fragment newsFragment=new NewsFragment();
            fragmentMap.put(R.id.navigation_news,newsFragment);

            //Creating all users fragment
            Fragment mapFragment=new MapFragment();
            fragmentMap.put(R.id.navigation_map,mapFragment);

            //Creating other models
            Fragment userProfileFragment=new UserProfileFragment();
            fragmentMap.put(R.id.navigation_user_profile,userProfileFragment);

            fragmentContainerMutableLiveData.setValue(fragmentMap.get(R.id.navigation_news));
        }
    }

    public void replaceFragment(int id){
        if (fragmentContainerMutableLiveData!=null && fragmentContainerMutableLiveData.getValue()!=fragmentMap.get(id)){
            fragmentContainerMutableLiveData.setValue(fragmentMap.get(id));
            bottomElementMutableLiveData.setValue(id);
        }
    }

    public LiveData<Fragment> getFragmentContainerLiveData(){
        return fragmentContainerMutableLiveData;
    }

    public LiveData<Integer> getBottomElementMutableLiveData() {
        return bottomElementMutableLiveData;
    }

}
