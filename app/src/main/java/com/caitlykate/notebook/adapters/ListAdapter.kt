package com.caitlykate.notebook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caitlykate.notebook.R
import com.caitlykate.notebook.databinding.ListNameItemBinding
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.entities.ShoppingListName

class ListAdapter(private val listener: Listener): ListAdapter<ShoppingListName, com.caitlykate.notebook.adapters.ListAdapter.ItemHolder>(
    ItemComparator()
) {

    //создается
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    //заполняется
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View): RecyclerView.ViewHolder(view){

        private  val binding = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem: ShoppingListName, listener: Listener) = with(binding){
            tvTitle.text = shopListNameItem.name
            tvDate.text = shopListNameItem.time
            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }
            ibDelete.setOnClickListener{
                listener.deleteItem(shopListNameItem.id!!)
            }
        }

        companion object{
            //статическая ф-я, экземпляр класса не нужен,
            // будет возвращать инициализированный класс ItemHolder
            fun create(parent: ViewGroup): ItemHolder {       //если мы хотим исп-ть layoutInflater, нужен контекст (из ViewGroup)
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_name_item, parent, false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<ShoppingListName>(){
        override fun areItemsTheSame(oldItem: ShoppingListName, newItem: ShoppingListName): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingListName, newItem: ShoppingListName): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener{
        fun deleteItem(id: Int)
        fun onClickItem(shoppingListName: ShoppingListName)
    }

}