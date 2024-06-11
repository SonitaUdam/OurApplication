package kh.edu.rupp.fe.dse.ourapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kh.edu.rupp.fe.dse.ourapplication.PayOutActivity
import kh.edu.rupp.fe.dse.ourapplication.adapter.CartAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentCartBinding
import kh.edu.rupp.fe.dse.ourapplication.model.CartItems

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var productNames:MutableList<String>
    private lateinit var productPrices:MutableList<String>
    private lateinit var productDescriptions:MutableList<String>
    private lateinit var productImagesUri:MutableList<String>
    private lateinit var quantity:MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)


        auth = FirebaseAuth.getInstance()
        retrieveCartItems()


        binding.proceedBtn.setOnClickListener{
            //get order item details before processing to check out
            getOrderItemsDetail()

        }

        return binding.root
    }

    private fun getOrderItemsDetail() {
        val orderIdReference:DatabaseReference = database.reference.child("user").child(userId).child("CartItems")

        val productName = mutableListOf<String>()
        val productPrices = mutableListOf<String>()
        val productDescriptions = mutableListOf<String>()
        val productImagesUri = mutableListOf<String>()

        // get items quantities
        val quantity = cartAdapter.getUpdatedItemsQuantities()

        orderIdReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children){
                    // get the cartItems to respective list
                    val orderItems = productSnapshot.getValue(CartItems::class.java)
                    // add item details into list
                    orderItems?.productName?.let { productName.add(it) }
                    orderItems?.productPrice?.let { productPrices.add(it) }
                    orderItems?.productDescription?.let { productDescriptions.add(it) }
                    orderItems?.productImage?.let { productImagesUri.add(it) }

                }
                orderNow(productName,productPrices,productDescriptions,productImagesUri,quantity)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Order making Failed. Please try again", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun orderNow(
        productName: MutableList<String>,
        productPrices: MutableList<String>,
        productDescriptions: MutableList<String>,
        productImagesUri: MutableList<String>,
        quantity: MutableList<Int>
    ) {
        if (isAdded && context!=null){
            val intent = Intent(requireContext(),PayOutActivity::class.java)
            intent.putExtra("ProductItemName",productName as ArrayList<String>)
            intent.putExtra("ProductItemPrice",productPrices as ArrayList<String>)
            intent.putExtra("ProductItemImage",productImagesUri as ArrayList<String>)
            intent.putExtra("ProductItemDescription",productDescriptions as ArrayList<String>)
            intent.putExtra("ProductItemQuantity",quantity as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {

        // database reference to the firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""
        val productReference :DatabaseReference = database.reference.child("user").child(userId).child("CartItems")

        // list to store cart items
        productNames = mutableListOf()
        productPrices = mutableListOf()
        productDescriptions = mutableListOf()
        productImagesUri = mutableListOf()
        quantity = mutableListOf()

        // fetch data from the database
        productReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children){
                    // get the cartItems object from the child node
                    val cartItems = productSnapshot.getValue(CartItems::class.java)

                    // add cart items details to the list
                    cartItems?.productName?.let { productNames.add(it)}
                    cartItems?.productPrice?.let { productPrices.add(it) }
                    cartItems?.productDescription?.let { productDescriptions.add(it) }
                    cartItems?.productImage?.let { productImagesUri.add(it) }
                    cartItems?.productQuantity?.let { quantity.add(it) }
                }

                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter = CartAdapter(requireContext(), productNames, productPrices, productDescriptions, productImagesUri, quantity)
                binding.cardRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                binding.cardRecyclerView.adapter = cartAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not Fetch", Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {

    }
}