package kh.edu.rupp.fe.dse.ourapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productName = intent.getStringExtra("MenuItemName")
        val productImage = intent.getIntExtra("MenuItemImage",0)
        binding.detailProductName.text = productName
        binding.DetailProductImage.setImageResource(productImage)

        binding.imageButton.setOnClickListener{
            finish()
        }

    }
}