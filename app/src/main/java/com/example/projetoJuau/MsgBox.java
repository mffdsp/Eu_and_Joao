package com.example.projetoJuau;

import androidx.annotation.NonNull;

public class MsgBox {

    public String DE;
    public String PARA;
    public String TEXTO;
    public String ID;

    public MsgBox(){

    }
    public MsgBox(String DE, String PARA, String TEXTO, String ID){
        this.DE = DE;
        this.PARA = PARA;
        this.TEXTO = TEXTO;
        this.ID = ID;
    }

    @Override
    public String toString(){
        return  "Mensagem de: " +  this.DE;
    }
}
