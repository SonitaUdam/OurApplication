package kh.edu.rupp.fe.dse.ourapplication.fragment

import ProductItemBottomSheetFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kh.edu.rupp.fe.dse.ourapplication.R
import kh.edu.rupp.fe.dse.ourapplication.adapter.ProductAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentHomeBinding
import kh.edu.rupp.fe.dse.ourapplication.model.ProductItem

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var productItems: MutableList<ProductItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewItems.setOnClickListener {
            val bottomSheetDialog = ProductItemBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }

        // retrieve and display popular item
        retrieveAndDisplayPopularItems()

        return binding.root
    }

    private fun retrieveAndDisplayPopularItems() {
        database = FirebaseDatabase.getInstance()
        val productRef: DatabaseReference = database.reference.child("menu")
        productItems = mutableListOf()

        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children) {
                    val productItem = productSnapshot.getValue(ProductItem::class.java)
                    productItem?.let {
                        productItems.add(it) }
                }
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }

    private fun randomPopularItems() {
        val index = productItems.indices.toList().shuffled()
        val numItemToShow = 6
        val subsetProductItems = index.take(numItemToShow).map { productItems[it] }

        setPopularItemsAdapter(subsetProductItems)
    }

    private fun setPopularItemsAdapter(subsetProductItems: List<ProductItem>) {
        val adapter = ProductAdapter(subsetProductItems, requireContext())
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                val itemPosition =  imageList[position]
                val itemMessage = "Select Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
