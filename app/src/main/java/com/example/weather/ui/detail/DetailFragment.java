package com.example.weather.ui.detail;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.MainViewModel;
import com.example.weather.data.DailyForecast;
import com.example.weather.databinding.DetailFragmentBinding;
import com.example.weather.util.IntentUtils;

public class DetailFragment extends Fragment {

    private MainViewModel mViewModel;
    private DetailFragmentBinding mBinding;
    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.detail_fragment, container, false);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment,container, false);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity(),
                new SavedStateViewModelFactory(getActivity().getApplication(), this))
                .get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);
        mViewModel.getSelected().observe(getViewLifecycleOwner(), forecast -> {
            if (forecast!=null) mBinding.imageViewDetailCond.setImageLevel(Integer.valueOf(forecast.getCondCodeD()));
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getView().getRootView().findViewById(R.id.detail_container) == null) {
            inflater.inflate(R.menu.sharing_menu, menu);
            inflater.inflate(R.menu.settings_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Navigation.findNavController(getView())
                        .navigate(R.id.action_detailFragment_to_settingsFragment);
                break;
            case R.id.sharing:
                DailyForecast forecast = mViewModel.getSelected().getValue();
                String s = mViewModel.getUnitState().getValue() ? getString(R.string.unit_c) : getString(R.string.unit_f);
                IntentUtils.sendMessage(this.getContext(),
                        String.format("分享「%s」的天气 \n%s，最高温度%s%s，最低温度%s%s，\n湿度为%s%s，压强为%s%s，风速为%s%s，请注意天气变化哦～",
                                forecast.getDate(),
                                forecast.getCondTxtD(),
                                forecast.getTmpMax(),s,
                                forecast.getTmpMin(),s,
                                forecast.getHum(),getString(R.string.unit_hum),
                                forecast.getPres(),getString(R.string.unit_pres),
                                forecast.getWindSpd(),getString(R.string.unit_spr)
                                )
                        , "分享天气");
                break;
            default:
                Navigation.findNavController(getView())
                        .navigate(R.id.action_detailFragment_to_mainFragment);
        }
        return true;
    }

}
