import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * @author sekwah41
 */
public class DataHandler implements SerialPortEventListener {

    // The port to send data
    SerialPort serialPort;

    /** The only port that will be used for this atm will be the windows one due to the prototype */
    /** The only port that will be used for this atm will be the windows one due to the prototype */
    private static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyUSB0", // Linux
            "COM3",
            "COM5"
    };

    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    private BufferedReader input;
    /** The output stream to the port */
    private OutputStream output;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 250000;

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine=input.readLine();
                System.out.println(inputLine);
            } catch (Exception e) {
                // Uncomment if there are errors
                //System.err.println("Data (Don't worry this is just a harmless error log)");
                //System.err.println(e.toString());
            }
        }
    }

    public void initialize() {

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
            //System.out.println(currPortId.getName());
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    System.out.println(currPortId.getName());
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        // Test
        /*while(true){
            Scanner scanner = new Scanner(System.in);
            try {
                output.write(scanner.next().toCharArray()[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void sendToArduino(int data){
        try {
            output.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendFiltToArduino(int data) {
        try {
            if(data == 1){
                data = 2;
            }
            output.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
