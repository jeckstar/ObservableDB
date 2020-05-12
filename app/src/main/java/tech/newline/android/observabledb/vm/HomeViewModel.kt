package tech.newline.android.observabledb.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tech.newline.android.domain.*
import tech.newline.android.observabledb.vm.Results.ERROR
import tech.newline.android.observabledb.vm.Results.SUCCESS
import kotlin.random.Random

class HomeViewModel(
    private val addItemUseCase: IAddItemUseCase,
    private val observeAllItemsFacade: IObserveAllItemsFacade,
    private val getItemUseCase: IGetItemUseCase,
    private val deleteItemUseCase: IDeleteItemUseCase
) : ViewModel(), IHomeViewModel {

    private val _itemsDMContent = MutableLiveData<List<String>>()
    override val itemsDMContent: LiveData<List<String>> get() = _itemsDMContent

    private val _searchResults = MutableLiveData<List<String>>()
    override val searchResults: LiveData<List<String>> get() = _searchResults

    private val _resultState = MutableLiveData<Results>()
    override val resultState: LiveData<Results> get() = _resultState

    private val compositeDisposable = CompositeDisposable()
    private val itemsContent = mutableListOf<ItemDto>()
    private var firstSearchResultId: Int = EMPTY_ID

    init {
        observeAllItemsFacade
            .observeItems()
            .doOnNext { it ->
                _searchResults.postValue(emptyList())
            }
            .map { items ->  items.map { it.content } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(_itemsDMContent::postValue)
            .run(compositeDisposable::add)
    }

    override fun addNewItemToDb() {
        val id = Random.nextInt(0, Int.MAX_VALUE)
        addItemUseCase.add(
            ItemDto(id, id.toString())
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _resultState.value = SUCCESS },
                {  _resultState.value = ERROR  }
            )
            .run(compositeDisposable::add)
    }

    override fun onSearchItem(content: String) {
        if (content.isEmpty()) {
            _searchResults.postValue(emptyList())
            firstSearchResultId = EMPTY_ID
        } else {
            Observable
                .fromIterable(itemsContent)
                .filter { it.content.length >= content.length }
                .filter { it.content.substring(0, content.length).contains(content) }
                .toList()
                .flatMap { it ->
                    if (it.isNotEmpty()) firstSearchResultId = it.first().id
                    return@flatMap Observable.fromIterable(it).map { it.content }.toList()
                }
                .subscribe(_searchResults::postValue)
                .run(compositeDisposable::add)
        }
    }

    override fun deleteFirstFromSearchResult() {
        if (firstSearchResultId != EMPTY_ID) {
            getItemUseCase
                .getById(firstSearchResultId)
                .toSingle()
                .flatMapCompletable { it ->
                    deleteItemUseCase.delete(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _resultState.postValue(SUCCESS) },
                    { _resultState.postValue(ERROR) }
                )
                .run(compositeDisposable::add)
        } else _resultState.postValue(ERROR)
    }

    override fun onCleared() {
        this.compositeDisposable.clear()
    }

    private companion object {
        const val EMPTY_ID = -1
    }
}