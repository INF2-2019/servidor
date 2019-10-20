package controller.diario.cursos;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Inserir", urlPatterns = "/diario/cursos/inserir")
public class InserirCursos extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){}
}
