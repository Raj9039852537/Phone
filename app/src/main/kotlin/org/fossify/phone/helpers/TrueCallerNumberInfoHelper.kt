package org.fossify.phone.helpers


import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import org.fossify.commons.extensions.*
import org.fossify.phone.R
import org.fossify.phone.activities.DialpadActivity
import org.fossify.phone.activities.SimpleActivity
import org.fossify.phone.extensions.config
import org.fossify.phone.services.TrueCallerService
import org.fossify.phone.truecaller.MainViewModel
import org.fossify.phone.truecaller.MainViewModelFactory
import org.fossify.phone.databinding.ActivityViewNumberTruecallerInfoBinding

class TruecallerNumberInfoHelper : SimpleActivity() {

    @SuppressLint("SetTextI18n")
    fun initTruecallerNumberInfo(number: String, activity: DialpadActivity) {

        if (number.isNotEmpty()) {
            val networkConnectionInterceptor = NetworkConnectionInterceptor(activity)
            val viewModel: MainViewModel = ViewModelProvider(activity, MainViewModelFactory(TrueCallerService()))[MainViewModel::class.java]
            networkConnectionInterceptor.let { viewModel.getResponse(activity.config.saveTrueCallerCountryCode!!, number, "Bearer " + activity.config.saveTrueCallerToken, it) }

            viewModel.trueCallerResponse.observe(activity) { response ->

                if ((null != response && response.isNotEmpty()) && (response[0].name != NO_INTERNET)) {
                    val truecallerResponse = response[0]
                    val binding = ActivityViewNumberTruecallerInfoBinding.inflate(activity.layoutInflater)

                    binding.truecallerName.text = if (truecallerResponse.name.isNullOrEmpty()) "Unknown" else truecallerResponse.name
                    Glide
                        .with(activity)
                        .load(truecallerResponse.image)
                        .placeholder(binding.truecallerImage.drawable)
                        .into(binding.truecallerImage)

                    truecallerResponse.addresses?.let {
                        val city = it.getOrNull(0)?.city
                        if (!city.isNullOrEmpty()) {
                            binding.truecallerCity.text = city
                            binding.truecallerCity.beVisible()
                        }
                    }

                    truecallerResponse.phones?.let {
                        val carrier = it.getOrNull(0)?.carrier
                        if (!carrier.isNullOrEmpty()) {
                            binding.truecallerCarrier.text = carrier
                            binding.truecallerCarrier.beVisible()
                        }
                    }

                    activity.getAlertDialogBuilder()
                        .setPositiveButton(org.fossify.commons.R.string.ok, null)
                        .setNegativeButton(null, null)
                        .apply {
                            activity.setupDialogStuff(binding.root, this) { alertDialog ->
                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                                    alertDialog.dismiss()
                                    activity.viewModelStore.clear()
                                }
                            }
                        }
                }
            }
        }
    }
}
