package app.siphondsp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.siphondsp.databinding.FragmentLibraryLoadErrorBinding
import app.siphondsp.utils.extensions.ContextExtensions.openPlayStoreApp

class LibraryLoadErrorFragment : Fragment() {
    private lateinit var binding: FragmentLibraryLoadErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLibraryLoadErrorBinding.inflate(layoutInflater, container, false)
        binding.rootlessNotice.setOnClickListener {
            requireContext().openPlayStoreApp("app.siphondsp")
        }
        return binding.root
    }

    companion object {
        fun newInstance(): LibraryLoadErrorFragment {
            return LibraryLoadErrorFragment()
        }
    }
}