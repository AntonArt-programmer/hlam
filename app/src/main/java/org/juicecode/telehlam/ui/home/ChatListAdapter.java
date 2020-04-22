package org.juicecode.telehlam.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import  org.juicecode.telehlam.ui.chat.ChatFragment;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    List<Contact> contacts;
    private Context parent;

    public ChatListAdapter(Context context, List<Contact> contacts) {
        parent = context;
        this.contacts = contacts;
    }
    public void deleteElement(String phone){
        for(Contact c:contacts){
            if(c.getPhone().equals(phone)){
                contacts.remove(c);
            }
        }
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
        // TODO(all): set real name
        holder.bind(contacts.get(position).getName(), "Hello world!",contacts.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private String phoneNumber;
        private ImageView chatAvatar;
        private TextView chatName;
        private TextView chatLastMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatAvatar = itemView.findViewById(R.id.contact_avatar);
            chatName = itemView.findViewById(R.id.chat_name);
            chatLastMessage = itemView.findViewById(R.id.chat_last_message);

            // TODO(all):make new class for listener
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentManagerSimplifier simplifier = (FragmentManagerSimplifier)v.getContext();
                    ChatFragment chatFragment = new ChatFragment();
                    Bundle sendingChatName = new Bundle();
                    String chatNameValue = (String) chatName.getText();
                    sendingChatName.putStringArray("information", new String[]{chatNameValue,phoneNumber});
                    chatFragment.setArguments(sendingChatName);
                    simplifier.replaceFragment(chatFragment,"chatFragment");
                }
            });

        }

        public void bind(String name, String lastMessage,String phoneNumber) {
            this.phoneNumber = phoneNumber;
            chatName.setText(name);
            chatLastMessage.setText(lastMessage);
        }
    }
}