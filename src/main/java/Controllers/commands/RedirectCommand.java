package Controllers.commands;

import Controllers.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RedirectCommand extends Command {

    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response) {
        String view = request.getParameter("view");
        HttpSession session = request.getSession();
        //Check here for user privlliges
        /*if (view.contains("catalog")) {
            List<Product> catalog = LogicFacade.getCatalog();
            session.setAttribute("catalog", catalog);
            List<Category> categories = LogicFacade.getCategories();
            session.setAttribute("categories", categories);
        }*/
        return view;
    }

}
