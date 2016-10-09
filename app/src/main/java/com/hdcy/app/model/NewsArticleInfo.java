package com.hdcy.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-17.
 */

public class NewsArticleInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<Content> content ;

    private int totalPages;

    private int totalElements;

    private boolean last;

    private int size;

    private int number;

    private int numberOfElements;

    private List<Sort> sort ;

    private boolean first;

    public void setContent(List<Content> content){
        this.content = content;
    }
    public List<Content> getContent(){
        return this.content;
    }
    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }
    public int getTotalPages(){
        return this.totalPages;
    }
    public void setTotalElements(int totalElements){
        this.totalElements = totalElements;
    }
    public int getTotalElements(){
        return this.totalElements;
    }
    public void setLast(boolean last){
        this.last = last;
    }
    public boolean getLast(){
        return this.last;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }
    public void setNumber(int number){
        this.number = number;
    }
    public int getNumber(){
        return this.number;
    }
    public void setNumberOfElements(int numberOfElements){
        this.numberOfElements = numberOfElements;
    }
    public int getNumberOfElements(){
        return this.numberOfElements;
    }
    public void setSort(List<Sort> sort){
        this.sort = sort;
    }
    public List<Sort> getSort(){
        return this.sort;
    }
    public void setFirst(boolean first){
        this.first = first;
    }
    public boolean getFirst(){
        return this.first;
    }

}
