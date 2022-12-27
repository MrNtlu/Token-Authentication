package com.mrntlu.tokenauthentication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mrntlu.tokenauthentication.R
import com.mrntlu.tokenauthentication.models.Auth
import com.mrntlu.tokenauthentication.utils.ApiResponse
import com.mrntlu.tokenauthentication.viewmodels.AuthViewModel
import com.mrntlu.tokenauthentication.viewmodels.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    /*
    https://codevoweb.com/golang-postgresql-api-access-and-refresh-tokens/
    https://github.com/MrNtlu/GraduationProject-Android/blob/master/app/src/main/java/com/mrntlu/localsocialmedia/viewmodel/BaseViewModel.kt
    https://github.com/MrNtlu/BiSU-Task/blob/main/app/src/main/java/com/mrntlu/bisu/ui/HomeFragment.kt
    https://www.google.com/search?q=android+interceptor+refresh+token&oq=android+inter&aqs=edge.1.69i57j69i59j0i512l5j69i60l2.2916j0j1&sourceid=chrome&ie=UTF-8
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val loginTV = view.findViewById<TextView>(R.id.loginTV)

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> loginTV.text = it.errorMessage
                ApiResponse.Idle -> loginTV.text = "Idle"
                ApiResponse.Loading -> loginTV.text = "Loading"
                is ApiResponse.Success -> {
                    val bundle = bundleOf(
                        "token" to it.data.token,
                    )
                    navController.navigate(
                        R.id.action_loginFragment_to_main_nav_graph,
                        bundle
                    )
                }
            }
        }

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            viewModel.login(
                Auth("test@gmail.com", "123Test"),
                object: CoroutinesErrorHandler {
                    override fun onError(message: String) {
                        loginTV.text = "Error! $message"
                    }
                }
            )
        }
    }
}