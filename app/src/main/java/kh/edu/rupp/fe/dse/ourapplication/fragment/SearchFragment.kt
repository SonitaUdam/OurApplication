package kh.edu.rupp.fe.dse.ourapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kh.edu.rupp.fe.dse.ourapplication.adapter.ProductAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentSearchBinding
import kh.edu.rupp.fe.dse.ourapplication.model.ProductItem


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter : ProductAdapter
    private lateinit var database: FirebaseDatabase
    private val originalProductItem = mutableListOf<ProductItem>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)

        // retrieve menu item from database
        retrieveMenuItem()
        // setup for search view
        setupSearchView()
        return binding.root
    }

    private fun retrieveMenuItem() {
        // get database ref
        database = FirebaseDatabase.getInstance()
        // ref to the menu node
        val productReference: DatabaseReference = database.reference.child("product")
        productReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children){
                    val productItem = productSnapshot.getValue(ProductItem::class.java)
                    productItem?.let {
                        originalProductItem.add(it)
                    }
                }
                showAllMenu()
            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun showAllMenu() {
        val filterProductItem = ArrayList(originalProductItem)
        setAdapter(filterProductItem)
    }

    private fun setAdapter(filterProductItem: List<ProductItem>) {
        adapter = ProductAdapter(filterProductItem, requireContext())
        binding.itemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsRecyclerview.adapter = adapter

    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterProductItem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterProductItem(newText)
                return true
            }
        })
    }

    private fun filterProductItem(query: String) {
        val filterProductItem = originalProductItem.filter {
            it.productName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filterProductItem)

    }

    companion object {
    }
}





