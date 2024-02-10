package com.surajpal.a1_notes.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.surajpal.a1_notes.R
import com.surajpal.a1_notes.adapter.NoteAdapter
import com.surajpal.a1_notes.databinding.FragmentHomeBinding
import com.surajpal.a1_notes.model.Note
import com.surajpal.a1_notes.viewmodel.NoteViewModel

class HomeFragment : Fragment() {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
        setupHomeRecyclerView()

        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

    }

    private fun updateUI(notes: List<Note>?) {
        if (notes != null) {
            if (notes.isNotEmpty()) {
                binding.emptyNotesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
            noteAdapter.submitList(notes)
            updateUI(notes)
        }
    }

    private fun searchNotes(query: String) {
        if (query.isBlank()) {
            // If the query is blank, show all notes
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
                noteAdapter.submitList(notes)
                updateUI(notes)
            }
        } else {
            // If there is a search query, perform the search
            noteViewModel.searchNote("%$query%").observe(viewLifecycleOwner) { notes ->
                noteAdapter.submitList(notes)
                updateUI(notes)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val searchItem = menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchNotes(it) }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null
    }
}
