package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<BaseMessageHolder> {
    private List<Message> messages;
    private Context context;

    public MessageChatAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

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
                view = inflater.inflate(R.layout.outcoming_message, parent, false);
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

        public OutgoingMessageHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.outcoming_message_field);
        }

        @Override
        public void bind(Message message) {
            text.setText(message.getText());
        }
    }

    static class IncomingMessageHolder extends BaseMessageHolder {

        public IncomingMessageHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.incoming_message_field);
        }

        @Override
        public void bind(Message message) {
            text.setText(message.getText());
        }
    }
}