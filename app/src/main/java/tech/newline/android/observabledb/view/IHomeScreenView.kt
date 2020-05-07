package tech.newline.android.observabledb.view

interface IHomeScreenView {
    fun updateDBInfo(info: List<String>)
    fun showSearchResults(info: List<String>)
    fun showSuccessMessage()
    fun showErrorMessage()
}