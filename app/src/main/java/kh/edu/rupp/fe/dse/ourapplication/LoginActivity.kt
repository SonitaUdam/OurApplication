package kh.edu.rupp.fe.dse.ourapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kh.edu.rupp.fe.dse.ourapplication.databinding.ActivityLoginBinding
import kh.edu.rupp.fe.dse.ourapplication.model.UserModel

class LoginActivity : AppCompatActivity() {
    private var userName: String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        // initialization of firebase auth
        auth = FirebaseAuth.getInstance()
        // initialization of firebase database
        database = FirebaseDatabase.getInstance().reference
        // google sign in
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // login with email and password
        binding.loginButton.setOnClickListener {
            // get data from text field
            email = binding.emailAddress.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_SHORT).show()
            } else {
                signInUser(email, password)
            }
        }

        binding.donthavebtn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        // google sign in
        binding.googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show()
                updateUi(user)
            } else {
                createUser(email, password)
            }
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "Create User & Login Successfully!", Toast.LENGTH_SHORT).show()
                saveUserData(userName, email)
                updateUi(user)
            } else {
                Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createUserAccount: Authentication Failed", task.exception)
            }
        }
    }

    private fun saveUserData(userName: String?, email: String) {
        val user = UserModel(userName, email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account: GoogleSignInAccount? = task.getResult(Exception::class.java)
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                Toast.makeText(this, "Sign-In Successfully!", Toast.LENGTH_SHORT).show()
                                updateUi(authTask.result?.user)
                            } else {
                                Toast.makeText(this, "Google Sign-in Failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Google Sign-in Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    // check if user already logged in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUi(currentUser)
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        user?.let {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
