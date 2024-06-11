package kh.edu.rupp.fe.dse.ourapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivityPayOutBinding
import kh.edu.rupp.fe.dse.ourapplication.model.OrderDetails

class PayOutActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayOutBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var name:String
    private lateinit var address:String
    private lateinit var phone:String
    private lateinit var totalAmount:String
    private lateinit var productItemName:ArrayList<String>
    private lateinit var productItemPrice:ArrayList<String>
    private lateinit var productItemImage:ArrayList<String>
    private lateinit var productItemDescription:ArrayList<String>
    private lateinit var productItemQuantity:ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase and User details
        auth = FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference()

        // set user data
        setUserData()

        // get user details from firebase
        val intent = intent
        productItemName = intent.getStringArrayListExtra("ProductItemName") as ArrayList<String>
        productItemPrice = intent.getStringArrayListExtra("ProductItemPrice") as ArrayList<String>
        productItemImage = intent.getStringArrayListExtra("ProductItemImage") as ArrayList<String>
        productItemDescription = intent.getStringArrayListExtra("ProductItemDescription") as ArrayList<String>
        productItemQuantity= intent.getIntegerArrayListExtra("ProductItemQuantity") as ArrayList<Int>

        totalAmount = calculateTotalAmount().toString() + "$"
        //binding.totalAmount.isEnabled = false
        binding.totalAmount.setText(totalAmount)

        binding.backBtn.setOnClickListener{
            finish()
        }

        binding.placeOrderBtn.setOnClickListener{
            //get data from textview
            name = binding.name.text.toString().trim()
            address = binding.address.text.toString().trim()
            phone = binding.phone.text.toString().trim()
            if (name.isBlank()&&address.isBlank()&&phone.isBlank()){
                Toast.makeText(this, "Please Enter all the Details", Toast.LENGTH_SHORT).show()
            }else{
                placeOrder()
            }



        }
    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetials").push().key
        val orderDetails = OrderDetails(userId,name,productItemName,productItemImage,productItemQuantity,productItemPrice,address,phone,totalAmount,time,itemPushKey,false,false)
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)

        }
            .addOnFailureListener{
                Toast.makeText(this, "failed to order", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }

    }

    private fun removeItemFromCart() {
        val cartItemReference = databaseReference.child("user").child(userId).child("CartItems")
        cartItemReference.removeValue()

    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until productItemPrice.size){
            var price = productItemPrice[i]
            val lastchar = price.last()
            val priceInValue = if(lastchar == '$'){
                price.dropLast(1).toInt()
            }else
            {
                price.toInt()
            }
            var quantity = productItemQuantity[i]
            totalAmount += priceInValue * quantity

        }
        return totalAmount


    }

    private fun setUserData() {

        val user = auth.currentUser
        if (user!=null){
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val names = snapshot.child("name").getValue(String::class.java)?:""
                        val addresses = snapshot.child("address").getValue(String::class.java)?:""
                        val phones = snapshot.child("phone").getValue(String::class.java)?:""
                        binding.apply{
                            name.setText(names)
                            address.setText(addresses)
                            phone.setText(phones)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

    }
}

