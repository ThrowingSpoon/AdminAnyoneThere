package uk.co.liammartin.adminanyonethere;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ShoutViewHolder> {

    //List of type user to hold the data for each user
    List<user> users;

    RVAdapter(List<user> users) {
        this.users = users;
    }

    public void updateData(ArrayList<user> data) {
        users.clear();
        users.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * Called by the RecyclerView when it starts observing the adapter (we are using the
     * RVAdapter class)
     *
     * @param recyclerView The RecyclerView instance which started observing this adapter
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Called to give the RecyclerView a ViewHolder to represent a user item
     *
     * @param viewGroup The parent ViewGroup that the user ViewHolder will added to after
     *                  it is bound to an adapter position (adapter will contain each
     *                  individual user view, the TextViews and ImageView)
     * @param i         The view type
     * @return The ViewHolder for a user so that the RecyclerView to display messages
     */
    @Override
    public ShoutViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //This part shows us that we are inflating our user_card xml file -------v
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card, viewGroup, false);
        Log.d("DEBUGGING", "OnCreateViewHolder Called with position " + i);
        return new ShoutViewHolder(v);
    }

    /**
     * Remove an item from the data List
     *
     * @param position the position from which to remove the item
     */
    public void delete(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * This will be called by the RecyclerView to display the data, from the 'messages' object at the
     * specified position (at i)
     *
     * @param userViewHolder The ViewHolder which should be updated to represent the contents of
     *                       the item at the given position in the data set
     * @param i              The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(final ShoutViewHolder userViewHolder, final int i) {
        userViewHolder.username.setText(users.get(i).username);
    }

    /**
     * Overriding this method to point to which object to get the size from (in this case
     * we are using the messages object)
     *
     * @return The total number of items in the adapter
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    class ShoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Creating a CardView variable which will be each one of our messages
        CardView user_card;

        //Creating variables for holding the items that will be in the CardViews
        TextView username;
        RecyclerView recyclerView;

        /**
         * Taking the View we passed it when inflating user_card.xml and then finding
         * all of the individual views inside of it (the TextViews and ImageView at the moment)
         *
         * @param itemView the user_card.xml inflated view (containing TextViews and ImageView)
         */
        ShoutViewHolder(View itemView) {
            super(itemView);
            user_card = (CardView) itemView.findViewById(R.id.user_card);
            username = (TextView) itemView.findViewById(R.id.username);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv);
            user_card.setOnClickListener(this);
        }

        //Implementing a click action at the ViewHolder level using getAdapterPosition(); to find
        //the position of the element we want to do something with. we have already set an
        //onclick listener on the item we want to listen for the click (the whole user cardView)
        //and can do anything now!
        @Override
        public void onClick(View v) {
            if (getAdapterPosition() >= 0) {
                //Bundling the username and opening the chat screen using an intent
                Context current_context = v.getContext();
                String username = users.get(getAdapterPosition()).username;
                String id = users.get(getAdapterPosition()).user_id;
                Intent open_chat = new Intent(current_context, ChatScreen.class);
                open_chat.putExtra("username", username);
                open_chat.putExtra("id", id);
                current_context.startActivity(open_chat);
            }
        }
    }
}


