package com.finalproject.reminderapp.ui.viewModels

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.model.Remind
import com.finalproject.reminderapp.data.repo.RemindersRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: RemindersRepo
) : ViewModel() {
    fun getReminder(): Flow<List<Remind>> {
        return repo.getRemind()
    }


    fun deleteReminder(remind: Remind) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteRemind(remind)
        }
    }

    fun confirmAndDeleteReminder(context: Context, remind: Remind) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete this reminder?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            deleteReminder(remind)
            showToast(context, "The reminder has been deleted successfully!")
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val deleteConfirmationDialog = builder.create()
        deleteConfirmationDialog.show()
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).remindersRepo
                HomeViewModel(
                    repo = myRepository
                )
            }
        }
    }

}
