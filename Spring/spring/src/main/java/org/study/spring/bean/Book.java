package org.study.spring.bean;

public class Book {

    private String IBSN;
    private double price;
    private User author;

    public Book(String IBSN, double price, User author) {
        this.IBSN = IBSN;
        this.price = price;
        this.author = author;
    }

    public Book() {
    }

    public String getIBSN() {
        return IBSN;
    }

    public void setIBSN(String IBSN) {
        this.IBSN = IBSN;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "IBSN='" + IBSN + '\'' +
                ", price=" + price +
                ", author=" + author.toString() +
                '}';
    }
}
