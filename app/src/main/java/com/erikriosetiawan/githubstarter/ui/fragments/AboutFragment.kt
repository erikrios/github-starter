package com.erikriosetiawan.githubstarter.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.erikriosetiawan.githubstarter.R
import com.erikriosetiawan.githubstarter.databinding.FragmentAboutBinding
import com.erikriosetiawan.githubstarter.models.User
import com.erikriosetiawan.githubstarter.ui.viewmodels.MainViewModel
import com.erikriosetiawan.githubstarter.ui.viewstate.UserViewState

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.apply {
            val username = getString(R.string.my_github_username)
            getUserDetails(username)
            userViewState.observe(viewLifecycleOwner, this@AboutFragment::handleState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(viewState: UserViewState) {
        handleLoading(viewState.loading)
        viewState.exception?.let { handleError(it) }
        viewState.user?.let { handleSuccess(it) }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.appBarLayout?.visibility = View.INVISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.appBarLayout?.visibility = View.VISIBLE
        }
    }

    private fun handleError(exception: Exception) =
        Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()

    private fun handleSuccess(user: User) = handleView(user)

    private fun openInBrowser(user: User) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
        startActivity(intent)
    }

    private fun share(user: User) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.share_message, user.name, user.htmlUrl)
        )
        intent.type = "text/plain"
        startActivity(intent)
    }

    private fun handleView(user: User) {
        binding?.apply {
            Glide.with(requireContext()).load(user.avatarUrl).into(imgAvatar)
            user.company?.let { tvCompany.text = it }
            user.location?.let { tvLocation.text = it }
            tvUsername.text = user.username
            tvFollowers.text = user.followers.toString()
            tvRepository.text = user.publicRepositories.toString()
            tvFollowing.text = user.following.toString()

            fabShare.setOnClickListener {
                share(user)
            }

            fabOpenInBrowser.setOnClickListener {
                openInBrowser(user)
            }

            toolbar.apply {
                title = user.name
                navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }
}