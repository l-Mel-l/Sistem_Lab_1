import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class ExternalProgramLauncher {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                System.out.println("Запуск процесса #" + count);

                try {
                    ProcessBuilder procBuilder = new ProcessBuilder("Calc.exe");
                    procBuilder.redirectErrorStream(true);
                    Process process = procBuilder.start();

                    InputStream stdout = process.getInputStream();
                    InputStreamReader isrStdout = new InputStreamReader(stdout);
                    BufferedReader brStdout = new BufferedReader(isrStdout);

                    String line;
                    while ((line = brStdout.readLine()) != null) {
                        System.out.println(line);
                    }

                    int exitVal = process.waitFor();

                    if (count == 10) {
                        timer.cancel();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Запускаем таймер
        timer.schedule(task, 0, 2000);
    }
}