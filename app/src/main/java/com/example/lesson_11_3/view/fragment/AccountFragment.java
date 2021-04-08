package com.example.lesson_11_3.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.lesson_11_3.R;
import com.example.lesson_11_3.databinding.FragmentAccountBinding;
import com.example.lesson_11_3.viewmodel.SavedStateViewModel;
import com.example.lesson_11_3.viewmodel.UserViewModel;


public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private NavController navController;
    private UserViewModel userViewModel;
    private SavedStateViewModel savedStateViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentAccountBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init view models
        userViewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(UserViewModel.class);

        savedStateViewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), this)).get(SavedStateViewModel.class);

        //init action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbarAcFragment);
        NavigationUI.setupWithNavController(binding.toolbarAcFragment, navController);

        //init nav view
        DrawerLayout drawerLayout = binding.getRoot();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout,
                binding.toolbarAcFragment,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navViewAcFragment.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_exit_menu_item:
                    savedStateViewModel.setUserEmailLiveData(null);
                    savedStateViewModel.setIsAuth(false);
                    navController.navigate(R.id.action_accountFragment_to_authorizationFragment);
                    break;
                case R.id.nav_settings_menu_item:
                    navController.navigate(R.id.action_accountFragment_to_settingFragment2);
                    break;
            }
            return true;
        });
        savedStateViewModel.getUserEmailLiveData().observe(getViewLifecycleOwner(), email -> {
            userViewModel.findUserByEmail(email).observe(requireActivity(), user -> {
                if (user != null) {
                    savedStateViewModel.setUserIdLiveData(user.getId());
                    TextView username = view.findViewById(R.id.tv_navHeader_username);
                    TextView userLastName = view.findViewById(R.id.tv_navHeader_userLastName);
                    ImageView userAvatar = view.findViewById(R.id.imv_navHeader_ava);
                    username.setText(user.getUserName());
                    userLastName.setText(user.getUserLastName());
                    if (user.getImgUri() != null) {
                        Glide.with(requireActivity()).load(user.getImgUri()).into(userAvatar);
                    }
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}