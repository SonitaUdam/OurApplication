package kh.edu.rupp.fe.dse.ourapplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.fe.dse.ourapplication.R
import kh.edu.rupp.fe.dse.ourapplication.adapter.ProductAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter : ProductAdapter
    private val originalMenuItemName = listOf("Flat Shoes", "Sandal", "Tote Bag", "Tripple Strap Sandal", "Strappy Sandal", "Sunglasses")
    private val originalMenuItemPrice = listOf("$5", "$6", "$6", "$6", "$6","$6",)
    private val originalMenuImage = listOf(
        R.drawable.flats_shoes,
        R.drawable.sandal,
        R.drawable.tote_bag,
        R.drawable.tripple_strap_sandals,
        R.drawable.strappy_sandal,
        R.drawable.sunglasses,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private val filterItemName = mutableListOf<String>()
    private val filterItemPrice = mutableListOf<String>()
    private val filterItemImage = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        adapter = ProductAdapter(filterItemName,filterItemPrice,filterItemImage)
        binding.itemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsRecyclerview.adapter = adapter

        // mid vdo no.7
        // setup for search view
        setupSearchView()

        // show all menu items
        showAllItems()
        return binding.root
    }




    private fun showAllItems() {
        filterItemName.clear()
        filterItemPrice.clear()
        filterItemImage.clear()

        filterItemName.addAll(originalMenuItemName)
        filterItemPrice.addAll(originalMenuItemPrice)
        filterItemImage.addAll(originalMenuImage)

        adapter.notifyDataSetChanged()
    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        filterItemName.clear()
        filterItemPrice.clear()
        filterItemImage.clear()

        originalMenuItemName.forEachIndexed { index, itemName ->
            if(itemName.contains(query, ignoreCase = true)){
                filterItemName.add(itemName)
                filterItemPrice.add(originalMenuItemPrice[index])
                filterItemImage.add(originalMenuImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }

    companion object {
    }
}