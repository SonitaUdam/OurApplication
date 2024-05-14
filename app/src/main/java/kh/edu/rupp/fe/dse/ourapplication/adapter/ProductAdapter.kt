package kh.edu.rupp.fe.dse.ourapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.ImageView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.fe.dse.ourapplication.DetailActivity
import kh.edu.rupp.fe.dse.ourapplication.databinding.ProductItemBinding

class ProductAdapter(private val MenuItemsName:MutableList<String>,
                  private val MenuItemPrice: MutableList<String>,
                  private val MenuImage:MutableList<Int>, private val requiredContext: Context) :RecyclerView.Adapter<ProductAdapter.MenuViewHolder>() {
    private val itemClickListener: OnClickListener ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = MenuItemsName.size

    inner class MenuViewHolder(private val binding: ProductItemBinding) :RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION ){
                    itemClickListener?.onItemClick(position)
                }
                //setOnClickListener
                val intent = Intent(requiredContext, DetailActivity::class.java)
                intent.putExtra("MenuItemName", MenuItemsName.get(position))
                intent.putExtra("MenuItemImage", MenuImage.get(position))
                requiredContext.startActivity(intent)
            }
        }
        fun bind(position: Int) {
            binding.apply {
                menuItemName.text=MenuItemsName[position]
                menuItemPrice.text=MenuItemPrice[position]
                itemImage.setImageResource(MenuImage[position])



            }
        }

    }
    interface OnClickListener{
        fun onItemClick(position: Int)
    }
}