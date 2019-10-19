package controller.diario.cursos;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Consultar", urlPatterns = "/diario/cursos/consultar")
public class Consultar extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){}
}
