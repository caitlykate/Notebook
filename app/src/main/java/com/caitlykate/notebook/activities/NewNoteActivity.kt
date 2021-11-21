package com.caitlykate.notebook.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.caitlykate.notebook.R
import com.caitlykate.notebook.databinding.ActivityNewNoteBinding
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.fragments.NoteFragment
import com.caitlykate.notebook.utils.HtmlManager
import com.caitlykate.notebook.utils.MyTouchListener
import com.caitlykate.notebook.utils.TimeManager
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()
        init()
        onClickColorPicker()
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
        } else if (item.itemId == R.id.bold){
            setBoldForSelectedText()
        } else if (item.itemId == R.id.color){
            if (binding.colorPicker.isShown) closeColorPicker() else openColorPicker()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getNote(){
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(){
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }

    private fun onClickColorPicker() = with(binding){
        ibBlack.setOnClickListener{ setColorForSelectedText(R.color.picker_black)}
        ibBlue.setOnClickListener{ setColorForSelectedText(R.color.picker_blue)}
        ibGreen.setOnClickListener{ setColorForSelectedText(R.color.picker_green)}
        ibGrey.setOnClickListener{ setColorForSelectedText(R.color.picker_grey)}
        ibOrange.setOnClickListener{ setColorForSelectedText(R.color.picker_orange)}
        ibRed.setOnClickListener{ setColorForSelectedText(R.color.picker_red)}
    }
    
    private fun fillNote() = with(binding){
        edTitle.setText(note?.title)
        edContent.setText(HtmlManager.getFromHtml(note?.content!!).trim())
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPos = edContent.selectionStart
        val endPos = edContent.selectionEnd

        val styles = edContent.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()){
            edContent.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }

        edContent.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edContent.text.trim()
        edContent.setSelection(startPos )
    }

    private fun setColorForSelectedText(colorId: Int) = with(binding) {
        val startPos = edContent.selectionStart
        val endPos = edContent.selectionEnd

        val styles = edContent.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)

        if (styles.isNotEmpty()){
            edContent.text.removeSpan(styles[0])
        }

        edContent.text.setSpan(ForegroundColorSpan(
            ContextCompat.getColor(this@NewNoteActivity, colorId)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edContent.text.trim()
        edContent.setSelection(startPos )
    }

    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setMainResult(){
        var editState = "new"
        val tempNote = if (note == null) createNewNote() else {
            editState = "update"
            updateNote()
        }
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun updateNote(): NoteItem? = with(binding){
        return note?.copy(
            title = edTitle.text.toString(),
            content = HtmlManager.toHtml(edContent.text),
            time = TimeManager.getCurrentTime()
        )
    }


    private fun createNewNote(): NoteItem{
        return NoteItem(null,
            binding.edTitle.text.toString(),
            HtmlManager.toHtml(binding.edContent.text),
            TimeManager.getCurrentTime(),
            ""
        )
    }
    
    private fun openColorPicker(){
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker(){
        val closeAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        closeAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        binding.colorPicker.startAnimation(closeAnim)
    }

    //колбэк, который ожидает когда нарисуется меню (при выделении текста) и стирает его
    private fun actionMenuCallback(){
        val actionCallback = object : ActionMode.Callback{
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.clear()
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.clear()
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

            }

        }
        binding.edContent.customSelectionActionModeCallback = actionCallback
    }
}

