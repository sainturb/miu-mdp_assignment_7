package miu.edu.gardenjournal.ui.home

import androidx.core.widget.ListViewAutoScrollHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _title = MutableLiveData<String>().apply {
        value = "Mobile Programming Development - Assignment 7"
    }
    private val _text = MutableLiveData<String>().apply {
        value = "In this assignment, I developed plant Journal App for keep plant details.\n\n" +
                "The app consists of 3 Fragments, Home, Garden, Plant.\n\n" +
                "Home - page has details about the application.\n\n" +
                "Garden - List of plants, when clicked on activity jumps to Plant Fragment.\n\n" +
                "Plant - Detailed information will be shown to user.\n\n"
    }
    val title: LiveData<String> = _title
    val text: LiveData<String> = _text
}