package com.example;

import com.example.persist.Product;
import com.example.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/all/*"})
public class AllProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        this.productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().println("<table style='width: 100%'>");
        for (Product prod : productRepository.findAll()) {
            resp.getWriter().println("<tr><td>" + prod.getId() + "</td><td><a href = '../item?id=" + prod.getId() + "'>" + prod.getName() + "</td><td>" + prod.getPrice() + "</td></tr>");
        }
    }
}
