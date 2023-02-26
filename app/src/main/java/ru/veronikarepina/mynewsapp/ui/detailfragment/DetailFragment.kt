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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.FragmentDetailBinding
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.utils.DateUtils

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val bundleArgs: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setDetail(bundleArgs.argument)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)

        favoriteDetail.setOnClickListener {
            viewModel.handleFAvorite()
        }
        backButton.setOnClickListener{
            findNavController().popBackStack()
        }
        setupObservers()
        buttonWeb.setOnClickListener{
            try {
                Intent().setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse(takeIf {
                        URLUtil.isValidUrl(bundleArgs.argument.url)
                    }?.let {
                        bundleArgs.argument.url
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
    private fun setupObservers(){
        with(viewModel){
            detailArticle.observe(viewLifecycleOwner){
                showInfo(it)
            }
        }
    }
    private fun showInfo(new: Article){
        with(binding){
            dataDetail.text = new.publishedAt?.let { DateUtils.toDefaultDate(it) }
            titleDetail.text = new.title
            descriptionDetail.text = new.description
            imageDetail.load(new.urlToImage)
            favoriteDetail.setImageResource(
                if (new.flag){
                    R.drawable.favorite_painted
                } else{
                    R.drawable.favorite_icon
                }
            )
        }
    }
}