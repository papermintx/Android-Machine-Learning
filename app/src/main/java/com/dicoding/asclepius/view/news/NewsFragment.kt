package com.dicoding.asclepius.view.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapter.ArticlesAdapter
import com.dicoding.asclepius.databinding.FragmentNewsBinding
import com.dicoding.asclepius.domain.model.ArticlesItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeRefreshLayout()
        observeDataNews()
        observeErrorMessage()
        setupRetryButton()
    }

    private fun gotoBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        articlesAdapter = ArticlesAdapter(emptyList()) { article ->
            article.url?.let { url ->
                if (url == "https://removed.com") {
                    Toast.makeText(requireContext(), "This article is not available", Toast.LENGTH_SHORT).show()
                } else {
                    gotoBrowser(url)
                }
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = articlesAdapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            Toast.makeText(requireContext(), "Refreshing...", Toast.LENGTH_SHORT).show()
            newsViewModel.refreshNews("cancer", "health", "en")
        }
    }

    private fun observeDataNews() {
        lifecycleScope.launch {
            newsViewModel.news.collect { articles ->
                updateData(articles ?: emptyList())
            }
        }

        lifecycleScope.launch {
            newsViewModel.loading.collect { isLoading ->
                binding.swipeRefreshLayout.isRefreshing = isLoading
            }
        }
    }

    private fun observeErrorMessage() {
        lifecycleScope.launch {
            newsViewModel.errorMessage.collect { message ->
                if (message != null) {
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.VISIBLE
                    binding.errorTextView.text = message
                } else {
                    binding.errorTextView.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                }
            }
        }
    }


    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            newsViewModel.refreshNews("cancer", "health", "en")
        }
    }

    private fun updateData(articles: List<ArticlesItem?>) {
        articlesAdapter.updateArticles(articles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
