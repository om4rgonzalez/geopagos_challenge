package net.omar.gonzalez.geopagoschallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.databinding.UiSummaryFragmentBinding
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel
import java.text.NumberFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResumeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResumeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: UiSummaryFragmentBinding
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UiSummaryFragmentBinding.inflate(layoutInflater)
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        paymentViewModel.getResumePayment().observe(paymentViewModel.getActivity(), Observer { summary ->
            val format = NumberFormat.getCurrencyInstance()
            format.currency = Currency.getInstance("ARS")
            val currency: String = format.format(summary.amount.toDouble())
            binding.txtTotalAmount.text = currency
            binding.txtPaymentDetail.text = summary.installmentAmount
            binding.txtBankName.text = summary.bankName
            binding.txtCardName.text = summary.cardName
            Picasso
                .get()
                .load(summary.cardUri)
                .into(binding.imgCard)

            Picasso
                .get()
                .load(summary.bankUri)
                .into(binding.imgBank)
        })

        binding.buttonnConfirm.setOnClickListener(View.OnClickListener { paymentViewModel.getNavController().navigate(
            R.id.action_resumeFragment_to_paymentDoneFragment) })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResumeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResumeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}