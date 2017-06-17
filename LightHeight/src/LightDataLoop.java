public class LightDataLoop implements Runnable {

    long lastTime = System.currentTimeMillis();


    float current = 1f;

    //int height = 0;
    @Override
    public void run() {
        byte[] data;
        int numBytesRead;
        while(true){
            while(System.currentTimeMillis() - lastTime < 1000.0 / 30);


            if(Float.isNaN(current)){
                current = 1f;
            }

            lastTime = System.currentTimeMillis();
            data = LightHeight.data;
            numBytesRead = LightHeight.numBytesRead;

            //System.out.println(data.length);

            //System.out.println(numBytesRead);

            float average = 0;
            for(int i = 0; i < numBytesRead; i++) {
                //System.out.println(data[i]);
                if(data[i] < 0){
                    average -= data[i];
                }
                else{
                    average += data[i];
                }
            }
            average /= (float) numBytesRead;

            average -= 25;
            average /= 20;
            if(average > 1){
                average = 1;
            }
            else if(average < 0){
                average = 0;
            }
            average *= 240;
            current = (current + average) / 2f;

            //System.out.println(current);

            /*if(++height > 240){
                height = 0;
            }*/
            //System.out.println(average);
            //System.out.println(average);
            LightHeight.arduinoHandler.sendToArduino((int) current);
        }
    }
}
