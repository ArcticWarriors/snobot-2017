package com.snobot.sd2017.auton;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.snobot.sd.auton.AutonPanel;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;

public class AutonWidget extends StaticWidget
{
    public static final String NAME = "2017 Auton Widget";

    private AutonPanel mPanel;
    private NetworkTable mAutonTable;

    public AutonWidget()
    {
        mPanel = new AutonPanel();

        setLayout(new BorderLayout());
        add(mPanel, BorderLayout.CENTER);

        mAutonTable = NetworkTableInstance.getDefault().getTable(SmartDashBoardNames.sAUTON_TABLE_NAME);

        addListener(mPanel, mAutonTable);

        this.setVisible(true);
    }

    private void addListener(AutonPanel aAutonPanel, NetworkTable aAutonTable)
    {
        aAutonPanel.addSaveListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                aAutonTable.getEntry(SmartDashBoardNames.sROBOT_COMMAND_TEXT).setString(aAutonPanel.getTextArea().getText());
                aAutonTable.getEntry(SmartDashBoardNames.sSAVE_AUTON).setBoolean(true);
            }
        });

        aAutonPanel.addTextChangedListener(new DocumentListener()
        {
            private void onChange()
            {
                aAutonTable.getEntry(SmartDashBoardNames.sSAVE_AUTON).setBoolean(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                onChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                onChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                onChange();
            }
        });


        TableEntryListener textUpdatedListener = new TableEntryListener()
        {
            @Override
            public void valueChanged(NetworkTable arg0, String arg1, NetworkTableEntry arg2, NetworkTableValue arg3, int arg4)
            {
                String auto_text = aAutonTable.getEntry(SmartDashBoardNames.sROBOT_COMMAND_TEXT).getString("Nothing Received");
                aAutonTable.getEntry(SmartDashBoardNames.sSAVE_AUTON).setBoolean(false);
                aAutonPanel.getTextArea().setText(auto_text);

            }
        };

        TableEntryListener errorListener = new TableEntryListener()
        {
            @Override
            public void valueChanged(NetworkTable arg0, String arg1, NetworkTableEntry arg2, NetworkTableValue arg3, int arg4)
            {
                boolean parseSuccess = aAutonTable.getEntry(SmartDashBoardNames.sSUCCESFULLY_PARSED_AUTON).getBoolean(false);
                aAutonPanel.setParseSuccess(parseSuccess);
            }
        };

        mAutonTable.addEntryListener(SmartDashBoardNames.sROBOT_COMMAND_TEXT, textUpdatedListener, 0xFF);
        mAutonTable.addEntryListener(SmartDashBoardNames.sSUCCESFULLY_PARSED_AUTON, errorListener, 0xFF);
    }

    @Override
    public void propertyChanged(Property arg0)
    {
        // Nothing to do
    }

    @Override
    public void init()
    {
        // Nothing to do
    }

}
