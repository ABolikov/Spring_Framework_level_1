package org.bolikov.controller;

import org.bolikov.Customer;
import org.bolikov.OrderItem;
import org.bolikov.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;

public class HibernateController {

    private EntityManagerFactory emFactory;
    private EntityManager em;

    public HibernateController() {
        this.emFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public Customer selectCustomer(String name, boolean isClose) {
        em = emFactory.createEntityManager();
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
            if (isClose) {
                em.close();
            }
        }
        return customer;
    }

    public Product selectProduct(String productName, boolean isClose) {
        em = emFactory.createEntityManager();
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
            if (isClose) {
                em.close();
            }
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

    public void addProduct(String nameProduct, BigDecimal cost) {
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
        Customer customer = selectCustomer(nameCustomer, false);
        Product product = selectProduct(nameProduct, false);
        if (product != null & customer != null) {
            em.getTransaction().begin();
            em.persist(new OrderItem(null, customer, product, product.getCost()));
            em.getTransaction().commit();
        }
        em.close();
    }

    public void setCostProduct(String nameProduct, BigDecimal cost) {
        Product product = selectProduct(nameProduct, false);
        if (product != null) {
            em.getTransaction().begin();
            product.setCost(cost);
            em.getTransaction().commit();
        }
        em.close();
    }
}
