package tech.newline.android.observabledb.presenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tech.newline.android.domain.*
import tech.newline.android.observabledb.view.IHomeScreenView
import kotlin.random.Random

class HomeScreenPresenter(
    private val view: IHomeScreenView,
    private val addItemUseCase: IAddItemUseCase,
    private val observeAllItemsFacade: ObserveAllItemsFacade,
    private val getItemUseCase: IGetItemUseCase,
    private val deleteItemUseCase: IDeleteItemUseCase
) : IHomeScreenPresenter {

    private val compositeDisposable = CompositeDisposable()
    private val itemsContent = mutableListOf<ItemDto>()
    private var firstSearchResultId: Int = EMPTY_ID

    init {
        observeAllItemsFacade
            .observeItems()
            .doOnNext { it ->
                itemsContent.clear()
                itemsContent.addAll(it)
                view.showSearchResults(emptyList())
            }
            .flatMapSingle { it -> Observable.fromIterable(it).map { it.content }.toList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateDBInfo)
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
                { view.showSuccessMessage() },
                { view.showErrorMessage() }
            )
            .run(compositeDisposable::add)
    }

    override fun onSearchItem(content: String) {
        if (content.isEmpty()) {
            view.showSearchResults(emptyList())
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
                .subscribe(view::showSearchResults)
                .run(compositeDisposable::add)
        }
    }

    override fun deleteFirstFromSearchResult() {
        if (firstSearchResultId != EMPTY_ID) {
            getItemUseCase
                .getById(firstSearchResultId)
                .flatMapCompletable {  it -> deleteItemUseCase.delete(it)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { view.showSuccessMessage() },
                    { view.showErrorMessage() }
                )
                .run(compositeDisposable::add)
        }
    }

    override fun onDisposableCleared() {
        this.compositeDisposable.clear()
    }

    private companion object {
        const val EMPTY_ID = -1
    }
}