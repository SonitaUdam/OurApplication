package kh.edu.rupp.fe.dse.ourapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.fe.dse.ourapplication.adapter.RecentBuyAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivityRecentOrderItemsBinding
import kh.edu.rupp.fe.dse.ourapplication.model.OrderDetails

class RecentOrderItems : AppCompatActivity() {
    private val binding : ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allProductNames:ArrayList<String>
    private lateinit var allProductImages:ArrayList<String>
    private lateinit var allProductPrices:ArrayList<String>
    private lateinit var allProductQuantities:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            finish()
        }



        val recentOrderItems = intent.getSerializableExtra("RecentBuyItems") as ArrayList<OrderDetails>
        recentOrderItems ?.let { orderDetails ->
            if (orderDetails.isNotEmpty()){
                val recentOrderItem = orderDetails[0]

                allProductNames = recentOrderItem.productNames as ArrayList<String>
                allProductImages = recentOrderItem.productImagesUri as ArrayList<String>
                allProductPrices = recentOrderItem.productPrices as ArrayList<String>
                allProductQuantities = recentOrderItem.quantity as ArrayList<Int>
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        val rv = binding.recentBuyRecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this,allProductNames,allProductPrices,allProductImages,allProductQuantities)
        rv.adapter = adapter
    }
}