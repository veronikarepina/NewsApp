package ru.veronikarepina.mynewsapp.ui.secondfragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.FragmentSecondBinding
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.ui.Listener

class SecondFragment: Fragment(), Listener {
    private lateinit var binding: FragmentSecondBinding
    private val adapter: AdapterFavorite by lazy { AdapterFavorite(this) }
    private val viewModel: DaoViewModel by viewModels()

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
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initRcView() = with(binding){
        secondRecycler.layoutManager = LinearLayoutManager(activity)
        secondRecycler.adapter = adapter
        viewModel.getAll.observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.Main){
                val flag = withContext(Dispatchers.IO){
                    viewModel.checkEmpty()
                }
                if (flag == 0){
                    empty.visibility = View.VISIBLE
                }
                else empty.visibility = View.INVISIBLE
            }
            adapter.submitList(it.asReversed())
        }
    }

    override fun addNewDb(new: Article) {
    }

    override fun delNewDb(new: Article) {
        viewModel.delNew(new)
    }

    override suspend fun checkFlag(title: String?): Int {
        TODO("Not yet implemented")
    }

    override fun onClick(new: Article) {
        val bundle = bundleOf("argument" to new)
        view?.findNavController()?.navigate(R.id.action_tabFragment_to_detailFragment, bundle)
    }

    override fun searchAndDeleteNew(title: String?) {
        TODO("Not yet implemented")
    }
}