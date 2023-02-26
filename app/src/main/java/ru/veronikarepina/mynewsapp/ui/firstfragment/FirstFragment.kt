package ru.veronikarepina.mynewsapp.ui.firstfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.FragmentFirstBinding
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.ui.Listener

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private val viewModel: MainViewModel by viewModels()
    private val newsListener = object : Listener{
        override fun addNewDb(new: Article) {
            viewModel.handleFavorites(new)
        }

        override fun delNewDb(new: Article) {
            viewModel.handleFavorites(new)
        }

        override fun onClick(new: Article) {
            navigateToDetailArticle(new)
        }
    }

    private val adapter: Adapter by lazy { Adapter(newsListener) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        viewModel.articleLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.favoriteArticles.observe(viewLifecycleOwner){
            viewModel.handleAllArticle(it)
        }
    }
    private fun initRcView() = with(binding){
        firstRecycler.layoutManager = LinearLayoutManager(activity)
        firstRecycler.adapter = adapter
    }

    private fun navigateToDetailArticle(new: Article){
        val bundle = bundleOf("argument" to new)
        findNavController().navigate(R.id.action_tabFragment_to_detailFragment, bundle)
    }
}