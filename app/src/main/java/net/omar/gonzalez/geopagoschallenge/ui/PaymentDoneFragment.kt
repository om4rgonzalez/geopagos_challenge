package net.omar.gonzalez.geopagoschallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.databinding.UiPaymentDoneFragmentBinding
import net.omar.gonzalez.geopagoschallenge.databinding.UiSelectBankFragmentBinding
import net.omar.gonzalez.geopagoschallenge.databinding.UiSummaryFragmentBinding
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private lateinit var binding: UiPaymentDoneFragmentBinding
private lateinit var paymentViewModel: PaymentViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentDoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentDoneFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = UiPaymentDoneFragmentBinding.inflate(layoutInflater)
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        paymentViewModel.reset()
        binding.buttonnDone.setOnClickListener(View.OnClickListener { paymentViewModel.getNavController().navigate(R.id.action_paymentDoneFragment_to_enterAmountFragment) })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PaymentDoneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentDoneFragment().apply {
            }
    }
}