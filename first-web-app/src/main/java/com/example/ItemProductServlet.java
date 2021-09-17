package com.example;

import com.example.persist.Product;
import com.example.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/item/*")
public class ItemProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        this.productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Product> prod = productRepository.findById(Long.parseLong(req.getParameter("id")));
        resp.getWriter().println("<table style='width: 100%'><tr><td>" + prod.get().getId() + "</td><td>" + prod.get().getName() + "</td><td>" + prod.get().getPrice() + "</td></tr>");

        }

}
