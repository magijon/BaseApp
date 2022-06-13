package com.mgijon.baseapp.example.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mgijon.baseapp.R
import com.mgijon.baseapp.databinding.ItemListCharactersBinding
import com.mgijon.baseapp.example.util.animateTranslationX
import com.mgijon.data.model.Character

class AdapterListCharacter(private val navigateTo: (id: String) -> Unit, private val remove : (id : String) -> Unit) : RecyclerView.Adapter<ViewHolderListCharacter>() {

    private val list: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListCharacter {
        val binding = ItemListCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListCharacter(binding, navigateTo, remove)
    }

    override fun onBindViewHolder(holder: ViewHolderListCharacter, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun updateCharacters(characters: MutableList<Character>?) {
        characters?.map {
            list.add(it)
            notifyItemChanged(list.size - 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacters(characters: MutableList<Character>?) {
        characters?.let {
            list.clear()
            list.addAll(characters)
            notifyDataSetChanged()
        }
    }
}

class ViewHolderListCharacter(private val item: ItemListCharactersBinding, private val navigateTo: (id: String) -> Unit,  private val remove: (id: String) -> Unit) :
    RecyclerView.ViewHolder(item.root) {
    private var xInitial = 0
    private val durationAnimation = 200

    @SuppressLint("ClickableViewAccessibility")
    fun onBind(characterModelUI: Character, position: Int) {
        item.apply {
            tvName.text = characterModelUI.name
            tvNumber.text = position.toString()
            Glide.with(item.root.context)
                .load(characterModelUI.image)
                .placeholder(R.drawable.iron_man)
                .into(ivCharacter)
        }
        item.itemSelect.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN)
                xInitial = motionEvent.rawX.toInt()
            if (motionEvent.action == MotionEvent.ACTION_UP)
                if (xInitial > motionEvent.rawX.toInt())
                    showRemoveButton()
                else if (xInitial < motionEvent.rawX.toInt())
                    hideRemoveButton()
                else
                    navigateTo.invoke(characterModelUI.id)
            true
        }
        item.btnRemove.setOnClickListener {
            remove.invoke(characterModelUI.id)
        }
    }

    private fun hideRemoveButton() {
        item.itemSelect.animateTranslationX(durationAnimation)
        item.btnRemove.animateTranslationX(durationAnimation)
    }

    private fun showRemoveButton() {
        item.itemSelect.animateTranslationX(durationAnimation, item.btnRemove.layoutParams.width * (-1))
        item.btnRemove.animateTranslationX(durationAnimation, item.btnRemove.layoutParams.width * (-1))
    }
}