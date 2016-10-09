package com.hdcy.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-30.
 */

public class ActivityRoot implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<ActivityContent> content ;

    private int totalElements;

    private int totalPages;

    private boolean last;

    private int size;

    private int number;

    private List<ActivitySort> sort ;

    private int numberOfElements;

    private boolean first;


    public List<ActivitySort> getSort() {
        return sort;
    }

    public void setSort(List<ActivitySort> sort) {
        this.sort = sort;
    }

    public void setContent(List<ActivityContent> content){
        this.content = content;
    }
    public List<ActivityContent> getContent(){
        return this.content;
    }
    public void setTotalElements(int totalElements){
        this.totalElements = totalElements;
    }
    public int getTotalElements(){
        return this.totalElements;
    }
    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }
    public int getTotalPages(){
        return this.totalPages;
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
    public void setFirst(boolean first){
        this.first = first;
    }
    public boolean getFirst(){
        return this.first;
    }

}
