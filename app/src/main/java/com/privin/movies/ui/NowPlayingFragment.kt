package com.privin.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.privin.movies.R
import com.privin.movies.databinding.FragmentNowPlayingBinding

class NowPlayingFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    lateinit var binding: FragmentNowPlayingBinding

    private val movieAdapter = MovieListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        viewModel.loadNowPlaying()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        viewModel.nowPlayingMovies.observe(requireActivity()) {
            if(it.isEmpty()){
                Toast.makeText(requireContext(), getString(R.string.end_of_list), Toast.LENGTH_SHORT).show()
            }
            movieAdapter.addMovieList(it)
        }
    }

    private fun setRecyclerView() {
        binding.listView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
            addOnScrollListener(object : OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as GridLayoutManager
                    if(lm.findLastCompletelyVisibleItemPosition() == movieAdapter.itemCount - 1){
                        viewModel.loadNowPlaying(viewModel.nextPage)
                    }
                }
            })
        }
    }
}