package com.example.androidmentoringprogram

import android.app.AlertDialog
import android.content.Context

class AlertDialogFragment(context: Context): AlertDialog.Builder(context) {
    fun show(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}