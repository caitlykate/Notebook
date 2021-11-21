package com.caitlykate.notebook.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.caitlykate.notebook.activities.ShopListActivity
import com.caitlykate.notebook.adapters.ListAdapter
import com.caitlykate.notebook.databinding.FragmentShopListNamesBinding
import com.caitlykate.notebook.dialogs.DeleteDialog
import com.caitlykate.notebook.dialogs.NewListDialog
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.entities.ShoppingListName
import com.caitlykate.notebook.utils.TimeManager
import com.caitlykate.notebook.viewmodel.MainViewModel
import com.caitlykate.notebook.viewmodel.factory

class ShopListNamesFragment : BaseFragment(), ListAdapter.Listener{

    private lateinit var binding: FragmentShopListNamesBinding
    private val mainViewModel: MainViewModel by activityViewModels { factory() }
//    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    lateinit var adapter: ListAdapter

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object: NewListDialog.Listener{
            override fun onClick(name: String) {
                Log.d("MyLog", "onClickNew 1")
                val shopListName = ShoppingListName(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,0, ""
                )
                Log.d("MyLog", "onClickNew 2: $shopListName")
                mainViewModel.insertShopListName(shopListName)
                Log.d("MyLog", "onClickNew 3: $shopListName")
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ListAdapter(this@ShopListNamesFragment)
        rcView.adapter = adapter
    }

    //viewLifecycleOwner привязан к жизненному циклу интерфейса фрагмента (onCreateView-onDestroyView)
    //если передать this, то данные будут приходить даже тогда, когда интерфейса не существует, может привести к крашу приложения
    //при объявлении в activity можно передать this
    private fun observer(){
        mainViewModel.allListsNames.observe(viewLifecycleOwner, {
            Log.d("MyLog", "observer 1: $it")
            adapter.submitList(it)
            Log.d("MyLog", "observer 2: $it")
        })
    }
/*
    private fun onEditResult(){
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if (editState == "update"){
                    mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                } else {
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                }

            }
        }
    }
*/

    companion object {
        //делаем синглтон, если вызовем newInstance, когда уже есть инстанция, то вызовет ее
        fun newInstance() = ShopListNamesFragment()

    }

    override fun deleteItem(id: Int) {
       DeleteDialog.showDialog(context as AppCompatActivity, object: DeleteDialog.Listener{
           override fun onClick() {
               mainViewModel.deleteShopListName(id)
           }
       })
    }

    override fun onClickItem(shoppingListName: ShoppingListName) {
        val i = Intent(activity, ShopListActivity::class.java).apply {
            putExtra(ShopListActivity.SHOP_LIST_NAME, shoppingListName)
        }
        startActivity(i)

    }


}