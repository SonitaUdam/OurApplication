package kh.edu.rupp.fe.dse.ourapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.fe.dse.ourapplication.databinding.RecentBuyItemsBinding

class RecentBuyAdapter(private var context: Context,
    private var productNameList: ArrayList<String>,
    private var productImageList: ArrayList<String>,
    private var productPriceList: ArrayList<String>,
    private var productQuantityList: ArrayList<Int>
    ):RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding = RecentBuyItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = productNameList.size
    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class RecentViewHolder(private val binding: RecentBuyItemsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                productName.text=productNameList[position]
                productPrice.text=productPriceList[position]
                productQuantity.text=productQuantityList[position].toString()
                val uriString = productImageList[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(productImage)
            }
        }

        }

    }