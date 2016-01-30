package cz.pavelpilar.calculator.calculator.history;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.pavelpilar.calculator.R;
import cz.pavelpilar.calculator.calculator.Display;
import cz.pavelpilar.calculator.calculator.InputManager;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<String> history;
    AppCompatActivity mActivity;

    HistoryAdapter(List<String> history, AppCompatActivity activity){
        this.history = history;
        mActivity = activity;
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item, viewGroup, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Display display = (Display) v.findViewById(R.id.history_display);
                String source = display.getSource();
                InputManager.setCurrent(source.substring(0, source.indexOf(' ')) + "|");
                if(mActivity != null) mActivity.finish();
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String s = history.get(i);
        viewHolder.display.show(s.split(" = ")[0]);
        viewHolder.result.setText(s.split(" = ")[1]);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Display display;
        TextView result;

        ViewHolder(View data) {
            super(data);
            display = (Display) data.findViewById(R.id.history_display);
            result = (TextView) data.findViewById(R.id.history_result);
        }
    }

}
