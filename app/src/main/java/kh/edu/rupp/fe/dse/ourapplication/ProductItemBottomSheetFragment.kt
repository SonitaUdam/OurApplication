import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kh.edu.rupp.fe.dse.ourapplication.R
import kh.edu.rupp.fe.dse.ourapplication.adapter.ProductAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentProductItemBottomSheetBinding

class ProductItemBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentProductItemBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductItemBottomSheetBinding.inflate(inflater,container,false)

        binding.btnback.setOnClickListener{
            dismiss()
        }
        val MenuItemName = listOf("Flat Shoes", "Sandal", "Tote Bag", "Tripple Strap Sandal", "Strappy Sandal", "Sunglasses")
        val MenuItemPrice = listOf("$5", "$6", "$6", "$6", "$6","$6",)
        val MenuImage = listOf(
            R.drawable.flats_shoes,
            R.drawable.sandal,
            R.drawable.tote_bag,
            R.drawable.tripple_strap_sandals,
            R.drawable.strappy_sandal,
            R.drawable.sunglasses,
        )

        val adapter = ProductAdapter(ArrayList(MenuItemName), ArrayList(MenuItemPrice), ArrayList(MenuImage),requireContext())
        binding.itemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsRecyclerview.adapter = adapter
        return binding.root
    }

    companion object {

    }
}