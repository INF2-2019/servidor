/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.diario.disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(name = "InserirDisciplinas", urlPatterns = {"/diario/disciplinas/inserir"})
public class InserirDisciplinas extends HttpServlet {
   protected void doPost(HttpServletRequest request, HttpServletResponse response){}
}
