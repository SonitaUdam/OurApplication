package kh.edu.rupp.fe.dse.ourapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kh.edu.rupp.fe.dse.ourapplication.databinding.CartItemBinding

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private var cartImages: MutableList<String>,
    private var cartDescriptions: MutableList<String>,
    private var cartQuantity: MutableList<Int>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // initialize firebase
    private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid ?: ""
        val cartItemNumber = cartItems.size
        itemQuantities = IntArray(cartItemNumber) { 1 }
        cartItemsReference = database.reference.child("user").child(userId).child("CartItems")
    }

    companion object {
        private var itemQuantities: IntArray = intArrayOf()
        private lateinit var cartItemsReference: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    // get updated quantity
    fun getUpdatedItemsQuantities(): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity


    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartItemName.text = cartItems[position]
                cartItemPrice.text = cartItemPrices[position]
                cartItemQuantity.text = quantity.toString()
                // Load image using Glide
                val uriString = cartImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImage)

                minusbtn.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusbtn.setOnClickListener {
                    increaseQuantity(position)
                }
                deletebtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                cartQuantity[position] = itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                cartQuantity[position] = itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

//        private fun deleteItem(position: Int) {
//            getUniqueKeyAtPosition(position) { uniqueKey ->
//                if (uniqueKey != null) {
//                    removeItem(position, uniqueKey)
//                }
//            }
//        }

        private fun deleteItem(position: Int) {
            if (position < 0 || position >= cartItems.size) {
                Toast.makeText(context, "Invalid position", Toast.LENGTH_SHORT).show()
                return
            }

            getUniqueKeyAtPosition(position) { uniqueKey ->
                if (uniqueKey != null) {
                    cartItemsReference.child(uniqueKey).removeValue()
                        .addOnSuccessListener {
                            if (cartItems.isNotEmpty()) cartItems.removeAt(position)
                            if (cartItems.isNotEmpty()) cartImages.removeAt(position)
                            if (cartItems.isNotEmpty()) cartDescriptions.removeAt(position)
                            if (cartItems.isNotEmpty()) cartItemPrices.removeAt(position)
                            if (itemQuantities.isNotEmpty()) {
                                itemQuantities =
                                    itemQuantities.filterIndexed { index, _ -> index != position }
                                        .toIntArray()
                            }
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, cartItems.size)
                            Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to Delete!", Toast.LENGTH_SHORT).show()
                        }

                }
            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete: (String?) -> Unit) {
            cartItemsReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey: String? = null
                    // loop through snapshot children
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == positionRetrieve) {
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Data not fetch", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
