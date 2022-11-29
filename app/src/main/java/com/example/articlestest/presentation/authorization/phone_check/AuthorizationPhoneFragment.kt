package com.example.articlestest.presentation.authorization.phone_check

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.articlestest.R
import com.example.articlestest.databinding.FragmentAuthorizationPhoneBinding
import com.example.articlestest.extension.observe
import com.example.articlestest.presentation.authorization.password_check.AuthorizationPasswordFragment
import com.example.articlestest.presentation.base.BaseViewState
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
        observeViewModel()
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

            val spannableString =
                SpannableString("Нажимая кнопку «Продолжить» вы соглашаетесь с пользовательским соглашением и политикой конфиденцальности ")

            val urlTerms = "https://polli-style.ru/terms/"//условия
            val clickableUrlTermsSpan: ClickableSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                    ds.color = Color.parseColor("#0082F9")
                    ds.linkColor = Color.parseColor("#0082F9")
                }

                override fun onClick(widget: View) {
                    val uri = Uri.parse(urlTerms)
                    val context = widget.context
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.packageName)
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Log.w("URLSpan", "Actvity was not found for intent, $intent")
                    }
                }
            }

            val urlPrivacy = "https://polli-style.ru/privacy/"//конфеденциальность
            val clickableUrlPrivacy: ClickableSpan = object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                    ds.color = Color.parseColor("#0082F9")
                }

                override fun onClick(widget: View) {
                    val uri = Uri.parse(urlPrivacy)
                    val context = widget.context
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.packageName)
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Log.w("URLSpan", "Actvity was not found for intent, $intent")
                    }
                }
            }

            spannableString.setSpan(clickableUrlTermsSpan, 45, 74, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                spannableString.indexOf("«"),
                spannableString.indexOf("»"),
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannableString.setSpan(
                clickableUrlPrivacy,
                77,
                spannableString.lastIndex,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )

            agreementPolicy.text = spannableString
            agreementPolicy.movementMethod = LinkMovementMethod.getInstance()
            agreementPolicy.highlightColor = Color.TRANSPARENT

            edtTxtPhone.addTextChangedListener(MaskWatcher { text, start, before, count ->
                btnContinue.isEnabled = !text.toString().isNullOrEmpty()
                btnContinue.alpha = if (btnContinue.isEnabled) 1F else 0.5F
            })

            btnContinue.apply {
                if (!this.isEnabled) this.alpha = 0.5F

                this.setOnClickListener {
                    viewModel.onTriggerEvent(
                        eventType = AuthorizationCheckContractEvent.PhoneCheck(
                            phone = edtTxtPhone.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun observeViewModel() {
        observe(viewModel.uiState.asLiveData()) {
            binding?.progress?.visibility =
                if (it is BaseViewState.Loading) View.VISIBLE else View.GONE
        }
    }
}