package org;

import java.io.IOException;
import java.io.InputStream;

/*
run Neon tool to create heuristic file and classify the sentences
 */
public class JarRunner{
    private String toolPath = "lib/neon.jar";

    public void run() throws IOException {
        Runtime rt =  Runtime.getRuntime();
        Process proc = rt.exec("java -jar " + toolPath);
        InputStream inputStream = proc.getInputStream();
        InputStream err = proc.getErrorStream();
        byte[] b =new byte[inputStream.available()];
        inputStream.read(b,0,b.length);
        System.out.println(new String(b));

    }
}
