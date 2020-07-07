package net.omar.gonzalez.geopagoschallenge.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.io.RetrofitApiService
import net.omar.gonzalez.geopagoschallenge.pojo.*
import net.omar.gonzalez.geopagoschallenge.utils.StringUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentViewModel : ViewModel() {
    private var paymentMethodsResponse = MutableLiveData<List<PaymentMethod>>()
    private var banksResponse = MutableLiveData<List<Bank>>()
    private var resumePayment = MutableLiveData<SummaryPayment>()
    private var costResponse = MutableLiveData<List<CostResponse>>()
    private var amountShow = MutableLiveData<String>()
    private var finalAmount = MutableLiveData<String>()
    private var finalCard = MutableLiveData<PaymentMethod>()
    private var finalBank = MutableLiveData<Bank>()
    private var finalCost = MutableLiveData<Cost>()
    private var goTo = MutableLiveData<String>()

    init {
        finalAmount.value = ""
        goTo.value = ""
        amountShow.value = "$0,00"
        if (resumePayment.value == null)
            resumePayment.value = SummaryPayment()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mercadopago.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun obtainMethods(context: Context) {
        this.getRetrofit().create(RetrofitApiService::class.java)
            .getPaymentMethods(context.getString(R.string.key)).enqueue(object :
                Callback<List<PaymentMethod>> {
                override fun onResponse(
                    call: Call<List<PaymentMethod>>,
                    response: Response<List<PaymentMethod>>
                ) {
                    if(!response.body()!!.isEmpty()){
                        paymentMethodsResponse.value = response.body()
                        goTo.value = "card"
                    }
                    else{
                        goTo.value = "card_error"
                    }
                }

                override fun onFailure(call: Call<List<PaymentMethod>>, t: Throwable) {
                    paymentMethodsResponse.value = null
                }
            })
    }

    fun obtainBanks(context: Context) {
        this.getRetrofit().create(RetrofitApiService::class.java)
            .getBanks(finalCard.value!!.id, context.getString(R.string.key)).enqueue(object :
                Callback<List<Bank>> {
                override fun onResponse(
                    call: Call<List<Bank>>,
                    response: Response<List<Bank>>
                ) {
                    if(!response.body()!!.isEmpty()){
                        banksResponse.value = response.body()
                        goTo.value = "bank"
                    }
                    else{
                        goTo.value = "bank_error"
                    }
                }

                override fun onFailure(call: Call<List<Bank>>, t: Throwable) {
                    banksResponse.value = null
                }
            })
    }

    fun obtainCosts(context: Context) {
        this.getRetrofit().create(RetrofitApiService::class.java).getCosts(
            finalCard.value!!.id,
            StringUtil.convertCodeAmount(finalAmount.value!!),
            finalBank.value!!.id,
            context.getString(R.string.key)
        ).enqueue(object :
            Callback<List<CostResponse>> {
            override fun onResponse(
                call: Call<List<CostResponse>>,
                response: Response<List<CostResponse>>
            ) {
                if(!response.body()!!.isEmpty()){
                    costResponse.value = response.body()
                    goTo.value = "cost"
                }
                else{
                    goTo.value = "cost_error"
                }
            }

            override fun onFailure(call: Call<List<CostResponse>>, t: Throwable) {
                costResponse.value = null
            }
        })
    }

    fun setNullBank() {
        banksResponse.value = emptyList()
    }

    fun setFinalAmount(amount: String) {
        finalAmount.value = amount
    }

    fun setFinalCard(card: PaymentMethod) {
        finalCard.value = card
    }

    fun setFinalBank(bank: Bank) {
        finalBank.value = bank
    }

    fun setFinalCost(cost: Cost){
        finalCost.value = cost
        setResumePayment()
    }

    fun getPaymentMethods(): LiveData<List<PaymentMethod>> {
        return paymentMethodsResponse
    }

    fun getBanks(): LiveData<List<Bank>> {
        return banksResponse
    }

    fun getCostResponse(): LiveData<List<CostResponse>> {
        return costResponse;
    }

    fun getFinalAmount(): LiveData<String> {
        return finalAmount
    }

    fun getResumePayment(): LiveData<SummaryPayment> {
        return resumePayment
    }

    fun setResumePayment(){
        with(resumePayment.value!!){
            amount = finalCost.value!!.totalAmount
            cardUri = finalCard.value!!.secureThumbnail
            bankUri = finalBank.value!!.secureThumbnail
            installmentAmount = finalCost.value!!.recommendedMessage
            cardName = finalCard.value!!.name
            bankName = finalBank.value!!.name
        }

    }

    fun getAmountShow(): LiveData<String> {
        return amountShow
    }

    fun reset(){
        amountShow.value = "$0,00"
        finalAmount.value = ""
        goTo.value = ""
    }

    fun getGoTo(): LiveData<String>{
        return goTo
    }

    fun postGoTo(go: String){
        goTo.value = go
    }

    fun add(number: String) {
        if (finalAmount.value!!.length <= 7) {
            if (!(finalAmount.value!!.length == 0 && number.trim().equals("0"))) {
                finalAmount.value = finalAmount.value!! + number
                amountShow.value = StringUtil.formatNumber(finalAmount.value!!)
            }
        }
    }


    fun remove() {
        if (finalAmount.value!!.length > 0) {
            if (finalAmount.value!!.length == 1) {
                finalAmount.value = ""
                amountShow.value = "$0,00"
            } else {
                finalAmount.value = finalAmount.value!!.substring(0, finalAmount.value!!.length - 1)
                amountShow.value = StringUtil.formatNumber(finalAmount.value!!)
            }
        }
    }

    fun removeAll() {
        finalAmount.value = ""
        amountShow.value = "$0,00"
    }

}

