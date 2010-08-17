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
package org.openengsb.yaste.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CliConsole implements Console {

    private StringBuilder outputBuffer;

	@Override
    public void writeInfo(String info) {
        System.out.println(info);
    }

    @Override
    public void writeError(String error) {
        System.err.println(error);
    }
    
    /**
     * This implementation channels stdout and stderr into one Stringbuilder object.
     */
	@Override
	public int execCommand(String command) throws IOException, InterruptedException {
		outputBuffer = new StringBuilder();
		int exitValue = -99;
		Runtime rt = Runtime.getRuntime();
		Process proc = null;
		try {
			proc = rt.exec(command);

			InputStream stdout = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdout);
            BufferedReader stdoutReader = new BufferedReader(isr);
            
            InputStream stderr = proc.getErrorStream();
            InputStreamReader esr = new InputStreamReader(stderr);
            BufferedReader stderrReader = new BufferedReader(esr);
            
            String line = null;
            while ( (line = stdoutReader.readLine()) != null) {
            	outputBuffer.append(line + "\n");
            }
            while ( (line = stderrReader.readLine()) != null) {
            	outputBuffer.append(line + "\n");
            }
            exitValue = proc.waitFor();
		} catch (IOException e) {
			throw e;
		} catch (InterruptedException e) {
			throw e;
		}  
		return exitValue;
    }

    @Override
    public String getStandardOutputAndErrorFromCommand()
    {
      return outputBuffer.toString();
    }
}
