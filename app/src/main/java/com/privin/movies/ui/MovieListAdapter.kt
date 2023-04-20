package com.privin.movies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.privin.movies.databinding.ItemMovieBinding
import com.privin.movies.model.Movie

class MovieListAdapter constructor(
    private val movies: ArrayList<Movie> = ArrayList()
) : RecyclerView.Adapter<MovieListAdapter.MovieView>() {


    fun updateMovieList(list: List<Movie>) {
        val diffCallback = DiffCallback(movies, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movies.clear()
        movies.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addMovieList(list: List<Movie>){
        val insertedPosition = movies.size
        movies.addAll(list)
        notifyItemInserted(insertedPosition)
    }

    class DiffCallback(
        private val oldList: List<Movie>,
        private val newList: List<Movie>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
        }

    }

    class MovieView constructor(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun setBackDrop(url: String?){
                if (url == null) return
                binding.apply {
                    Glide.with(root.context)
                        .load(url)
                        .into(backdrop)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieView {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieView(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieView, position: Int) {
        if (position >= movies.size) return
        val movie = movies[position]
        holder.binding.apply {
            val genres = movie.genres?.joinToString(",") ?: ""
            title.text = movie.title
        }
        holder.setBackDrop(movie.getBackDropUrl())
    }
}