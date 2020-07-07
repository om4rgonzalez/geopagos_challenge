package net.omar.gonzalez.geopagoschallenge.pojo

import com.google.gson.annotations.SerializedName

class CostResponse {
    @SerializedName("payment_method_id")
    var paymentMethodId: String = ""
    @SerializedName("payer_costs")
    var costDetails: List<Cost> = ArrayList()
}