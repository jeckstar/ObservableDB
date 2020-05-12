package tech.newline.android.observabledb.vm

import androidx.lifecycle.LiveData

interface IHomeViewModel {

    val itemsDMContent: LiveData<List<String>>

    val searchResults: LiveData<List<String>>

    val resultState: LiveData<Results>

    fun addNewItemToDb()

    fun onSearchItem(content: String)

    fun deleteFirstFromSearchResult()
}
