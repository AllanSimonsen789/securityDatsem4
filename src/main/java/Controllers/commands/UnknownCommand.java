package Controllers.commands;

import Controllers.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends Command {

    @Override
    protected String execute(HttpServletRequest request, HttpServletResponse response){
        throw new UnsupportedOperationException("System does not recognize ");
    }
}
