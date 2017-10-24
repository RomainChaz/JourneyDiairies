package dodochazoenterprise.journeydiaries.view;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.JourneyManageBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneyManageFragment extends Fragment implements View.OnClickListener {
    private Journey journey = null;
    private Uri outputFileUri;

    public JourneyManageFragment(Journey journey) {
        super();
        this.journey = journey;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        JourneyManageBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.journey_manage, container, false);
        binding.setJvm(new JourneyViewModel(binding.getRoot().getContext(), journey));
        return binding.getRoot();
    }

    public void openImageIntent()
    {
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = null;
            imageView = (ImageView) imageView.findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public void onClick(View v) {

    }
}
