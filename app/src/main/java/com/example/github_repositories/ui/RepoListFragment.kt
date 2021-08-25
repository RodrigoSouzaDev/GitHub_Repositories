package com.example.github_repositories.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.github_repositories.R
import com.example.github_repositories.databinding.FragmentRepoListBinding
import com.example.github_repositories.presentation.RepoListFragViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel: RepoListFragViewModel by viewModel()
    private val binding : FragmentRepoListBinding by lazy{ FragmentRepoListBinding.inflate(layoutInflater)}
    private val adapter by lazy { RepoListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as AppCompatActivity).setSupportActionBar(binding.listRepoToolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.recyclerRepos.adapter = adapter

        viewModel.getRepoList("EzequielMessore")

        viewModel.repos.observe(viewLifecycleOwner, {
            when (it){
                RepoListFragViewModel.State.Loading -> {
                    dialog?.show()
                }
                is RepoListFragViewModel.State.Error -> {
                    dialog?.dismiss()
                    createDialog{
                        setMessage(it.error.message)
                    }?.show()
                }
                is RepoListFragViewModel.State.Sucess -> {
                    dialog?.dismiss()
                    adapter.submitList(it.list)
                }
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        val menuItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView : SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepoList(it) }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG,"OnQueryTextChange= $newText")
        return true
    }

    private fun View.hideSoftKeyboard(){
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken,0)
    }

    private fun createDialog(block: MaterialAlertDialogBuilder.() -> Unit = {}): AlertDialog?
    {
        val builder = context?.let { MaterialAlertDialogBuilder(it) }
        builder?.setPositiveButton(android.R.string.ok,null)
        builder?.let { block(it) }
        return builder?.create()
    }

    private fun createProgressDialog(): AlertDialog? {
        return createDialog {
            val padding = context.resources.getDimensionPixelOffset(R.dimen.layout_padding)
            val progressBar = ProgressBar(context)
            progressBar.setPadding(padding,padding,padding,padding)
            setView(progressBar)

            setPositiveButton(null, null)
            setCancelable(false)
        }
    }

    companion object{
        private const val TAG ="TAG"
    }
}

