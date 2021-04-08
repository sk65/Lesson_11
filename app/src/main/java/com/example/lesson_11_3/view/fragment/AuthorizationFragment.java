package com.example.lesson_11_3.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lesson_11_3.R;
import com.example.lesson_11_3.databinding.FragmentAuthorizationBinding;
import com.example.lesson_11_3.util.ValidationUtil;
import com.example.lesson_11_3.viewmodel.SavedStateViewModel;
import com.example.lesson_11_3.viewmodel.UserViewModel;


public class AuthorizationFragment extends Fragment {
    private NavController navController;
    private FragmentAuthorizationBinding binding;
    private UserViewModel userViewModel;
    private SavedStateViewModel savedStateViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //init viewModels
        savedStateViewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this)).get(SavedStateViewModel.class);

        userViewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(UserViewModel.class);
        //init buttons
        binding.buttonAuthFragmentReg.setOnClickListener(v ->
                navController.navigate(R.id.action_authorizationFragment_to_registrationFragment));

        binding.buttonAuthFragmentLogin.setOnClickListener(v -> {
                    if (validateInput()) {
                        return;
                    }

                    String emailInput = ValidationUtil.getStringFromInputLayout(binding.tilAuthFragmentEmail);
                    String passwordInput = ValidationUtil.getStringFromInputLayout(binding.tilAuthFragmentPassword);

                    userViewModel.findUserByEmail(emailInput).observe(getViewLifecycleOwner(), user -> {
                        if (user == null) {
                            Toast.makeText(getActivity(), getContext().getString(R.string.no_user_explanations), Toast.LENGTH_SHORT).show();
                        } else if (!user.getPassword().equals(passwordInput)) {
                            Toast.makeText(getActivity(), getContext().getString(R.string.wrong_password_explanations), Toast.LENGTH_SHORT).show();
                        } else {
                            savedStateViewModel.setImgUriLiveData(Uri.parse(user.getImgUri()));
                            savedStateViewModel.setUserEmailLiveData(emailInput);
                            savedStateViewModel.setIsAuth(true);
                            navController.navigate(R.id.action_authorizationFragment_to_accountFragment);
                        }
                    });
                }
        );
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean validateInput() {
        boolean isValidEmail = ValidationUtil.validateEmail(binding.tilAuthFragmentEmail, getContext());
        boolean isValidPassword = ValidationUtil.validatePassword(binding.tilAuthFragmentPassword, getContext());
        return !isValidEmail || !isValidPassword;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}