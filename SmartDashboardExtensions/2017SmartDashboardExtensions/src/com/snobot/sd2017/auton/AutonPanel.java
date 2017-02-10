package com.snobot.sd2017.auton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;

public class AutonPanel extends JPanel
{

    private JButton mSaveButton;
    private JTextArea mTextArea;
    private JPanel mBoolPanel;

    public AutonPanel()
    {
        setPreferredSize(new Dimension(300, 150));
        setLayout(new BorderLayout());

        mTextArea = new JTextArea("No auton received");
        mSaveButton = new JButton("Send & Save");
        mBoolPanel = new JPanel();
        JPanel buttonAndSuccesPanel = new JPanel();

        JScrollPane pane = new JScrollPane();
        pane.setViewportView(mTextArea);

        buttonAndSuccesPanel.setLayout(new GridLayout(2, 1));
        buttonAndSuccesPanel.add(mSaveButton);
        buttonAndSuccesPanel.add(mBoolPanel);

        this.add(pane, BorderLayout.CENTER);
        this.add(buttonAndSuccesPanel, BorderLayout.SOUTH);
        setParseSuccess(false);
    }

    public void addSaveListener(ActionListener aListener)
    {
        mSaveButton.addActionListener(aListener);
    }

    public JTextArea getTextArea()
    {
        return this.mTextArea;
    }

    public void setParseSuccess(boolean parseSuccess)
    {
        if (parseSuccess)
        {
            mBoolPanel.setBackground(Color.GREEN);
        }
        else
        {
            mBoolPanel.setBackground(Color.RED);
        }
    }

    public void addTextChangedListener(DocumentListener aListener)
    {
        mTextArea.getDocument().addDocumentListener(aListener);
    }
}
