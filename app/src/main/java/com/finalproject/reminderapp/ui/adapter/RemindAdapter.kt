package com.finalproject.reminderapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.reminderapp.data.model.Remind
import com.finalproject.reminderapp.databinding.ItemLayoutReminderBinding

class RemindAdapter(
    private var remind: List<Remind>,
    private val onDeleteClick: (Remind) -> Unit,
    private val onClick: (Remind) -> Unit
): RecyclerView.Adapter<RemindAdapter.ReminderViewHolder>() {
    class ReminderViewHolder(val binding: ItemLayoutReminderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutReminderBinding.inflate(inflater,parent,false)
        return ReminderViewHolder(binding)
    }

    override fun getItemCount() = remind.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val remind = remind[position]
        holder.binding.reminder = remind
        holder.binding.ivDelete.setOnClickListener {
            onDeleteClick(remind)
        }
        holder.binding.llReminder.setOnClickListener {
            onClick(remind)
        }
    }


    fun setReminders(reminds:List<Remind>) {
        this.remind = reminds
        notifyDataSetChanged()
    }
}