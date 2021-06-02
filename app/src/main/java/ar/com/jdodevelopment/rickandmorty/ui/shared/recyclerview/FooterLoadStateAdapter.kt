package ar.com.jdodevelopment.rickandmorty.ui.shared.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.jdodevelopment.rickandmorty.databinding.FooterLoadStateItemBinding

class FooterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter< FooterLoadStateAdapter.LoadStateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FooterLoadStateItemBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding, retry)
    }


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    class LoadStateViewHolder(
        private val binding: FooterLoadStateItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMessage.text = loadState.error.localizedMessage
            }
            
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.errorMessage.isVisible = loadState !is LoadState.Loading

            Log.d("TAG", "End of pagination" + loadState.endOfPaginationReached.toString())

        }

    }
}
