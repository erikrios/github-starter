package com.erikriosetiawan.githubstarter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.githubstarter.R
import com.erikriosetiawan.githubstarter.adapters.UserAdapter
import com.erikriosetiawan.githubstarter.databinding.FragmentDashboardBinding
import com.erikriosetiawan.githubstarter.models.User

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding

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
        setRecyclerView(generateUsers())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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

    private fun generateUsers(): List<User> {
        val users = mutableListOf<User>()

        for (i in 0..50) {
            val user = User(
                username = "username $i",
                htmlUrl = "https://github.com/erikrios",
                id = i.toLong(),
                avatarUrl = "https://avatars.githubusercontent.com/u/42199285?v=4"
            )
            users.add(user)
        }

        return users
    }
}