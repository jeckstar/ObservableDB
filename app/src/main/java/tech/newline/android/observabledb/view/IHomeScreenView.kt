package tech.newline.android.observabledb.view

import tech.newline.android.observabledb.vm.Results

interface IHomeScreenView {

    fun updateDBInfo(info: List<String>)

    fun showSearchResults(info: List<String>)

    fun showMessage(result: Results)

}
