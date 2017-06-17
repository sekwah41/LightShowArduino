public class ArduinoLights {

    public final int LIGHTS;

    // Creates a reset code
    // Cant go above 255
    private final int RESET_CODE = 1;
    private final DataHandler arduinoHandler;

    private LightData[] lightData;

    public ArduinoLights(int lightCount, DataHandler arduinoHandler){
        this.arduinoHandler = arduinoHandler;
        this.LIGHTS = lightCount;

        this.lightData = new LightData[lightCount];
        for(int i = 0; i < this.lightData.length; i++){
            this.lightData[i] = new LightData();
        }
    }

    /**
     * Output is actually in GRB order
     * @param pixelID
     * @param red
     * @param blue
     * @param green
     */
    public void setPixel(int pixelID, int red, int green, int blue){
        //System.out.println(pixelID);
        lightData[pixelID].red = red;
        lightData[pixelID].green = green;
        lightData[pixelID].blue = blue;
    }

    public void sendLightCodes(){
        //System.out.println("Test");
        for(LightData lightDat : lightData){
            this.arduinoHandler.sendFiltToArduino(lightDat.red);
            this.arduinoHandler.sendFiltToArduino(lightDat.green);
            this.arduinoHandler.sendFiltToArduino(lightDat.blue);
        }
        this.sendResetCode();
    }

    public void sendResetCode(){
        //System.out.println("Test2");
        //DirectReference.updatingLights = true;
        this.arduinoHandler.sendToArduino(this.RESET_CODE);
    }




}
