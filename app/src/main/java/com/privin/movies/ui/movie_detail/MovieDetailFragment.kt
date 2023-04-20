package com.privin.movies.ui.movie_detail

import android.app.Activity
import android.app.Dialog
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.privin.movies.R
import com.privin.movies.databinding.FragmentMovieDetailBinding
import com.privin.movies.height
import com.privin.movies.setFullScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment: BottomSheetDialogFragment() {

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
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            bottomSheetBehavior.isFitToContents = true
            bottomSheetBehavior.isDraggable = false
            bottomSheetBehavior.peekHeight = resources.displayMetrics.heightPixels
            view.requestLayout()
//            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    if (newState!= BottomSheetBehavior.STATE_EXPANDED && newState!= BottomSheetBehavior.STATE_COLLAPSED) {
//                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                    }
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                }
//
//            })
        }
        viewModel.movieDetail.observe(this){ movie ->
            binding.apply {
                title.text = movie.title
                Glide.with(requireContext())
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(poster)
                genres.text = movie.genres.joinToString(", ", transform = { it.name })
                tagline.text = movie.tagline
                overview.text = movie.overview
                release.text = movie.releaseDate

            }
        }
    }

}