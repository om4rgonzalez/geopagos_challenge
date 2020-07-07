package net.omar.gonzalez.geopagoschallenge.io

import net.omar.gonzalez.geopagoschallenge.pojo.Bank
import net.omar.gonzalez.geopagoschallenge.pojo.Cost
import net.omar.gonzalez.geopagoschallenge.pojo.CostResponse
import net.omar.gonzalez.geopagoschallenge.pojo.PaymentMethod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("payment_methods")
    fun getPaymentMethods(@Query("public_key") publicKey: String): Call<List<PaymentMethod>>

    @GET("payment_methods/card_issuers")
    fun getBanks(@Query("payment_method_id") paymentMethodId: String,
                       @Query("public_key") publicKey: String): Call<List<Bank>>

    @GET("payment_methods/installments")
    fun getCosts(@Query("payment_method_id") paymentMethodId: String,
                        @Query("amount") amount: String,
                        @Query("issuer.id") issuerId: String,
                        @Query("public_key") publicKey: String): Call<List<CostResponse>>
}