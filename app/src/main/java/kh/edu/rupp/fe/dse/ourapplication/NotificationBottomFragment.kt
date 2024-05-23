package kh.edu.rupp.fe.dse.ourapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kh.edu.rupp.fe.dse.ourapplication.adapter.NotificationAdapter
import kh.edu.rupp.fe.dse.ourapplication.databinding.FragmentNotificationBottomBinding

class NotificationBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)
        val notification = listOf("Your order has been canceled successfully", "Order has been taken by the driver", "Congrats Your Order Placed")
        val notificationImage = listOf(R.drawable.sademoji, R.drawable.truck, R.drawable.congratulation)
        val adapter = NotificationAdapter(
            ArrayList(notification),
            ArrayList(notificationImage)
        )
        binding.notificationnRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationnRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}