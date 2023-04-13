package org.emu.lightningx;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.appcompat.app.AppCompatActivity;

import org.emu.lightningx.services.DatabaseService;
import org.emu.lightningx.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseService.initDatabase(this.getBaseContext());
    }

    @Override
    public void onBackPressed() {
        NavController navController = Navigation.findNavController(findViewById(R.id.rootFragmentNavGraph));

        navController.navigateUp();
    }
}