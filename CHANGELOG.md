## BUILD 7

### 05.08.2018

1. Change the `DimmerDialogActivity` from 5-128 to 100-0 (Default: 0-100)
2. Added a Name button tag to `add_button_function.xml`
3. Made improvements to the back stack (updated `DevicesActivity`)
4. Updates: `AddButtonFunctionFragment`
5. Updates: `GenericFillButtonFragment`
6. Overload: `HelperMethod` -> `startFragmentMethod`
7. Improvement: `HelperMethod` -> `updateButtonTags`

## BUILD 6

### 16.07.2018

1. Added CONTROL and SWITCH actions to `GenericFillButtonFragment`
2. Wrote an AsyncHTTP Library for HTTP tasks
3. Converted `DimmerDialogActivity` Activity -> Dialog (Manifest options)
4. Updated `nav_header_devices.xml` text

## BUILD 5

### 10.07.2018

1. Created SettingsScreen -> Add HOST IP Address
2. Added `HTTP_POST` to `GenericFillButtonFragment`
3. Wrote HTTPMethods Library
4. Updated Gradle: okHttp Library 

## BUILD 4

#### 03.07.2018

1. Changed Light Theme to Dark Theme in AndroidManifest
2. Changed Dimming to Control
3. Theme Material Added to `content_devices.xml` (base layout for buttons) 

#### 25.06.2018

1. Added icon for Buttons
2. Modify icon for Buttons
3. Made UI Changes to `fragment_add_button_function`
4. Added `setSelectedItemId` to BottomNavigation

#### 21.06.18

1. Bug while deleting images from Room
2. Updated Back Button press from ModifyRoomActivity
3. Updated BackButton press from AddRoomActivity
4. Cleaned Fragment -> ListRoomFragment
5. Cleaned adapter -> RoomRecyclerAdapter

#### 19.06.18

1. Removed side navigation menus
2. Changed BottomNavigation Notification to Analytics and added custom ICON
3. Added Title for rooms
4. Added Icon for ListRoom
5. Added Delete Icon for ListRoom with Delete Room param
6. Changed activity_modify_room.xml

## BUILD 3

14.06.2018

1. Added Room and Favourites
2. Added longButton Press to Rooms to modify and delete rooms
3. Added longButton Press to Favourites to modify button activity
