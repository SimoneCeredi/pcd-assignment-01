import controller.Controller;
import controller.ControllerImpl;
import model.Model;
import model.ModelImpl;
import view.ConsoleViewImpl;
import view.View;

import java.io.File;

public class Parallel {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Wrong args given, should be D NI MAXL N, args given were " + args.length);
            System.exit(1);
        }
        File d = new File(args[0]); // D directory presente sul file system
        if (!d.exists()) {
            System.err.println("File or directory " + d.getAbsolutePath() + " does not exist!");
            System.exit(1);
        }
        int ni = Integer.parseInt(args[1]); // NI numero di intervalli
        int maxl = Integer.parseInt(args[2]); // MAXL numero numero massimo di linee di codice per delimitare l'estremo sinistro dell'ultimo intervallo
        int n = Integer.parseInt(args[3]); // N sorgenti con il numero maggiore di linee di codice
        View view = new ConsoleViewImpl();
        Model model = new ModelImpl(d, ni, maxl, n);
        Controller controller = new ControllerImpl(model);
        model.addObserver(view);
        controller.start();
    }
}
