package ru.veronikarepina.mynewsapp.ui.detailfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import kotlinx.coroutines.launch
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.FragmentDetailBinding
import ru.veronikarepina.mynewsapp.ui.secondfragment.DaoViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val bundleArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DaoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        val article = bundleArgs.argument
        article.let { myArg ->
            dataDetail.text = myArg.publishedAt
            titleDetail.text = myArg.title
            descriptionDetail.text = myArg.description
            imageDetail.load(myArg.urlToImage)
            lifecycleScope.launch{
                val flag = viewModel.getFlag(myArg.title)
                if(flag == 1){
                    favoriteDetail.setImageResource(R.drawable.favorite_painted)
                }
                else {
                    favoriteDetail.setImageResource(R.drawable.favorite_icon)
                }
            }
            favoriteDetail.setOnClickListener {
                lifecycleScope.launch {
                    val flag =  viewModel.getFlag(myArg.title)
                    if (flag == 1){
                        myArg.flag = false
                        viewModel.searchNewToDelete(myArg.title)
                        favoriteDetail.setImageResource(R.drawable.favorite_icon)
                    }else{
                        myArg.flag = true
                        viewModel.addNew(myArg)
                        favoriteDetail.setImageResource(R.drawable.favorite_painted)
                    }
                }
            }
            buttonWeb.setOnClickListener{
                try {
                    Intent().setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(Uri.parse(takeIf {
                            URLUtil.isValidUrl(myArg.url)
                        }?.let {
                                myArg.url
                            }?:"https://google.com"))
                        ?.let {
                            ContextCompat.startActivity(requireContext(), it, null)
                            }
                }
                catch (e: Exception){
                    Toast.makeText(context, "The device doesn't have any browser to view the document!", Toast.LENGTH_SHORT)
                }
            }
        }
        backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}