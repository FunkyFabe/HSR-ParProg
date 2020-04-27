import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SuperComputerGUI extends JPanel {
    private static final long serialVersionUID = 4998293627753886206L;
    private final JLabel statusLabel;
    private final JLabel resultLabel;
    private final TheSupercomputer theSupercomputer;

    public SuperComputerGUI(TheSupercomputer theSupercomputer) {
        this.theSupercomputer = theSupercomputer;
        var layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        addImage();
        addLabel("Confused? google the result.");
        addStartButton();
        statusLabel = addLabel("Status: ?");
        resultLabel = addLabel("Result: ?");
        add(Box.createRigidArea(new Dimension(0, 5)));
        theSupercomputer.addListener(event -> {
            String status = "Status: " + theSupercomputer.getStatus();
            statusLabel.setText(status);
        });
    }

    private void addStartButton() {
        add(Box.createRigidArea(new Dimension(0, 5)));
        var startButton = new JButton("Start");
        startButton.setToolTipText(
                "Starts the calculation to find the answer to the Ultimate Question of Life, the Universe, and Everything");
        startButton.addActionListener(event -> {
            new Thread(() -> {
                String result = theSupercomputer.calculateUltimateAnswer();
                SwingUtilities.invokeLater(() -> {
                    resultLabel.setText("Result: " + result);
                });
            }).start();
        });
        add(startButton);
    }

    private JLabel addLabel(String text) {
        add(Box.createRigidArea(new Dimension(0, 5)));
        var label = new JLabel(text);
        add(label);
        return label;
    }

    private void addImage() {
        var imgURL = getClass().getResource("supercomputer.jpg");
        if (imgURL != null) {
            add(new JLabel(new ImageIcon(imgURL)));
        }
    }

    private static void createAndShowGUI(TheSupercomputer theSupercomputer) {
        var frame = new JFrame("The Supercomputer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var contentPane = new SuperComputerGUI(theSupercomputer);
        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        var theSupercomputer = new TheSupercomputer();
        SwingUtilities.invokeLater(() -> createAndShowGUI(theSupercomputer));
    }
}
