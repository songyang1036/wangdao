package model;

import java.util.List;

public class POrder {
    private List<CartList> cartList;

    public List<CartList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartList> cartList) {
        this.cartList = cartList;
    }

    @Override
    public String toString() {
        return "POrder{" +
                "cartList=" + cartList +
                '}';
    }
}
