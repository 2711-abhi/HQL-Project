package com.example;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class App {

    public static void main(String[] args) {

        // Create SessionFactory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Books.class)
                .buildSessionFactory();

        Session session = factory.openSession();

        // -----------------------------
        // Insert Books
        // -----------------------------
        session.beginTransaction();

        Books b1 = new Books("Java Programming", "James Gosling", 2019);
        Books b2 = new Books("Python Basics", "Guido Van Rossum", 2021);
        Books b3 = new Books("Database Systems", "Elmasri", 2018);

        session.save(b1);
        session.save(b2);
        session.save(b3);

        session.getTransaction().commit();

        // -----------------------------
        // Fetch All Books
        // -----------------------------
        session = factory.openSession();
        session.beginTransaction();

        Query<Books> query = session.createQuery("FROM Books", Books.class);

        List<Books> books = query.getResultList();

        System.out.println("All Books in Library:");

        for (Books b : books) {
            System.out.println(b.getId() + " | " + b.getBookName() + " | " + b.getAuthor() + " | " + b.getYear());
        }

        session.getTransaction().commit();

        // -----------------------------
        // Search Books by Author
        // -----------------------------
        session = factory.openSession();
        session.beginTransaction();

        Query<Books> query2 =
                session.createQuery("FROM Books WHERE author='James Gosling'", Books.class);

        List<Books> result = query2.getResultList();

        System.out.println("\nBooks by James Gosling:");

        for (Books b : result) {
            System.out.println(b.getBookName() + " | " + b.getAuthor());
        }

        session.getTransaction().commit();

        session.close();
        factory.close();
    }
}