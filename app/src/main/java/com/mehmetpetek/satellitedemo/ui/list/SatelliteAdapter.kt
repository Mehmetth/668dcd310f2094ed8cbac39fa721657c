package com.mehmetpetek.satellitedemo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetpetek.satellitedemo.app.R
import com.mehmetpetek.satellitedemo.app.databinding.SatelleiteRvItemBinding
import com.mehmetpetek.satellitedemo.common.extensions.visibleInvisibleIf
import com.mehmetpetek.satellitedemo.data.local.model.Satellite

class SatelliteAdapter(
    var satelliteList: List<Satellite>,
    private val onSatelliteClickListener: OnSatelliteClickListener
) : RecyclerView.Adapter<SatelliteAdapter.SatelliteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        return SatelliteViewHolder(
            SatelleiteRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private var selectedPosition = 0
    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {
        holder.bindData(
            satelliteList[position],
            satelliteList.size,
            object : OnSatelliteClickListener {
                override fun onSelectSatelliteClicked(satellite: Satellite) {
                    selectedPosition = holder.absoluteAdapterPosition
                    onSatelliteClickListener.onSelectSatelliteClicked(satellite)
                    notifyDataSetChanged()
                }
            })
    }


    override fun getItemCount(): Int {
        return satelliteList.size
    }

    fun filterList(filteredList: List<Satellite>) {
        satelliteList = filteredList
        notifyDataSetChanged()
    }

    class SatelliteViewHolder(
        private val binding: SatelleiteRvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            satellite: Satellite, size: Int, onSatelliteClickListener: OnSatelliteClickListener
        ) {
            binding.tvSatalite.text = satellite.name
            if (satellite.active == true) {
                binding.tvStatus.text = binding.tvStatus.context.getString(R.string.active)
                binding.ivIcon.setImageResource(R.drawable.ic_active)
                binding.clText.alpha = 1f
            } else {
                binding.tvStatus.text = binding.tvStatus.context.getString(R.string.passive)
                binding.ivIcon.setImageResource(R.drawable.ic_passive)
                binding.clText.alpha = 0.5f
            }

            binding.root.setOnClickListener {
                onSatelliteClickListener.onSelectSatelliteClicked(satellite)
            }

            binding.divider.visibleInvisibleIf(absoluteAdapterPosition != size - 1)
        }
    }

    interface OnSatelliteClickListener {
        fun onSelectSatelliteClicked(satellite: Satellite)
    }
}