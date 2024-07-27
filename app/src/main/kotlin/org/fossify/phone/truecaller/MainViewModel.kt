package org.fossify.phone.truecaller


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.fossify.phone.helpers.NetworkConnectionInterceptor
import org.fossify.phone.models.TrueCallerDataResponse
import org.fossify.phone.models.TrueCallerResponse
import org.fossify.phone.services.TrueCallerService
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val trueCallerService: TrueCallerService) : ViewModel() {

    val trueCallerResponse: MutableLiveData<List<TrueCallerDataResponse>> = MutableLiveData()

    fun getResponse(countryCode: String, mobileNumber: String, authorizationToken: String, networkConnectionInterceptor: NetworkConnectionInterceptor) {
        viewModelScope.launch {
            val response: Response<TrueCallerResponse> =
                trueCallerService.getTrueCallerResponse(countryCode, mobileNumber, authorizationToken, networkConnectionInterceptor)
            trueCallerResponse.value = response.body()?.data
        }
    }
}
