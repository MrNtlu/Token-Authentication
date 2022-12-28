package com.mrntlu.tokenauthentication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mrntlu.tokenauthentication.R
import com.mrntlu.tokenauthentication.models.Auth
import com.mrntlu.tokenauthentication.utils.ApiResponse
import com.mrntlu.tokenauthentication.viewmodels.AuthViewModel
import com.mrntlu.tokenauthentication.viewmodels.CoroutinesErrorHandler
import com.mrntlu.tokenauthentication.viewmodels.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null)
                navController.navigate(R.id.action_loginFragment_to_main_nav_graph)
        }

        val loginTV = view.findViewById<TextView>(R.id.loginTV)
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> loginTV.text = it.errorMessage
                ApiResponse.Loading -> loginTV.text = "Loading"
                is ApiResponse.Success -> {
                    tokenViewModel.saveToken(it.data.token)
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