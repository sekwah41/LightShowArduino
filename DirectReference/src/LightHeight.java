public class LightHeight extends Effect {

    public LightHeight(ArduinoLights lights) {
        super(lights);
    }

    public void updateData(byte[] audioData, int numBytesRead){
        float average = 0;
        for(int i = 0; i < numBytesRead; i++) {
            if(audioData[i] < 0){
                average -= audioData[i];
            }
            else{
                average += audioData[i];
            }
        }
        average /= numBytesRead;

        average /= 50;
        if(average > 1){
            average = 1;
        }
        int lightCount = (int) ((this.lights.LIGHTS - 1) * average);
        //System.out.println(lights);
        //System.out.println(this.lights.LIGHTS);
        for(int i = 0; i < lightCount; i++){
            this.lights.setPixel(i, 255,255,0);
        }

        for(int i = lightCount; i < (this.lights.LIGHTS - 2); i++){
            this.lights.setPixel(i, 0,0,0);
        }

        //this.lights.sendResetCode();

        //System.out.println(average);
    }

}
