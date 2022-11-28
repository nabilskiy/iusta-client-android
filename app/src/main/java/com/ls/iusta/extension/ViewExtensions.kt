package com.ls.iusta.extension

import android.annotation.SuppressLint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

/**
 * Shorthand extension function to make view gone
 */
fun View.makeGone() {
    this.visibility = View.GONE
}

/**
 * Shorthand extension function to make view visible
 */
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

@SuppressLint("ClickableViewAccessibility")
fun TextView.setDrawableClickListener(listener: () -> Unit) {
    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (right - compoundDrawables[2].bounds.width())) {
                listener.invoke()
                return@setOnTouchListener true
            }
        }
        false
    }
}

fun EditText.isPassword(): Boolean {
    return transformationMethod is PasswordTransformationMethod
}

fun EditText.setFocusEnabled(isEnabled: Boolean) {
    if (isEnabled) {
        isFocusableInTouchMode = true
        isFocusable = true
    } else {
        isFocusable = false
    }
}

fun EditText.setPasswordState(isEnabled: Boolean) {
    transformationMethod = if (isEnabled) PasswordTransformationMethod.getInstance()
    else HideReturnsTransformationMethod.getInstance()
}

fun TextView.setRightDrawable(@DrawableRes res: Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
}

fun TextView.setLeftDrawable(@DrawableRes res: Int) {
    setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
}

// Animations

fun View.fadeIn(duration: Long = 200) {
    fadeIn(duration, 0)
}

fun View.fadeIn(duration: Long = 200, fadeOutOffset: Long = 0) {
    if (visibility == View.VISIBLE) return

    val fadeIn = AlphaAnimation(0F, 1F)
    fadeIn.interpolator = AccelerateInterpolator()
    fadeIn.fillAfter = true
    fadeIn.duration = duration
    fadeIn.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            isVisible = true
        }

        override fun onAnimationEnd(animation: Animation) {
            clearAnimation()
            if (fadeOutOffset != 0L) fadeOut(offset = fadeOutOffset)
        }

        override fun onAnimationRepeat(animation: Animation) {

        }
    })

    startAnimation(fadeIn)
}

fun View.fadeOut(duration: Long = 200) {
    fadeOut(duration, 0)
}

fun View.fadeOut(duration: Long = 200, offset: Long) {
    if (visibility != View.VISIBLE) return
    if (animation != null) return

    val fadeOut = AlphaAnimation(1F, 0F)
    fadeOut.interpolator = AccelerateInterpolator()
    fadeOut.fillAfter = true
    fadeOut.startOffset = offset
    fadeOut.duration = duration
    fadeOut.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {

        }

        override fun onAnimationEnd(animation: Animation) {
            isInvisible = true
            clearAnimation()
        }

        override fun onAnimationRepeat(animation: Animation) {

        }
    })

    startAnimation(fadeOut)
}