package org.fossify.phone.interfaces


import org.fossify.phone.helpers.TRUECALLER_URL
import org.fossify.phone.helpers.NetworkConnectionInterceptor
import org.fossify.phone.models.TrueCallerResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TrueCallerApi {

    @GET("search")
    suspend fun getTrueCallerResponse(
        @Query("countryCode") countryCode: String,
        @Query("q") mobileNumber: String,
        @Query("type") type: String,
        @Header("Authorization") authorizationToken: String
    ): Response<TrueCallerResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): TrueCallerApi {

            val okHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor).build()

            val retrofit =
                Retrofit.Builder()
                    .client(okHttpclient)
                    .baseUrl(TRUECALLER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(TrueCallerApi::class.java)
        }
    }
}
