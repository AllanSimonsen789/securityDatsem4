package Controllers;

import Controllers.commands.UnknownCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public abstract class Command {


    private static HashMap<String, Command> commands;


    private static void initCommands() {
        commands = new HashMap<>();
        //commands.put("get_view", new ViewPageCommand());
    }

    static Command from(HttpServletRequest request) {
        String cmd = request.getParameter("cmd");
        if (commands == null) {
            initCommands();
        }
        return commands.getOrDefault(cmd, new UnknownCommand());
    }

    protected abstract String execute(HttpServletRequest request, HttpServletResponse response);
}
