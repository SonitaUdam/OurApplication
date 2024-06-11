package kh.edu.rupp.fe.dse.ourapplication.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kh.edu.rupp.fe.dse.ourapplication.RecentOrderItems
import kh.edu.rupp.fe.dse.ourapplication.adapter.BuyAgainAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentHistoryBinding
import kh.edu.rupp.fe.dse.ourapplication.model.OrderDetails


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOderItem: MutableList<OrderDetails> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        // initialize firebase auth
        auth = FirebaseAuth.getInstance()
        // initialize firebase database
        database = FirebaseDatabase.getInstance()

        // Retrieve and display the user order history
        retrieveBuyHistory()

        // recent buy button click
        binding.recentBuyItem.setOnClickListener{
            seeItemsRecentBuy()
        }
        binding.receivedButton.setOnClickListener{
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey = listOfOderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    // function to see items recent buy
    private fun seeItemsRecentBuy() {
        listOfOderItem.firstOrNull()?.let { recentBuy ->
            val intent = Intent(requireContext(), RecentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem", recentBuy)
            startActivity(intent)
        }
    }
    // function to retrieve items buy history
    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid?:""
        val buyItemReference :DatabaseReference = database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children){
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOderItem.add(it)
                    }
                }
                listOfOderItem.reverse()
                if (listOfOderItem.isNotEmpty()){
                    // display the most recent order details
                    setDataInRecentBuyItem()
                    // set up recyclerview with previous order details
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }
    // function to display most recent order details
    private fun setDataInRecentBuyItem() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOderItem = listOfOderItem.firstOrNull()
        recentOderItem?.let {
            with(binding){
                buyAgainItemName.text = it.productNames?.firstOrNull()?:""
                buyAgainItemPrice.text = it.productPrices?.firstOrNull()?:""
                val image = it.productImagesUri?.firstOrNull()?:""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainItemImage)

                val isOrderIsAccepted = listOfOderItem[0].orderAccepted ?: false
                Log.d("TAG", "setDataInRecentBuyItem: $isOrderIsAccepted")
                if (isOrderIsAccepted){
                    orderStatus.background.setTint(Color.GREEN)
                    receivedButton.visibility = View.VISIBLE
                }

                listOfOderItem.reverse()
                if (listOfOderItem.isNotEmpty()){

                }


            }
        }


    }

    // function to set up to recyclerview with previous order detials
    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainItemName = mutableListOf<String>()
        val buyAgainItemPrice = mutableListOf<String>()
        val buyAgainItemImage = mutableListOf<String>()
        for (i in 1 until listOfOderItem.size){
            listOfOderItem[i].productNames?.firstOrNull()?.let { buyAgainItemName.add(it)
            listOfOderItem[i].productPrices?.firstOrNull()?.let { buyAgainItemPrice.add(it)
            listOfOderItem[i].productImagesUri?.firstOrNull()?.let { buyAgainItemImage.add(it) }
        }

        val rv = binding.BuyAgainRecyclerView
        rv.layoutManager = LinearLayoutManager(requireContext())
        buyAgainAdapter = BuyAgainAdapter(
            buyAgainItemName,
            buyAgainItemPrice,
            buyAgainItemPrice,
            requireContext())
        rv.adapter = buyAgainAdapter
        }}
    }
}



