package com.yuanmao9527.cli;

import com.yuanmao9527.cli.command.ConfigCommand;
import com.yuanmao9527.cli.command.GenerateCommand;
import com.yuanmao9527.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "CommandExecutor", version = "CommandExecutor 1.0", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {
    private final CommandLine commandLine;
    {
        commandLine = new CommandLine(this);
        commandLine.addSubcommand(new GenerateCommand());
        commandLine.addSubcommand(new ConfigCommand());
        commandLine.addSubcommand(new ListCommand());
    }

    @Override
    public void run() {
        // 不输入子命令时给出友好提示
        System.out.println("请输入具体命令，或者输入--help查看命令提示");
    }
    //执行命令
    public Integer doExecute(String[] args){
        return commandLine.execute(args);
    }
}

