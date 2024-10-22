import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Main {
    int puntuacion = 0;
    int contadorTiempoRestante = 60;
    String[] animales = {"mono", "caballo", "perro", "gato", "canguro", "jirafa", "koala", "agaporni"};
    JLabel label2;
    JLabel label3;
    JButton[] botones = new JButton[16];
    int botonesSeleccionados = 0;
    String primerAnimal = "";
    JButton primerBotonSeleccionado;

    public Main() {
        JFrame frame = new JFrame("Memory game");
        JPanel contentPane = (JPanel) frame.getContentPane();
        JPanel panel = new JPanel();
        JPanel mainPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(4, 4));

        JLabel label = new JLabel("Juego de memoria");
        label2 = new JLabel("Puntuación: " + puntuacion);
        label3 = new JLabel("Tiempo restante: " + contadorTiempoRestante);

        label.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);

        JPanel scorePanel = new JPanel(new GridLayout(2, 1));
        scorePanel.add(label);
        scorePanel.add(label2);

        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(label3, BorderLayout.SOUTH);

        List<String> listaAnimales = new ArrayList<>();
        for (String animal : animales) {
            listaAnimales.add(animal);
            listaAnimales.add(animal);
        }

        Collections.shuffle(listaAnimales);

        for (int i = 0; i < 16; i++) {
            JButton button = new JButton();
            panel.add(button);
            botones[i] = button;

            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    manejarBotonSeleccionado(button, listaAnimales.get(finalI));
                }
            });
        }

        mainPanel.add(panel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setVisible(true);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contadorTiempoRestante > 0) {
                    contadorTiempoRestante--;
                    label3.setText("Tiempo restante: " + contadorTiempoRestante);
                } else {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(frame, "¡Se acabó el tiempo!");
                    System.exit(0);
                }
            }
        });
        timer.start();
    }

    private void manejarBotonSeleccionado(JButton button, String animalSeleccionado) {
        button.setText(animalSeleccionado);
        button.setEnabled(false);
        botonesSeleccionados++;

        if (botonesSeleccionados == 1) {
            primerAnimal = animalSeleccionado;
            primerBotonSeleccionado = button;
        } else if (botonesSeleccionados == 2) {
            if (primerAnimal.equals(animalSeleccionado)) {
                puntuacion += 30 + Math.random() * (50 - 30);
                label2.setText("Puntuación: " + puntuacion);
                comprobarFinDelJuego();
            } else {
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        primerBotonSeleccionado.setText("");
                        button.setText("");
                        primerBotonSeleccionado.setEnabled(true);
                        button.setEnabled(true);
                        ((Timer) e.getSource()).stop();
                    }
                });
                timer.start();
            }
            botonesSeleccionados = 0;
            primerAnimal = "";
        }
    }

    private void comprobarFinDelJuego() {
        boolean todoDescubierto = true;
        for (JButton boton : botones) {
            if (boton.isEnabled()) {
                todoDescubierto = false;
                break;
            }
        }

        if (todoDescubierto) {
            JOptionPane.showMessageDialog(null, "¡Has ganado! Puntuación final: " + puntuacion);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
