package ru.veronikarepina.mynewsapp.ui.firstfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.*
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.CardViewBinding
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.ui.Listener

class Adapter(private val listener: Listener): ListAdapter<Article, Adapter.NewsHolder>(Comparator()) {
    class NewsHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CardViewBinding.bind(view)
        @OptIn(DelicateCoroutinesApi::class)
        fun bind(item: Article, listener: Listener) = with(binding){
            GlobalScope.launch{
                val flag = listener.checkFlag(item.title)
                if(flag == 1){
                    favoriteIcon.setImageResource(R.drawable.favorite_painted)
                }
                else {
                    favoriteIcon.setImageResource(R.drawable.favorite_icon)
                }
            }
            dataText.text = item.publishedAt
            titleText.text = item.title
            descriptionText.text = item.description
            imageView.load(item.urlToImage)
            itemView.setOnClickListener{
                listener.onClick(item)
            }
            binding.favoriteIcon.setOnClickListener{
                GlobalScope.launch {
                    val flag = listener.checkFlag(item.title)
                    if (flag == 1){
                        item.flag = false
                        listener.searchAndDeleteNew(item.title)
                        favoriteIcon.setImageResource(R.drawable.favorite_icon)
                    }else{
                        item.flag = true
                        listener.addNewDb(item)
                        favoriteIcon.setImageResource(R.drawable.favorite_painted)
                    }
                }
            }
        }
    }

    private class Comparator: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.flag == newItem.flag
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