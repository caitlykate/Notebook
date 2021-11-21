package com.caitlykate.notebook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.caitlykate.notebook.databinding.ActivityShopListBinding
import com.caitlykate.notebook.entities.ShoppingListName
import com.caitlykate.notebook.viewmodel.MainViewModel
import com.caitlykate.notebook.viewmodel.factory

class ShopListActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityShopListBinding
    private lateinit var shoppingListName: ShoppingListName
    private val mainViewModel: MainViewModel by viewModels { factory() }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        shoppingListName = intent.getSerializableExtra(SHOP_LIST_NAME) as ShoppingListName
        binding.textView.text = shoppingListName.name
    }

    companion object{
        const val SHOP_LIST_NAME = "shop_list_name"
    }
}