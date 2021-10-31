package com.caitlykate.notebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caitlykate.notebook.databinding.NoteListItemBinding
import com.caitlykate.notebook.entities.NoteItem

class NoteAdapter: ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {

    //создается
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    //заполняется
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class ItemHolder(view: View): RecyclerView.ViewHolder(view){

        private  val binding = NoteListItemBinding.bind(view)

        fun setData(note: NoteItem) = with(binding){
            tvTitle.text = note.title
            tvText.text = note.content
            tvDate.text = note.time
        }

        companion object{
            //статическая ф-я, экземпляр класса не нужен,
            // будет возвращать инициализированный класс ItemHolder
            fun create(parent: ViewGroup): ItemHolder {       //если мы хотим исп-ть layoutInflater, нужен контекст (из ViewGroup)
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<NoteItem>(){
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return  oldItem == newItem
        }

    }


}