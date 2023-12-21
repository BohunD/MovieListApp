package com.bohunapps.movielistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bohunapps.movielistapp.databinding.MovieItemRvBinding
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RvAdapter(
    private var list: List<MovieEntity>
): RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    var onMovieClicked: ((MovieEntity) -> Unit)? = null
    class ViewHolder(binding: MovieItemRvBinding): RecyclerView.ViewHolder(binding.root){
        val ivPhoto = binding.ivPhoto
        val tvName = binding.tvName
        val tvDescription = binding.tvDescription
        val tvOnWatchlist = binding.tvOnWatchlist
        val layout = binding.ll
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = StringBuilder(list[position].title)
            .append(" (")
            .append((list[position].extractYear())?:"")
            .append(")")
            .toString()
        holder.tvDescription.text = StringBuilder(list[position].duration)
            .append(" - ")
            .append(list[position].showGenre())
        holder.ivPhoto.setImageResource(list[position].photo)
        holder.layout.setOnClickListener {
            onMovieClicked?.invoke(list[position])
        }
        holder.tvOnWatchlist.visibility= if(list[position].isInWatchList){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    fun updateList(newList: List<MovieEntity> ){
        this.list = newList
    }


}