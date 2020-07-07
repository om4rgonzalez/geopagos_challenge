package net.omar.gonzalez.geopagoschallenge.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.databinding.UiListBankItemBinding
import net.omar.gonzalez.geopagoschallenge.pojo.Bank
import net.omar.gonzalez.geopagoschallenge.utils.CellClickListener
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel

class RecyclerBanksAdapter(private val items: List<Bank>,
                           private val context: Context?,
                           private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecyclerBanksAdapter.ViewHolder>() {
    init {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UiListBankItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if(items == null) 0 else items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.binding.txtBankName.text = items[position].name
        Picasso
            .get()
            .load(items[position].secureThumbnail)
            .into(holder.binding.imgBank)

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(items[position])
        }
    }


    inner class ViewHolder(val binding: UiListBankItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}