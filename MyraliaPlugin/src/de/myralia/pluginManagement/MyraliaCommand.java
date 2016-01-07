package de.myralia.pluginManagement;

import org.bukkit.command.CommandSender;

public abstract class MyraliaCommand {

	private String name;
	private String recht;
	private Boolean console;
	private Boolean human;
	
	public abstract boolean onCommand(CommandSender sender, String[] args);
	
	public void setParams(String _name, String _recht, Boolean _console, Boolean _human){
		this.console = _console;
		this.name = _name;
		this.recht = _recht;
		this.human = _human;
	}
	
	public final String getName()
	{
		if(this.name == null)
		{
			return "null";
		}
		else
		{
			return this.name;			
		}		
	}
	
	public final String getRecht()
	{
		if(this.recht==null)
		{
			return "Myralia.user";
		}
		else
		{
			return this.recht;
		}
	}
	
	public final Boolean isConsoleCommand()
	{
		if(this.console==true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public final Boolean isHumanCommand()
	{
		if(this.human==true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
}
