package user;

import java.util.ArrayList;
import java.util.List;

import course.Module_;

public class Teacher extends User{
	private ArrayList<Module_> modules;

	public Teacher() {
	    this.modules = new ArrayList<>();
	}
	
	public ArrayList<Module_> getModules() {
		return modules;
	}

	public void setModules(ArrayList<Module_> modules) {
		this.modules = modules;
	}
	
	public void addModule(Module_ module) {
		modules.add(module);
	}
	
	public List<String> getModuleNames() {
	    List<String> moduleNames = new ArrayList<>();

	    // Iterate through the modules for the teacher
	    for (Module_ module : getModules()) {
	        moduleNames.add(module.getModuleName());
	    }

	    return moduleNames;
	}
	
}
