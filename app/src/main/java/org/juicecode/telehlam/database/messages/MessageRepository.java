package org.juicecode.telehlam.database.messages;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.juicecode.telehlam.database.DBClient;

import java.util.List;

public class MessageRepository {
    private MessageDao dao;
    private LiveData<List<Message>> messages;

    public MessageRepository(Application application) {
        dao = DBClient.getInstance(application).getAppDataBase().messageDao();
        messages = dao.getAll();
    }

    public void insert(Message message) {
        new InsertAsyncTask(dao).execute(message);
    }

    public LiveData<List<Message>> getChatMessages(long receiverId) {
        return dao.getAllById(receiverId);
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    private static class InsertAsyncTask extends AsyncTask<Message, Void, Void> {
        private MessageDao dao;

        InsertAsyncTask(MessageDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            if (messages.length != 0) {
                dao.insert(messages[0]);
            }
            return null;
        }
    }

}
