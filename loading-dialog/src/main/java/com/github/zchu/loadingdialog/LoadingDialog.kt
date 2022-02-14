package com.github.zchu.loadingdialog

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialog

class LoadingDialog(
    context: Context,
    @StyleRes resId: Int? = null,
) : AppCompatDialog(context, resolveDialogTheme(context, resId)) {
    init {
        if (context is Activity) {
            setOwnerActivity(context)
        }
    }

    private var tvMessage: TextView? = null
    private var customPanel: ViewGroup? = null
    private var contentPanel: ViewGroup? = null

    private var _message: CharSequence? = null
    private var _customView: View? = null
    private var _customViewParams: ViewGroup.LayoutParams? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.layout_loading_dialog)
        contentPanel = findViewById(R.id.loading_content_panel)
        customPanel = findViewById(R.id.loading_custom_panel)
        if (_customView == null) {
            val obtainStyledAttributes =
                context.obtainStyledAttributes(intArrayOf(R.attr.loadingDialogCustomView))
            val customViewLayoutId = obtainStyledAttributes.getResourceId(0, -1)
            if (customViewLayoutId != -1) {
                setCustomView(customViewLayoutId)
            }
            obtainStyledAttributes.recycle()
        } else {
            setCustomView(_customView, _customViewParams)
        }
        tvMessage = findViewById(R.id.tv_loading_dialog_message)
        tvMessage?.visibility = View.VISIBLE
        setMessage(_message ?: tvMessage?.text?.toString())

        setupFixedKeyboard()
    }


    private fun setupFixedKeyboard() {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(intArrayOf(R.attr.loadingFixedKeyboard))
        val xiaoLaLoadingFixedKeyboard = obtainStyledAttributes.getBoolean(0, false)
        obtainStyledAttributes.recycle()
        if (xiaoLaLoadingFixedKeyboard) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
    }

    override fun setContentView(layoutResID: Int) {
        setCustomView(layoutResID)
    }

    override fun setContentView(view: View) {
        setCustomView(view, null)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        setCustomView(view, params)
    }

    fun setCustomView(@LayoutRes layoutResId: Int) {
        setCustomView(LayoutInflater.from(context).inflate(layoutResId, null), null)
    }

    @JvmOverloads
    fun setCustomView(view: View?, params: ViewGroup.LayoutParams? = null) {
        _customView = view
        _customViewParams = params
        val localCustomPanel = customPanel ?: return
        if (view == null) {
            localCustomPanel.visibility = View.GONE
            localCustomPanel.removeAllViews()
            contentPanel?.visibility = View.VISIBLE
        } else {
            localCustomPanel.visibility = View.VISIBLE
            if (params != null) {
                localCustomPanel.addView(view, params)
            } else {
                localCustomPanel.addView(view)
            }
            contentPanel?.visibility = View.GONE
        }

    }

    override fun setTitle(title: CharSequence?) {
        setMessage(title)
    }

    override fun setTitle(@StringRes titleId: Int) {
        setMessage(titleId)
    }

    fun setMessage(text: CharSequence?) {
        _message = text
        val localTvMessage = tvMessage ?: return
        if (text.isNullOrBlank()) {
            localTvMessage.visibility = View.GONE
            localTvMessage.text = null
        } else {
            localTvMessage.visibility = View.VISIBLE
            localTvMessage.text = text
        }
    }

    fun setMessage(@StringRes resId: Int) {
        setMessage(context.getText(resId))
    }

    companion object {
        private var defaultTheme: Int = R.style.ThemeOverlay_LoadingDialog

        @StyleRes
        private fun resolveDialogTheme(context: Context, @StyleRes resId: Int? = null): Int {
            // Check to see if this resourceId has a valid package ID.
            return if (resId != null && (resId ushr 24 and 0x000000ff >= 0x00000001)) {   // start of real resource IDs.
                resId
            } else {
                val outValue = TypedValue()
                context.theme.resolveAttribute(R.attr.loadingDialogTheme, outValue, true)
                val resourceId = outValue.resourceId
                if (resourceId == 0) {
                    defaultTheme
                } else {
                    resourceId
                }
            }
        }

        fun setDefaultTheme(@StyleRes resid: Int) {
            if (resid ushr 24 and 0x000000ff >= 0x00000001) {   // start of real resource IDs.
                defaultTheme = resid
            }
        }
    }


}