package com.privin.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.privin.movies.databinding.FragmentMovieListBinding

abstract class MovieListFragment : Fragment() {
    protected lateinit var viewModel: HomeViewModel

    protected lateinit var binding: FragmentMovieListBinding

    protected val movieAdapter = MovieListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.listView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as GridLayoutManager
                    if (lm.findLastCompletelyVisibleItemPosition() == movieAdapter.itemCount - 1) {
                        loadMore()
                    }
                }
            })
        }
    }

    abstract fun loadMore()
}