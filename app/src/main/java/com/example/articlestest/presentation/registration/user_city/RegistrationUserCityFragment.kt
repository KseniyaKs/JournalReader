package com.example.articlestest.presentation.registration.user_city

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.articlestest.R
import com.example.articlestest.databinding.FragmentRegistrationUserCityBinding
import com.example.articlestest.presentation.MainActivity
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegistrationUserCityFragment : Fragment() {

    companion object {
        private const val NAME = "NAME"
        private const val SURNAME = "SURNAME"
        private const val PATRONYMIC = "PATRONYMIC"
        private const val EMAIL = "EMAIL"

        fun newInstance(name: String, surname: String, patronymic: String, email: String) =
            RegistrationUserCityFragment().apply {
                arguments = Bundle()
                    .apply {
                        putSerializable(NAME, name)
                        putSerializable(SURNAME, surname)
                        putSerializable(PATRONYMIC, patronymic)
                        putSerializable(EMAIL, email)
                    }
            }
    }

    private val viewModel: RegistrationUserCityViewModel by viewModels()
    private var binding: FragmentRegistrationUserCityBinding? = null
    private var citiesIds = mutableMapOf<Number, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationUserCityBinding.inflate(inflater, container, false)
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
                is NavDestination.BackClick -> {
                    parentFragmentManager.popBackStack()
                }
                is NavDestination.AppMain -> {
                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                else -> {}
            }
        }
    }

    private fun initViews() {
        binding?.apply {

            back.setOnClickListener {
                viewModel.onNavigationEvent(eventType = NavDestination.BackClick)
            }

            btnDone.setOnClickListener {
                viewModel.onTriggerEvent(
                    eventType = RegistrationUserCityEvent.CreateUserInfo(
                        name = requireArguments().get("NAME") as String,
                        surname = requireArguments().get("SURNAME") as String,
                        patronymic = requireArguments().get("PATRONYMIC") as String,
                        email = requireArguments().get("EMAIL") as String,
                        city = viewModel.citiesState.value.first {
                            it.name == txtEnterCity.text.toString()
                        }.id.toString()
                    )
                )
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.citiesState.collect { cityList ->
                    if (cityList.isNotEmpty()) {
                        txtEnterCity.setAdapter(
                            ArrayAdapter<Any?>(
                                requireContext(),
                                R.layout.city_item,
                                cityList.map { it.name }
                            )
//                            FilteredArrayAdapter(
//                                requireContext(),
//                                cityList
//                            )
                        )
                    }
                }
            }
        }
    }
}