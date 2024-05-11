package kh.edu.rupp.fe.dse.ourapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private val binding: ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.alrhavebtn.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }
}