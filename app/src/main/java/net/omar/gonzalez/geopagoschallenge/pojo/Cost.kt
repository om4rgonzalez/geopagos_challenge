package net.omar.gonzalez.geopagoschallenge.pojo

import com.google.gson.annotations.SerializedName

class Cost {
    @SerializedName("installments") //numero de cuota
    var installments: String = ""
    @SerializedName("installment_rate") //interes
    var installmentRate: String = ""
    @SerializedName("discount_rate") //descuento
    var discountRate: String = ""
    @SerializedName("reimbursement_rate")
    var reimbursementRate: String = ""
    @SerializedName("recommended_message")
    var recommendedMessage: String = ""
    @SerializedName("installment_amount") //monto de la cuota
    var installmentAmount: String = ""
    @SerializedName("total_amount") //total a pagar con intereses
    var totalAmount: String = ""
    @SerializedName("payment_method_option_id")
    var paymentMethodOptionId: String = ""
}