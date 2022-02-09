package com.challenge.task.presentation.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.challenge.task.R
import com.challenge.task.databinding.FragmentUserCreationBinding
import com.challenge.task.utils.ViewModelFactory
import com.challenge.task.utils.appComponent
import com.challenge.task.utils.observeEvent
import com.challenge.task.utils.observeToast
import javax.inject.Inject

class UserCreationFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UserCreationViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentUserCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)

        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserCreationBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeToast(viewModel)
    }

    private fun initViews() {
        viewModel.exit.observeEvent(viewLifecycleOwner) { exit ->
            if (exit) {
                dismiss()
            }
        }
    }
}
