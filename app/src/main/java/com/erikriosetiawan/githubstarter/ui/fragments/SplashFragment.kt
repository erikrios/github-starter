package com.erikriosetiawan.githubstarter.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.erikriosetiawan.githubstarter.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding
    private var handler: Handler? = null
    private val runnable: Runnable = Runnable {
        val action = SplashFragmentDirections.actionSplashFragmentToDashboardFragment()
        findNavController().navigate(action)
    }

    companion object {
        private const val SPLASH_DELAY = 100L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        setTransparentStatusBar(activity)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(runnable, SPLASH_DELAY)
    }

    override fun onStop() {
        super.onStop()
        setTransparentStatusBar(activity, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
    }

    private fun setTransparentStatusBar(
        activity: FragmentActivity?,
        isTransparent: Boolean = true
    ) {
        val defaultStatusBarColor = activity?.window?.statusBarColor

        if (isTransparent) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else {
            activity?.window?.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                defaultStatusBarColor?.let { statusBarColor = defaultStatusBarColor }
            }
        }
    }
}