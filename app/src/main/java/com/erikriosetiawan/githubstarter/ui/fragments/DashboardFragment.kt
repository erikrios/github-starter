package com.erikriosetiawan.githubstarter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.githubstarter.R
import com.erikriosetiawan.githubstarter.adapters.UserAdapter
import com.erikriosetiawan.githubstarter.databinding.FragmentDashboardBinding
import com.erikriosetiawan.githubstarter.models.User
import com.erikriosetiawan.githubstarter.ui.viewmodels.MainViewModel
import com.erikriosetiawan.githubstarter.ui.viewstate.UsersViewState

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        mainViewModel.usersViewState.observe(
            viewLifecycleOwner,
            this@DashboardFragment::handleState
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(viewState: UsersViewState) {
        handleLoading(viewState.loading)
        viewState.exception?.let { handleError(it) }
        viewState.users?.let { handleSuccess(it) }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding?.apply {
            if (isLoading) {
                rvUsers.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                rvUsers.visibility = View.VISIBLE
            }
        }
    }

    private fun handleError(exception: Exception) =
        Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()

    private fun handleSuccess(users: List<User>) = setRecyclerView(users)

    private fun setRecyclerView(users: List<User>) {
        val adapter = UserAdapter(users)
        adapter.setOnItemClickListener { user ->
            val action = DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(user)
            findNavController().navigate(action)
        }
        binding?.rvUsers?.adapter = adapter
    }

    private fun handleToolbar() {
        val menu = binding?.toolbar?.menu
        menu?.findItem(R.id.item_about)?.apply {
            setOnMenuItemClickListener {
                val action = DashboardFragmentDirections.actionDashboardFragmentToAboutFragment()
                findNavController().navigate(action)
                return@setOnMenuItemClickListener true
            }
        }
    }
}