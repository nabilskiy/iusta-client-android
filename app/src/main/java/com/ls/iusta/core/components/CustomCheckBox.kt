package com.ls.iusta.core.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import com.ls.iusta.R

class CustomCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatCheckBox(context, attrs) {

    init {
        setButtonDrawable(R.drawable.ic_bookmark)
    }
}
