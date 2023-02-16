package com.ls.iusta.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.core.components.TextDrawable.Companion.builder
import com.ls.iusta.databinding.ActivityMainBinding
import com.ls.iusta.domain.models.push.MainScreenUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.setupWithNavController
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.viewmodel.user.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModels()

    private var currentNavController: LiveData<NavController>? = null
    private var backPressedOnce = false
    private var viewNotifications: MenuItem? = null

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top_main, menu)
        viewNotifications = menu?.findItem(R.id.navigationGraphNotifications)
        updateUserInfo()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigationGraphNotifications -> {
                currentNavController?.value?.navigate(R.id.notificationsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        if (currentNavController?.value?.graph?.startDestinationId == currentNavController?.value?.currentDestination?.id) {
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
        } else {
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
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                viewModel.saveToken(token)
            }
            .addOnFailureListener {
                viewModel.saveToken(null, it.message.toString())
            }
            .addOnCanceledListener {
                viewModel.saveToken(null)
            }
    }

    fun updateUserInfo(){
        viewModel.getUserInfo()
    }

    override fun initViewModel() {
        observe(viewModel.mainData, ::onTokenSaved)
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun onTokenSaved(event: MainScreenUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is MainScreenUIModel.Loading -> {
                //    handleLoading(true)
            }
            is MainScreenUIModel.Token -> {
                //     handleLoading(false)
            }
            is MainScreenUIModel.User -> {
                //     handleLoading(false)
                val count = event.data.unread_messages
                if (count > 0)
                    binding.apply {
                        val badge = builder()
                            .beginConfig()
                            .width(68) // width in px
                            .height(68)
                            .withBorder(4, Color.YELLOW)
                            .bold()
                            .endConfig()
                            .buildRound(count.toString(), Color.RED)
                        viewNotifications?.icon = badge
                    }
                else {
                    viewNotifications?.icon = getDrawable(R.drawable.ic_baseline_notifications_none_24)
                }
            }
            is MainScreenUIModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }


}