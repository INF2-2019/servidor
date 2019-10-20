package controller.diario.disciplinas;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AtualizarDisciplinas", urlPatterns = "/diario/disciplinas/atualizar")
public class AtualizarDisciplinas extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){}
}
