package com.sneha.mynotes.Interfaces;


import com.sneha.mynotes.models.CheckListItemModel;

public interface CheckListItemInterface {

    public void editCheckListItem(CheckListItemModel objItem);

    public void checkItemClicked(CheckListItemModel objItem, boolean isChecked, int pos);

}
