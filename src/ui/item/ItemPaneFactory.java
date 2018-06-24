package ui.item;

import todoitem.Item;

public class ItemPaneFactory {
    public static ItemPane createPaneByItemType(Item item, boolean fromAdd) {
        switch (item.getItemType()) {
            case ANNIVERSARY:
                return new AnniversaryItemPane(item, fromAdd);
            case COURSE:
                return new CourseItemPane(item, fromAdd);
            case MEETING:
                return new MeetingItemPane(item, fromAdd);
            case INTERVIEW:
                return new InterviewItemPane(item, fromAdd);
            case TRAVEL:
                return new TravelItemPane(item, fromAdd);
            case DATE:
                return new AppointmentItemPane(item, fromAdd);
            case CUSTOM:
                return new CustomItemPane(item, fromAdd);
            default:
                return null;
        }
    }
}
