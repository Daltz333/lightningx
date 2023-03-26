package org.emu.lightningx.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.emu.lightningx.R;
import org.emu.lightningx.models.ClassModel;
import org.emu.lightningx.placeholder.PlaceholderContent.PlaceholderItem;
import org.emu.lightningx.databinding.FragmentClassBinding;
import org.emu.lightningx.services.GlobalStateService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {

    private final List<ClassModel> mClasses;
    private final Context mContext;

    public ClassRecyclerViewAdapter(List<ClassModel> classes, Context context) {
        mClasses = classes;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentClassBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mClass = mClasses.get(position);
        holder.mIdView.setText("NULL");
        holder.mContentView.setText(mClasses.get(position).getName());
        holder.mDateView.setText(mClasses.get(position).getClassCreationDate());
    }

    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public ClassModel mClass;

        public TextView mDateView;

        public ViewHolder(FragmentClassBinding binding) {
            super(binding.getRoot());
            mIdView = binding.numStudents;
            mContentView = binding.className;
            mDateView = binding.classCreationDate;

            binding.getRoot().setOnClickListener(view1 -> itemOnClick(binding));
            binding.getRoot().setOnLongClickListener(view1 -> onLongClick());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        public boolean onLongClick() {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

            alert.setTitle("Delete " + mClass.getName() + "?");
            alert.setMessage("This action is permanent and irreversible. Are you sure?");

            alert.setPositiveButton("Delete", (dialog, whichButton) -> {
                // Not necessarily required, but it's good practice to always mutate
                // the origin list, instead of the reference
                int position = GlobalStateService.getInstance().getSelectedProfessor().
                        getClasses().
                        indexOf(mClass);

                GlobalStateService.getInstance().getSelectedProfessor().removeClass(mClass);

                if (getBindingAdapter() != null) {
                    getBindingAdapter().notifyItemRemoved(position);
                } else {
                    Log.println(Log.WARN, "LightningX", "Failed to delete class, adapter was null!");
                }
            });

            alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
                dialog.cancel();
            });

            alert.create();
            alert.show();

            return true;
        }

        public void itemOnClick(FragmentClassBinding binding) {
            NavController controller = Navigation.findNavController(binding.getRoot());

            controller.navigate(R.id.studentsFragment);
        }
    }
}