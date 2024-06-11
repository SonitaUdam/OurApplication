package kh.edu.rupp.fe.dse.ourapplication.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.fe.dse.ourapplication.DetailActivity
import kh.edu.rupp.fe.dse.ourapplication.databinding.ProductItemBinding
import kh.edu.rupp.fe.dse.ourapplication.model.ProductItem

class ProductAdapter(
    private val menuItems: List<ProductItem>,
    private val requiredContext: Context)
    :RecyclerView.Adapter<ProductAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: ProductItemBinding) :RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION ){
                    openDetailActivity(position)
                }
                //set

            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]
            val intent = Intent(requiredContext, DetailActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.productName)
                putExtra("MenuItemPrice", menuItem.productPrice)
                putExtra("MenuItemImageUrl", menuItem.productImageUrl)
                putExtra("MenuItemDescription", menuItem.productDescription)

            }
            // start the details activity
            requiredContext.startActivity(intent)

        }

        // set data into recyclerview items name, price, image
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuItemName.text=menuItem.productName
                menuItemPrice.text=menuItem.productPrice
                val uri = Uri.parse(menuItem.productImageUrl)
                Glide.with(requiredContext).load(uri).into(itemImage)
            }
        }

    }
    interface OnClickListener{
        fun onItemClick(position: Int)
    }
}


