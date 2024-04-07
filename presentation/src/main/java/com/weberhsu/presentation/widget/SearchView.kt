package com.weberhsu.presentation.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.weberhsu.presentation.databinding.ViewSearchBinding
import com.weberhsu.presentation.extensions.clickWithTrigger
import com.weberhsu.presentation.extensions.gone
import com.weberhsu.presentation.extensions.hideSoftKeyboard
import com.weberhsu.presentation.extensions.showSoftKeyboard
import com.weberhsu.presentation.extensions.visible


/**
 * author : weber
 * desc :
 */
class SearchView : ConstraintLayout {

    private val binding: ViewSearchBinding by lazy { ViewSearchBinding.inflate(LayoutInflater.from(context), this, true) }

    var onTextChanged: (String) -> Unit = {}
    var onClose: () -> Unit = {}

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        binding.layoutInit.clickWithTrigger {
            showInput()
        }
        binding.imgArrow.clickWithTrigger {
            showInput(false)
        }
        binding.imgClose.clickWithTrigger {
            showInput(false)
        }
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && binding.layoutInput.isVisible) {
                    onTextChanged.invoke(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
//        binding.editText.setOnKeyListener(OnKeyListener { v, keyCode, event -> // If the event is a key-down event on the "enter" button
//            if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                // Perform action on key press
//
//                return@OnKeyListener true
//            }
//            false
//        })
    }

    fun setPlaceholderText(text: String) {
        binding.tvTitle.text = text
    }

    fun resetUi() {
        if (!binding.layoutInit.isVisible) {
            showInput(false)
        }
    }

    /**
     * Show input layout to input search text
     */
    private fun showInput(show: Boolean = true) {
        if (show) {
            binding.layoutInit.gone()
            binding.layoutInput.visible()
            binding.editText.run {
                requestFocus()
                showSoftKeyboard()
            }
        } else {
            onClose.invoke()
            binding.layoutInit.visible()
            binding.layoutInput.gone()
            binding.editText.run {
                hideSoftKeyboard()
                setText("")
            }
        }
    }

    private fun openSearch() {
//        binding.searchInputText.setText("")
//        binding.searchInputText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                p0?.let {
//                    onTextChanged.invoke(it.toString())
//                }
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//        })
//        binding.searchOpenView.visibility = View.VISIBLE
//        val circularReveal = ViewAnimationUtils.createCircularReveal(
//            binding.searchOpenView,
//            (binding.openSearchButton.right + binding.openSearchButton.left) / 2,
//            (binding.openSearchButton.top + binding.openSearchButton.bottom) / 2,
//            0f, width.toFloat()
//        )
//        circularReveal.duration = 300
//        circularReveal.start()
    }

    private fun closeSearch() {
//        val circularConceal = ViewAnimationUtils.createCircularReveal(
//            binding.searchOpenView,
//            (binding.openSearchButton.right + binding.openSearchButton.left) / 2,
//            (binding.openSearchButton.top + binding.openSearchButton.bottom) / 2,
//            width.toFloat(), 0f
//        )
//
//        circularConceal.duration = 300
//        circularConceal.start()
//        circularConceal.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(animation: Animator) = Unit
//            override fun onAnimationCancel(animation: Animator) = Unit
//            override fun onAnimationStart(animation: Animator) = Unit
//            override fun onAnimationEnd(animation: Animator) {
//                binding.searchOpenView.visibility = View.INVISIBLE
//                binding.searchInputText.setText("")
//                circularConceal.removeAllListeners()
//            }
//        })
    }

}