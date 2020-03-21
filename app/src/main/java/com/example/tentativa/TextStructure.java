package com.example.tentativa;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TextStructure {

    private String title = "voidInfo";
    private String body = "voidInfo";
    private String ID = "voidInfo";
    public String EMAIL = "voidInfo";
    public String SIGN = "voidInfo";
    public List<Integer> UPVOTE;

    @Override
    public String toString(){
        return this.title;
    }
    public TextStructure(){

    }
    public TextStructure(String title, String body, String ID, String sign){
        this.ID = ID;
        this.body = body;
        this.title = title;
        this.SIGN = sign;
        this.EMAIL = InfoClass.getAccountEmail();
        UPVOTE = new ArrayList<>();
        UPVOTE.add(0);
        UPVOTE.add(0);
        UPVOTE.add(0);

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
