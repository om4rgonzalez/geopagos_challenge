package net.omar.gonzalez.geopagoschallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.adapters.RecyclerAmountPaymentsAdapter
import net.omar.gonzalez.geopagoschallenge.databinding.UiSelectAmountPaymentFragmentBinding
import net.omar.gonzalez.geopagoschallenge.pojo.Bank
import net.omar.gonzalez.geopagoschallenge.pojo.Cost
import net.omar.gonzalez.geopagoschallenge.utils.CellClickListener
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectAmountPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectAmountPaymentFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var binding: UiSelectAmountPaymentFragmentBinding
    private lateinit var recyclerAmountPaymentsAdapter: RecyclerAmountPaymentsAdapter
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UiSelectAmountPaymentFragmentBinding.inflate(layoutInflater)
        binding.recyclerAmountPayment.layoutManager = LinearLayoutManager(context)
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        paymentViewModel.getCostResponse().observe(requireActivity(), Observer { cr ->
            recyclerAmountPaymentsAdapter = RecyclerAmountPaymentsAdapter(cr[0],this)
            binding.recyclerAmountPayment.adapter = recyclerAmountPaymentsAdapter
        })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectAmountPaymentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectAmountPaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCellClickListener(data: Any) {
        when (data) {
            is Cost -> {
                paymentViewModel.setFinalCost(data)
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_selectAmountPaymentFragment_to_resumeFragment)
            }
        }
    }
}