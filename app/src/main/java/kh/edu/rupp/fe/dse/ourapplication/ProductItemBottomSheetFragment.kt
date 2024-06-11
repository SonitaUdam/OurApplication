
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kh.edu.rupp.fe.dse.ourapplication.adapter.ProductAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentProductItemBottomSheetBinding
import kh.edu.rupp.fe.dse.ourapplication.model.ProductItem

class ProductItemBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentProductItemBottomSheetBinding
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
        binding = FragmentProductItemBottomSheetBinding.inflate(inflater,container,false)
        binding.btnback.setOnClickListener{
            dismiss()
        }
        retrieveMenuItems()
        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val productRef: DatabaseReference = database.reference.child("menu")
        productItems = mutableListOf()

        productRef.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //loop for through each food item
                for (productSnapshot in snapshot.children){
                    val productItem = productSnapshot.getValue(ProductItem::class.java)
                    productItem?.let{ productItems.add(it)}
                }
                // once data receive, set to adapter
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun setAdapter() {
        val adapter = ProductAdapter(productItems,requireContext())
        binding.itemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsRecyclerview.adapter = adapter
    }
    companion object {

    }
}