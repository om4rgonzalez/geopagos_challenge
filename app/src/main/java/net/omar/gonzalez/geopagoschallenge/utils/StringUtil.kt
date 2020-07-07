package net.omar.gonzalez.geopagoschallenge.utils

import android.util.Log

class StringUtil {

    companion object {
        fun formatNumber(number: String): String {
            var formatedNumber: String = ""
            if (number.length < 3) {
                when (number.length) {
                    1 -> formatedNumber = "$0,0" + number
                    2 -> formatedNumber = "$0," + number
                }
            } else {
                if (number.length >= 6) {
                    formatedNumber =
                        "$" + number.substring(0, number.length - 5) +
                                "." +
                                number.substring(number.length - 5, number.length - 2) +
                                "," +
                                number.substring(
                                    number.length - 2,
                                    number.length
                                )
                } else {
                    formatedNumber =
                        "$" + number.substring(0, number.length - 2) +
                                "," +
                                number.substring(
                                    number.length - 2,
                                    number.length
                                )
                }
            }
            return formatedNumber
        }

        fun convertCodeAmount(number: String): String {
            var finalNumber: String = ""
            formatNumber(number).split(".").forEach {
                finalNumber += it.trim()
            }
            finalNumber = finalNumber.replace(",", ".")
            finalNumber = finalNumber.replace("$", "")
            Log.v("MONTO", finalNumber.trim())
            return finalNumber.trim()
        }
    }
}