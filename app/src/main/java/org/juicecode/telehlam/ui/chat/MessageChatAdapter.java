package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.messages.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageChatAdapter extends RecyclerView.Adapter<BaseMessageHolder> {
    private List<Message> messages;

    public MessageChatAdapter() {
        this.messages = new ArrayList<>();
    }

    public void addItem(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    public void addItems(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public BaseMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view;
        switch (viewType) {
            case Message.MESSAGE_INCOMING:
                view = inflater.inflate(R.layout.incoming_message, parent, false);
                return new IncomingMessageHolder(view);

            case Message.MESSAGE_OUTGOING:
                view = inflater.inflate(R.layout.outgoing_message, parent, false);
                return new OutgoingMessageHolder(view);

            default:
                // TODO: create custom Error class
                throw new Error("Unknown message type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMessageHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Message.MESSAGE_INCOMING:

            case Message.MESSAGE_OUTGOING:
                holder.bind(messages.get(position));
                break;

            default:
                // TODO(all): create custom class for error
                throw new Error("Unknown message type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    static class OutgoingMessageHolder extends BaseMessageHolder {
        TextView date;

        OutgoingMessageHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            text = itemView.findViewById(R.id.message_text);
        }

        @Override
        public void bind(Message message) {
            text.setText(message.getText());
            String formatted = formatDate(message.getTimestamp());
            date.setText(formatted);
        }
    }

    static class IncomingMessageHolder extends BaseMessageHolder {

        TextView dataField;
        public IncomingMessageHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.incoming_message_field);
            dataField = itemView.findViewById(R.id.dataField);
        }

        @Override
        public void bind(Message message) {
            text.setText(message.getText());
            String formatted = formatDate(message.getTimestamp());
            dataField.setText(formatted);
        }

    }

    public static String formatDate(long unixTime){
        Date date = new Date(unixTime);
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);
    }
}