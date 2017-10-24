package dodochazoenterprise.journeydiaries.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dodochazoenterprise.journeydiaries.R;

/**
 * Created by Donatien on 23/10/2017.
 */

public class ShowJourneyDialog extends DialogFragment {

    private String address;
    private String note;

    public ShowJourneyDialog(String address, String note) {
        this.address = address;
        this.note = note;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_journey_dialog, container, false);
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.show_journey_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShowJourneyDialog.this.getDialog().cancel();
                    }
                });
        View inflatedView = inflater.inflate(R.layout.show_journey_dialog, null);
        builder.setView(inflatedView);

        TextView address = (TextView) inflatedView.findViewById(R.id.addressDialog);
        TextView note = (TextView) inflatedView.findViewById(R.id.noteDialog);
        address.setText(this.address);
        note.setText(this.note);

        return builder.create();
    }
}