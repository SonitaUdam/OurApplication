package kh.edu.rupp.fe.dse.ourapplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.fe.dse.ourapplication.R
import kh.edu.rupp.fe.dse.ourapplication.adapter.BuyAgainAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        setupRecyclerView()
        return binding.root
    }
    private fun setupRecyclerView(){
        val buyAgainItemName = arrayListOf("Sunglasses", "Tote bag", "Flats shoes")
        val buyAgainItemPrice = arrayListOf("$5", "$3", "$14")
        val buyAgainItemImage = arrayListOf(R.drawable.sunglasses,R.drawable.tote_bag,R.drawable.flats_shoes)
        buyAgainAdapter = BuyAgainAdapter(buyAgainItemName,buyAgainItemPrice,buyAgainItemImage)
        binding.BuyAgainRecyclerView.adapter=buyAgainAdapter
        binding.BuyAgainRecyclerView.layoutManager= LinearLayoutManager(requireContext())
    }
    companion object {

    }
}