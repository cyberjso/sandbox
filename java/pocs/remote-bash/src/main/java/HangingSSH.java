import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;


public class HangingSSH {
    private static Logger logger = LoggerFactory.getLogger(HangingSSH.class);

    public static void main(String[] args) {
        String host  = "34.219.123.89";
        String keyPairPath = "~/.ssh/KP_infrastructure.pem";
        String user =  "ec2-user";
        // Make foo a huge file,ideally greater than 100GB
        String command =  "aws s3 cp foo s3://cassandra-bkp/my-file";
        String[] executableCommand = { "sh", "-c", String.format(" ssh %s %s@%s \"%s\" ", loadExtraSShParameters(keyPairPath), user, host, command) };

        try {
            Long startTime = System.currentTimeMillis();
            logger.info("Starting");
            Process process = Runtime.getRuntime().exec(executableCommand);
            process.waitFor();
            String successfulOutput = IOUtils.toString(process.getInputStream(), Charset.defaultCharset()).trim();
            String errorOutput = IOUtils.toString(process.getErrorStream(), Charset.defaultCharset()).trim();
            Long totalTime = startTime - System.currentTimeMillis();

            logger.info("Elapsed time: {}s", (totalTime - startTime) / 1000);
            boolean hasError = StringUtils.isNotEmpty(errorOutput);
            if (hasError) {
                logger.error("Error when performing remote command. Host: {}\nReply from command \n Error: {}\n", Arrays.toString(executableCommand), errorOutput);

            } else {
                logger.info("Host: {}\nReply from command \n Success:{}\n", Arrays.toString(executableCommand), successfulOutput);
            }
            process.destroy();
        } catch (Exception e) {
            logger.error("Error when  trying to  perform command ", e);
        }

    }

    private static String loadExtraSShParameters(String keyPair) {
        return new StringBuilder().append("-n ")
                .append("-i ").append(keyPair).append(" ")
                .append("-A ")
                .append("-q ")
                .append("-o \"StrictHostKeyChecking=no\" ")
                .append("-o BatchMode=yes ")
                .toString();
    }


}
