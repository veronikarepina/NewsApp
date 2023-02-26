package ru.veronikarepina.mynewsapp.ui.firstfragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.FragmentFirstBinding
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.ui.Listener
import ru.veronikarepina.mynewsapp.ui.secondfragment.DaoViewModel

class FirstFragment : Fragment(), Listener {
    private lateinit var binding: FragmentFirstBinding
    private val adapter: Adapter by lazy { Adapter(this) }
    private val viewModel: MainViewModel by viewModels()
    private val viewModelDao: DaoViewModel by viewModels()
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
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initRcView() = with(binding){
        firstRecycler.layoutManager = LinearLayoutManager(activity)
        firstRecycler.adapter = adapter
        viewModel.viewModelLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it.articles)
        }
    }

    override fun addNewDb(new: Article) {
        viewModelDao.addNew(new)
    }

    override fun delNewDb(new: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun checkFlag(title: String?): Int {
        return viewModelDao.getFlag(title)
    }

    override fun onClick(new: Article) {
        val bundle = bundleOf("argument" to new)
        view?.findNavController()?.navigate(R.id.action_tabFragment_to_detailFragment, bundle)
    }

    override fun searchAndDeleteNew(title: String?) {
        viewModelDao.searchNewToDelete(title)
    }
}