package net.omar.gonzalez.geopagoschallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import net.omar.gonzalez.geopagoschallenge.databinding.ActivityMainBinding
import net.omar.gonzalez.geopagoschallenge.viewmodel.PaymentViewModel

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null
    private var paymentViewModel: PaymentViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //el simbolo !! significa notNull
        //el simbolo ? significa safe
        setContentView(binding!!.root)
        navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)
            ?.getNavController()
        paymentViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        navController?.let { paymentViewModel!!.setNavController(it) }
    }
}