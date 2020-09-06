package org.bolikov.controller;

import org.bolikov.Customer;
import org.bolikov.Market;
import org.bolikov.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateController {

    private EntityManagerFactory emFactory;

    public HibernateController() {
        this.emFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public Customer selectCustomer(String name) {
        EntityManager em = emFactory.createEntityManager();
        Customer customer;
        try {
           customer = em.createQuery("from Customer where name = :name", Customer.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Не найден переданный покупатель");
            customer = null;
        } finally {
            em.close();
        }
        return customer;
    }

    public Product selectProduct(String productName) {
        EntityManager em = emFactory.createEntityManager();
        Product product;
        try {
            product = em.createQuery("from Product where productName = :productName", Product.class)
                    .setParameter("productName", productName)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Не найден переданный товар");
            product = null;
        } finally {
            em.close();
        }
        return product;
    }

    public void addCustomer(String name) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(new Customer(null, name));
        em.getTransaction().commit();
        em.close();
    }

    public void deleteCustomer(String name) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE Customer WHERE name = :name").
                setParameter("name", name).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public void addProduct(String nameProduct, Double cost) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(new Product(null, nameProduct, cost));
        em.getTransaction().commit();
        em.close();
    }

    public void deleteProduct(String name) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE Product WHERE productName = :name").
                setParameter("name", name).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public void addMarket(String nameCustomer, String nameProduct) {
        Customer customer = selectCustomer(nameCustomer);
        Product product = selectProduct(nameProduct);
        if (product != null & customer != null) {
            EntityManager em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.persist(new Market(null, customer.getCustomerId(), product.getProductId()));
            em.getTransaction().commit();
            em.close();
        }
    }
}
