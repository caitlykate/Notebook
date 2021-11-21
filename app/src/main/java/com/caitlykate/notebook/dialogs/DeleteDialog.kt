package com.caitlykate.notebook.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.LayoutInflater
import com.caitlykate.notebook.databinding.DeleteDialogBinding
import com.caitlykate.notebook.databinding.NewListDialogBinding

object DeleteDialog {
    fun showDialog(context: Context, listener: Listener){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            bDelete.setOnClickListener{
                listener.onClick()
                dialog?.dismiss()
            }
            bCancel.setOnClickListener{
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.show()
    }

    interface Listener{
        fun onClick()
    }
}