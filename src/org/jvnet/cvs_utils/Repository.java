package org.jvnet.cvs_utils;

import java.io.*;
import java.util.ArrayList;

/**
 * This class handles -repo processing...
 */
public class Repository {
//    public static void main(String[] args) throws IOException {
//        if(args.length != 1) usage();
//        System.err.println("Recursively updating **/CVS/Repository to: " + args[0] + "/*");
//        updateRepository(new File("."), args[0]);
//    }

//    private static void updateRepository(File dir, String newRepository) throws IOException {
//        File[] contents = dir.listFiles();
//        for (File f : contents) {
//            if (f.isDirectory()) {
//                if (f.getName().equals("CVS")) {
//                    File repository = new File(f, "Repository");
//                    if (repository.exists()) {
//                        // inject the new value
//                        System.out.println("Processing: " + repository.getPath());
//                        injectRepository(repository, newRepository);
//                    } else {
//                        // noop
//                    }
//                    continue;
//                }
//                updateRepository(f, newRepository);
//            }
//        }
//    }

    public static void process(File repository) throws IOException {
        final Options opts = Options.getOpts();
        if (repository.exists()) {
            // inject the new value
            if(opts.isDryrun() || opts.isVerbose()) {
                System.out.println("Processing: " + repository.getPath() + " -> new repo '" + opts.getRepository() + "'");
            }
            if(!opts.isDryrun())
                injectRepository(repository, Options.getOpts().getRepository());
        } else {
            // noop
        }
    }

    private static void injectRepository(File repository, String newRepository) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(repository));
        String contents = reader.readLine();
        reader.close();

        PrintWriter writer = new PrintWriter(new FileWriter( repository ));
        writer.println(newRepository + "/" + contents);
        writer.flush();
        writer.close();
    }
}
