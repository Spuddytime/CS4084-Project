package ie.ul.cs4084;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<MessageModel> messages;

    public ChatAdapter(List<MessageModel> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        MessageModel message = messages.get(position);
        holder.textViewMessage.setText(message.getMessage());
        holder.textViewSender.setText(message.getSender());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage, textViewSender;

        public ChatViewHolder(View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            textViewSender = itemView.findViewById(R.id.textViewSender);
        }
    }
}

