package ru.veronikarepina.mynewsapp.ui.secondfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.CardViewBinding
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.ui.Listener

class AdapterFavorite(private val listener: Listener): ListAdapter<Article, AdapterFavorite.FavoriteHolder>(Comparator()) {
    class FavoriteHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CardViewBinding.bind(view)
        fun bind(item: Article, listener: Listener) = with(binding){
            dataText.text = item.publishedAt
            titleText.text = item.title
            descriptionText.text = item.description
            imageView.load(item.urlToImage)
            favoriteIcon.setImageResource(R.drawable.favorite_painted)
            itemView.setOnClickListener{
                listener.onClick(item)
            }
            favoriteIcon.setOnClickListener{
                listener.delNewDb(item)
            }
        }
    }
    private class Comparator: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}