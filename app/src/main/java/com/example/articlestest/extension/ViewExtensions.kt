package com.example.articlestest.extension

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TYPE_ALL = "*/*"

fun <T> Fragment.observeAllowNulls(liveData: LiveData<T>, observer: ((T?) -> Unit)) {
    liveData.observe(viewLifecycleOwner, Observer {
        observer.invoke(it)
    })
}

fun Fragment.showDatePicker(calendar: Calendar, listener: DatePickerDialog.OnDateSetListener) {
    DatePickerDialog(
        requireContext(),
        listener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun Fragment.openLink(
    url: String
) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url).takeIf { it.scheme != null }
            ?: Uri.parse("http://$url")
    )
    startActivity(intent)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.changeVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.changeVisibilityLeavingView(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun EditText.setTextIfChanged(text: String) {
    if (this.text.toString() != text) {
        this.setText(text)
    }
}

fun View.hideKeyboardAndClearFocus() {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let { inputMethodService ->
        findFocus()?.let { view ->
            inputMethodService.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }
}


val View.activity: Activity
    get() {
        var context = this.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        error("No activity is found for this view")
    }


fun <L : Any> Fragment.getImplementation(klass: Class<L>): L? {
    val activity = this.activity
    val parentFragment = this.parentFragment
    val targetFragment = this.targetFragment

    return when {
        klass.isInstance(activity) -> activity as L
        klass.isInstance(parentFragment) -> parentFragment as L
        klass.isInstance(targetFragment) -> targetFragment as L
        else -> parentFragment?.getImplementation(klass)
    }
}

interface RemovableViewHolderChecker {
    fun mayRemove(viewHolder: RecyclerView.ViewHolder): Boolean
}

//convert px to dp
val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

//convert dp to px
val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()