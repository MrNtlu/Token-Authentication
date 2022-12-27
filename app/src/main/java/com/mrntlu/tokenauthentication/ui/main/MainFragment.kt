package com.mrntlu.tokenauthentication.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.mrntlu.tokenauthentication.R
import com.mrntlu.tokenauthentication.utils.ApiResponse
import com.mrntlu.tokenauthentication.viewmodels.CoroutinesErrorHandler
import com.mrntlu.tokenauthentication.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var token: String

    companion object {
        var staticToken: String? = null //TODO For testing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.getString("token") != null) {
            token = requireArguments().getString("token")!!
            staticToken = token
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainTV = view.findViewById<TextView>(R.id.infoTV)
        viewModel.userInfoResponse.observe(viewLifecycleOwner) {
            mainTV.text = when(it) {
                is ApiResponse.Failure -> "Code: ${it.code}, ${it.errorMessage}"
                ApiResponse.Idle -> ""
                ApiResponse.Loading -> "Loading"
                is ApiResponse.Success -> "ID: ${it.data.data._id}\nMail: ${it.data.data.email_address}"
            }
        }

        view.findViewById<Button>(R.id.infoButton).setOnClickListener {
            viewModel.getUserInfo(object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    mainTV.text = "Error! $message"
                }

            })
        }
    }
}