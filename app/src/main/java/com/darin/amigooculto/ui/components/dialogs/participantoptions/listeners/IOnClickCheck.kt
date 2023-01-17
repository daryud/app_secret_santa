package com.darin.amigooculto.ui.components.dialogs.participantoptions.listeners

import android.widget.CheckBox

interface IOnClickCheck {
    fun onCheck(checkBox: CheckBox, santaNotAllowedId: Int)
    fun onUncheck(checkBox: CheckBox, santaNotAllowedId: Int)
}