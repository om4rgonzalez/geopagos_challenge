package net.omar.gonzalez.geopagoschallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.ViewUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.adapters.RecyclerBanksAdapter
import net.omar.gonzalez.geopagoschallenge.adapters.RecyclerCardsAdapter
import net.omar.gonzalez.geopagoschallenge.databinding.UiSelectCardFragmentBinding
import net.omar.gonzalez.geopagoschallenge.pojo.PaymentMethod
import net.omar.gonzalez.geopagoschallenge.utils.CellClickListener
import net.omar.gonzalez.geopagoschallenge.utils.ViewUtils.Companion.showAlert
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectCardFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var binding: UiSelectCardFragmentBinding
    private lateinit var recyclerCardsAdapter: RecyclerCardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UiSelectCardFragmentBinding.inflate(layoutInflater)
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        paymentViewModel.getPaymentMethods().observe(paymentViewModel.getActivity(), Observer { pm ->
            binding.loading.visibility = View.GONE
            if ((pm == null) || (pm.isEmpty())) {
                net.omar.gonzalez.geopagoschallenge.utils.ViewUtils.showAlert(
                    R.string.error_empty_card_list_tittle,
                    R.string.error_empty_card_list_message,
                    paymentViewModel.getActivity()
                )
            } else {
                recyclerCardsAdapter = RecyclerCardsAdapter(pm, this)
                binding.recyclerCards.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(context)
                binding.recyclerCards.adapter = recyclerCardsAdapter
            }
        })

        paymentViewModel.getGoTo().observe(requireActivity(), Observer { go ->
            binding.loading.visibility = View.GONE
            if (go.trim().equals("bank")) {
                paymentViewModel.postGoTo("")
                paymentViewModel.getNavController().navigate(R.id.action_selectCardFragment_to_bankListFragment)
            }
            if (go.trim().equals("bank_error")) {
                paymentViewModel.postGoTo("")
                net.omar.gonzalez.geopagoschallenge.utils.ViewUtils.showAlert(
                    R.string.error_empty_bank_list_tittle,
                    R.string.error_empty_bank_list_message, paymentViewModel.getActivity()
                )
            }
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
         * @return A new instance of fragment SelectCardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectCardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCellClickListener(data: Any) {
        when (data) {
//            is PaymentMethod ->  Toast.makeText(requireContext(),"Cell clicked "+ data.name , Toast.LENGTH_SHORT).show()
            is PaymentMethod -> {
                paymentViewModel.setFinalCard(data)
                paymentViewModel.obtainBanks(requireContext())
                binding.loading.visibility = View.VISIBLE
            }
        }
    }


}