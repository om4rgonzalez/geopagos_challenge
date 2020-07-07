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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.adapters.RecyclerCardsAdapter
import net.omar.gonzalez.geopagoschallenge.databinding.UiEnterAmountFragmentBinding
import net.omar.gonzalez.geopagoschallenge.databinding.UiSelectCardFragmentBinding
import net.omar.gonzalez.geopagoschallenge.utils.ViewUtils
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel
import java.text.NumberFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnterAmountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnterAmountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: UiEnterAmountFragmentBinding
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UiEnterAmountFragmentBinding.inflate(layoutInflater)
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        paymentViewModel.getAmountShow().observe(requireActivity(), Observer { number ->
            binding.txtAmount.text = number
            })
        binding.buttonNext.setOnClickListener{v ->
            if(validateAmount()){
                binding.loading.visibility = View.VISIBLE
                paymentViewModel!!.obtainMethods(requireContext())
            }
            }
        paymentViewModel.getGoTo().observe(requireActivity(), Observer { go ->
            binding.loading.visibility = View.GONE
            if (go.trim().equals("card")) {
                paymentViewModel.postGoTo("")
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_enterAmountFragment_to_selectCardFragment)
            }
            if (go.trim().equals("card_error")) {
                paymentViewModel.postGoTo("")
                net.omar.gonzalez.geopagoschallenge.utils.ViewUtils.showAlert(
                    R.string.error_empty_card_list_tittle,
                    R.string.error_empty_card_list_message, requireActivity()
                )
            }
        })

        return binding.root
    }

    fun validateAmount(): Boolean{
        var ok = true
        if(binding.txtAmount.text.toString().trim().equals("$0,00")) {
            ok = false
            ViewUtils.showAlert(R.string.error_invalid_amount_format_tittle,
                R.string.error_invalid_amount_format_message,
                requireActivity())
        }
        return ok
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EnterAmountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EnterAmountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}