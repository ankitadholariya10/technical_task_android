package com.challenge.task.utils

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.transition.TransitionManager


@BindingAdapter("bindInvisibleUnless")
fun bindInvisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    TransitionManager.beginDelayedTransition(view.parent as ViewGroup)
}

@BindingAdapter("bindEnabledUnless")
fun bindEnabledUnless(view: View, disabled: Boolean) {
    view.isEnabled = !disabled
}
