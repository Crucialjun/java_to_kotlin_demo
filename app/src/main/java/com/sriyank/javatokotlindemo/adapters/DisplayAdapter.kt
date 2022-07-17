package com.sriyank.javatokotlindemo.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.sriyank.javatokotlindemo.adapters.DisplayAdapter.MyViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sriyank.javatokotlindemo.R
import android.content.Intent
import android.net.Uri
import android.view.View
import com.sriyank.javatokotlindemo.app.toast
import com.sriyank.javatokotlindemo.models.Repository
import io.realm.Realm
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.txvName

class DisplayAdapter(context: Context, repositoryList: List<Repository>) :
    RecyclerView.Adapter<MyViewHolder>() {
    private var repositoryList: List<Repository>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = repositoryList[position]
        holder.setData(current, position)
    }

    override fun getItemCount(): Int = repositoryList.size


    fun swap(datalist: List<Repository>) {
        if (datalist.isEmpty()) context.toast("No Items Found")
        repositoryList = datalist
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemPosition = 0
        private var current: Repository? = null

        fun setData(current: Repository?, position: Int) {
            current?.let {
                itemView.txvName.text = current.name
                itemView.txvLanguage.text = current.language
                itemView.txvForks.text = current.forks.toString()
                itemView.txvWatchers.text = current.watchers.toString()
                itemView.txvStars.text = current.stars.toString()

            }
            this.itemPosition = position
            this.current = current

        }

        private fun bookmarkRepository(current: Repository?) {
            current?.let {
                val realm = Realm.getDefaultInstance()
                realm.executeTransactionAsync(
                    { innerRealm -> innerRealm.copyToRealmOrUpdate(current) },
                    { context.toast("Bookmarked Successfully") })
                { context.toast("Error occured") }
            }
        }

        init {

            itemView.imgBookmark.setOnClickListener { bookmarkRepository(current) }
            itemView.setOnClickListener {
                current?.let{
                    val url = current!!.htmlUrl
                    val webpage = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }

            }
        }
    }

    companion object {
        private val TAG = DisplayAdapter::class.java.simpleName
    }

    init {
        this.repositoryList = repositoryList
        this.context = context
    }
}