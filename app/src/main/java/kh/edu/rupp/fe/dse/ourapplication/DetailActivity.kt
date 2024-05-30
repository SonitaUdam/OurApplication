package kh.edu.rupp.fe.dse.ourapplication

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivityDetailBinding
import kh.edu.rupp.fe.dse.ourapplication.model.CartItems

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var productName: String? = null
    private var productPrice: String? = null
    private var productDescription: String? = null
    private var productImageUrl: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init firebase auth
        auth = FirebaseAuth.getInstance()

        productName = intent.getStringExtra("MenuItemName")
        productDescription = intent.getStringExtra("MenuItemDescription")
        productPrice = intent.getStringExtra("MenuItemPrice")
        productImageUrl = intent.getStringExtra("MenuItemImageUrl")

        with(binding){
            detailProductName.text = productName
            detailDescription.text = productDescription
            Glide.with(this@DetailActivity).load(Uri.parse(productImageUrl)).into(detailProductImage)
        }


        binding.imageButton.setOnClickListener{
            finish()
        }
        binding.addItemButton.setOnClickListener{
            addItemToCart()
        }

    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""

        // create a cart items object
        val cartItem = CartItems(productName.toString(), productPrice.toString(), productDescription.toString(), productImageUrl.toString(),1)

        //save data to cart item to firebase
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Items Added to cart Successfully 😁", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Items Not Added😒", Toast.LENGTH_SHORT).show()
        }
    }
}