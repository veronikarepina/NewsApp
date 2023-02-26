package ru.veronikarepina.mynewsapp.ui.firstfragment

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
import ru.veronikarepina.mynewsapp.utils.DateUtils

class Adapter(private val listener: Listener): ListAdapter<Article, Adapter.NewsHolder>(Comparator()) {
    class NewsHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CardViewBinding.bind(view)
        fun bind(item: Article, listener: Listener) = with(binding){
            dataText.text = item.publishedAt?.let { DateUtils.toDefaultDate(it) }
            titleText.text = item.title
            descriptionText.text = item.description
            imageView.load(item.urlToImage)
            itemView.setOnClickListener{
                listener.onClick(item)
            }
            favoriteIcon.setOnClickListener{
                if (item.flag){
                    listener.delNewDb(item)
                } else{
                    listener.addNewDb(item)
                }
            }
            favoriteIcon.setImageResource(
                if(item.flag){
                    R.drawable.favorite_painted
                } else{
                    R.drawable.favorite_icon
                }
            )
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return NewsHolder(view)
    }
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}