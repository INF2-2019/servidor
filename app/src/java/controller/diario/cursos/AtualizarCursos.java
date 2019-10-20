package controller.diario.cursos;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Atualizar", urlPatterns = "/diario/cursos/atualizar")
public class AtualizarCursos extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {}
}
