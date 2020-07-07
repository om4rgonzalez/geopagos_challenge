package net.omar.gonzalez.geopagoschallenge.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.databinding.UiListCostItemBinding
import net.omar.gonzalez.geopagoschallenge.pojo.Cost
import net.omar.gonzalez.geopagoschallenge.pojo.CostResponse
import net.omar.gonzalez.geopagoschallenge.utils.CellClickListener
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel
import java.text.NumberFormat
import java.util.*

class RecyclerAmountPaymentsAdapter(
    private val items: CostResponse,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<RecyclerAmountPaymentsAdapter.ViewHolder>() {

    init {
//        Log.v("R_COST", "Se instancia el adaptador del recycler costos")
//        Log.v("R_COST", "Tamaño del array "+items.costDetails.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UiListCostItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if (items == null) 0 else items.costDetails.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtQuoteNumber.text = items.costDetails[position].installments

        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance("ARS")

        if (items.costDetails[position].installments.equals("1"))
            holder.binding.txtQuoteName.text = "Cuota"
        else
            holder.binding.txtQuoteName.text = "Cuotas"
        holder.binding.txtQuoteAmount.text =
            format.format(items.costDetails[position].installmentAmount.toDouble())
        holder.binding.txtDiscount.text = items.costDetails[position].discountRate + "%"
        holder.binding.txtFee.text = items.costDetails[position].installmentRate + "%"
        holder.binding.txtTotalAmount.text =
            format.format(items.costDetails[position].totalAmount.toDouble())
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(items.costDetails[position])
        }
    }


    inner class ViewHolder(val binding: UiListCostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}