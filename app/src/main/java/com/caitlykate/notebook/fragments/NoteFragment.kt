package com.caitlykate.notebook.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.caitlykate.notebook.NoteAdapter
import com.caitlykate.notebook.R
import com.caitlykate.notebook.activities.NewNoteActivity
import com.caitlykate.notebook.databinding.FragmentNoteBinding
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.viewmodel.MainViewModel
import com.caitlykate.notebook.viewmodel.factory

class NoteFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteBinding
    private val mainViewModel: MainViewModel by activityViewModels { factory() }
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    lateinit var adapter: NoteAdapter

    override fun onClickNew() {
        editLauncher.launch(Intent(activity,NewNoteActivity::class.java))      //startActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteAdapter()
        rcViewNote.adapter = adapter
    }

    //viewLifecycleOwner привязан к жизненному циклу интерфейса фрагмента (onCreateView-onDestroyView)
    //если передать this, то данные будут приходить даже тогда, когда интерфейса не существует, может привести к крашу приложения
    //при объявлении в activity можно передать this
    private fun observer(){
        mainViewModel.allNotes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private  fun onEditResult(){
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
            }
        }
    }

    companion object {
        //делаем синглтон, если вызовем newInstance, когда уже есть инстанция, то вызовет ее
        fun newInstance() = NoteFragment()
        const val NEW_NOTE_KEY = "NEW_NOTE_KEY"
    }


}