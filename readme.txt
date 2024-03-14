1.  Introduction: 
 The purpose of my apps is allowing the user to set an alarm clock. When the time has come , the user will recieve a notification and the user would be able to cancel the alarm and set a new alarm.
2. Design Rationale: 
AlarmReciever should exist as an activity itself, because an notification should appear when the time arrives, which creates a new activity with title and messages with it.
3. Novel Features:
This is a similar app as most of the alarm app. But a user can click the notification and check another activitity.
4. Challenges and Future Improvements: 
There are serveral Challenages through out the development process. First, I experienced a bug where the app closes when presssing the ok button in selecttime. I debuged it step by step by setting break points, turns out it is because of the formating of the message , which no error message shows, it also affect the setalarm function which is hard to debug. Future improvements would be allowing user to include their message in the notification, which act as a reminder for the use.