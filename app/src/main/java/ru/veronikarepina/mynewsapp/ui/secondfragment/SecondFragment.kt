package ru.veronikarepina.mynewsapp.ui.secondfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.FragmentSecondBinding
import ru.veronikarepina.mynewsapp.model.Article

class SecondFragment: Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val viewModel: DaoViewModel by viewModels()
    private val adapter: AdapterFavorite by lazy {
        AdapterFavorite(
            onClickItemListener = { article ->
                navigateToDetailFragment(article)
            },
            deleteItemFavorite = { article ->
                viewModel.delNew(article)
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        setupObservers()
    }
    private fun setupObservers(){
        with(viewModel){
            favoriteArticles.observe(viewLifecycleOwner){ list ->
                binding.empty.isVisible = list.isNullOrEmpty()
                adapter.submitList(list.asReversed())
            }
        }
    }
    private fun initRcView() {
        with(binding) {
            secondRecycler.layoutManager = LinearLayoutManager(activity)
            secondRecycler.adapter = adapter
        }
    }

    private fun navigateToDetailFragment(new: Article) {
        val bundle = bundleOf("argument" to new)
        findNavController().navigate(R.id.action_tabFragment_to_detailFragment, bundle)
    }

}