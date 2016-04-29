package io.sandbox.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class HDFSClient {

    public void connet() {
        FileSystem hdfs = createHdfsConnection(new ConfigurationBuilder().build());

        if (!isntCreatedYet("/testest.log", hdfs)) {
            try {
                OutputStream fileStream =  hdfs.create(new Path("/test.log"));
                BufferedWriter br = new BufferedWriter( new OutputStreamWriter( fileStream, "UTF-8" ) );
                br.write("Firts line");
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean isntCreatedYet(String path, FileSystem fs) {
        try {
            return fs.exists(new Path(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileSystem createHdfsConnection(Configuration configuration) {
        try {
            return FileSystem.get(configuration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
