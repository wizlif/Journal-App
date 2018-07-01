package com.wizlif144.journalapp.ui.main.main.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wizlif144.journalapp.data.enums.VIEW_TYPE;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.databinding.ItemNoNotesBinding;
import com.wizlif144.journalapp.databinding.ItemNoteBinding;
import com.wizlif144.journalapp.ui.base.BaseViewHolder;
import com.wizlif144.journalapp.ui.main.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private final List<Note> mNotesList;
    private final MainActivityPresenter mainActivityPresenter;

    public NotesAdapter(MainActivityPresenter presenter){
        this.mNotesList = new ArrayList<>();
        this.mainActivityPresenter = presenter;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                ItemNoteBinding notesViewBinding = ItemNoteBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new NoteViewHolder(notesViewBinding);
            case 1:
                default:
                ItemNoNotesBinding noNoteBinding = ItemNoNotesBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(noNoteBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if(mNotesList.isEmpty()){
            return 1;
        }else{
            return mNotesList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mNotesList.isEmpty()){
            return VIEW_TYPE.EMPTY.ordinal();
        }else{
            return VIEW_TYPE.NORMAL.ordinal();
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        private final ItemNoNotesBinding mBinding;

        public EmptyViewHolder(ItemNoNotesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
        }

    }

    public class NoteViewHolder extends BaseViewHolder implements NoteItemPresenterListener {

        private final ItemNoteBinding mBinding;

        public NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mBinding.setPresenter(new NoteItemPresenter(mNotesList.get(position),this));
            mBinding.executePendingBindings();
        }


        @Override
        public void onItemSelected() {
            mainActivityPresenter.selectedItem = this.mBinding.getPresenter();
            mainActivityPresenter.getNavigator().onNoteSelected(this.mBinding.getPresenter());
        }
    }

    public void addItems(List<Note> notesList) {
        mNotesList.addAll(notesList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mNotesList.clear();
    }
}
