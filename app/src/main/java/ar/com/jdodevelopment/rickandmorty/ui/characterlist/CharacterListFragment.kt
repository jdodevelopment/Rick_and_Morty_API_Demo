package ar.com.jdodevelopment.rickandmorty.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.jdodevelopment.rickandmorty.data.model.Character
import ar.com.jdodevelopment.rickandmorty.databinding.CharacterListFragmentBinding
import ar.com.jdodevelopment.rickandmorty.ui.shared.recyclerview.FooterLoadStateAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var binding: CharacterListFragmentBinding

    private lateinit var pagingDataAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        initListeners()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
//        pagingDataAdapter.refresh()
    }

    /**
     * Adapter
     */
    private fun initAdapter() {
        pagingDataAdapter = CharacterAdapter { _, obj -> openCharacterDetail(obj) }
        pagingDataAdapter.addLoadStateListener { loadState ->
            loadState.source.append.let {
                val emptyList = loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached && pagingDataAdapter.itemCount < 1
                if (emptyList) {
                    showEmptyList()
                } else {
                    hideEmptyList()
                }
            }
            loadState.source.refresh.let {
                if (it is LoadState.Error) {
                    binding.errorMessage.text = it.error.message
                }
                binding.loadingLayout.isVisible = it is LoadState.Loading
                binding.recyclerView.isVisible = it is LoadState.NotLoading
                binding.errorLayout.isVisible = it is LoadState.Error
            }
            (loadState.source.prepend as? LoadState.Error ?: loadState.prepend as? LoadState.Error)?.let {
                Snackbar.make(binding.root, it.error.toString(), Snackbar.LENGTH_LONG).show()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }


    private fun openCharacterDetail(character: Character) {
        val navDirections = CharacterListFragmentDirections.actionOpenDetail(character)
        findNavController().navigate(navDirections)
    }

    /**
     * RecyclerView
     */
    private fun initRecyclerView() {
        binding.recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(context)
            adapter = pagingDataAdapter.withLoadStateFooter(
                FooterLoadStateAdapter { pagingDataAdapter.refresh() }
            )
        }
    }

    /**
     * Listeners
     */
    private fun initListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            pagingDataAdapter.refresh()
        }
    }

    /**
     * Observers
     */
    private fun initObservers() {
        viewModel.getPagingData().observe(viewLifecycleOwner, { handlePagingData(it) })
    }

    private fun handlePagingData(pagingData: PagingData<Character>) {
        pagingDataAdapter.submitData(lifecycle, pagingData)
        pagingDataAdapter.notifyDataSetChanged()
    }

    private fun showEmptyList() {
        binding.emptyListLayout.isVisible = true
//        binding.lottieAnimationEmpty.playAnimation()
    }

    private fun hideEmptyList() {
        binding.emptyListLayout.isVisible = false
    }

}