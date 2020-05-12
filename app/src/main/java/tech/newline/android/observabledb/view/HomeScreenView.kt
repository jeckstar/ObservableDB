package tech.newline.android.observabledb.view

import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import tech.newline.android.observabledb.R
import tech.newline.android.observabledb.vm.Results

class HomeScreenView(private val activity: FragmentActivity) : IHomeScreenView {

    private val tvDBItems = activity.findViewById<TextView>(R.id.tv_db_items)
    private val tvDBSearchItems = activity.findViewById<TextView>(R.id.tv_db_search_items)

    override fun updateDBInfo(info: List<String>) {
        Observable.fromIterable(info)
            .reduce { acc, velue -> "$acc\n$velue" }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> tvDBItems.text = it }.run {  }
    }

    override fun showSearchResults(result: List<String>) {
        Observable.fromIterable(result)
            .defaultIfEmpty("Empty")
            .reduce { acc, velue -> "$acc\\n$velue" }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> tvDBSearchItems.text = it }.run {  }
    }

    override fun showMessage(result: Results) {
        when (result) {
            Results.SUCCESS -> showSuccessMessage()
            Results.ERROR -> showErrorMessage()
        }
    }

    private fun showSuccessMessage() {
        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage() {
        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
    }
}