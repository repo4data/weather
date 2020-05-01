package com.example.weather.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weather.R;
import com.example.weather.data.DailyForecast;
import com.example.weather.databinding.MainFragmentBinding;
import com.example.weather.MainViewModel;
import com.example.weather.util.IntentUtils;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private MainFragmentBinding mBinding;
    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.main_fragment, container, false);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
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
        ForecastAdapter adapter = new ForecastAdapter(mViewModel);
        mBinding.listCond.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.listCond.setAdapter(adapter);
        mViewModel.getForecastList().observe(getViewLifecycleOwner(), forecasts -> {
            if (forecasts!=null && forecasts.size()>0) {
                ArrayList<DailyForecast> list = new ArrayList<>();
                list.addAll(forecasts);
                list.remove(0);
                adapter.setForecasts(list);
            }
        });
        mViewModel.getUnitState().observe(getViewLifecycleOwner(), useC -> {
            adapter.setUnit(getString(useC?R.string.unit_c_s:R.string.unit_f_s));
        });

        mViewModel.getToday().observe(getViewLifecycleOwner(), forecast -> {
            if (forecast!=null) mBinding.imageView.setImageLevel(Integer.valueOf(forecast.getCondCodeD()));
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.locating_menu, menu);
        inflater.inflate(R.menu.settings_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.settings:
                NavController controller = Navigation.findNavController(getView());
                controller.navigate(R.id.action_mainFragment_to_settingsFragment);
                break;
            case R.id.locating:
                IntentUtils.to(this.getContext(),
                        getString(R.string.intent_amap_format, mViewModel.getLocation().getValue()));
                break;
        }
        return true;
    }
}
