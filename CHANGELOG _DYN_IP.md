# NOTE

Check `CHANGELOG.md` for BUILD 7 (Branch has been taken from there)

## BUILD 7 _DYN_IP

### 06.08.2018

1. IMPORTANT: Updated `AddButtonFunctionFragment` to store individual fields in SharedPreferences
2. Updated `DimmerDialogActivity` and `GenericFillButtonFragment` to correspond to individual button IP address
3. IMPORTANT: Updated `HelperMethods` -> `setButtonArtifacts` to use individual fields in SharedPreferences

### API USAGE

#### **For `Switch` Functionality**

`http://<ipaddress>/<url>`

Example:

`http://192.168.29.100/LED` (TOGGLES THE INTERNAL LED)

This will also be the request sent

#### **For `Control` Functionality**

`http://<ipaddress>/DIMMING?<pin_no>=`

Example:

`http://192.168.29.100/DIMMING?pin1=`

NOTE: The above request looks a bit weird. However, A slider is implemented which send a value between 0-100 to dim the respective pin

Request Sent by the Android App

`http://192.168.29.100/DIMMING?pin1=66` (depending on the slider movement)