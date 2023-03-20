package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import br.com.alura.gerenciador.acao.Acao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet("/entrada")
public class UnicaEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 6L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String paramAcao = request.getParameter("acao");
	        
        String nome;
        String nomeDaClasse = "br.com.alura.gerenciador.acao." + paramAcao;
        
		try {
		    Class classe = Class.forName(nomeDaClasse);//carrega a classe com o nome 
		    Acao acao = (Acao) classe.newInstance(); 
		    nome = acao.executa(request, response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		    throw new ServletException(e);
		}
	
			
		String[] tipoEEndereco = nome.split(":");
	    if(tipoEEndereco[0].equals("forward")) {
	        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/" + tipoEEndereco[1]);
	        rd.forward(request, response);
	    } else {
	        response.sendRedirect(tipoEEndereco[1]);

	    }
		
	}
}
