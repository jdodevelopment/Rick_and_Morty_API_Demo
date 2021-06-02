package ar.com.jdodevelopment.rickandmorty.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ar.com.jdodevelopment.rickandmorty.R
import ar.com.jdodevelopment.rickandmorty.databinding.NavigationActivityBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {


    private lateinit var binding: NavigationActivityBinding

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initToolbar()
        initNavigationView()
    }


    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initNavigationView() {
        navController = findNavController(R.id.nav_host)
    }


    override fun onBackPressed() {
        val canNavigateUp = navController.navigateUp()
        if (!canNavigateUp) {
            openExitDialog()
        }
    }

    private fun openExitDialog() {
        val title = getString(R.string.exit)
        val message = getString(R.string.confirm_exit)

        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(R.string.cancel) { dialogInterface, _ -> dialogInterface.dismiss() }
            .setPositiveButton(R.string.accept) { _, _ -> finish() }
            .show()
    }

}