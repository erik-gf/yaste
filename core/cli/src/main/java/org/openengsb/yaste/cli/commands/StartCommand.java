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

import java.io.IOException;

import org.openengsb.yaste.cli.Console;
import org.openengsb.yaste.cli.TestProject;

public class StartCommand extends AbstractCommand {

    public StartCommand() {
        addName("start");
    }

    /**
     * Assembles and executes a command string that starts the default vagrant VM 
     * (if it is not already running).
     */
    @Override
    public void runCommand(String request, TestProject testProject, Console console) {
    	StringBuilder command = new StringBuilder();
    	command.append("vagrant");
    	command.append(" up");
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
