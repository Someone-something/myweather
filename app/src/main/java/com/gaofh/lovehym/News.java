package com.gaofh.lovehym;

public class News {
    String title;
    String content;
    public News(String title,String content){
        this.title=title;
        this.content=content;
    }
    public News(){

    }

    public String getTitle() {
        if (title == null) {
            return "暂时没有内容，看看其他的吧";
        }
        return title;
    }

    public String getContent(){
        if (content==null){
            return "暂时没有内容，看看其他的吧";
        }
        return content;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setContent(String content){
        this.content=content;
    }

}
