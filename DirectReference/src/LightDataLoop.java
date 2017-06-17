public class LightDataLoop implements Runnable {
    @Override
    public void run() {
        while(true){
            while(DirectReference.updatingLights){
                System.out.println("Waiting");
            }
            byte[] data = DirectReference.data;
            int numBytesRead = DirectReference.numBytesRead;

            DirectReference.currentEffect.updateData(data, numBytesRead);
            DirectReference.lights.sendLightCodes();
            DirectReference.lights.sendResetCode();
        }
    }
}
