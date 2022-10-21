package com.example.articlestest.presentation.registration.user_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.user_city.RegistrationUserCityFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationUserDataFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationUserDataFragment()
    }

    val viewModel: RegistrationUserDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_user_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.RegistrationUserCity -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.container, RegistrationUserCityFragment.newInstance())
                        .addToBackStack("registration_user_city")
                        .commit()
                }
                else -> {}
            }
        }
    }
}