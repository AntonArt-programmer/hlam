package org.juicecode.hlam.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;

import java.util.Locale;
import java.util.Random;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private static int chatItemCount = 0;
    private Context parent;

    ChatListAdapter(Context context, int chatCount) {
        parent = context;
        chatItemCount = chatCount;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);

        ChatViewHolder viewHolder = new ChatViewHolder(view);
        viewHolder.chatName.setText("WTF");
        // chatItemCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // TODO(all): delete name generator
        StringBuilder name = new StringBuilder();
        Random random = new Random();

        name.append((char) (random.nextInt(90 - 65 + 1) + 65));
        for (int i = 0; i < 6; i++) {
            name.append((char) (random.nextInt(122 - 97 + 1) + 97));
        }

        holder.bind(
                String.format(Locale.getDefault(), "%s N%d", name.toString(), position),
                "Lorem ipsum dolor sit amet..."
        );
    }

    @Override
    public int getItemCount() {
        return chatItemCount;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        private ImageView chatAvatar;
        private TextView chatName;
        private TextView chatLastMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatAvatar = itemView.findViewById(R.id.chat_avatar);
            chatName = itemView.findViewById(R.id.chat_name);
            chatLastMessage = itemView.findViewById(R.id.chat_last_message);

            // TODO(all):make new class for listener
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(parent, chatName.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }

        public void bind(String name, String lastMessage) {
            chatName.setText(name);
            chatLastMessage.setText(lastMessage);
        }
    }
}
