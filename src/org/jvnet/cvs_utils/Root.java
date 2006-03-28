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

import java.io.*;

/**
 * This class handles -root processing
 *
 * @author Ryan.Shoemaker@Sun.COM
 * @version $Revision: 1.1 $
 * @since 1.0
 */
public class Root {
    public static void process(File root) throws IOException {
        final Options opts = Options.getOpts();
        if (root.exists()) {
            // inject the new value
            if(opts.isDryrun() || opts.isVerbose()) {
                System.out.println("Processing: " + root.getPath() + " -> new root '" + opts.getRoot() + "'");
            }
            if(!opts.isDryrun())
                injectRoot(root, opts.getRoot());
        } else {
            // noop
        }
    }

    private static void injectRoot(File root, String newRoot) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter( root ));
        writer.println(newRoot);
        writer.flush();
        writer.close();
    }

}
