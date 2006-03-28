/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://jwsdp.dev.java.net/CDDLv1.0.html
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * https://jwsdp.dev.java.net/CDDLv1.0.html  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 */
package org.jvnet.cvs_utils;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.BooleanOptionHandler;
import org.kohsuke.args4j.spi.Setter;

/**
 * This class contains all the command line option values.
 *
 * @author Ryan.Shoemaker@Sun.COM
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public final class Options {

    public static Options theInstance = null;

    @Option(name = "-repo", metaVar = "<new-repo>", usage = "recursively set **/CVS/Repository to <new-repo>")
    private String repository;

    @Option(name = "-root", metaVar = "<new-root>", usage = "recursively set **/CVS/Root to <new-root>")
    private String root;

    @Option(name = "-quiet", usage = "silence verbose trace messages while processing")
    private boolean quiet;

    @Option(name = "-N", usage = "do not modify files, just issue a report")
    private boolean dryrun;

    private Options() {}

    public static Options getOpts() {
        if(theInstance==null) {
            theInstance = new Options();
        }
        return theInstance;
    }

    public String getRepository() {
        return repository;
    }

    public String getRoot() {
        return root;
    }

    public boolean isVerbose() {
        return !quiet;
    }

    public boolean isDryrun() {
        return dryrun;
    }
}
