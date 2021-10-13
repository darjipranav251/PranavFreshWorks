package com.app.pranavfreshworks.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.app.pranavfreshworks.customview.CustomProgressDialog
import com.app.pranavfreshworks.model.ErrorModel
import com.app.pranavfreshworks.utils.MessageDialog
import com.app.pranavfreshworks.viewModel.BaseViewModel

abstract class BaseFragment : Fragment() {

    protected abstract fun initializeComponent(view: View?)

    protected abstract fun defineLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(defineLayoutResource(), container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponent(view)
    }

    fun setUpBaseViewModel(viewModel: BaseViewModel) {
        CustomProgressDialog(requireActivity())
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        if (it) {
            CustomProgressDialog.showDialog()
        } else {
            CustomProgressDialog.dismissDialog()
        }
    }

    private val onMessageErrorObserver = Observer<ErrorModel> {
        CustomProgressDialog.getDialogObject().dismiss()
        MessageDialog.getInstance(requireContext(), false, it.errorMessage).show()
    }
}