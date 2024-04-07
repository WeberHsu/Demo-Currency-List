package com.weberhsu.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.activity.viewModels
import com.weberhsu.presentation.base.BaseActivity
import com.weberhsu.presentation.base.observe
import com.weberhsu.presentation.contract.MainEvent
import com.weberhsu.presentation.contract.MainState
import com.weberhsu.presentation.databinding.ActivityDemoBinding
import com.weberhsu.presentation.extensions.clickWithTrigger
import com.weberhsu.presentation.extensions.gone
import com.weberhsu.presentation.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : BaseActivity<ActivityDemoBinding>() {

    private val viewModel: CurrencyListViewModel by viewModels()

    override val bindLayout: (LayoutInflater) -> ActivityDemoBinding
        get() = ActivityDemoBinding::inflate

    @SuppressLint("CommitTransaction")
    override fun prepareView(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction().add(binding.content.id, CurrencyListFragment()).commit()

        binding.btnClear.clickWithTrigger {
            viewModel.handleEvent(MainEvent.Clear)
        }

        binding.btnInsert.clickWithTrigger {
            viewModel.handleEvent(MainEvent.Insert)
        }

        binding.btnA.clickWithTrigger {
            viewModel.handleEvent(MainEvent.ShowCryptos)
        }

        binding.btnB.clickWithTrigger {
            viewModel.handleEvent(MainEvent.ShowFiats)
        }

        viewModel.observe(this, ::onStateChanged)
    }

    private fun onStateChanged(state: MainState) {
        when (state) {
            MainState.Loading -> showLoading()
            else -> hideLoading()
        }
    }

    private fun showLoading() {
        binding.loading.visible()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun hideLoading() {
        binding.loading.gone()
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}
