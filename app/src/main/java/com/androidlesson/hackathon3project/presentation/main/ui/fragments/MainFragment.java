package com.androidlesson.hackathon3project.presentation.main.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.databinding.FragmentMainBinding;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    private MainFragmentViewModel vm;
    @Inject
    MainFragmentViewModelFactory vmFactory;

    private SharedViewModel sharedVM;
    @Inject
    SharedViewModelFactory sharedVMFactory;

    private LinearLayout ll_b_news, ll_b_map, ll_b_user_profile;
    private ImageView iv_user_profile_icon, iv_map_icon, iv_news_icon;
    private TextView tv_news_button_text, tv_map_button_text, tv_user_profile_button_text;
    private RelativeLayout rl_bottom_navigation_view;

    private Fragment currFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);

        Log.d("MainFragment","Created");

        initialization();

        setObserver();

        setOnClickListener();

        return binding.getRoot();
    }

    private void initialization() {
        ((App) requireActivity().getApplication()).appComponent.injectMainFragment(this);

        vm = new ViewModelProvider(this, vmFactory).get(MainFragmentViewModel.class);
        sharedVM=new ViewModelProvider(requireActivity(),sharedVMFactory).get(SharedViewModel.class);

        sharedVM.getUserEmail();

        ll_b_news=binding.llBNews;
        ll_b_map=binding.llBMap;
        ll_b_user_profile=binding.llBUserProfile;
        iv_user_profile_icon=binding.ivUserProfileIcon;
        iv_map_icon=binding.ivMapIcon;
        iv_news_icon=binding.ivNewsIcon;
        tv_map_button_text=binding.tvMapButtonText;
        tv_news_button_text=binding.tvNewsButtonText;
        tv_user_profile_button_text=binding.tvUserProfileButtonText;
        rl_bottom_navigation_view = binding.rlBottomNavigationView;

        currFragment = vm.getFragmentContainerLiveData().getValue();
        if (currFragment != null) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fl_main_fragment_container, currFragment)
                    .commit();
        }

    }

    private void setObserver() {
        vm.getFragmentContainerLiveData().observe(getViewLifecycleOwner(), new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
                if (fragment != null && (currFragment == null || !currFragment.getClass().equals(fragment.getClass()))) {
                    currFragment = fragment;
                    getParentFragmentManager().beginTransaction().replace(R.id.fl_main_fragment_container, currFragment).commit();
                }
            }
        });

        vm.getBottomElementMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer selectedItemId) {
                int selectedWidth = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 112, getResources().getDisplayMetrics());
                int margin20dp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
                int margin24dp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
                int margin10dp = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                int defaultWidth = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());

                ViewGroup.LayoutParams paramsNews = ll_b_news.getLayoutParams();
                ViewGroup.LayoutParams paramsMap = ll_b_map.getLayoutParams();
                ViewGroup.LayoutParams paramsProfile = ll_b_user_profile.getLayoutParams();

                ViewGroup.MarginLayoutParams paramsMarginNews = (ViewGroup.MarginLayoutParams) ll_b_news.getLayoutParams();
                ViewGroup.MarginLayoutParams paramsMarginMap = (ViewGroup.MarginLayoutParams) ll_b_map.getLayoutParams();
                ViewGroup.MarginLayoutParams paramsMarginProfile = (ViewGroup.MarginLayoutParams) ll_b_user_profile.getLayoutParams();


                if (selectedItemId == R.id.navigation_news) {
                    iv_news_icon.setImageResource(R.drawable.icon_selected_news);
                    iv_map_icon.setImageResource(R.drawable.icon_map);
                    iv_user_profile_icon.setImageResource(R.drawable.icon_user_profile);

                    tv_news_button_text.setVisibility(View.VISIBLE);
                    tv_map_button_text.setVisibility(View.GONE);
                    tv_user_profile_button_text.setVisibility(View.GONE);

                    ll_b_news.setBackgroundResource(R.drawable.bg_rounded_40dp_selected_bottom_navigation);
                    ll_b_map.setBackgroundResource(R.drawable.bg_rounded_null);
                    ll_b_user_profile.setBackgroundResource(R.drawable.bg_rounded_null);

                    paramsNews.width = selectedWidth;
                    paramsMap.width = defaultWidth;
                    paramsProfile.width = defaultWidth;

                    paramsMarginNews.setMargins(0,0,margin20dp,0);
                    paramsMarginMap.setMargins(0,0,0,0);
                    paramsMarginProfile.setMargins(margin20dp,0,0,0);

                } else if (selectedItemId == R.id.navigation_map) {
                    iv_news_icon.setImageResource(R.drawable.icon_news);
                    iv_map_icon.setImageResource(R.drawable.icon_selected_map);
                    iv_user_profile_icon.setImageResource(R.drawable.icon_user_profile);

                    tv_news_button_text.setVisibility(View.GONE);
                    tv_map_button_text.setVisibility(View.VISIBLE);
                    tv_user_profile_button_text.setVisibility(View.GONE);

                    ll_b_news.setBackgroundResource(R.drawable.bg_rounded_null);
                    ll_b_map.setBackgroundResource(R.drawable.bg_rounded_40dp_selected_bottom_navigation);
                    ll_b_user_profile.setBackgroundResource(R.drawable.bg_rounded_null);

                    paramsNews.width = defaultWidth;
                    paramsMap.width = selectedWidth;
                    paramsProfile.width = defaultWidth;

                    paramsMarginNews.setMargins(margin24dp,0,margin10dp,0);
                    paramsMarginMap.setMargins(0,0,0,0);
                    paramsMarginProfile.setMargins(margin10dp,0,0,0);
                } else {
                    iv_news_icon.setImageResource(R.drawable.icon_news);
                    iv_map_icon.setImageResource(R.drawable.icon_map);
                    iv_user_profile_icon.setImageResource(R.drawable.icon_selected_profile);

                    tv_news_button_text.setVisibility(View.GONE);
                    tv_map_button_text.setVisibility(View.GONE);
                    tv_user_profile_button_text.setVisibility(View.VISIBLE);

                    ll_b_news.setBackgroundResource(R.drawable.bg_rounded_null);
                    ll_b_map.setBackgroundResource(R.drawable.bg_rounded_null);
                    ll_b_user_profile.setBackgroundResource(R.drawable.bg_rounded_40dp_selected_bottom_navigation);

                    paramsNews.width = defaultWidth;
                    paramsMap.width = defaultWidth;
                    paramsProfile.width = selectedWidth;

                    paramsMarginNews.setMargins(margin24dp,0,margin20dp,0);
                    paramsMarginMap.setMargins(0,0,0,0);
                    paramsMarginProfile.setMargins(margin20dp,0,0,0);
                }

                ll_b_news.setLayoutParams(paramsNews);
                ll_b_map.setLayoutParams(paramsMap);
                ll_b_user_profile.setLayoutParams(paramsProfile);

                ll_b_news.setLayoutParams(paramsMarginNews);
                ll_b_map.setLayoutParams(paramsMarginMap);
                ll_b_user_profile.setLayoutParams(paramsMarginProfile);
            }
        });
    }

    private void setOnClickListener(){
        ll_b_news.setOnClickListener(v->{
            vm.replaceFragment(R.id.navigation_news);
        });
        ll_b_map.setOnClickListener(v->{
            vm.replaceFragment(R.id.navigation_map);
        });
        ll_b_user_profile.setOnClickListener(v->{
            vm.replaceFragment(R.id.navigation_user_profile);
        });
    }
}