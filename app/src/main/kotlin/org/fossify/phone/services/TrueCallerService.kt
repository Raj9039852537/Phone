package org.fossify.phone.services

import org.fossify.phone.helpers.NetworkConnectionInterceptor
import org.fossify.phone.helpers.TYPE
import org.fossify.phone.interfaces.TrueCallerApi
import org.fossify.phone.models.TrueCallerResponse
import retrofit2.Response

class TrueCallerService {

    suspend fun getTrueCallerResponse(countryCode: String, mobileNumber: String, authorizationToken: String, networkConnectionInterceptor: NetworkConnectionInterceptor): Response<TrueCallerResponse> {
        return TrueCallerApi.invoke(networkConnectionInterceptor).getTrueCallerResponse(countryCode,mobileNumber,TYPE,authorizationToken)
    }
}
