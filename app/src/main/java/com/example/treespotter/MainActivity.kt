package com.example.treespotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.util.Date

private const val  TAG = "Main_Activity"

class MainActivity : AppCompatActivity() {

    val CURRENT_FRAGMENT_BUNDLE_KEY = "current fragment bundle key"
    var currentFragmentTag = "MAP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragmentTag = savedInstanceState?.getString(CURRENT_FRAGMENT_BUNDLE_KEY) ?: "MAP"
        showFragment(currentFragmentTag)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.show_map -> { showFragment("MAP"); true }
                R.id.show_list -> { showFragment("LIST"); true }
                else -> false
            }
        }


//        val db = Firebase.firestore

//        val tree = mapOf("name" to "Pine", "dateSpotted" to Date())
//
//        db.collection("trees").add(tree)

//        db.collection("trees")
//            .whereEqualTo("name", "Pine")
//            .orderBy("dateSpotted", Query.Direction.DESCENDING)
//            .get()
//            .addOnSuccessListener { treeCollection ->
//                val trees = treeCollection.toObjects(Tree::class.java)
//                for (tree in trees) {
//                    val name = tree.name
//                    val dateSpotted = tree.dateSpotted
//                    Log.d(TAG, "A pine tree: $name, $dateSpotted")
//                }
//        }
//            .addOnFailureListener { error ->
//                Log.e(TAG, "Error querying trees", error)
//            }
//
//        val palm = Tree("Palm", Date())
//        db.collection("trees").add(palm)
//
//        db.collection("trees").addSnapshotListener { treeDocuments, error ->
//            if (error != null) {
//                Log.e(TAG, "Error connecting to tree collection", error)
//            }
//
//            if (treeDocuments != null) {
//                for(treeDoc in treeDocuments) {
//                    val name = treeDoc["name"]
//                    val dateSpotted = treeDoc["dateSpotted"]
//                    Log.d(TAG, "$name, $dateSpotted")
//                }
//            }
//        }
    }
    private fun showFragment(tag: String) {

        currentFragmentTag = tag

        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            val transaction = supportFragmentManager.beginTransaction()
            Log.d(TAG, "attempting to show map")
            when (tag) {
                "MAP" -> transaction.replace(R.id.fragmentContainerView, TreeMapFragment.newInstance(), "MAP")
                "LIST" -> transaction.replace(R.id.fragmentContainerView, TreeListFragment.newInstance(), "LIST")
            }
            transaction.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_BUNDLE_KEY, currentFragmentTag)
    }

}