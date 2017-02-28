package com.grumbybirb.recyclapple.barcode;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grumbybirb.recyclapple.R;

import java.util.List;

/**
 * Created by bkazi on 28/02/2017.
 */

public class InstructionsAdapter extends ArrayAdapter {
    private Activity mContext;

    public InstructionsAdapter(Activity mContext, List<Instruction> instructions) {
        super(mContext, 0, instructions);
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Instruction instruction = (Instruction) getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.instruction_view, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        TextView instructionView = (TextView) convertView.findViewById(R.id.instruction);

        nameView.setText(instruction.getName());
        instructionView.setText(instruction.getInstruction());

        return convertView;
    }

}
