package kh.edu.rupp.fe.dse.ourapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.ourapplication.databinding.ProductItemBinding
class ProductAdapter(private val MenuItemsName:MutableList<String>,
                  private val MenuItemPrice: MutableList<String>,
                  private val MenuImage:MutableList<Int>
) :RecyclerView.Adapter<ProductAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = MenuItemsName.size

    inner class MenuViewHolder(private val binding: ProductItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                menuItemName.text=MenuItemsName[position]
                menuItemPrice.text=MenuItemPrice[position]
                itemImage.setImageResource(MenuImage[position])



            }
        }

    }
}