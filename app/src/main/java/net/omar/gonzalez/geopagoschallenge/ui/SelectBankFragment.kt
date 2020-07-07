package net.omar.gonzalez.geopagoschallenge.ui

import android.app.Activity
import android.content.ContextWrapper
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import net.omar.gonzalez.geopagoschallenge.R
import net.omar.gonzalez.geopagoschallenge.adapters.RecyclerBanksAdapter
import net.omar.gonzalez.geopagoschallenge.databinding.UiSelectBankFragmentBinding
import net.omar.gonzalez.geopagoschallenge.databinding.UiSelectCardFragmentBinding
import net.omar.gonzalez.geopagoschallenge.pojo.Bank
import net.omar.gonzalez.geopagoschallenge.pojo.PaymentMethod
import net.omar.gonzalez.geopagoschallenge.utils.CellClickListener
import net.omar.gonzalez.geopagoschallenge.utils.ViewUtils
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BankListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BankListFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var binding: UiSelectBankFragmentBinding
    private lateinit var recyclerBanksAdapter: RecyclerBanksAdapter
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UiSelectBankFragmentBinding.inflate(layoutInflater)
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        binding.recyclerBanks.layoutManager =
            LinearLayoutManager(context)
        paymentViewModel.getBanks().observe(requireActivity(), Observer { banks ->
            recyclerBanksAdapter = RecyclerBanksAdapter(banks, context, this)
            binding.recyclerBanks.adapter = recyclerBanksAdapter
            binding.loading.visibility = View.GONE
        })

        paymentViewModel.getGoTo().observe(requireActivity(), Observer { go ->
            binding.loading.visibility = View.GONE
            if (go.trim().equals("cost")) {
                paymentViewModel.postGoTo("")
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_bankListFragment_to_selectAmountPaymentFragment)
            }
            if(go.trim().equals("cost_error")){
                paymentViewModel.postGoTo("")
                net.omar.gonzalez.geopagoschallenge.utils.ViewUtils.showAlert(
                    R.string.error_empty_cost_list_tittle,
                    R.string.error_empty_cost_list_message, requireActivity()
                )
            }
        })
        return binding.root
    }

    private fun goBack() {
        requireActivity().onBackPressed()
    }

    fun viewAlert(tittle: Int, message: Int) {
        net.omar.gonzalez.geopagoschallenge.utils.ViewUtils.showAlert(
            tittle,
            message, requireActivity()
        )
    }

    private fun showMessage(tittle: Int, message: Int) {
        val alertDialog: AlertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(tittle)
                setMessage(message)
                setCancelable(false)
                setPositiveButton(R.string.button_acept) { dialog, id ->
                    dialog.dismiss()
                    goBack()
                }
            }
            builder.create()
        }
        alertDialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BankListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BankListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCellClickListener(data: Any) {
        when (data) {
            is Bank -> {
                paymentViewModel.setFinalBank(data)
                paymentViewModel.obtainCosts(requireContext())
                binding.loading.visibility = View.VISIBLE
            }
        }
    }
}