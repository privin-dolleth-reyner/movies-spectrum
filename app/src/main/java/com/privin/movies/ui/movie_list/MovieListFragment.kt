package com.privin.movies.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.privin.movies.databinding.FragmentMovieListBinding
import com.privin.movies.databinding.ViewErrorBinding
import com.privin.movies.databinding.ViewLoaderBinding
import com.privin.movies.model.Movie
import com.privin.movies.ui.movie_detail.MovieDetailFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class MovieListFragment : Fragment() {

    protected lateinit var binding: FragmentMovieListBinding
    protected lateinit var bindingError: ViewErrorBinding
    protected lateinit var bindingLoader: ViewLoaderBinding

    protected lateinit var movieAdapter: MovieListAdapter

    private val onClickMovieItem : ((movie: Movie) -> Unit) = {
        val fragment = MovieDetailFragment()
        fragment.arguments = Bundle().apply {
            putLong("id", it.id)
        }
        fragment.show(childFragmentManager, "detail")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MovieListAdapter(onItemClickListener = onClickMovieItem)
        observeError()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        bindingError = ViewErrorBinding.bind(binding.root)
        bindingLoader = ViewLoaderBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        bindingError.retry.setOnClickListener {
            bindingLoader.loaderGrp.isVisible = true
            bindingError.errorGrp.isVisible = false
            onRetry()
        }
    }

    fun scrollToTop() {
        binding.listView.scrollToPosition(0)
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

    abstract suspend fun onError()
    abstract fun onRetry()
    private fun observeError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                onError()
            }
        }
    }
}