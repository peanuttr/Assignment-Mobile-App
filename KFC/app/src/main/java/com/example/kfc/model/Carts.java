package com.example.kfc.model;

public class Carts {
    Integer prodId;
    String prodName;
    Integer prodPrice;
    Integer ProdQty;
    Integer prodTotal;

    public Carts(Integer prodId, String prodName,Integer prodPrice,Integer prodQty,Integer prodTotal) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.ProdQty = prodQty;
        this.prodTotal = prodTotal;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Integer getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Integer prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Integer getProdQty() {
        return ProdQty;
    }

    public void setProdQty(Integer prodQty) {
        ProdQty = prodQty;
    }

    public Integer getProdTotal() {
        return prodTotal;
    }

    public void setProdTotal(Integer prodTotal) {
        this.prodTotal = prodTotal;
    }
}
