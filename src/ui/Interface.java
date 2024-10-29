package ui;

import task.EndCleanToExecuteTask;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Interface extends JFrame {

    private JRadioButton radio1, radio2, radio3;
    private JCheckBox checkbox1, checkbox2, checkbox3, checkbox4;
    private JTextField entry;
    private String selectedOption = "Option 1";

    public Interface() {
        setTitle("Application avec des Boutons Radio et Checkboxes");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Zone de saisie de texte
        entry = new JTextField(30);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(entry, gbc);

        // Boutons radio
        radio1 = new JRadioButton("Option 1");
        radio2 = new JRadioButton("Option 2");
        radio3 = new JRadioButton("Option 3");

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        group.add(radio3);
        radio1.setSelected(true);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(radio1, gbc);
        gbc.gridx = 1;
        checkbox1 = new JCheckBox("Case à cocher 1");
        add(checkbox1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(radio2, gbc);
        gbc.gridx = 1;
        checkbox2 = new JCheckBox("Case à cocher 2");
        add(checkbox2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(radio3, gbc);
        gbc.gridx = 1;
        checkbox3 = new JCheckBox("Case à cocher 3");
        add(checkbox3, gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        checkbox4 = new JCheckBox("Case à cocher 4");
        add(checkbox4, gbc);

        // Bouton pour afficher la sélection
        JButton showSelectionButton = new JButton("Afficher la sélection");
        showSelectionButton.addActionListener(e -> showSelection());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(showSelectionButton, gbc);

        // Bouton pour afficher les cases cochées
        JButton showCheckedButton = new JButton("Afficher cases à cocher sélectionnées");
        showCheckedButton.addActionListener(e -> showChecked());
        gbc.gridy = 6;
        add(showCheckedButton, gbc);

        // Bouton Valider
        JButton validateButton = new JButton("Valider");
        validateButton.addActionListener(e -> taskToExecute());
        gbc.gridy = 7;
        add(validateButton, gbc);

        // Listener pour gérer l'affichage des cases à cocher
        radio1.addActionListener(e -> onSelect("Option 1"));
        radio2.addActionListener(e -> onSelect("Option 2"));
        radio3.addActionListener(e -> onSelect("Option 3"));

        // Cacher initialement les cases à cocher
        checkbox1.setVisible(false);
        checkbox2.setVisible(false);
        checkbox3.setVisible(false);
        checkbox4.setVisible(false);

        // Sélection initiale
        onSelect("Option 1");
    }

    private void onSelect(String option) {
        selectedOption = option;
        if (selectedOption.equals("Option 1")) {
            checkbox1.setVisible(true);
            checkbox2.setVisible(true);
            checkbox3.setVisible(true);
            checkbox4.setVisible(true);
        } else {
            checkbox1.setVisible(false);
            checkbox2.setVisible(false);
            checkbox3.setVisible(false);
            checkbox4.setVisible(false);
        }
    }

    private void showSelection() {
        System.out.println("Vous avez sélectionné : " + selectedOption);
    }

    private void showChecked() {
        List<String> checkedBoxes = new ArrayList<>();

        if (checkbox1.isSelected()) checkedBoxes.add("PropsType");
        if (checkbox2.isSelected()) checkedBoxes.add("DefaultProps");
        if (checkbox3.isSelected()) checkedBoxes.add("Clean Import");
        if (checkbox4.isSelected()) checkedBoxes.add("Clean Import");

        if (!checkedBoxes.isEmpty()) {
            System.out.println("Vous avez sélectionné : " + selectedOption);
            System.out.println("Cases à cocher sélectionnées : " + String.join(", ", checkedBoxes));
        } else {
            System.out.println("Vous avez sélectionné : " + selectedOption);
            System.out.println("Aucune case à cocher sélectionnée.");
        }
    }

    private void taskToExecute() {
        String entryText = entry.getText();
        List<String> checkedBoxes = new ArrayList<>();

        if (checkbox1.isSelected()) checkedBoxes.add("PropsType");
        if (checkbox2.isSelected()) checkedBoxes.add("DefaultProps");
        if (checkbox3.isSelected()) checkedBoxes.add("Clean Import");
        if (checkbox4.isSelected()) checkedBoxes.add("Clean Export");

        if (entryText.isEmpty()) {
            System.out.println("Veuillez saisir un texte");
        } else {
            System.out.println("Texte de la zone de saisie : " + entryText);
            EndCleanToExecuteTask.endCleanTaskToExecute(entryText, checkedBoxes);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interface app = new Interface();
            app.setVisible(true);
        });
    }
}
