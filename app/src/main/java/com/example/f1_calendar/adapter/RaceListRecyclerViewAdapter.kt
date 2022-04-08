package com.example.f1_calendar.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.f1_calendar.databinding.ListItemEventBinding
import com.example.f1_calendar.databinding.ListItemHeaderBinding
import com.example.f1_calendar.model.ui.race_list.RaceWeekListItem
import com.example.f1_calendar.ui.fragments.race_list.OnEventItemSelectedListener
import com.example.f1_calendar.ui.fragments.race_list.OnHeaderItemSelectedListener

class RaceListRecyclerViewAdapter(
    private var onHeaderItemSelectedListener: OnHeaderItemSelectedListener,
    private val onEventItemSelectedListener: OnEventItemSelectedListener
) :
    ListAdapter<RaceWeekListItem, RecyclerView.ViewHolder>(DiffCallBack()) {

    companion object {
        const val TYPE_UNKNOWN = 0
        const val TYPE_HEADER = 1
        const val TYPE_EVENT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 0) {
            TYPE_UNKNOWN
        } else {
            when (currentList[position]) {
                is RaceWeekListItem.Header -> TYPE_HEADER
                is RaceWeekListItem.Event -> TYPE_EVENT
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> {
                RaceHeaderViewHolder(ListItemHeaderBinding.inflate(layoutInflater, parent, false))
            }
            TYPE_EVENT -> {
                RaceEventViewHolder(ListItemEventBinding.inflate(layoutInflater, parent, false))
            }
            else -> {
                throw IllegalStateException("No ViewHolder defined for type $viewType.")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentData = currentList[position]
        when (holder.itemViewType) {
            TYPE_HEADER -> {
                val viewData = buildRaceViewData(currentData)
                (holder as RaceHeaderViewHolder).bind(data = viewData as RaceWeekListItem.Header)

                onHeaderItemSelectedListener.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener.onHeaderItemSelected(header = viewData)
                    }
                }
            }
            TYPE_EVENT -> {
                val viewData = buildRaceViewData(currentData)
                (holder as RaceEventViewHolder).bind(data = viewData as RaceWeekListItem.Event)

                onEventItemSelectedListener.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener.onEventItemSelected(event = viewData)
                    }
                }
            }
        }
    }

    private fun buildRaceViewData(currentData: RaceWeekListItem?): RaceWeekListItem {
        return when (currentData) {
            is RaceWeekListItem.Header -> currentData
            is RaceWeekListItem.Event -> currentData
            else -> throw IllegalArgumentException("not defined for type $currentData")
        }
    }


    private class DiffCallBack : DiffUtil.ItemCallback<RaceWeekListItem>() {
        override fun areContentsTheSame(
            oldItem: RaceWeekListItem,
            newItem: RaceWeekListItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: RaceWeekListItem,
            newItem: RaceWeekListItem
        ): Boolean {
            return oldItem.getDiffUtilId() == newItem.getDiffUtilId()
        }
    }
}

class RaceHeaderViewHolder(private val binding: ListItemHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: RaceWeekListItem.Header) {
        binding.apply {
            headerEventType.text = data.raceName
            headerCircuitName.text = data.circuitName

            val millis = data.dateTime.toInstant().toEpochMilli()
            headerDate.text = DateUtils.formatDateTime(
                binding.root.context,
                millis,
                DateUtils.FORMAT_SHOW_DATE
            )
            headerTime.text =
                DateUtils.formatDateTime(binding.root.context, millis, DateUtils.FORMAT_SHOW_TIME)
        }
    }

}

class RaceEventViewHolder(private val binding: ListItemEventBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: RaceWeekListItem.Event) {
        binding.apply {
            eventEventType.text = data.eventType

            val millis = data.dateTime.toInstant().toEpochMilli()
            eventDate.text = DateUtils.formatDateTime(
                binding.root.context,
                millis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_ALL
            )
            eventTime.text =
                DateUtils.formatDateTime(binding.root.context, millis, DateUtils.FORMAT_SHOW_TIME)
        }
    }
}