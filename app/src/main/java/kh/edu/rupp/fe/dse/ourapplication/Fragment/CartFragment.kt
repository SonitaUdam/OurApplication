package kh.edu.rupp.fe.dse.ourapplication.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.fe.dse.ourapplication.PayOutActivity
import kh.edu.rupp.fe.dse.ourapplication.R
import kh.edu.rupp.fe.dse.ourapplication.adapter.CartAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        val cartItemName = listOf("Flat Shoes", "Sandal", "Tote Bag", "Tripple Strap Sandal", "Strappy Sandal", "Sunglasses")
        val cartItemPrice = listOf("$5", "$6", "$6", "$6", "$6","$6",)
        val cartImage = listOf(
            R.drawable.flats_shoes,
            R.drawable.sandal,
            R.drawable.tote_bag,
            R.drawable.tripple_strap_sandals,
            R.drawable.strappy_sandal,
            R.drawable.sunglasses,
        )
        val adapter = CartAdapter(ArrayList(cartItemName), ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cardRecyclerView.adapter = adapter

        binding.proceedBtn.setOnClickListener{
            val intent = Intent(requireContext(),PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}