package dodochazoenterprise.journeydiaries.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.JourneyItemBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

/**
 * Created by Romain on 09/10/2017.
 */

class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.BindingHolder>
{
    private List<Journey> journeys;
    private Context context;
    JourneyListAdapter(Context context, List<Journey> journeys) {
        this.journeys = journeys;
        this.context = context;
    }
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JourneyItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.journey_item,parent,false);

        return new BindingHolder(binding);
    }
    @Override
    public void onBindViewHolder(JourneyListAdapter.BindingHolder holder, int
            position) {
        JourneyItemBinding binding = holder.binding;

        binding.setJvm(new JourneyViewModel(this.context, journeys.get(position)));

    }
    @Override
    public int getItemCount() {
        return journeys.size();
    }
    static class BindingHolder extends RecyclerView.ViewHolder {
        private JourneyItemBinding binding;
        BindingHolder(JourneyItemBinding binding) {
            super(binding.journeyItem);
            this.binding = binding;
        }
    }
}
