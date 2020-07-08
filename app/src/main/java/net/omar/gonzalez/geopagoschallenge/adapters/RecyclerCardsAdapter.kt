package net.omar.gonzalez.geopagoschallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.omar.gonzalez.geopagoschallenge.databinding.UiListCardItemBinding
import net.omar.gonzalez.geopagoschallenge.pojo.PaymentMethod
import net.omar.gonzalez.geopagoschallenge.utils.CellClickListener
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel

class RecyclerCardsAdapter(private val items: List<PaymentMethod>,
                           private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecyclerCardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UiListCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if(items == null) 0 else items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.binding.txtCardName.text = items[position].name
        Picasso
            .get()
            .load(items[position].secureThumbnail)
            .into(holder.binding.imgCard)
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(items[position])
        }
    }



    inner class ViewHolder(val binding: UiListCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}