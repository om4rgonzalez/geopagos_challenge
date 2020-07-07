package net.omar.gonzalez.geopagoschallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import net.omar.gonzalez.geopagoschallenge.databinding.UtilsCustomKeyboardFragmentBinding
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CustomKeyboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomKeyboardFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var binding: UtilsCustomKeyboardFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        binding = UtilsCustomKeyboardFragmentBinding.inflate(layoutInflater)
        binding.button0.setOnClickListener(this)
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.buttonDelete.setOnClickListener(View.OnClickListener { paymentViewModel.remove() })
        binding.buttonDelete.setOnLongClickListener(View.OnLongClickListener {
            paymentViewModel.removeAll()
            false})

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CustomKeyboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CustomKeyboardFragment().apply {
            }
    }

    override fun onClick(v: View?) {
        paymentViewModel.add((v as Button).text as String)
    }
}