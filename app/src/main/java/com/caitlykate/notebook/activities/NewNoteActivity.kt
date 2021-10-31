package com.caitlykate.notebook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.caitlykate.notebook.R
import com.caitlykate.notebook.databinding.ActivityNewNoteBinding
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.fragments.NoteFragment
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityNewNoteBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save){
            setMainResult()
        } else if (item.itemId == R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setMainResult(){
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, createNewNote() )
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun getCurrentTime(): String{
        val formatter = SimpleDateFormat("hh:mm:ss yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time )
    }
    
    private fun createNewNote(): NoteItem{
        return NoteItem(null,
            binding.edTitle.text.toString(),
            binding.edContent.text.toString(),
            getCurrentTime(),
            ""
        )
    }
}

