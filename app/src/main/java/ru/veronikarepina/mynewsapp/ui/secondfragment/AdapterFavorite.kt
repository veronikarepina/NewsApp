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
import ru.veronikarepina.mynewsapp.utils.DateUtils

class AdapterFavorite(
    private val onClickItemListener: (Article) -> Unit,
    private val deleteItemFavorite: (Article) -> Unit
): ListAdapter<Article, AdapterFavorite.FavoriteHolder>(Comparator()) {
    class FavoriteHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CardViewBinding.bind(view)
        fun bind(
            item: Article,
            onClickItemListener: (Article) -> Unit,
            deleteItemFavorite: (Article) -> Unit
        ) = with(binding){
            dataText.text = item.publishedAt?.let { DateUtils.toDefaultDate(it) }
            titleText.text = item.title
            descriptionText.text = item.description
            imageView.load(item.urlToImage)
            favoriteIcon.setImageResource(R.drawable.favorite_painted)
            itemView.setOnClickListener{
                onClickItemListener.invoke(item)
            }
            favoriteIcon.setOnClickListener{
                deleteItemFavorite.invoke(item)
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
        holder.bind(getItem(position), onClickItemListener, deleteItemFavorite)
    }
}