package com.example.weather.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.MainViewModel;
import com.example.weather.R;
import com.example.weather.data.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.Holder> {
    private List<DailyForecast> mForecasts = new ArrayList<>();
    private String mUnit = "";
    private MainViewModel mViewModel;


    public ForecastAdapter(MainViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setForecasts(List<DailyForecast> forecasts) {
        mForecasts = forecasts;
        notifyDataSetChanged();
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    private View view;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.condition_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DailyForecast forecast = mForecasts.get(position);
        holder.date.setText(forecast.getDate());
        holder.cond.setText(forecast.getCondTxtD());
        holder.tmpMax.setText(forecast.getTmpMax());
        holder.tmpMin.setText(forecast.getTmpMin());
        holder.img.setImageLevel(Integer.valueOf(forecast.getCondCodeD()));
        holder.tmpMaxUnit.setText(mUnit);
        holder.tmpMinUnit.setText(mUnit);
        view.findViewById(R.id.cardViewCondition).setOnClickListener(v -> {
            mViewModel.getIndex().setValue(position + 1);
            if (view.getRootView().findViewById(R.id.detail_container) == null) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_mainFragment_to_detailFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        public final TextView date;
        public final TextView cond;
        public final TextView tmpMax;
        public final TextView tmpMaxUnit;
        public final TextView tmpMin;
        public final TextView tmpMinUnit;
        public final ImageView img;

        public Holder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textViewDate);
            cond = itemView.findViewById(R.id.textViewCondText);
            tmpMax = itemView.findViewById(R.id.textViewUpper2);
            tmpMin = itemView.findViewById(R.id.textViewLower2);
            img = itemView.findViewById(R.id.imageViewCondImg);
            tmpMaxUnit = itemView.findViewById(R.id.textViewUpper);
            tmpMinUnit = itemView.findViewById(R.id.textViewLower);
        }
    }
}
