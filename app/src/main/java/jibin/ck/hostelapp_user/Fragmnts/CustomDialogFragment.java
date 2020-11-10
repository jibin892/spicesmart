package jibin.ck.hostelapp_user.Fragmnts;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import java.util.Objects;

import jibin.ck.hostelapp_user.R;

public class CustomDialogFragment extends DialogFragment {

    private Toolbar toolbar;
    private ImageButton button;
ImageView closelogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_alert_full, container, false);
        toolbar = view.findViewById(R.id.trans_tool2);
        closelogin = view.findViewById(R.id.closelogin);

        toolbar.setBackground(ContextCompat.getDrawable(getActivity(), android.R.drawable.dialog_holo_light_frame));
        toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(),android.R.color.transparent));
        toolbar.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(),android.R.color.transparent)));

        closelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //You can change it as needed
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;


    }
}
