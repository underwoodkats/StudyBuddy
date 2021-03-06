package ch.epfl.sweng.studdybuddy.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.AddFriendsActivity;
import ch.epfl.sweng.studdybuddy.activities.CourseSelectActivity;
import ch.epfl.sweng.studdybuddy.auth.AuthManager;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.CourseAdapter;
import ch.epfl.sweng.studdybuddy.tools.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.onClickLaunch;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private final List<String> userCourses = new ArrayList<>();
    private final List<Group> userGroups = new ArrayList<>();
    private final List<User> userFriends = new ArrayList<>();
    private GroupsRecyclerAdapter ad;
    private CourseAdapter adCourse;
    private ParticipantAdapter adFriend;
    private User user;
    private String userID;
    private MetaGroup metabase = new MetaGroup();
    private AuthManager mAuth = null;
    Button addFriends;

    /**
     *  Required empty public constructor
     */
    public ProfileFragment() {}

    /**
     * Sets up the graphical elements of the Fragment
     * @param inflater Inflater containing the list of existing chats
     * @param container container of the list
     * @param savedInstanceState previously saved state
     * @return The inflatet view containing the list of chats
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setupUser();
        setUI(v);
        setCoursesUp();
        setGroupsUp();
        setFriendsUp();


        return v;
    }

    /**
     * Get the necessary information about the current user
     */
    public void setupUser() {
        user = ((StudyBuddy) getActivity().getApplication()).getAuthendifiedUser();
        userID = user.getUserID().toString();
    }

    /**
     * Retrieves the correct groups from firebase
     * @return a listener providing the aforementioned functionality
     */
    public ValueEventListener setGroupsUp() {
        metabase.addListenner(new RecyclerAdapterAdapter(ad));
        return metabase.getUserGroups(userID, userGroups);
    }
    /**
     * Retrieves the correct Courses from firebase
     * @return a listener providing the aforementioned functionality
     */
    public ValueEventListener setCoursesUp() {
        metabase.addListenner(new RecyclerAdapterAdapter(adCourse));
        return metabase.getUserCourses(userID, userCourses);
    }
    /**
     * Retrieves the correct Friends from firebase
     * @return a listener providing the aforementioned functionality
     */
    public ValueEventListener setFriendsUp() {
        metabase.addListenner(new RecyclerAdapterAdapter(adFriend));
        return metabase.getBuddies(userID, userFriends);
    }

    /**
     * Modifies the provided view, by adding all the information of the user
     * @param v the vire to be modified
     */
    private void setUI(View v) {
        TextView nameView = (TextView) v.findViewById(R.id.profile_name_text);
        nameView.setText(user.getName());

        adCourse = new CourseAdapter(userCourses);
        RecyclerView recyclerView_courses = (RecyclerView) v.findViewById(R.id.courses_list);
        adCourse.initRecyclerView(v.getContext(), recyclerView_courses);

        adFriend = new ParticipantAdapter(userFriends);
        RecyclerView recyclerView_friends = v.findViewById(R.id.friends_list);
        adFriend.initRecyclerView(v.getContext(), recyclerView_friends);

        addFriends = v.findViewById(R.id.btn_add_friend);
        addFriends.setOnClickListener(getOnClickListener());

        ad = new GroupsRecyclerAdapter(userGroups, userID);
        RecyclerView recyclerView_groups = (RecyclerView) v.findViewById(R.id.groups_list);
        recyclerView_groups.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView_groups.setAdapter(ad);
        //Intentable toCourseEdit = new Intentable(v.getContext(), new Intent(v.getContext(), CourseSelectActivity.class));
        Button editCourses = v.findViewById(R.id.editCourses);
        editCourses.setOnClickListener(onClickLaunch(new Intentable(v.getContext(), CourseSelectActivity.class)));
    }

    /**
     * Method used for testing
     * @return the firebase reference
     */
    public ReferenceWrapper getDB() {
        return new FirebaseReference();
    }

    /**
     *
     * @return a listener which starts a new activity
     */
    @NonNull
    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddFriendsActivity.class);
                startActivity(intent);
            }
        };
    }
}
