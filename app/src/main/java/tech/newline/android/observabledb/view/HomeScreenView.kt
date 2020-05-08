package tech.newline.android.observabledb.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import tech.newline.android.observabledb.R

class HomeScreenView(
    private val activity: FragmentActivity,
    onAddItem: () -> Unit,
    onSearchItem: (String) -> Unit,
    onDeleteFirstSearchItem: () -> Unit
) : IHomeScreenView {

    private val tvDBItems = activity.findViewById<TextView>(R.id.tv_db_items)
    private val tvDBSearchItems = activity.findViewById<TextView>(R.id.tv_db_search_items)
    private val btnRemoveFromDB = activity.findViewById<Button>(R.id.btn_remove_first_item)
    private val btnAddToDB = activity.findViewById<Button>(R.id.btn_add_new_item)
    private val etSearchItem = activity.findViewById<EditText>(R.id.et_search_item)

    init {
        btnAddToDB.setOnClickListener { onAddItem() }
        btnRemoveFromDB.setOnClickListener { onDeleteFirstSearchItem() }
        etSearchItem.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(editable: Editable) {
                    onSearchItem(editable.toString())
                }
            })
    }

    override fun updateDBInfo(info: List<String>) {
        Observable.fromIterable(info)
            .reduce { acc, velue -> "$acc\n$velue" }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> tvDBItems.text = it }.run {  }
    }

    override fun showSearchResults(result: List<String>) {
        Observable.fromIterable(result)
            .defaultIfEmpty("Empty")
            .reduce { acc, velue -> "$acc\n$velue" }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> tvDBSearchItems.text = it }.run {  }
    }

    override fun showSuccessMessage() {
        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage() {
        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
    }
}