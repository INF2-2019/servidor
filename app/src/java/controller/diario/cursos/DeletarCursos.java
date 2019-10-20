package controller.diario.cursos;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Deletar", urlPatterns = "/diario/cursos/deletar")
public class DeletarCursos extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {}
}
