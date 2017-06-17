
import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class DirectReference {

    public static LightHeight currentEffect;
    public static ArduinoLights lights;
    public static DataHandler arduinoHandler;
    public static byte[] data;
    public static int numBytesRead;

    public static boolean updatingLights = false;

    public static Object sync = new Object();

    // TODO make an interface that you can change what the lights are doing.
    // Also add a fade effect for certain things.
    public static void main(String[] args){
        AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, true);
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
            data = new byte[audioIn.getBufferSize() / 5];
            audioIn.start();

            lights = new ArduinoLights(60 * 4, arduinoHandler);

            //int bytesRead = 0;
            //int updates = 0;


            currentEffect = new LightHeight(lights);

            Thread thread = new Thread(new LightDataLoop());

            thread.start();


            while (true) {
                //updates++;
                synchronized (sync){
                    numBytesRead = audioIn.read(data, 0, CHUNK_SIZE);
                }
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
