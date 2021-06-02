package ar.com.jdodevelopment.rickandmorty.ui.characterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ar.com.jdodevelopment.rickandmorty.data.model.Character
import ar.com.jdodevelopment.rickandmorty.databinding.CharacterItemBinding


class CharacterAdapter(
    private val onItemClickListener: (view: View, item: Character) -> Unit
) : PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(
    CharacterDiffCallback()
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = CharacterItemBinding.inflate(layoutInflater, viewGroup, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            viewHolder.bindTo(item, onItemClickListener)
        } else {
            TODO("Bind Place holder")
        }
    }


    /**
     * ViewHolder
     */
    class CharacterViewHolder(private val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: Character, onItemClickListener: (view: View, item: Character) -> Unit) {
            binding.setObject(item)
            binding.root.setOnClickListener { view: View ->
                onItemClickListener(view, item)
            }
        }
    }

    /**
     * Diff Callback
     */
    class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {

        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return newItem.id == oldItem.id
        }

    }
}
