package panda.web.filters;


import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({
		"/faces/view/guestuser/login.xhtml",
		"/view/guestuser/login.xhtml",
		"/faces/view/guestuser/register.xhtml",
		"/view/guestuser/register.xhtml",
		"/faces/view/index.xhtml",
		"/view/index.xhtml",
		"/"
})
public class GuestFilter implements Filter {
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		if (session.getAttribute("username") != null) {
			resp.sendRedirect("/view/loginuser/home.xhtml");
		} else {
			chain.doFilter(req, resp);
		}
		
	}
	
	
}
