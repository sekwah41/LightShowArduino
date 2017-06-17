
import javax.sound.sampled.*;

public class LightHeight {

    public static DataHandler arduinoHandler;
    public static byte[] data;
    public static int numBytesRead;

    public static boolean updatingLights = false;

    public static Object sync = new Object();

    // TODO make an interface that you can change what the lights are doing.
    // Also add a fade effect for certain things.
    public static void main(String[] args){
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        try {

            arduinoHandler = new DataHandler();
            arduinoHandler.initialize();

            TargetDataLine audioIn;

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            audioIn = (TargetDataLine) AudioSystem.getLine(info);
            audioIn.open(format);

            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            //int numBytesRead;
            int CHUNK_SIZE = 1024;
            data = new byte[1024];
            System.out.println(data.length);
            audioIn.start();

            Thread thread = new Thread(new LightDataLoop());

            thread.start();


            while (true) {
                //updates++;
                numBytesRead = audioIn.read(data, 0, CHUNK_SIZE);
                //int latest = 0;
                //int time = 0;
                //System.out.println(updates);
                // write the mic data to a stream for use later
                // out.write(data, 0, numBytesRead);
            }
            //audioIn.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
