import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

//=the windows in Processing
public class MainFrame extends JFrame {
    protected GamePanel gamePanel = new GamePanel();
    private static final String defaultFilename = "gameoflife.gol";

    public MainFrame() {
        super("Game of Life");

        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);

        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");

        Menu fileMenu = new Menu("File");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);

        setMenuBar(menuBar);

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Game of Life Files","gol");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setSelectedFile(new File(defaultFilename));

                int userOption = fileChooser.showSaveDialog(MainFrame.this);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File selectedFile=fileChooser.getSelectedFile();
                    gamePanel.open(selectedFile);
                }
            }
        });
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setSelectedFile(new File(defaultFilename));
                int userOption = fileChooser.showSaveDialog(MainFrame.this);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File selectedFile=fileChooser.getSelectedFile();
                    gamePanel.save(selectedFile);
                }
            }
        });


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                int code = e.getKeyCode();
                switch (code) {
                    case 32:
                        gamePanel.next();
                        break;
                    case 8:
                        gamePanel.clear();
                        break;
                    case 10:
                        gamePanel.randomize();
                        break;
                }
            }
        });

        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
