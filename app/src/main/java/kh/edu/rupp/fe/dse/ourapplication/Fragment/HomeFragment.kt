package kh.edu.rupp.fe.dse.ourapplication.Fragment

import ProductItemBottomSheetFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import kh.edu.rupp.fe.dse.ourapplication.R
import kh.edu.rupp.fe.dse.ourapplication.adapter.PopularAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.viewItems.setOnClickListener {
            val bottomSheetDialog = ProductItemBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,null)
        }



        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)
        imageSlider.setItemClickListener(object :ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage="Select Image $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()
            }
        })
        val productName = listOf("Sunglasses", "Bag", "Sandal", "Shoes")
        val price = listOf("$5", "$7", "$19", "50")
        val popularProductImages = listOf(R.drawable.sunglasses,R.drawable.bag,R.drawable.sandal,R.drawable.tripple_strap_sandals)
        val adapter = PopularAdapter(productName,price,popularProductImages,requireContext())
        binding.PopularRecyclerView.layoutManager =LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter =adapter
    }
    companion object {

    }
}