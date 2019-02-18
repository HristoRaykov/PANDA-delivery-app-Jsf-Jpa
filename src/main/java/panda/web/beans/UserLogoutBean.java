package panda.web.beans;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
public class UserLogoutBean {
	
	
	public void logoutUser() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/view/index.xhtml");
	}
	
}
