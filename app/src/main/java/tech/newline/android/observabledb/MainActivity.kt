package tech.newline.android.observabledb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.newline.android.domain.*
import tech.newline.android.observabledb.presenter.HomeScreenPresenter
import tech.newline.android.observabledb.presenter.IHomeScreenPresenter
import tech.newline.android.observabledb.view.HomeScreenView
import tech.newline.android.persistence.ItemRepositoryProvider

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: IHomeScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = HomeScreenPresenter(
            HomeScreenView(
                this,
                { presenter.addNewItemToDb() },
                { presenter.onSearchItem(it) },
                { presenter.deleteFirstFromSearchResult() }
            ),
            AddItemUseCase(ItemRepositoryProvider.getInstance(applicationContext)),
            ObserveAllItemsFacade(ItemRepositoryProvider.getInstance(applicationContext)),
            GetItemUseCase(ItemRepositoryProvider.getInstance(applicationContext)),
            DeleteItemUseCase(ItemRepositoryProvider.getInstance(applicationContext))
        )

    }

    override fun onDestroy() {
        presenter.onDisposableCleared()
        super.onDestroy()
    }
}
