package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImageClassifier {

    public static void runImageClassifier() {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "test.bat");
        File dir = new File("C:\\Users\\Andrew\\desktop\\handwritingclassifier\\NeuralNet");
        processBuilder.directory(dir);
        try {
            Process p = processBuilder.start();
            BufferedReader stdInput = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );

            String s = stdInput.readLine();
            System.out.println(s);
            App.title.setText("Prediction: " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
