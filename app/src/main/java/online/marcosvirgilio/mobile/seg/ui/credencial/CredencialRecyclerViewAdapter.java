package online.marcosvirgilio.mobile.seg.ui.credencial;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import online.marcosvirgilio.mobile.seg.databinding.FragmentConCredencialBinding;
import online.marcosvirgilio.mobile.seg.model.Credencial;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Credencial}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CredencialRecyclerViewAdapter extends RecyclerView.Adapter<CredencialRecyclerViewAdapter.ViewHolder> {

    private final List<Credencial> mValues;

    public CredencialRecyclerViewAdapter(List<Credencial> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentConCredencialBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getClientID());
        holder.mContentView.setText(mValues.get(position).getEndpoint());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Credencial mItem;

        public ViewHolder(FragmentConCredencialBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}