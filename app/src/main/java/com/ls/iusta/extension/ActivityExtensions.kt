package com.ls.iusta.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.ls.iusta.R

internal fun Activity.showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        anchorView = view.rootView.findViewById(R.id.bottomNavigationView)
        show()
    }
}

internal fun Fragment.showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        anchorView = view.rootView.findViewById(R.id.bottomNavigationView)
        show()
    }
}

internal fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

internal inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}

// Keyboard
fun Activity.hideKeyboard() {
    val view = findViewById<View>(android.R.id.content)
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

// Intent
fun FragmentActivity.showDialog(dialog: DialogFragment) {
    try {
        dialog.show(supportFragmentManager, dialog.tag)
    } catch (exception: Exception) {

    }
}

fun Activity.openBrowser(url: String?) {
    val result = if (url?.contains("https://") == true) url else "https://$url"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
    openActivity(intent)
}

fun Activity.startWithAnimation(
    intent: Intent,
    clearStack: Boolean = false,
    requestCode: Int? = null
) {
    if (requestCode == null) startActivity(intent)
    else startActivityForResult(intent, requestCode)

    overridePendingTransition(0, R.anim.close_fade_out)
    if (clearStack) finishAffinity()
}

private fun Activity.openActivity(intent: Intent) {
    try {
        startActivity(intent)
    } catch (exception: ActivityNotFoundException) {

    }
}

