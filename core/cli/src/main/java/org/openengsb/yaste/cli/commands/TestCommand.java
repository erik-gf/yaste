/**

   Copyright 2010 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package org.openengsb.yaste.cli.commands;

import java.io.File;
import java.io.IOException;

import org.openengsb.yaste.cli.Console;
import org.openengsb.yaste.cli.TestProject;

public class TestCommand extends AbstractCommand {

    public TestCommand() {
        addName("test");
    }

    /**
     * Assembles a command string that executes the maven test target inside a vagrant VM
     * via ssh and deals with returned Cli Exceptions.
     */
    @Override
    public void runCommand(String request, TestProject testProject, Console console) {
    	StringBuilder command = new StringBuilder();
    	command.append("/usr/bin/ssh");
    	// TODO: make box name configurable, i.e. don't hard code hello-world value
    	if (testProject.doesFileExists(new File("boxes/hello-world/vagrant_rsa"))) {
    		command.append(" -i boxes/hello-world/vagrant_rsa");
    	} else {
    		command.append(" -f"); // Required for direct tty password input, but seems to screw up the return value
    	}
    	// TODO: source info below from Vagrantfile?
    	command.append(" -p 2222");
    	command.append(" -l vagrant");
    	command.append(" localhost");
    	command.append(" cd /vagrant;");
    	command.append(" mvn test");
    	int returnVal = -99;
    	try {
    		returnVal = console.execCommand(command.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("The return value of the command was: " + returnVal);
		System.out.println("stdout & stderr of the command:");
		System.out.println(console.getStandardOutputAndErrorFromCommand());
    }
}
