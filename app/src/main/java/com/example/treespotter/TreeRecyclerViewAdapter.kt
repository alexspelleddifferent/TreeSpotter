package com.example.treespotter

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

private const val TAG = "Tree_Recycler_Adapter"

class TreeRecyclerViewAdapter(var trees: List<Tree>, val heartTreeListener: (Tree, Boolean) -> Unit) :
    RecyclerView.Adapter<TreeRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        // i had to change the way this function worked from your example. when i had the code as it was
        // what would happen was if i clicked on one heart it updated the favorite status of multiple other trees
        // this way of accomplishing this goal got around this error

        private val treeNameTextView: TextView = view.findViewById(R.id.tree_name)
        private val dateSpottedTextView: TextView = view.findViewById(R.id.date_spotted)
        private val heartCheckbox: CheckBox = view.findViewById(R.id.heart_check)

        fun bind(tree: Tree) {
            treeNameTextView.text = tree.name
            dateSpottedTextView.text = "${tree.dateSpotted}"

            // making sure that the listener gets reset, i think that mightve been part of issue
            heartCheckbox.setOnCheckedChangeListener(null)

            heartCheckbox.isChecked = tree.favorite

            heartCheckbox.setOnCheckedChangeListener { checkbox, isChecked ->
                    Log.d(TAG, "Setting ${tree.name} favorite $isChecked")
                    heartTreeListener(tree, isChecked)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_tree_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tree = trees[position]
        holder.bind(tree)
    }

    override fun getItemCount(): Int {
        return trees.size
    }
}