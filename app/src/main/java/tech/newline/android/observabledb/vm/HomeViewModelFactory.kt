package tech.newline.android.observabledb.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.newline.android.domain.*


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val addItemUseCase: AddItemUseCase,
    private val observeAllItemsFacade: ObserveAllItemsFacade,
    private val getItemUseCase: GetItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == HomeViewModel::class.java) {
            return HomeViewModel(
                    addItemUseCase,
                    observeAllItemsFacade,
                    getItemUseCase,
                    deleteItemUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    /**or
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return modelClass.getConstructor(
                IAddItemUseCase::class.java,
                IObserveAllItemsFacade::class.java,
                IGetItemUseCase::class.java,
                IDeleteItemUseCase::class.java
            ).newInstance(
                addItemUseCase,
                observeAllItemsFacade,
                getItemUseCase,
                deleteItemUseCase
            )
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    */
}