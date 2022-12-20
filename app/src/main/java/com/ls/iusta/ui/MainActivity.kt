package com.ls.iusta.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityMainBinding
import com.ls.iusta.databinding.ActivitySplashBinding
import com.ls.iusta.extension.setupWithNavController
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import com.ls.iusta.presentation.viewmodel.user.LoginViewModel
import com.ls.iusta.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.System.exit

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>()  {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: BaseViewModel by viewModels()

    private var currentNavController: LiveData<NavController>? = null
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.home_nav_graph,
            R.navigation.history_nav_graph,
            R.navigation.settings_nav_graph
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.navigationHostFragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.navigationGraphNotifications -> showSnackBar(binding.root, "Notifications")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        if (currentNavController?.value?.graph?.startDestination == currentNavController?.value?.currentDestination?.id) {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }
            backPressedOnce = true
            showSnackBar(binding.root, getString(R.string.app_exit_label))
            lifecycleScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }else {
            super.onBackPressed()
        }
    }

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startWithAnimation(intent, true)
        }
    }

    override fun initUI() {
    }

    override fun initViewModel() {
    }

}