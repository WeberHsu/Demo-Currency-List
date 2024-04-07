package com.weberhsu.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.weberhsu.presentation.adapter.CurrencyListAdapter
import com.weberhsu.presentation.base.BaseFragment
import com.weberhsu.presentation.base.observe
import com.weberhsu.presentation.contract.MainEvent
import com.weberhsu.presentation.contract.MainSideEffect
import com.weberhsu.presentation.contract.MainState
import com.weberhsu.presentation.databinding.FragmentCurrencyListBinding
import com.weberhsu.presentation.extensions.gone
import com.weberhsu.presentation.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

/**
 * author : weber
 * desc : Fragment for different currency list
 */
@AndroidEntryPoint
class CurrencyListFragment : BaseFragment<FragmentCurrencyListBinding>() {
    private val viewModel: CurrencyListViewModel by activityViewModels()
    private val currencyListAdapter: CurrencyListAdapter by lazy { CurrencyListAdapter() }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCurrencyListBinding
        get() = FragmentCurrencyListBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.searchView.onTextChanged = { text ->
            viewModel.handleEvent(MainEvent.Search(text))
        }
        binding.searchView.onClose = {
            viewModel.handleEvent(MainEvent.Search(null))
        }
        binding.recyclerview.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currencyListAdapter
        }

        // Default show cryptos
        viewModel.handleEvent(MainEvent.ShowCryptos)

        viewModel.observe(this, ::onStateChanged, ::onEffect)
    }

    private fun onStateChanged(state: MainState) {
        when (state) {
            is MainState.CurrencyList -> {
                state.currencies?.let {
                    binding.searchView.setPlaceholderText(if (state.isCryptoCurrency) "CRYPTOCURRENCY" else "FIATCURRENCY")
                    if (it.isEmpty()) {
                        currencyListAdapter.refreshData(emptyList())
                        showEmptyView()
                    } else {
                        currencyListAdapter.refreshData(it)
                        showEmptyView(false)
                    }
                }
            }

            else -> {}
        }
    }

    private fun onEffect(effect: MainSideEffect) {
        when (effect) {
            is MainSideEffect.ShowError -> Toast.makeText(requireContext(), effect.msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEmptyView(isEmpty: Boolean = true) {
        if (isEmpty) {
            binding.recyclerview.gone()
            binding.groupEmpty.visible()
        } else {
            binding.recyclerview.visible()
            binding.groupEmpty.gone()
        }
    }
}