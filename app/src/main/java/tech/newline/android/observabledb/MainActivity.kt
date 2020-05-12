package tech.newline.android.observabledb

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import tech.newline.android.domain.AddItemUseCase
import tech.newline.android.domain.DeleteItemUseCase
import tech.newline.android.domain.GetItemUseCase
import tech.newline.android.domain.ObserveAllItemsFacade
import tech.newline.android.observabledb.view.HomeScreenView
import tech.newline.android.observabledb.view.IHomeScreenView
import tech.newline.android.observabledb.vm.HomeViewModel
import tech.newline.android.observabledb.vm.HomeViewModelFactory
import tech.newline.android.persistence.ItemRepositoryProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: HomeViewModel
    private lateinit var view: IHomeScreenView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = HomeViewModelFactory(
            AddItemUseCase(ItemRepositoryProvider.getInstance(applicationContext)),
            ObserveAllItemsFacade(ItemRepositoryProvider.getInstance(applicationContext)),
            GetItemUseCase(ItemRepositoryProvider.getInstance(applicationContext)),
            DeleteItemUseCase(ItemRepositoryProvider.getInstance(applicationContext))
        )
        vm = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        view = HomeScreenView(this)

        btn_add_new_item.setOnClickListener { vm.addNewItemToDb() }
        btn_remove_first_item.setOnClickListener { vm.deleteFirstFromSearchResult() }
        et_search_item.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                vm.onSearchItem(editable.toString())
            }
        })

        vm.itemsDMContent.observe(this, Observer(view::updateDBInfo))
        vm.searchResults.observe(this, Observer(view::showSearchResults))
        vm.resultState.observe(this, Observer(view::showMessage))
    }

}
