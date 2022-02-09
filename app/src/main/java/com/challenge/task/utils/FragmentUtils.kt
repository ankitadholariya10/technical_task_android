package com.challenge.task.utils

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.challenge.task.MyApplication
import com.challenge.task.di.AppComponent

val Activity.appComponent: AppComponent
    get() = (application as MyApplication).component

val Fragment.appComponent: AppComponent
    get() = (requireActivity().application as MyApplication).component

fun Fragment.setupToolbar(toolbar: Toolbar) {
    (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
}

fun Fragment.observeToast(viewModel: BaseViewModel) {
    viewModel.toast.observeEvent(viewLifecycleOwner) { toast ->
        val length = when (toast.isLong) {
            true -> Toast.LENGTH_LONG
            else -> Toast.LENGTH_SHORT
        }
        Toast.makeText(requireContext(), toast.message, length).show()
    }
}
