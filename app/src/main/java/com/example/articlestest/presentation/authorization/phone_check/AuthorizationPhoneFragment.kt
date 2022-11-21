package com.example.articlestest.presentation.authorization.phone_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.databinding.FragmentAuthorizationPhoneBinding
import com.example.articlestest.presentation.authorization.password_check.AuthorizationPasswordFragment
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.confirmcode_check.RegistrationConfirmCodeFragment
import com.example.articlestest.presentation.view.MaskWatcher
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthorizationPhoneFragment : Fragment() {

    companion object {
        fun newInstance() = AuthorizationPhoneFragment()
    }

    private val viewModel: AuthorizationPhoneViewModel by viewModels()
    private var binding: FragmentAuthorizationPhoneBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAuthorizationPhoneBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun initNavigation() {
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.AuthorizationPassword -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            AuthorizationPasswordFragment.newInstance(destination.phone)
                        )
                        .addToBackStack("authorization_password")
                        .commit()
                }
                is NavDestination.RegistrationConfirmationCode -> {
                    parentFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            RegistrationConfirmCodeFragment.newInstance(destination.phone)
                        )
                        .addToBackStack("registration_confirm_code")
                        .commit()
                }
                else -> {}
            }
        }
    }

    private fun initViews() {
        binding?.apply {

            edtTxtPhone.addTextChangedListener(MaskWatcher.buildCpf());

            btnContinue.setOnClickListener {
                viewModel.onTriggerEvent(
                    eventType = AuthorizationCheckContractEvent.PhoneCheck(
                        phone = edtTxtPhone.text.toString()
                    )
                )
            }
        }
    }
}