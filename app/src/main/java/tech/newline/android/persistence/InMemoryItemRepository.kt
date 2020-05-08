package tech.newline.android.persistence

import io.reactivex.*
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.subjects.PublishSubject
import tech.newline.android.domain.ItemDto
import tech.newline.android.domain.ItemsRepository


class InMemoryItemRepository : ItemsRepository {

    private val store = HashMap<Int, ItemDto>() //ObservableMap
    var source = PublishSubject.create<List<ItemDto>>()

    override fun getById(id: Int) =
        Maybe.fromCallable<ItemDto> { store.get(key = id) }


    override fun observeAll(): Flowable<List<ItemDto>> {
        source.onNext(store.values.toList())
        return source.toFlowable(LATEST)
    }


    override fun add(dto: ItemDto): Completable {
        store[dto.id] = dto
        return Completable
            .fromObservable<List<ItemDto>> {
                source
                    .onNext(store.values.toList())
            }
    }

    override fun update(dto: ItemDto): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(dto: ItemDto): Completable {
        return Observable
            .just(store.remove(dto.id))
            .flatMapCompletable { CompletableSource { source.onNext(store.values.toList()) } }
    }

}