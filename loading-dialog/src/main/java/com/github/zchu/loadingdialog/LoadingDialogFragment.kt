package com.github.zchu.loadingdialog

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment

class LoadingDialogFragment : DialogFragment() {

    private var message: CharSequence? = null

    override fun getTheme(): Int {
        return arguments?.getInt("themeId") ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            message = savedInstanceState.getString(SAVED_MESSAGE, null)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        message?.let {
            outState.putString(SAVED_MESSAGE, it.toString())
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val xiaoLaLoadingDialog = LoadingDialog(requireContext(), theme)
        message?.let {
            xiaoLaLoadingDialog.setMessage(message)
        }
        return xiaoLaLoadingDialog
    }

    fun setMessage(text: CharSequence?) {
        this.message = text
        val dialog = dialog
        if (dialog != null && dialog is LoadingDialog) {
            dialog.setMessage(text)
        }
    }

    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(cancelable)
    }

    companion object {
        private const val SAVED_MESSAGE = "xiaola:message"

        fun newInstance(@StyleRes themeId: Int? = null): LoadingDialogFragment {
            val args = Bundle()
            themeId?.let {
                args.putInt("themeId", themeId)
            }
            val fragment = LoadingDialogFragment()
            fragment.arguments = args
            return fragment
        }

    }

}