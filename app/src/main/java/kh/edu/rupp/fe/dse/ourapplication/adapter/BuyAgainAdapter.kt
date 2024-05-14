package kh.edu.rupp.fe.dse.ourapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.ourapplication.databinding.BuyAgainItemBinding

class BuyAgainAdapter(private val buyAgainItemName: ArrayList<String>, private val buyAgainItemPrice:ArrayList<String>, private val buyAgainItemImage:ArrayList<Int>): RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainItemName[position],buyAgainItemPrice[position],buyAgainItemImage[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainItemName.size
    class BuyAgainViewHolder(private val binding: BuyAgainItemBinding): RecyclerView.ViewHolder
        (binding.root){
        fun bind(itemName: String, itemPrice: String, itemImage: Int) {
            binding.buyAgainItemName.text=itemName
            binding.buyAgainItemPrice.text=itemPrice
            binding.buyAgainItemImage.setImageResource(itemImage)

        }

    }


}