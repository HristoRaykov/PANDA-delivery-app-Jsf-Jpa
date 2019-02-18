package panda.web.filters;


import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({
		"/view/loginuser/home.xhtml",
		"/view/packages/package-details.xhtml",
		"/view/loginuser/receipts.xhtml",
		"/view/receipts/receipt-details.xhtml",
})
public class UserFilter implements Filter {
	
	@Override
	@SuppressWarnings("Duplicates")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		String role = (String) session.getAttribute("role");

		if (username==null){
			resp.sendRedirect("/view/guestuser/login.xhtml");
			return;
		}

		chain.doFilter(req,resp);
	}
	
	
	
}
