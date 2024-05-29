package kh.edu.rupp.fe.dse.ourapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var productName: String? = null
    private var productPrice: String? = null
    private var productDescription: String? = null
    private var productImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    }
}