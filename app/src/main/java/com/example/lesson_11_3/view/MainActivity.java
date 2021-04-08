package com.example.lesson_11_3.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.lesson_11_3.R;
import com.example.lesson_11_3.util.FileUtil;
import com.example.lesson_11_3.view.fragment.RegistrationFragment;
import com.example.lesson_11_3.viewmodel.SavedStateViewModel;
import com.example.lesson_11_3.viewmodel.UserViewModel;

import java.io.File;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.lesson_11_3.view.fragment.RegistrationFragment.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_PHOTO;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private SavedStateViewModel savedStateViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host);
        savedStateViewModel = new ViewModelProvider(this,
                new SavedStateViewModelFactory(getApplication(), this)).get(SavedStateViewModel.class);

//        savedStateViewModel.getIsAuthLiveData().observe(this, isAuth -> {
//            NavGraph navGraph = navController.getGraph();
//            navGraph.setStartDestination(R.id.authorizationFragment);
//            NavOptions navOptions = new NavOptions.Builder()
//                    .setPopUpTo(R.id.accountFragment, true)
//                    .build();
//            navController.navigate(R.id.action_authorizationFragment_to_accountFragment, null, navOptions);
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RegistrationFragment.GALLERY_REQUEST:
                    Uri galleryImageUri = data.getData();
                    savedStateViewModel.setImgUriLiveData(galleryImageUri);
                    break;
                case RegistrationFragment.REQUEST_CAMERA:
                    if (FileUtil.getCurrentPhotoPath() != null) {
                        File file = new File(FileUtil.getCurrentPhotoPath());
                        Uri cameraImageUri = Uri.fromFile(file);
                        savedStateViewModel.setImgUriLiveData(cameraImageUri);
                    }
            }
        }
    }


}