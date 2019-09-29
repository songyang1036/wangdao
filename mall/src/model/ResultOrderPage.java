package model;

import java.util.List;

public class ResultOrderPage {
    private int total;

    private List<Orders> orders;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "ResultOrderPage{" +
                "total=" + total +
                ", orders=" + orders +
                '}';
    }
}
