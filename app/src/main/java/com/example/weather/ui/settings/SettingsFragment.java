package com.example.weather.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

import com.example.addresspicker.CityPickerDialog;
import com.example.addresspicker.InitAreaTask;
import com.example.addresspicker.address.City;
import com.example.addresspicker.address.County;
import com.example.addresspicker.address.Province;
import com.example.weather.MainViewModel;
import com.example.weather.PromptService;
import com.example.weather.R;
import com.example.weather.databinding.SettingsFragmentBinding;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private MainViewModel mViewModel;
    private SettingsFragmentBinding mBinding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//      return inflater.inflate(R.layout.settings_fragment, container, false);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.locationCard.setOnClickListener(v -> {
            ArrayList<Province> provinces = new ArrayList<>();
            InitAreaTask initAreaTask = new InitAreaTask(getContext(), provinces);
            initAreaTask.execute(0);
            initAreaTask.setOnLoadingAddressListener(new InitAreaTask.onLoadingAddressListener() {
                @Override
                public void onLoadFinished() {
                    new CityPickerDialog(getActivity(), provinces,
                            null, null, null, new CityPickerDialog.onCityPickedListener() {
                        @Override
                        public void onPicked(Province selectProvince,
                                             City selectCity, County selectCounty) {
                            StringBuilder address = new StringBuilder();
                            String id = "";
                            if (selectProvince != null) {
                                id = selectProvince.getAreaId();
                                String areaName = selectProvince.getAreaName();
                                if (areaName != null) {
                                    address.append(areaName);
                                }
                            }
                            if (selectCity != null) {
                                id = selectCity.getAreaId();
                                String areaName = selectCity.getAreaName();
                                if (areaName != null) {
                                    address.append(',').append(areaName);
                                }
                            }
                            if (selectCounty != null) {
                                String areaId = selectCounty.getAreaId();
                                if (areaId!=null) {
                                    id = areaId;
                                }
                                String areaName = selectCounty.getAreaName();
                                if (areaName != null) {
                                    address.append(',').append(areaName);
                                }
                            }
                            mViewModel.getLocation().setValue(address.toString());
                            mViewModel.getLocalCode().setValue(id);
                        }
                    }).show();
                }
                @Override
                public void onLoading() {
                }
            });

        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity(),
                new SavedStateViewModelFactory(getActivity().getApplication(), this))
                .get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);
        mViewModel.getNotificationState().observe(getViewLifecycleOwner(), isOn -> {
            mBinding.textViewNotificationInfo.setText(isOn ?
                    getString(R.string.enabled) :
                    getString(R.string.disabled));
            PromptService.setServiceAlarm(getContext(), isOn);
        });
        mViewModel.getUnitState().observe(getViewLifecycleOwner(), isOn -> {
            mBinding.textViewUnitState.setText(isOn ?
                    getString(R.string.unit_c) :
                    getString(R.string.unit_f));
        });
        mViewModel.getLocalCode().observe(getViewLifecycleOwner(), s -> {
            mViewModel.save();
        });
    }
}
