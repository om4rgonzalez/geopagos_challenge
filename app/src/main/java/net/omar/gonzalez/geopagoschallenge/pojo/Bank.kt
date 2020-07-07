package net.omar.gonzalez.geopagoschallenge.pojo

import com.google.gson.annotations.SerializedName

class Bank {
    @SerializedName("id")
    var id: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("secure_thumbnail")
    var secureThumbnail: String = ""
}
