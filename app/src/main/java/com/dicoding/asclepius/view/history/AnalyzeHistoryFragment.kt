package com.dicoding.asclepius.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapter.AnalyzeHistoriesAdapter
import com.dicoding.asclepius.databinding.FragmentAnalyzeHistoryBinding
import com.dicoding.asclepius.domain.model.AnalyzeHistories
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyzeHistoryFragment : Fragment() {
    private lateinit var binding: FragmentAnalyzeHistoryBinding
    private val viewModel: AnalyzeHistoriesViewModel by viewModels()
    private lateinit var adapter: AnalyzeHistoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalyzeHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        viewModel.getAllAnalyzeHistory()
    }

    private fun setupRecyclerView() {
        adapter = AnalyzeHistoriesAdapter(analyzeHistories = emptyList()){ history ->
            viewModel.deleteById(history.id)
        }
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
        binding.rvHistory.setHasFixedSize(true)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.analyzeHistories.collect { histories ->
                    showHistory(histories)
                }
            }
        }
    }

    private fun showHistory(history: List<AnalyzeHistories>) {
        if (history.isEmpty()) {
            binding.rvHistory.visibility = View.GONE
            binding.emptyHistoryText.visibility = View.VISIBLE
        } else {
            binding.rvHistory.visibility = View.VISIBLE
            binding.emptyHistoryText.visibility = View.GONE
            adapter.updateData(history)
        }
    }

}
