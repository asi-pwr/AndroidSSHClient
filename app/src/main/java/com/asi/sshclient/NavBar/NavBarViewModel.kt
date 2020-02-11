package com.asi.sshclient.NavBar

class NavBarViewModel @Inject constructor(){
    fun changeCurrentAuth(id: Int){
        authService.findAuthById(id)
    }
}