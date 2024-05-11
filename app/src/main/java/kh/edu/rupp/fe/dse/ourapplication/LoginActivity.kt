package kh.edu.rupp.fe.dse.ourapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginbutton.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        binding.donthavebtn.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
    }
}