package model;

public class OrderPage {
    private int currentPage;

    private int pagesize;

    private int state;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderPage{" +
                "currentPage=" + currentPage +
                ", pagesize=" + pagesize +
                ", state=" + state +
                '}';
    }
}
