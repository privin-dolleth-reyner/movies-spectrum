package com.privin.movies.ui.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.privin.movies.R
import com.privin.movies.Util
import com.privin.movies.databinding.FragmentMovieDetailBinding
import com.privin.movies.model.MovieDetail
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : BottomSheetDialogFragment() {

    companion object{
        private const val TAG = "MovieDetailFragment"
    }
    private lateinit var binding: FragmentMovieDetailBinding

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        viewModel.loadMovieDetail()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModel.movieDetail.observe(this) { movie ->
            display(movie)
        }
        viewModel.favMovie.observe(this){
            binding.toggleFav.isChecked = it
        }
    }

    private fun setListeners() {
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.isDraggable = false
        }
        binding.back.setOnClickListener {
            dismiss()
        }
    }

    private fun display(movie: MovieDetail) {
        binding.apply {
            title.text = movie.title
            Glide.with(requireContext())
                .load(movie.getBackDropUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster)
            val genre = movie.genres.joinToString(", ", transform = { it.name })
            genres.text = getString(R.string.genre, genre)
            tagline.text = movie.tagline
            if (movie.tagline.isNullOrEmpty()) tagline.isVisible = false
            overview.text = movie.overview
            release.text = getString(R.string.release, getReleaseDate(movie.releaseDate))
            voteCount.text = Util.displayTextVotes(movie.voteCount)
            voteAvg.text = String.format("%.1f / 10", movie.voteAverage)
            status.text = getString(R.string.status, movie.status)
            languages.text = getString(R.string.languages, movie.spokenLanguages)
            viewModel.isFavMovie(movie.id)
            toggleFav.setOnCheckedChangeListener { _, fav ->
                if (fav){
                    viewModel.addToFavMovie(movie)
                }else{
                    viewModel.removeFromFavMovie(movie.id)
                }
            }
        }
    }

    private fun getReleaseDate(releaseDate: String?): String {
        if (releaseDate.isNullOrEmpty()) return ""
        return Util.formatDate(releaseDate, "yyyy-MM-dd", "dd MMM yyyy")
    }

}