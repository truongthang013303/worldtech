package com.example.demo1.dto;

import java.util.ArrayList;
import java.util.Collection;

public class DataTable {
    public Integer draw;
    public Integer recordsTotal;
    public Integer recordsFiltered;
    public Collection data;

    public DataTable(Integer draw, Integer recordsTotal, Integer recordsFiltered, Collection data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public DataTable() {
        this.draw = 0;
        this.recordsFiltered=0;
        this.recordsTotal=0;
        this.data=new ArrayList<>();
    }

    public Collection getData() {
        return data;
    }

    public void setData(Collection data) {
        this.data = data;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
