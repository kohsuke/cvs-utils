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

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.CmdLineException;

import java.io.File;
import java.io.IOException;

/**
 * This class is the main driver for cvs-utils.
 *
 * @author Ryan.Shoemaker@Sun.COM
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class Main {
    // bean for cmd line options
    private static final Options opts = Options.getOpts();

    public static void main(String[] args) {
        (new Main()).run(args);
    }

    public void run(String[] args) {
        CmdLineParser cmdParser = new CmdLineParser(opts);
        try {
            cmdParser.parseArgument(args);
            // check to see that one of the major modes has been selected
            if((opts.getRepository()==null) && (opts.getRoot()==null)) {
                usage(cmdParser);
                return;
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            usage(cmdParser);
            return;
        }

        // acknowledge dry run mode
        if(opts.isDryrun())
            System.out.println("Dry run mode selected - no files will be modified");

        // for now, all we have are recursive modes, so just recurse and process...
        recurse(new File("."));
    }

    private void recurse(File dir) {
        File[] contents = dir.listFiles();
        for (File f : contents) {
            if (f.isDirectory()) {
                if (f.getName().equals("CVS")) {
                    final File repository = new File(f, "Repository");
                    final File root = new File(f, "Root");
                    if(opts.getRepository()!=null) {
                        try {
                            Repository.process(repository);
                        } catch (IOException e) {
                            System.err.println("Error processing: " + repository.getPath());
                            e.printStackTrace();
                        }
                    }
                    if(opts.getRoot()!=null) {
                        try {
                            Root.process(root);
                        } catch (IOException e) {
                            System.err.println("Error processing: " + root.getPath());
                            e.printStackTrace();
                        }
                    }
                    continue;
                }
                recurse(f);
            }
        }
    }

    private void usage(CmdLineParser cmdParser) {
        System.err.println("java -jar cvs-utils.jar [options...] arguments...");
        cmdParser.printUsage(System.err);
    }
}
