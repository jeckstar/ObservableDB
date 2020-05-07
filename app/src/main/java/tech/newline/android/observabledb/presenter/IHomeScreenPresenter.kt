package tech.newline.android.observabledb.presenter

interface IHomeScreenPresenter {

    fun addNewItemToDb()
    fun onDisposableCleared()
    fun onSearchItem(content: String)
    fun deleteFirstFromSearchResult()
}