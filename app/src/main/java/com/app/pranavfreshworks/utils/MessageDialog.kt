package com.app.pranavfreshworks.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.app.pranavfreshworks.R
import kotlinx.android.synthetic.main.layout_message_dialog.*

class MessageDialog(
    context: Context,
    private val isCancelButtonNeeded: Boolean,
    private val message: String?,
) :
    Dialog(context) {

    private lateinit var btnPositive: Button
    private lateinit var btnNegative: Button
    private var listener: OkButtonListener? = null

    companion object {
        fun getInstance(
            context: Context,
            isCancelNeeded: Boolean,
            message: String?
        ): MessageDialog {
            return MessageDialog(context, isCancelNeeded, message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        setContentView(R.layout.layout_message_dialog)
        val window: Window = window!!
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setGravity(Gravity.CENTER)
        getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnPositive = findViewById(R.id.btnOk)
        btnNegative = findViewById(R.id.btnCancel)

        btnPositive.text = "Ok"
        btnNegative.text = "Cancel"


        if (isCancelButtonNeeded) {
            btnNegative.visibility = View.VISIBLE
        } else {
            btnNegative.visibility = View.GONE
        }

        txtMessage.text = message

        btnPositive.setOnClickListener {
            if (listener == null) {
                dismiss()
            } else {
                listener?.onOkPressed(this)
            }
        }

        btnNegative.setOnClickListener {
            dismiss()
        }
    }

    fun setListener(listener: OkButtonListener?): MessageDialog {
        this.listener = listener
        return this
    }

    interface OkButtonListener {
        fun onOkPressed(dialog: MessageDialog)
    }
}