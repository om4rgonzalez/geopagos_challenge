package net.omar.gonzalez.geopagoschallenge.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaymentMethod: Serializable{
    @SerializedName("id")
    var id: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("secure_thumbnail")
    var secureThumbnail: String = ""
}