package kh.edu.rupp.fe.dse.ourapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.fe.dse.ourapplication.databinding.BuyAgainItemBinding

class BuyAgainAdapter(private val buyAgainItemName: MutableList<String>, private val buyAgainItemPrice:MutableList<String>, private val buyAgainItemImage:MutableList<String>, private var requireContext: Context): RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainItemName[position],buyAgainItemPrice[position],buyAgainItemImage[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainItemName.size
    inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding): RecyclerView.ViewHolder
        (binding.root){
        fun bind(itemName: String, itemPrice: String, itemImage: String) {
            binding.buyAgainItemName.text=itemName
            binding.buyAgainItemPrice.text=itemPrice
            val uriString = itemImage
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainItemImage)

        }

    }


}