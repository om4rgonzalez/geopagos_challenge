package net.omar.gonzalez.geopagoschallenge.utils

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import net.omar.gonzalez.geopagoschallenge.R

class ViewUtils{

    companion object {
        fun showAlert(title: Int, message: Int, fragmentActivity: FragmentActivity){
            fragmentActivity.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setTitle(title)
                    setMessage(message)
                    setCancelable(false)
                    setPositiveButton(R.string.button_acept) { dialog, id ->
                        dialog.dismiss()
                    }
                }
                builder.create()
            }.show()
        }
    }
}