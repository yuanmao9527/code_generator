package ${basePackage};

import ${basePackage}.cli.command.CommandExecutor;



/*
*  Main 方式
* */
public class Main {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }

}
