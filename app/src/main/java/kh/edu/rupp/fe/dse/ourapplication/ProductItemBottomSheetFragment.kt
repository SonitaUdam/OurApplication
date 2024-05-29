import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.protobuf.Value
import kh.edu.rupp.fe.dse.ourapplication.R
import kh.edu.rupp.fe.dse.ourapplication.adapter.ProductAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentProductItemBottomSheetBinding
import kh.edu.rupp.fe.dse.ourapplication.model.ProductItem

class ProductItemBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentProductItemBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<ProductItem>

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
        retrieveMenuItems()

        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val productRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        productRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (productSnapshot in snapshot.children){
                    val menuItem = productSnapshot.getValue(ProductItem::class.java)
                    menuItem?.let{ menuItems.add(it)}
                }
                Log.d("ITEMS", "onDataChange: Data Received")
                // once data receive, set to adapter
                setAdapter()

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun setAdapter() {
        if (menuItems.isNotEmpty()){
            val adapter = ProductAdapter(menuItems, requireContext())
            binding.itemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.itemsRecyclerview.adapter = adapter
            Log.d("ITEMS", "setAdapter: dataset")
        }else{
           Log.d("ITEMS", "setAdapter: data NOT set")
        }
    }
    companion object {
    }

}