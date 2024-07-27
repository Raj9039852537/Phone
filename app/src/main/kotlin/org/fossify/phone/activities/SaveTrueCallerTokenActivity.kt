package org.fossify.phone.activities


import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AlertDialog
import org.fossify.commons.extensions.*
import org.fossify.phone.R
import org.fossify.phone.extensions.config
import org.fossify.phone.databinding.DialogAddTruecallerTokenBinding

@SuppressLint("InflateParams")
class SaveTrueCallerTokenActivity(val activity: Activity, val callback: () -> Unit)  {

    init {
        val binding = DialogAddTruecallerTokenBinding.inflate(activity.layoutInflater)

        val truecallerToken:String? = activity.config.saveTrueCallerToken
        val truecallerCountryCode:String? = activity.config.saveTrueCallerCountryCode
        val view = activity.layoutInflater.inflate(R.layout.dialog_add_truecaller_token, null).apply {
            if (truecallerToken?.isNotEmpty() == true) {
                binding.addTruecallerTokenEdittext.setText(truecallerToken)
            }
            if (truecallerCountryCode?.isNotEmpty() == true) {
                binding.addTruecallerCountryCodeEdittext.setText(truecallerCountryCode)
            }

        }

        activity.getAlertDialogBuilder()
            .setPositiveButton(org.fossify.commons.R.string.ok, null)
            .setNegativeButton(org.fossify.commons.R.string.cancel, null)
            .apply {
                activity.setupDialogStuff(view, this) { alertDialog ->
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val newTrueCallerToken = binding.addTruecallerTokenEdittext.text.toString()
                        val newTruecallerCountryCode = binding.addTruecallerCountryCodeEdittext.text.toString()
                        activity.config.saveTrueCallerToken=newTrueCallerToken
                        activity.config.saveTrueCallerCountryCode=newTruecallerCountryCode
                        callback()
                        alertDialog.dismiss()
                    }
                }
            }
    }
}
