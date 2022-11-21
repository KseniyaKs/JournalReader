package com.example.articlestest.presentation.view

import android.text.Editable
import android.text.TextWatcher


class MaskWatcher(private val mask: String) : TextWatcher {
    private var isRunning = false
    private var isDeleting = false
    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(editable: Editable) {
        val editableLength = editable.length
        if (editableLength == 0) {
            return
        }

        if (isDeleting) {
            if (editable.last().toString() == "(" || editable.toString() == "+7 ") {
                editable.clear()
                return
            }
        }
        if (isRunning || isDeleting) {
            return
        }
        isRunning = true
        if (mask.length < editableLength) {
            editable.delete(mask.length, editable.length)
            isRunning = false
            return
        }
        if (editableLength < mask.length) {
            if (editableLength == 1) {
                if (editable.toString() == "7" || editable.toString() == "8") {
                    editable.clear()
                    editable.append("+7 (")
                } else {
                    editable.insert(0, "+7 (")
                    isRunning = false
                    return
                }
            }
            if (mask[editableLength] != '#') {
                editable.append(mask[editableLength])
            } else if (mask[editableLength - 1] != '#') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
        }
        isRunning = false
    }

    companion object {
        fun buildCpf(): MaskWatcher {
            return MaskWatcher("## (###) ### ##-##")
        }
    }
}

//1) номер начался не с 7 и не с 8 -> автомтаический вставить +7 и введенную цифру после него
//2) Номер начаося с 7 -> ничего не делаем
//3) Номер начался с 8 -> Заменяем на +7
