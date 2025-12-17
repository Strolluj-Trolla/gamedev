package gamedev.put.poznan.pl;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

public class Dialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private static Dialog dialog;
	private JLabel questionLabel;
    private JPanel optionsPanel;
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private String response;
    
    public static Dialog getDialog() {
    	if(dialog==null) {
    		dialog=new Dialog();
    	}
    	return dialog;
    }
    
    public static String showDialog(String question, List<String> options, int defaultOpt) {
    	Dialog dialog=getDialog();
    	dialog.updateGuiWithQuestion(question, options, defaultOpt);
    	dialog.setVisible(true);
    	return dialog.response;
    }
    
    public static void showResults(String results) {
    	Dialog dialog=getDialog();
    	dialog.showResult(results);
    	dialog.setVisible(true);
    }
    
    private Dialog() {
        setTitle("Choosing a career in GameDev");
        setSize(500, 400);
        setModal(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        questionLabel = new JLabel("Loading questions...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        add(new JScrollPane(optionsPanel), BorderLayout.CENTER);

        submitButton = new JButton("Dalej >");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitButton.addActionListener(e -> {
        	response=getSelectedButtonText(optionsGroup);
        	setVisible(false);
        });
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.add(submitButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateGuiWithQuestion(String question, List<String> options, int defaultOpt) {
        questionLabel.setText("<html><div style='text-align: center; width: 350px;'>" + question + "</div></html>");
        optionsPanel.removeAll();
        optionsGroup = new ButtonGroup();

        int i=0;
        for (String option : options) {
            JRadioButton rb = new JRadioButton(option);
            if(i==defaultOpt) rb.setSelected(true);
            rb.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            rb.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            optionsGroup.add(rb);
            optionsPanel.add(rb);
            optionsPanel.add(Box.createVerticalStrut(10)); 
            i++;
        }

        optionsPanel.revalidate();
        optionsPanel.repaint();
        submitButton.setEnabled(true);
    }

    private void showResult(String result) {
        questionLabel.setText("You should become a(n):");
        optionsPanel.removeAll();
        
        JLabel resultLabel = new JLabel("<html><h2 style='color: blue;'>" +result + "</h2></html>");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(resultLabel);
        
        submitButton.setEnabled(false);
        submitButton.setVisible(false);
        
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}
